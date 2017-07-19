package eu.paasage.upperware.milp_solver.exec

import com.typesafe.scalalogging.slf4j.LazyLogging
import eu.paasage.upperware.metamodel.cp.ConstraintProblem
import eu.paasage.upperware.milp_solver.{CDOClient, Helpers, MILPSolver, OptimizationFailed}
import jCMPL.CmplException
import org.zeromq.ZMQ

import scala.collection.JavaConversions._


/** Solve constraint problem stored in CDO server and update it in place */
object Daemon extends App with LazyLogging {
  val context = ZMQ.context(1)

  if(args.length != 2) {
    println(s"You need to provide subscribe and publish ports.")
    sys.exit()
  }

  val subscribePort = args(0).toInt
  val publishPort = args(1).toInt

  val publisher = context.socket(ZMQ.PUB)
  publisher.bind("tcp://*:" + publishPort.toString)

  val subscriber = context.socket(ZMQ.SUB)
  subscriber.connect("tcp://*:" + subscribePort.toString)


  def handleRequest(camelResource: String, resourceName: String, timestamp: Long)
  {
    def sendZeroMQSuccess() {

      publisher.send("MILPSolutionAvailable".getBytes(), ZMQ.SNDMORE)
      publisher.send(camelResource.getBytes(), ZMQ.SNDMORE)
      publisher.send(resourceName.getBytes(), 0)
    }

    def sendZeroMQError(message: String) {
      publisher.send("MILPError".getBytes(), ZMQ.SNDMORE)
      publisher.send(camelResource.getBytes(), ZMQ.SNDMORE)
      publisher.send(resourceName.getBytes(), ZMQ.SNDMORE)
      publisher.send(message.getBytes(), 0)
    }

    try {
      CDOClient.open_default(cdo => {
        logger.info(s"Retrieving CP $resourceName")
        cdo.processModel[ConstraintProblem](resourceName, cp => {
          logger.info("Solving with CMPL...")
          val solution = MILPSolver.default_solve(cp, timestamp, true, true)

          logger.debug("CMPL done.")
          logger.debug("Results: ")
          logger.debug(Helpers.solutionToString(solution))
          logger.debug("Commiting transaction.")
        })
        logger.info("Done")
        sendZeroMQSuccess()
      })
    } catch {
      case _:OptimizationFailed => {
        logger.error("Optimization failed.")
        sendZeroMQError("Optimization failed.")
      }
      case _:CmplException => {
        logger.error("CMPL failed.")
        sendZeroMQError("CMPL failed.");
      }
    }
  }


  subscriber.subscribe("startSolving".getBytes())
  logger.info("Waiting for jobs...")
  while(true) {
    try {
      val topic = subscriber.recvStr()
      val camelModelRef = if (subscriber.hasReceiveMore())
      {
        subscriber.recvStr()
      } else throw new Exception("CamelModel is missing")

      val cpModelRef = if (subscriber.hasReceiveMore()) subscriber.recvStr()
                                                   else throw new Exception("cpModel is missing")

      val timestamp = if (!subscriber.hasReceiveMore()) 0 else
      {
        val timestampStr = subscriber.recvStr()
        if (timestampStr != null){
          timestampStr.toLong
        } else {
          0
        }
      }
      logger.info("Got task, solving")
      handleRequest(camelModelRef, cpModelRef, timestamp)
      logger.info("Got task, done")
    } catch {
        case e: Exception => logger.error("ZeroMQ processing failed " + e.getMessage())
    }
  }
}
