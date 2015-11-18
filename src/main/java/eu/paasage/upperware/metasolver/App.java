package eu.paasage.upperware.metasolver;


//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import eu.paasage.upperware.metasolver.*;
import eu.paasage.upperware.metasolver.metrics.Mapper;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;

public class App implements Daemon {
	 

	   static Logger LOGGER = LoggerFactory.getLogger(App.class.getSimpleName());

	    private Thread myThread;
	    private static boolean stopped = false;

	//add method here to run metasolver with zeroMQ

	public static void main (String args[]){
	String arg = null;
		if(args.length == 0)
	    {
	       arg = "nothing";
	       System.exit(0);
	    }
		
		else if (args[0].contains("daemon"))
	   {
		LOGGER.info("MetaSolver main method called !!!");
        LOGGER.info("PAASAGE_CONFIG_DIR: {}", System.getenv("PAASAGE_CONFIG_DIR"));
  
       metasolver mslv = new metasolver();
       mslv.startSolving();
	   }
	   
	   else {
		   try{
				String modID= args[1];
				Mapper map = new Mapper();
				long mapResult = map.mapMetricVariables(modID);
				 metasolver mslv = new metasolver();
			      
				mslv.runMILPSolver(modID, mapResult);
			}
			catch(Exception e){
				System.out.println("error starting metasolver " + e);
			}
		}
	   }

	
	  public void init(DaemonContext daemonContext) throws DaemonInitException {

	        LOGGER.info("metasolver init method called !!!");

	        myThread = new Thread() {

	            @Override
	            public synchronized void start() {
	                App.stopped = false;
	                super.start();
	            }
	      
	            @Override
	            public void run() {
	                main(null);
	            }
	        };
	    }

	    @Override
	    public void start() throws Exception {
	        LOGGER.info("metasolver start method called !!!");
	        myThread.start();
	    }

	    @Override
	    public void stop() throws Exception {
	        LOGGER.info("metasolver stop method called !!!");
	        App.stopped = true;
	        try {
	            myThread.join(1000);
	        } catch (InterruptedException e) {
	            System.err.println(e.getMessage());
	            throw e;
	        }
	    }

	    @Override
	    public void destroy() {
	        LOGGER.info("destroy method called !!!");
	        myThread = null;
	    }
	    
	  
}
