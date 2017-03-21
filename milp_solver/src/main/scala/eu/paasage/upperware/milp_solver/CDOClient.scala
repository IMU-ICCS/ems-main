package eu.paasage.upperware.milp_solver

import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration
import org.eclipse.emf.cdo.net4j.CDONet4jUtil
import org.eclipse.emf.cdo.session.CDOSession
import org.eclipse.emf.cdo.transaction.CDOTransaction
import org.eclipse.emf.cdo.view.CDOView
import eu.paasage.upperware.metamodel.cp.CpPackage
import org.eclipse.emf.ecore.EObject
import java.util.concurrent.{ThreadFactory, Executors}
import org.eclipse.net4j.util.lifecycle.LifecycleUtil
import org.eclipse.net4j.Net4jUtil
import org.eclipse.net4j.FactoriesProtocolProvider
import org.eclipse.net4j.buffer.IBufferProvider
import org.eclipse.net4j.protocol.IProtocolProvider
import org.eclipse.emf.common.util.EList

import scala.collection.JavaConversions._
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.LazyLogging
import java.io.File


object CDOClient {
  val paasage_config_dir = System.getenv("PAASAGE_CONFIG_DIR")
  val config_file = s"$paasage_config_dir/eu.paasage.cdo.client.properties"

  val cdo_config = {
    val file_config = ConfigFactory.parseFile(new File(config_file))
    ConfigFactory.load(file_config)
  }

  def open(host: String, port: Int, block: CDOClient => Unit) {
    val client = new CDOClient(host, port)
    try {
      block(client)
    } finally {
      client.closeSession
    }
  }

  def open_default(block: CDOClient => Unit) {
    CDOClient.open(cdo_config.getString("host"), cdo_config.getInt("port"), block)
  }
}

class CDOClient(val host:String, val port:Int) extends LazyLogging {

  logger.debug(s"Connecting to CDO server at $host:$port")

  val threadGroup = new ThreadGroup("net4j")
  val receiveExecutor = Executors.newCachedThreadPool(new ThreadFactory() {
    def newThread(r:Runnable) = {
      val thread = new Thread(threadGroup, r)
      thread.setDaemon(true)
      thread
    }
  })

  val bufferProvider:IBufferProvider = Net4jUtil.createBufferPool()
  LifecycleUtil.activate(bufferProvider)

  val protocolProvider:IProtocolProvider = new FactoriesProtocolProvider(new org.eclipse.emf.cdo.internal.net4j.protocol.CDOClientProtocolFactory())


  val selector = new org.eclipse.net4j.internal.tcp.TCPSelector()
  selector.activate()

  // Prepare connector
  val connector:org.eclipse.net4j.internal.tcp.TCPClientConnector = new org.eclipse.net4j.internal.tcp.TCPClientConnector()
  connector.getConfig.setBufferProvider(bufferProvider)
  connector.getConfig.setReceiveExecutor(receiveExecutor)
  connector.getConfig.setProtocolProvider(protocolProvider)
  connector.getConfig.setNegotiator(null)
  connector.setSelector(selector)
  connector.setHost(host)
  connector.setPort(port)
  connector.activate()

  // Create configuration
  val configuration:CDONet4jSessionConfiguration = CDONet4jUtil.createNet4jSessionConfiguration()
  configuration.setConnector(connector)
  configuration.setRepositoryName("repo1")

  // Open session
  val session:CDOSession = configuration.openNet4jSession()

  session.getPackageRegistry.putEPackage(CpPackage.eINSTANCE)

  def transactionDo[T](block: CDOTransaction => T) = {
    val trans = session.openTransaction()
    try {
      block(trans)
    } catch {
      case e:Exception => {
        println(e)
        throw e
      }
    }
    finally {
      if(trans != null) trans.close()
    }
  }


  def viewDo[T](block: CDOView => T):T = {
    val view = session.openView()
    try {
      block(view)
    } catch {
      case e:Exception => {
        println(e)
        throw e
      }
    } finally {
      if(view != null) view.close()
    }
  }

  def storeModel(model: EObject, resourceName: String)  {
    transactionDo( transaction => {
      val resource = transaction.getOrCreateResource(resourceName)
      val list = resource.getContents
      list.insert(1, model) // temporary workaround for clearing resource not working!
      transaction.commit()
      transaction.close()
    })
  }

  def processModel[T](resourceName: String, block: T => Unit) {
    transactionDo( transaction => {
      val resource = transaction.getResource(resourceName)
      val list = resource.getContents
      block(list(1).asInstanceOf[T])
      transaction.commit()
      transaction.close()
    })
  }

  def retrieveModel[T,V](path: String, block: T => V):V = {
    viewDo( view => {
      val items:EList[EObject] = view.getResource(path).getContents
      block(items(1).asInstanceOf[T])
    })
  }

  def closeSession {
    logger.debug("Closing CDO session")
    session.close()
    connector.deactivate()
  }
}
