package eu.paasage.upperware.metasolver;


//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import eu.paasage.upperware.metasolver.exception.MetricMapperException;
import eu.paasage.upperware.metasolver.metrics.Mapper;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eclipsesource.json.JsonObject;


public class App implements Daemon {


	static Logger LOGGER = LoggerFactory.getLogger(App.class.getSimpleName());

	private Thread myThread;
	private static boolean stopped = false;

	//add method here to run metasolver with zeroMQ

	public static void main (String args[]){
		String arg = null;
		System.out.println("in main");
		if(args.length == 0)
		{
			arg = "nothing";
		System.out.println(" args empty");
		}

		else if (args[3].contains("daemon"))
		{
		System.out.println("in daemon");
			LOGGER.info("MetaSolver main method called !!!");
			LOGGER.info("PAASAGE_CONFIG_DIR: {}", System.getenv("PAASAGE_CONFIG_DIR"));

			metasolver mslv = new metasolver();
			try {
				mslv.startSolving(args[0],args[1]);
			} catch (MetricMapperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {
			try{
				System.out.println("in normal");
				String modID= args[1];
				Mapper map = new Mapper();
				/* syc17
				26Nov15 aligned with updated mapper
				long mapResult = map.mapMetricVariables(modID);
				*/
				metasolver mslv = new metasolver();
				JsonObject jObj = map.mapMetricVariables(modID);				
				mslv.runMILPSolver(jObj.get("id").asString(), jObj.get("solution_timp").asLong());
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
				String[] dam = new String[2];
				dam[0]="daemon";
				dam[1]="daemon";
				main(dam);
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
	public static void runMILPSolver(String input, long timestamp){

		try{	
			Process p1 = Runtime.getRuntime().exec("java -jar milp-solver-assembly.jar " + input + ' ' + timestamp);


			// you can pass the system command or a script to exec command. here i used uname -a system command
			BufferedReader stdInput = new BufferedReader(new
					InputStreamReader(p1.getInputStream()));

			BufferedReader stdError = new BufferedReader(new
					InputStreamReader(p1.getErrorStream()));

			// read the output from the command
			String s1="";
			StringBuilder sb = new StringBuilder(); 
			while ((s1 = stdInput.readLine()) != null) {

				sb.append(s1);
				sb.append("\n");
			}

			while ((s1 = stdError.readLine()) != null) {

				sb.append(s1);
				sb.append("\n");
			}


			s1= sb.toString();
			System.out.println(" output = " + s1);

			if (s1.length() > 1 || Integer.parseInt(s1) > 0){

				//		Process p2 = Runtime.getRuntime().exec("./LAStart " + args[1]);
				String s2 = "";
				while ((s2 = stdInput.readLine()) != null) {

					System.out.println("Std OUT: "+s2);
				}

				while ((s2 = stdError.readLine()) != null) {
					System.out.println("Std ERROR : "+s2);
				}

			}

		} catch (IOException e) {

			e.printStackTrace();
		}



	}


}
