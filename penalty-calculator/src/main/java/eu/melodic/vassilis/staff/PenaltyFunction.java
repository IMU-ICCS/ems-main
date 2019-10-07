/*
 * Copyright (C) 2019 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.vassilis.staff;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.log4j.BasicConfigurator;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.impl.InfluxDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;



@Slf4j
@Service
public class PenaltyFunction {

    @Autowired
    private PenaltyFunctionProperties properties;

    public double evaluatePenaltyFunction(Collection<ConfigurationElement> actualConfiguration, Collection<ConfigurationElement> newConfiguration) {
		log.info("PROPERTIES: startup times:\n{}", properties.getStartupTimes());
		log.info("PROPERTIES: state info:\n{}", properties.getStateInfo());
		log.info("PROPERTIES: Memcached Port operation info:\n{}", properties.getPort());
		log.info("PROPERTIES: Memcached Host operattion info:\n{}", properties.getHost());

		// ........
        List<ConfigurationElement> toBeDeleted = new ArrayList<ConfigurationElement>();
        List<ConfigurationElement> toBeAdded = new ArrayList<ConfigurationElement>();
        List<ConfigurationElement> toBeChanged = new ArrayList<ConfigurationElement>();
        double resultss = 0;
        double result = 0;
        double resultt = 0;
        double finalll = 0;
        int value1 = 0;
        double value2 = 0;
        double result2 = 0;
        int tableStringLength;

        //double[][] xx = null;
        //double[][] xx = new double[tableStringLength][tableStringLength];

        // Vriskw ayta pou einai sto actual config. alla oxi sto neo config.
        // Ayta tha diagrafoun
        for (ConfigurationElement s : actualConfiguration) {
            //log.debug("LOOP-1: checking CE: {}", toString(s));
            if (!containsEquivalent(newConfiguration, s)) {
                //log.debug("LOOP-1: NO EQUIV FOUND: {}", s.getId());
                toBeDeleted.add(s);
                log.info(">>>>>>>>>: mcc: {}", toBeDeleted);
            }
        }
        //log.debug("To-Be-Deleted:\n{}", PenaltyFunction.toString(toBeDeleted));

        //isEquivalent isEquivalentFunction = new isEquivalent();
        //uncommon = penaltyCalculator.evaluatePenaltyFunction(collection_1, collection_2);

        // Vriskw ayta pou einai sto new config. alla oxi sto actual config.
        // Ayta tha prostethoun
        for (ConfigurationElement s : newConfiguration) {
            if (!containsEquivalent(actualConfiguration, s)) {
                toBeAdded.add(s);
                log.info(">>>>>>>>>: mcc: {}", toBeAdded);

            }
        }
        //log.debug("To-Be-Added:\n{}", PenaltyFunction.toString(toBeAdded));

        // Vriskw ayta pou einai KAI sto new config. KAI sto actual config.
        // Gia ayta tha vrw ti diafora twn cardinalities
        for (ConfigurationElement s1 : newConfiguration) {
            for (ConfigurationElement s2 : actualConfiguration) {
                if (isEquivalent(s1, s2)) {
                    int newCardinality = s1.getCardinality() - s2.getCardinality();
                    ConfigurationElement s_new = new ConfigurationElement(s1.getId(), s1.getNodeCandidate(), newCardinality);
                    if (newCardinality > 0) {
                        toBeChanged.add(s_new);
                    } else {
                        toBeDeleted.add(s1);
                    }
                }
            }
        }
        //log.debug("To-Be-Changed:\n{}", PenaltyFunction.toString(toBeChanged));
        //log.debug("To-Be-Deleted-UPDATED:\n{}", PenaltyFunction.toString(toBeDeleted));

        // Ta apotelesmata pou theloume einai ta 'toBeAdded' kai ta 'toBeChanged'
        List<ConfigurationElement> results = new ArrayList<ConfigurationElement>(toBeChanged);
        results.addAll(toBeAdded);

        log.info("----------------------------------------------------------------------");
        log.info("Uncommon elements:\n{}", PenaltyFunction.toString(results));
        log.info("Penalty: ++++++");
        String str1 = "";
        String str2 = "";
	    // Get memcached connection info from properties file
		str1 = properties.getHost(); 
		str2 = properties.getPort();
		

        //initialize the SockIOPool that maintains the Memcached Server Connection Pool

        BasicConfigurator.configure();
        String[] servers = {str1 + ":" + str2};
        SockIOPool pool = SockIOPool.getInstance("Test2");
        System.out.println(servers);
        System.out.println(Arrays.toString(servers));


        pool.setServers(servers);
        pool.setFailover(true);
        pool.setInitConn(11);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaintSleep(30);
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setAliveCheck(true);
        pool.initialize();

        //Get the Memcached Client from SockIOPool named Test2
        MemCachedClient mcc = new MemCachedClient("Test2");

        // connect to Daniel's InfluxDB and created database with queries

        //final InfluxDB influxDB = InfluxDBFactory.connect("http://134.60.152.213:8888", "vasilis", "EiWeif0w");
        //final String dbName = "aTimeSeries";
        //influxDB.createDatabase(dbName);
        //final BatchPoints batchPoints = BatchPoints.database(dbName).tag("async", "true").retentionPolicy("default").build();
        //final Point point1 = Point.measurement("cpu").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS).field("idle", Long.valueOf(90L)).field("system", Long.valueOf(9L)).field("system", Long.valueOf(1L)).build();
        //final Point point2 = Point.measurement("disk").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS).field("used", Long.valueOf(80L)).field("free", Long.valueOf(1L)).build();
        //batchPoints.point(point1);
        //batchPoints.point(point2);
        //influxDB.write(batchPoints);
        //final Query query = new Query("SELECT idle FROM cpu", dbName);
        //final QueryResult queryResult = influxDB.query(query);
        //System.out.println(queryResult);
        //log.info(">>>>>>>>>: queryResult: {}",queryResult);

        //log.info(">>>>>>>>>: queryResult: {}",dbName);
        //influxDB.deleteDatabase(dbName);
        /*
		InfluxDB influxDB = InfluxDBFactory.connect("http://134.60.152.213:8888", "vasilis", "EiWeif0w");
        String dbName = "aTimeSeries";
        influxDB.query(new Query("CREATE DATABASE " + dbName));
        String rpName = "aRetentionPolicy";
        influxDB.query(new Query("CREATE RETENTION POLICY " + rpName + " ON " + dbName + " DURATION 30h REPLICATION 2 DEFAULT"));

        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy(rpName)
                .consistency(ConsistencyLevel.ALL)
                .build();
        Point point1 = Point.measurement("cpu")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("idle", 90L)
                    .addField("user", 9L)
                    .addField("system", 1L)
                    .build();
        Point point2 = Point.measurement("disk")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("used", 80L)
                    .addField("free", 1L)
                    .build();
        batchPoints.point(point1);
        batchPoints.point(point2);
        influxDB.write(batchPoints);
        Query query = new Query("SELECT idle FROM cpu", dbName);
        influxDB.query(query);
	    log.info(">>>>>>>>>: queryResult: {}",queryResult);
		
		log.info(">>>>>>>>>: queryResult: {}",dbName);
        influxDB.query(new Query("DROP RETENTION POLICY " + rpName + " ON " + dbName));
        influxDB.query(new Query("DROP DATABASE " + dbName));
		*/
		
		/*
		
		Configuration configuration = new Configuration("134.60.152.213", "8888", "vasilis", "EiWeif0w", "mydb");
        Query query = new Query();
		query.setMeasurement("sampleMeasurement1");
		List list = new ArrayList();
        list.add("sampleMeasurement1");
        list.add("sampleMeasurement2");
        query.setMeasurements(list);
		
		query.addField("field1");
        query.addField("field2");
		
		query.setDuration("1h");
		
		query.setRange(new Date(2012, 12, 31), new Date());
		
		query.setAggregateFunction(AggregateFunction.MEAN);
		
		query.setGroupByTime("1m");
		
		query.setLimit(1000);
		
		query.fillNullValues("0");
		
		DataReader dataReader = new DataReader(query, configuration);
        ResultSet resultSet = dataReader.getResult();
        System.out.println(resultSet);
		 
		*/
		
        //comment out the Influx DB functionality since no Comp Deployment Times are stored yet
        /*
        String dbName = "centos_test_db";

        InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086", "root", "root");

        // Flush every 2000 Points, at least every 100ms
        influxDB.enableBatch(2000, 100, TimeUnit.MILLISECONDS);

        for (int i = 0; i < 1; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Point point2 = Point.measurement("ComponentTime")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("timeDepl", Math.random() * 400L)
                    .addField("ComponentName", "AppResponse")
                    .build();
            influxDB.write(dbName, "autogen", point2);
        }

        System.out.println();

        Query query = new Query("SELECT * FROM ComponentTime", dbName);
        influxDB.query(query);
        System.out.println(influxDB.query(query));

        //InfluxDB connection = connectDatabase();

        // querying from centos_test_db DB

        InfluxDBMapper influxDBMapper = new InfluxDBMapper(influxDB);
        //Query query1 = select("timeDepl").from(dbName,"ComponentTime");
        Query query1 = new Query("SELECT timeDepl FROM ComponentTime", dbName);
        //Logger.info("Executing query "+query1.getCommand());
        List<ComponMeasurement> ComponMeasurements = influxDBMapper.query(query1, ComponMeasurement.class);
        ComponMeasurements.forEach(System.out::println);


        String arr = ComponMeasurements.toString();
		*/
		String arr = null;
        System.out.println(arr);
		
		//check if we have Null Component Deployment Times and act accordingy +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
	if (arr != null && !arr.isEmpty()) {
            
        
        //Find the Average Component Deployment Time ==>  avg
        // cnt are the number of Components Deployed along with their times
        double sum = 0;
        int cnt = 0;
        double avg = 0;
        /*
        for (ComponMeasurement cm : ComponMeasurements) {

            sum += cm.timeDepl();
            cnt++;
        }

        if (cnt > 0) {
            avg = sum / cnt;
        } else
            throw new RuntimeException("some error message"); */
        System.out.println(cnt);
        System.out.println(avg);


        //Find the maximum Component Deployment time
        double maxx = 0;
        /*
        for (ComponMeasurement cmm : ComponMeasurements) {

            if (maxx < cmm.timeDepl()) {
                maxx = cmm.timeDepl(); //swapping

            }

        }
        */

        System.out.println("The max Component Deployment Time value is " + maxx);

      
        //Close connection to Influx DB 

        //influxDB.close();
		
        		
		

        HashMap<String, String> hm = new HashMap<String, String>();


        // load old first properties file DATA 
		//IPATINI:
		Map<String,String> prop = properties.getStartupTimes();
		prop.forEach((key, value) -> mcc.set(String.valueOf(key), String.valueOf(value)));
		prop.forEach((key, value) -> hm.put((String) key, (String) value));
		log.info(">>>>>>>>>: hm: {}", hm);
		

        // get the values of the HashMap hm returned as an Array
        String[] yy = hm.values().toArray(new String[0]);

        System.out.println(Arrays.toString(yy));

        //Instantiate data for train of OLSMulitple regression algorithm
        //convert String Array to double Array
        double[] y = Arrays.stream(yy).mapToDouble(Double::parseDouble).toArray();

        //Find the maximum VM Startup time
        double max = y[0];
        for (int i = 1; i < y.length; i++) {
            if (max < y[i]) {
                max = y[i]; //swapping
                y[i] = y[0];
            }
        }
        System.out.println("The max VM Startup value is " + max);


        //Find the mimimum VM Startup time		
        double min = y[0];
        for (int i = 1; i < y.length; i++) {
            if (min > y[i]) {
                min = y[i]; //swapping
                y[i] = y[0];
            }
        }
        System.out.println("The min VM Startup value is " + min);


        log.info(">>>>>>>>>: y: {}", y);
        //System.out.println(Arrays.toString(values));

        log.info(">>>>>>>>>: y.length: ", y.length);
        System.out.println(y.length);

        tableStringLength = y.length;

        log.info(">>>>>>>>>: tableStringLength: ", tableStringLength);

        System.out.println(y.length);
        //double [][]tableDouble= null;

        //instantiate the double array
        double[][] xx = new double[tableStringLength][3];

        System.out.println(xx.length);

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        
        
		// load old second properties file DATA : STEFANIDI
		//String[] a = properties.getProperty("stateInfo").split(";");
	    String[] a = properties.getStateInfo().split(";");



        //create the two dimensional array with correct size
        String[][] array = new String[a.length][a.length];

        //combine the arrays split by semicolin and comma
        for (int i = 0; i < a.length; i++) {
            array[i] = a[i].split(",");
         }

        //Convert two dimensions String Array to two dimensions Double Array

        System.out.println(a.length);
        System.out.println(xx.length);
        System.out.println("array: " + java.util.Arrays.deepToString(array));
        System.out.println("xx: " + java.util.Arrays.deepToString(xx));
            for (int k = 0; k < tableStringLength; k++) {
                for (int j = 0; j < 3; j++) {
                    //tableDouble[k][j]= Double.parseDouble(tableString[k][j]);
                    //xx[k][j]= Double.valueOf(array[k][j]).doubleValue();
                    xx[k][j] = Double.parseDouble(array[k][j]);

                }
            }

        System.out.println("array: " + java.util.Arrays.deepToString(array));
        System.out.println("xx_after_fill: " + java.util.Arrays.deepToString(xx));
         
        log.info(">>>>>>>>>: xx: {}", xx);
        

        regression.newSampleData(y, xx);
        regression.setNoIntercept(true);
        // Get the regression parameters and residuals
        double[] betaHat = regression.estimateRegressionParameters();
        double[] residuals = regression.estimateResiduals();
        double rSquared = regression.calculateRSquared();
        //print them

        System.out.println("Regression parameters: ");
        for (int i = 0; i < betaHat.length; i++) {
            System.out.println(betaHat[i]);
        }

        System.out.println("Residual parameter:");
        for (int i = 0; i < residuals.length; i++) {
            System.out.println(residuals[i]);
        }

        //System.out.println("residual: " + residuals);
        System.out.println("rSquared: " + rSquared);


        for (String key : hm.keySet()) {
            int value = 0;


            // value=((Integer) hm.get(key)).intValue();//here is an ERROR
            value = Integer.parseInt((String) hm.get(key));
            for (ConfigurationElement s33 : results) {
                log.info("KEY: {},  s33: {}", key, s33.getNodeCandidate().getHardware().getName());

                if (key.equals(s33.getNodeCandidate().getHardware().getName())) {

                    //value = Integer.valueOf((String) hm.get(key));
                    result += value;
                    log.info("RESULT:{}", result);
                    value1 = value1 + 1;
                    System.out.println("KEY:" + key + " VALUE:" + hm.get(key));
                }

                if (!(hm.containsKey(s33.getNodeCandidate().getHardware().getName()))) {

                    value2 = betaHat[0] + betaHat[1] * (s33.getNodeCandidate().getHardware().getCores()) + betaHat[2] * (s33.getNodeCandidate().getHardware().getRam()) + betaHat[3] * (s33.getNodeCandidate().getHardware().getDisk());
                    System.out.println("value custom:" + value2);
                    result2 += value2;
                    value1 = value1 + 1;
                }


                //	if (!(s33.getNodeCandidate().getHardware().getName()).equals("t1.micro") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.small") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.large") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.xlarge") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.medium") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.xxlarge") &&
                //	!(s33.getNodeCandidate().getHardware().getName()).equals("m1.tiny") && !(s33.getNodeCandidate().getHardware().getName()).equals("m1.small") && !(s33.getNodeCandidate().getHardware().getName()).equals("m1.medium") && !(s33.getNodeCandidate().getHardware().getName()).equals("m1.large") && !(s33.getNodeCandidate().getHardware().getName()).equals("m1.xlarge")) {
                //	value2=betaHat[0]+betaHat[1] * (s33.getNodeCandidate().getHardware().getCores())+betaHat[2] * (s33.getNodeCandidate().getHardware().getRam())+betaHat[3] * (s33.getNodeCandidate().getHardware().getDisk());
                //    System.out.println("value custom:"+value2);
                //    result2 += value2;
                //	value1 = value1 + 1;
                //
                //
                //	}


            }


        }


        //resultss= (((((result+result2)/value1)+ avg)-min)/(maxx-min));
        //resultss= ((((result+result2)/value1)-min)/(max-min));

        resultss = ((((((result + result2) / value1) + avg) / 2) - min) / (maxx - min));
        return resultss;
		
		
		
	}
    else {
          //do appropriate things for only VM startup times existing  
	    HashMap<String,String> hm = new HashMap<String, String>();
		
		
		// load first properties file--REMOVED
/*		try (InputStream input = new FileInputStream("src\\main\\resources\\config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
			// print key and values
            prop.forEach((key, value) -> System.out.println("VMtype : " + key + ", Boot time Value : " + value));
			// for each key - value pair set to the memcached
			prop.forEach((key, value) -> mcc.set(String.valueOf(key),String.valueOf(value))); 
            //prop.forEach(key -> add.keys(String.valueOf(key))); 
			
			
            //prop.forEach((key) -> keys.add(String.valueOf(key)));			
			//prop.forEach((entry) -> keys.add(String.valueOf(entry.getKey())));
			// Get all key
            //prop.keySet().forEach(x -> keys.add(String.valueOf(x)));
            prop.keySet().forEach(x -> System.out.println(x));
            Set<Object> objects = prop.keySet();
			//---new-----
			prop.forEach((key, value) -> hm.put((String) key,(String) value));
			log.info(">>>>>>>>>: mcc: {}",hm);
		    
			

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		   */

		
		
        // load old first properties file DATA 
		//IPATINI:
		Map<String,String> prop = properties.getStartupTimes();
		prop.forEach((key, value) -> mcc.set(String.valueOf(key), String.valueOf(value)));
		prop.forEach((key, value) -> hm.put((String) key, (String) value));
		log.info(">>>>>>>>>: hm: {}", hm);
		

         // get the values of the HashMap hm returned as an Array
		String[] yy = hm.values().toArray(new String[0]);
		
		System.out.println(Arrays.toString(yy));
		
		//Instantiate data for train of OLSMulitple regression algorithm
		//convert String Array to double Array
		double[] y = Arrays.stream(yy).mapToDouble(Double::parseDouble).toArray();
		
		//Find the maximum VM Startup time
		double max = y[0];
        for (int i = 1; i < y.length; i++){
             if(max<y[i]){
                  max=y[i]; //swapping
                  y[i]=y[0];
                }
            }
            System.out.println("The max VM Startup value is "+ max);
		

        //Find the mimimum VM Startup time		
		double min = y[0];
        for (int i = 1; i < y.length; i++){
             if(min>y[i]){
                  min=y[i]; //swapping
                  y[i]=y[0];
                }
            }
            System.out.println("The min VM Startup value is "+ min);
		
		
		
		
		
		
         log.info(">>>>>>>>>: y: {}",y);
		//System.out.println(Arrays.toString(values)); 
		
		log.info(">>>>>>>>>: y.length: ",y.length);
		System.out.println(y.length); 
		
		tableStringLength=y.length;
		   
		log.info(">>>>>>>>>: tableStringLength: ",tableStringLength);
		 
		System.out.println(y.length); 
        //double [][]tableDouble= null;
		   
		//instantiate the double array
        double[][] xx = new double[tableStringLength][3];
		   
		System.out.println(xx.length);
		  
		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
	    //Instantiate data for train of OLSMulitple regression algorithm
	    //double[] y = new double[] {50, 100, 110, 120, 130, 130, 55, 79, 88, 132,140};
		 
		 
		/*  load second properties files ----- REMOVED  
		//double[][] x = new double[tableStringLength][tableStringLength];
	    // load second properties file
		try (InputStream input1 = new FileInputStream("src\\main\\resources\\config1.properties")){

            Properties prop1 = new Properties();

            // load a properties file
            prop1.load(input1);
			
			//get array split up by the semicolon
            String[] a = prop1.getProperty("stateInfo").split(";");
			
			//get two dimensional array from the properties file that has been delineated
            //double[][] x = fetchArrayFromPropFile("stateInfo",prop1);
			
			//create the two dimensional array with correct size
            String[][] array = new String[a.length][a.length];

	        //combine the arrays split by semicolin and comma 
            for(int i = 0;i < a.length;i++) {
                array[i] = a[i].split(",");
        }
			
		//Convert two dimensions String Array to two dimensions Double Array
			
		System.out.println(a.length);
		System.out.println(xx.length);
		System.out.println("array: "+java.util.Arrays.deepToString(array));
		System.out.println("xx: "+java.util.Arrays.deepToString(xx));
			for(int k=0; k<tableStringLength; k++) {
                 for(int j=0; j<3; j++) {
                     //tableDouble[k][j]= Double.parseDouble(tableString[k][j]);
					 //xx[k][j]= Double.valueOf(array[k][j]).doubleValue();
                       xx [k][j]= Double.parseDouble(array[k][j]);
					   
					   }
                    }
			
		System.out.println("array: "+java.util.Arrays.deepToString(array));
	    System.out.println("xx_after_fill: "+java.util.Arrays.deepToString(xx));
		   
		//x[][]= Double.valueOf(array[][]).doubleValue();
			
	    //double[][] x = Arrays.stream(array).mapToDouble(Double::parseDouble).toArray();
		//double [][] x = array ;
		}
			
			catch (IOException ex) {
            ex.printStackTrace();
        }
	

        log.info(">>>>>>>>>: xx: {}",xx);
	    */
           
			
	       
		// load old second properties file DATA : STEFANIDI
		//String[] a = properties.getProperty("stateInfo").split(";");
	    String[] a = properties.getStateInfo().split(";");



        //create the two dimensional array with correct size
        String[][] array = new String[a.length][a.length];

        //combine the arrays split by semicolin and comma
        for (int i = 0; i < a.length; i++) {
            array[i] = a[i].split(",");
         }

        //Convert two dimensions String Array to two dimensions Double Array

        System.out.println(a.length);
        System.out.println(xx.length);
        System.out.println("array: " + java.util.Arrays.deepToString(array));
        System.out.println("xx: " + java.util.Arrays.deepToString(xx));
            for (int k = 0; k < tableStringLength; k++) {
                for (int j = 0; j < 3; j++) {
                    //tableDouble[k][j]= Double.parseDouble(tableString[k][j]);
                    //xx[k][j]= Double.valueOf(array[k][j]).doubleValue();
                    xx[k][j] = Double.parseDouble(array[k][j]);

                }
            }

        System.out.println("array: " + java.util.Arrays.deepToString(array));
        System.out.println("xx_after_fill: " + java.util.Arrays.deepToString(xx));
         
        log.info(">>>>>>>>>: xx: {}", xx);   
	       

	    regression.newSampleData(y, xx);
	    regression.setNoIntercept(true);
	    // Get the regression parameters and residuals
	    double[] betaHat = regression.estimateRegressionParameters();
	    double[] residuals = regression.estimateResiduals();
	    double rSquared = regression.calculateRSquared();
	    //print them
	        
	    System.out.println("Regression parameters: ");
	    for (int i = 0; i < betaHat.length; i++) {
	            System.out.println(betaHat[i]);
	    }

	    System.out.println("Residual parameter:");
	    for (int i = 0; i < residuals.length; i++) {
	            System.out.println(residuals[i]);
	    }
	        
	    //System.out.println("residual: " + residuals);
	    System.out.println("rSquared: " + rSquared);
			
			
	    //Double[] decMax = {-2.8, -8.8, 2.3, 7.9, 4.1, -1.4, 11.3, 10.4, 8.9, 8.1, 5.8, 5.9, 7.8, 4.9, 5.7, -0.9, -0.4, 7.3, 8.3, 6.5, 9.2, 3.5, 3.0, 1.1, 6.5, 5.1, -1.2, -5.1, 2.0, 5.2, 2.1};
        //List<double> a = new ArrayList<double>(Arrays.asList(yyy));
        //System.out.println("The highest VM Startup time is: " + Collections.max(a));
	    //System.out.println("rSquared: " + rSquared);
		
	for(String key : hm.keySet()){
			int value =0;
			
			
			// value=((Integer) hm.get(key)).intValue();//here is an ERROR 
			value=Integer.parseInt((String) hm.get(key));
			for (ConfigurationElement s33 : results) {
				log.info("KEY: {},  s33: {}", key, s33.getNodeCandidate().getHardware().getName());
				
				if (key.equals(s33.getNodeCandidate().getHardware().getName())){
			
			//value = Integer.valueOf((String) hm.get(key));
			result += value; 
			log.info("RESULT:{}", result);
			value1 = value1 + 1;
			System.out.println("KEY:"+key+" VALUE:"+hm.get(key));
			}
			
			if (!(hm.containsKey(s33.getNodeCandidate().getHardware().getName()))){
				
			value2=betaHat[0]+betaHat[1] * (s33.getNodeCandidate().getHardware().getCores())+betaHat[2] * (s33.getNodeCandidate().getHardware().getRam())+betaHat[3] * (s33.getNodeCandidate().getHardware().getDisk());
            System.out.println("value custom:"+value2);
            result2 += value2;			
			value1 = value1 + 1;
			}
				
			
		//	if (!(s33.getNodeCandidate().getHardware().getName()).equals("t1.micro") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.small") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.large") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.xlarge") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.medium") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.xxlarge") && 
		//	!(s33.getNodeCandidate().getHardware().getName()).equals("m1.tiny") && !(s33.getNodeCandidate().getHardware().getName()).equals("m1.small") && !(s33.getNodeCandidate().getHardware().getName()).equals("m1.medium") && !(s33.getNodeCandidate().getHardware().getName()).equals("m1.large") && !(s33.getNodeCandidate().getHardware().getName()).equals("m1.xlarge")) {
		//	value2=betaHat[0]+betaHat[1] * (s33.getNodeCandidate().getHardware().getCores())+betaHat[2] * (s33.getNodeCandidate().getHardware().getRam())+betaHat[3] * (s33.getNodeCandidate().getHardware().getDisk());
        //    System.out.println("value custom:"+value2);
        //    result2 += value2;			
		//	value1 = value1 + 1;
		//	
		//	
		//	}
			
			
		}
			
			
			
	}
	   
	   
	   resultss= ((((result+result2)/value1)-min)/(max-min));
		
		return resultss;
    }
    }

  

    public static boolean containsEquivalent(Collection<ConfigurationElement> collection, ConfigurationElement element) {
        for (ConfigurationElement ce : collection) {
            //log.debug("containsEquivalent: comparing col. elem. to given elem.: \n\t{}\n\t{}", toString(ce), toString(element));
            if (isEquivalent(ce, element)) {
                //log.debug("containsEquivalent: ARE EQUIV");
                return true;
            }
        }
        //log.debug("containsEquivalent: NO EQUIV FOUND");
        return false;
    }

    public static boolean isEquivalent(ConfigurationElement a, ConfigurationElement b) {
		/*log.debug("isEquivalent:              checking: {} <--> {}", toString(a), toString(b));
		log.debug("isEquivalent:                  ram:   {} <--> {}", a.getNodeCandidate().getHardware().getRam()-b.getNodeCandidate().getHardware().getRam()==0);
		log.debug("isEquivalent:                  cores: {} <--> {}", a.getNodeCandidate().getHardware().getCores()-b.getNodeCandidate().getHardware().getCores()==0);
		log.debug("isEquivalent:                  disk:  {} <--> {}", a.getNodeCandidate().getHardware().getDisk()-b.getNodeCandidate().getHardware().getDisk()==0);*/
        if (a.getNodeCandidate().getHardware().getRam() - b.getNodeCandidate().getHardware().getRam() == 0) {
            //log.debug("isEquivalent:                  PASS-1");
            if (a.getNodeCandidate().getHardware().getCores() - b.getNodeCandidate().getHardware().getCores() == 0) {
                //log.debug("isEquivalent:                  PASS-2");
                if (a.getNodeCandidate().getHardware().getName() == b.getNodeCandidate().getHardware().getName()) {
                    //log.debug("isEquivalent:                  PASS-3");
                    if (a.getNodeCandidate().getHardware().getDisk() - b.getNodeCandidate().getHardware().getDisk() == 0) {
					/*if (a.getCardinality() == b.getCardinality()){
						return false;
					}*/
                        //log.debug("isEquivalent:              checking: EQUIV");
                        return true;
                    }
                }
            }

        }
        //return true;
        //log.debug("isEquivalent:              checking: NOT EQUIV");
        return false;
    }

    public static String toString(ConfigurationElement ce) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("id=").append(ce.getId());
        sb.append(",");
        sb.append("cardinality=").append(ce.getCardinality());
        sb.append(",");
        sb.append("nodeCandidate={")
                .append("id:").append(ce.getNodeCandidate().getId()).append(",")
                .append("Name:").append(ce.getNodeCandidate().getHardware().getName()).append(",")
                .append("ram:").append(ce.getNodeCandidate().getHardware().getRam()).append(",")
                .append("cores:").append(ce.getNodeCandidate().getHardware().getCores()).append(",")
                .append("disk:").append(ce.getNodeCandidate().getHardware().getDisk()).append("}");
        sb.append("}");
        return sb.toString();
    }

    public static String toString(Collection<ConfigurationElement> collection) {
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (ConfigurationElement ce : collection) {
            if (first) first = false;
            else sb.append(",");
            sb.append(toString(ce));
        }
        sb.append("]");
        return sb.toString();
    }
	

}
