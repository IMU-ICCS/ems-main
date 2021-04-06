/*
 * Copyright (C) 2017-2020 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.penaltycalculator;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.log4j.BasicConfigurator;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.*;
import java.util.Map;

import java.util.Collection;
import org.apache.commons.*; 
import java.lang.Object;
import java.lang.Number;
import java.lang.Double;
import java.lang.Integer;
import java.util.Properties; 
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;



@Slf4j
@Service
@RequiredArgsConstructor
public class PenaltyFunction {

    private final PenaltyFunctionProperties properties;

    private MemCachedClient memCachedClient;

	public double[] vmStartupTimesArray = new double[30];
    private double vmStartupTimesMax;
    private double vmStartupTimesMin;
    private double[][] vmDataArray;
	
	private double[] betaHat;
 

	private List<Double> vmStartupTimeslist = new ArrayList<>();
	private List<String> vmStartupTimeslistkeys = new ArrayList<>();
	
	private List<String> vmStartupTimeslistkeysinfluxdbget = new ArrayList<String>();
	
	 
	private List<String> vmStartupTimeslistkeysinfluxdb = new ArrayList<String>();
		 

    private List<ComponMeasurement> componentMeasurementsFromInfluxDbList;
    private long componentMeasurementsFromInfluxDbTimestamp = -1;
    private double componentMeasurementsAverage;
    private double componentMeasurementsMin;
    private double componentMeasurementsMax;


    private static boolean isEquivalent(PenaltyConfigurationElement a, PenaltyConfigurationElement b) {
        if (a.getNodeCandidate().getHardware().getRam().equals(b.getNodeCandidate().getHardware().getRam())) {
            if (a.getNodeCandidate().getHardware().getCores().equals(b.getNodeCandidate().getHardware().getCores())) {
                if (a.getNodeCandidate().getHardware().getName().equals(b.getNodeCandidate().getHardware().getName())) {
                   return (a.getNodeCandidate().getHardware().getDisk().equals(b.getNodeCandidate().getHardware().getDisk()));

                }
            }
        }
        return false;
    }

    private static boolean containsEquivalent(Collection<PenaltyConfigurationElement> collection, PenaltyConfigurationElement element) {
        for (PenaltyConfigurationElement ce : collection) {
            if (isEquivalent(ce, element)) {
                return true;
            }
        }
        return false;
    }

    public static String toString(PenaltyConfigurationElement ce) {
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

    public static String toString(Collection<PenaltyConfigurationElement> collection) {
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (PenaltyConfigurationElement ce : collection) {
            if (first) first = false;
            else sb.append(",");
            sb.append(toString(ce));
        }
        sb.append("]");
        return sb.toString();
    }

    @PostConstruct
    private synchronized void initializePenaltyFunction() {
        initializeMemcacheClient();
        loadVmStartupTimesFromMemcache();
        if (vmStartupTimesArray ==null)
            loadVmStartupTimesFromProperties();
        calculateRegressionParameters();
    }

    private synchronized void initializeMemcacheClient() {

        // check if memcache client is already initialized
        if (memCachedClient!=null)
            return;

        // Get memcached connection settings from properties
        String memcacheHost = properties.getMemcacheHost();
        int memcachePort = properties.getMemcachePort();

        // Initialize the SockIOPool that maintains the Memcached Server Connection Pool
        BasicConfigurator.configure();
        String[] servers = { memcacheHost + ":" + memcachePort };
        SockIOPool pool = SockIOPool.getInstance("Test2");

        log.debug("Connecting to Memcache servers: {}", Arrays.toString(servers));

        // Connection pool settings
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

        // Create new Memcache Client instance
		MemCachedClient mcc = new MemCachedClient("Test2");

        // Add VM startup time from properties to Memcache
        log.debug("-----> Adding VM startup times to Memcache");
        properties.getVmData().forEach(
                (vmName, vmData) -> mcc.set(vmName, Integer.toString(vmData.getStartupTime())));
				
		//print VM data details
		 log.debug("end of VM data details");
		 Properties prop = System.getProperties();
		 log.debug("print of VM data details from properties file");
		 for (Object key: prop.keySet()) {
			System.out.println(key + ": " + prop.getProperty(key.toString()));
		 }
		 vmDataArray = properties.getVmData().values().stream()
                .map(PenaltyFunctionProperties.VmData::getCharacteristics)
                .collect(Collectors.toList())
                .toArray(new double[0][0]);
				
		log.debug("-----> print vmDataArray stored in Properties file");
		log.debug("#################  vmDataArray", vmDataArray);
				
		// Get multiple keys from MemCache

		//store in memcached the Vm startup times
		Map<String, Map<String, String>> Test = mcc.statsCacheDump(1,0);
        Map<String,String> newTest = new LinkedHashMap<String, String>();

        for(Map.Entry<String, Map<String,String>> entry : Test.entrySet()) {
            for (Map.Entry<String, String> value : entry.getValue().entrySet()) {
                   newTest.put(value.getKey(),value.getValue());
                 }
             }
	    log.debug("-----> print VM startup times stored in Memcache");
		newTest.keySet().stream()
				.forEach(System.out::println);
		newTest.values().stream()
				.forEach(System.out::println);
		System.out.println(Arrays.asList(newTest)); // method 1
		log.debug("-----> Adding Arrays.asList(newTest) and print the following key-values stored");
		for (Map.Entry<String, String> entry : newTest.entrySet()) {
             System.out.println(entry.getKey()+" : "+entry.getValue());
        }
		
		String[] keys = newTest.keySet().toArray(new String[0]);
		
        HashMap<String,Object> hm = (HashMap<String, Object>) mcc.getMulti(keys);
        log.debug("print what to be stored in vmStartupTimeslistkeys" );
        for(String key : hm.keySet())
           {
              log.info("KEY: "+key+" VALUE: "+hm.get(key));
			  
			  vmStartupTimeslist.add(Double.valueOf(hm.get(key).toString()).doubleValue());
			  vmStartupTimeslistkeys.add(key);
		   }
		
		log.debug("Print vmstartuptimes array" );
		for (int j =0; j < vmStartupTimeslist.size(); j++) {
            vmStartupTimesArray[j] = vmStartupTimeslist.get(j); 
			System.out.println(vmStartupTimesArray[j]);
			System.out.println(vmStartupTimeslistkeys.get(j));
		}
		
		
		log.debug("Connect to Influx DB to find which VM startup times Vm types have been measured so far" );
		// Connect to Influx DB to find which VM startup times Vm types have been mesaured so far. 
		
        String influxDbHost = properties.getInfluxDbHost();
        int influxDbPort = properties.getInfluxDbPort();
        String influxDbUsername = properties.getInfluxDbUsername();
        String influxDbPassword = properties.getInfluxDbPassword();
        String influxDbName = properties.getInfluxDbName();

        InfluxDB influxDB = InfluxDBFactory.connect(influxDbHost + ":" + influxDbPort, influxDbUsername, influxDbPassword);

        // Query Influx DB for VM startup Times measured
        
		//initial code
		HashMap<String,Object> hminfluxdbstartupkeys = new HashMap<String,Object>();
        		
		String queryStrType ="SELECT \"value\" AS \"value\", \"hardware_provider\" AS \"vmtype\" FROM \"cloudiator\".\"autogen\".\"vm-start-time\"";
		//log.info("#################  Query queryStrType ", queryStrType);

		Query queryType = new Query(queryStrType, influxDbName);

		QueryResult queryResultt = influxDB.query(queryType);

		QueryResult.Series seriess = queryResultt.getResults().get(0).getSeries().get(0);

        String seriesNamee = seriess.getName();
		System.out.println(seriesNamee);
		System.out.println((int)(seriess.getValues()).size());

		for (int j =0; j < (seriess.getValues()).size(); j++) {
      		
		
		vmStartupTimeslistkeysinfluxdbget.add(String.valueOf(seriess.getValues().get(j).get(2)));

		
		}
		
		Set<String> treesetList = new TreeSet<String>(vmStartupTimeslistkeysinfluxdbget);
		System.out.printf("\nUnique VM types values using TreeSet - Sorted order: %s%n", treesetList);
        treesetList.removeAll(Arrays.asList("null")); 		
		System.out.printf("\nUnique VM types values using TreeSet after removal of null - Sorted order: %s%n", treesetList);

		
		vmStartupTimeslistkeysinfluxdb.addAll(treesetList);
		
		for(String element : vmStartupTimeslistkeysinfluxdb){
            System.out.println( element );
        }    
		 
		HashMap<String,Object> hminfluxdbstartup = new HashMap<String,Object>();
        //query influx db for startup times after we have found the vm types that have been measured		
		for (int k =0; k < vmStartupTimeslistkeysinfluxdb.size(); k++) {
        
			System.out.println(vmStartupTimeslistkeysinfluxdb.get(k));
		
		
		String queryStrr ="SELECT mean(\"value\") AS \"value\" FROM \"cloudiator\".\"autogen\".\"vm-start-time\""
				 +
                 "WHERE \"hardware_provider\"='"+ vmStartupTimeslistkeysinfluxdb.get(k) + "'";
				 
		log.debug("#################  Query result each time", queryStrr);
		System.out.println(queryStrr);
		Query query = new Query(queryStrr, influxDbName);
		System.out.println(query);
		QueryResult queryResult = influxDB.query(query);
		System.out.println(queryResult);

        
        QueryResult.Series series = queryResult.getResults().get(0).getSeries().get(0);
		System.out.println(series);
        String seriesName = series.getName();
        //String seriesValues = series.getValues();		
		System.out.println(seriesName);
		System.out.println(series.getValues().get(0).get(1));
		double mean = ((double)(series.getValues().get(0).get(1)))/1000;
		System.out.println(mean);
		hminfluxdbstartup.put(vmStartupTimeslistkeysinfluxdb.get(k), mean);
		
        		
		}		 
		
        System.out.println(Arrays.asList(hminfluxdbstartup));
		

		
		Collection<String> similar = new HashSet<String>(vmStartupTimeslistkeys);
        Collection<String> different = new HashSet<String>();

        different.addAll(vmStartupTimeslistkeys);
        different.addAll(vmStartupTimeslistkeysinfluxdb);

        similar.retainAll(vmStartupTimeslistkeysinfluxdb);
        different.removeAll(similar);
		different.removeAll(vmStartupTimeslistkeys);

        System.out.printf("Memcached:%s%nInfluxDB:%s%nSimilar:%s%nDifferent:%s%n", vmStartupTimeslistkeys, vmStartupTimeslistkeysinfluxdb, similar, different);
        log.debug("for-each loop write to memcahced the new VMtypes");
		// for-each loop write to memcahced the new VMtypes
		int m=0;
        for (String s : different) {
        System.out.println("key= " + s.replace(".","_"));

		System.out.println("value= " + hminfluxdbstartup.get(s));

		mcc.set((s.replace(".","_")),hminfluxdbstartup.get(s));
		vmStartupTimesArray[m]=((double)hminfluxdbstartup.get(s));
		m=m+1;
           }
		   
		log.debug("for-each loop update to memcahced the existing VMtypes");
		// for-each loop update to memcahced the existing VMtypes
        for (String t : similar) {
	
        System.out.println("key= " + t);
		
		System.out.println("value= " + hminfluxdbstartup.get(t));
		mcc.replace(t,hminfluxdbstartup.get(t));
		
		//m=m+1;
	
        }
		
		log.debug("#################  vmStartupTimesArray  after .......:");
		for (int j =0; j < vmStartupTimesArray.length; j++) {
			System.out.println(j);


            System.out.println(vmStartupTimesArray[j]);
        }
        
		   
		
		log.debug("hminfluxdbstartup.size():");
	    System.out.println(hminfluxdbstartup.size());
        // Close connection to Influx DB
        influxDB.close();
		
        log.debug("Memcache client initialized");
		/*
		steps
		1. init mia lista me karfwta ta keys apo influxdb ==> vmStartupTimeslistkeysinfluxdb
		2. ena loop me queries gia mean value me where vmStartupTimeslistkeysinfluxdb.get(j) kai perneis to mean statrup time
		    Sto idio loop add vmStartupTimeslistVALUESinfluxdb(i) to mean startup time
        3. 	sygkrine tis dyo listes: vmStartupTimeslistkeys & 	vmStartupTimeslistkeysinfluxdb ==> vres koina kai diaforetika keys
        4. gia ta koina kane mcc.update & gia ta diaforetika kane mcc.set 		
         */

     }

    private void loadVmStartupTimesFromMemcache() {
       // vmStartupTimesArray = null;
		
		
    }

    private void loadVmStartupTimesFromProperties() {

        // Load VM startup times from properties
        vmStartupTimesArray = properties.getVmData().values().stream()
                .mapToDouble(PenaltyFunctionProperties.VmData::getStartupTime).toArray();
        log.debug("-----> VM startup times from properties: {} METHOD: loadVmStartupTimesFromProperties()", vmStartupTimesArray);

        if (vmStartupTimesArray.length==0)
            return;

        // Find the minimum and maximum VM Startup time
        vmStartupTimesMax = Arrays.stream(vmStartupTimesArray).max().getAsDouble();
        vmStartupTimesMin = Arrays.stream(vmStartupTimesArray).min().getAsDouble();
        log.debug("-----> VM startup times from properties: min={}, max={}", vmStartupTimesMin, vmStartupTimesMax);

        // Initialize the VM characteristics array
        vmDataArray = properties.getVmData().values().stream()
                .map(PenaltyFunctionProperties.VmData::getCharacteristics)
                .collect(Collectors.toList())
                .toArray(new double[0][0]);
        log.debug("------> VM core/ram/disk from properties: {}", Arrays.deepToString(vmDataArray));
    }

    private synchronized void calculateRegressionParameters() {
        
        double[] residuals;
        double rSquared;
		// Create OSL Multiple Linear regression instance
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

        // Calculate regression parameters
        log.debug("-----> Calculating regression parameters...");
		// Initialize the VM characteristics array
        

		
		
		log.debug("------> VM core/ram/disk from properties222: {}", Arrays.deepToString(vmDataArray));
		log.debug("Print vmstartuptimes array for RegressionParameters ....." );
		System.out.println(Arrays.toString(vmStartupTimesArray));
		
        regression.newSampleData(vmStartupTimesArray, vmDataArray);
        regression.setNoIntercept(true);

        // Get the regression parameters and residuals
        betaHat = regression.estimateRegressionParameters();
        residuals = regression.estimateResiduals();
        rSquared = regression.calculateRSquared();

        // print regression results
        log.debug("-----> Regression parameters: {}", Arrays.toString(betaHat));
        log.debug("-----> Residual parameter: {}", Arrays.toString(residuals));
        log.debug("-----> rSquared: {}", rSquared);
    }

    public PenaltyFunctionResult evaluatePenaltyFunction(Collection<PenaltyConfigurationElement> actualConfiguration, Collection<PenaltyConfigurationElement> newConfiguration) {
        try {
            return _evaluatePenaltyFunction(actualConfiguration, newConfiguration);
        } catch (Throwable t) {
            log.error("-----> EXCEPTION: ", t);
            throw t;
        }
    }

    private List<PenaltyConfigurationElement> calculateConfigurationDifference(Collection<PenaltyConfigurationElement> actualConfiguration, Collection<PenaltyConfigurationElement> newConfiguration) {
        // initialize lists
        List<PenaltyConfigurationElement> toBeDeleted = new ArrayList<>();
        List<PenaltyConfigurationElement> toBeAdded = new ArrayList<>();
        List<PenaltyConfigurationElement> toBeChanged = new ArrayList<>();

        // find the elements in actual-current config. but not in new configuration
        // these elements will be deleted.
        for (PenaltyConfigurationElement s : actualConfiguration) {
            if (!containsEquivalent(newConfiguration, s)) {
                toBeDeleted.add(s);
                log.debug("calculateConfigurationDifferences: to-be-deleted: {}", toBeDeleted);
            }
        }

        // find the elelements that exist in new configuration but not in actual-current configuration
        // these elements will be added.
        for (PenaltyConfigurationElement s : newConfiguration) {
            if (!containsEquivalent(actualConfiguration, s)) {
                toBeAdded.add(s);
                log.debug("calculateConfigurationDifferences: to-be-added: {}", toBeAdded);
            }
        }

        // find the elements that exist in current configuration and will be in the new config also.
        // for these elements calculate the diff in cardinalities (number)
        for (PenaltyConfigurationElement s1 : newConfiguration) {
            for (PenaltyConfigurationElement s2 : actualConfiguration) {
                if (isEquivalent(s1, s2)) {
                    int newCardinality = s1.getCardinality() - s2.getCardinality();
                    PenaltyConfigurationElement s_new = new PenaltyConfigurationElement(s1.getId(), s1.getNodeCandidate(), newCardinality);
                    if (newCardinality > 0) {
                        toBeChanged.add(s_new);
                    } else {
                        toBeDeleted.add(s1);
                    }
                }
            }
        }

        //The results we need are: 'toBeAdded' & 'toBeChanged'
        List<PenaltyConfigurationElement> results = new ArrayList<PenaltyConfigurationElement>(toBeChanged);
        results.addAll(toBeAdded);

        log.debug("calculateConfigurationDifferences: ----------------------------------------------------------------------");
        log.debug("calculateConfigurationDifferences: Uncommon elements:\n{}", PenaltyFunction.toString(results));

        return results;
    }

    private List<ComponMeasurement> queryInfluxDbForComponentMeasurements() {

        // Connect to Influx DB
        String influxDbHost = properties.getInfluxDbHost();
        int influxDbPort = properties.getInfluxDbPort();
        String influxDbUsername = properties.getInfluxDbUsername();
        String influxDbPassword = properties.getInfluxDbPassword();
        String influxDbName = properties.getInfluxDbName();

        InfluxDB influxDB = InfluxDBFactory.connect(influxDbHost + ":" + influxDbPort, influxDbUsername, influxDbPassword);

        // Query Influx DB for Component Deployment Times
        String queryStr1 =
                "SELECT \"time\" AS \"time\", \"task\" AS \"ComponentName\", \"value\" AS \"timeDepl\" " +
                        "FROM \"cloudiator\".\"autogen\".\"process-start-time\"";
        Query query1 = new Query(queryStr1, influxDbName);
        QueryResult queryResult1 = influxDB.query(query1);
        List<ComponMeasurement> listComponMeasurements = new ArrayList<>();

        QueryResult.Series series = queryResult1.getResults().get(0).getSeries().get(0);
        String seriesName = series.getName();
        List<String> seriesColumns = series.getColumns();
        log.debug("#################  series: name={}, columns={}", seriesName, seriesColumns);

        // Process query results
        List<List<Object>> seriesValues = series.getValues();
        log.debug("#################  values: {}", seriesValues);
        seriesValues.forEach(row -> {
            for (int i = 0; i < seriesColumns.size(); i++) {
                //log.info("   -------->    {} = {} / {}", seriesColumns.get(i), row.get(i), row.get(i).getClass());

                eu.melodic.upperware.penaltycalculator.ComponMeasurement cm;
                cm = new eu.melodic.upperware.penaltycalculator.ComponMeasurement();
                cm.setTime(Instant.parse(row.get(0).toString()));
                cm.setComponentName(row.get(1).toString());
                cm.setTimeDepl(Double.parseDouble(row.get(2).toString()));

                listComponMeasurements.add(cm);
            }
        });
        log.debug("ComponMeasurements: {}", listComponMeasurements);

        // Close connection to Influx DB
        influxDB.close();

        return listComponMeasurements;
    }

    private synchronized List<ComponMeasurement> getComponentMeasurementsFromInfluxDb() {
        if (componentMeasurementsFromInfluxDbList!=null)
            return componentMeasurementsFromInfluxDbList;

        // Query Influx DB for component deployment times
        log.debug("Querying InfluxDB for component measurements...");
        try {
            componentMeasurementsFromInfluxDbList = queryInfluxDbForComponentMeasurements();
            componentMeasurementsFromInfluxDbTimestamp = System.currentTimeMillis();
        } catch (Exception ex) {
            log.error("Exception while querying InfluxDB: ", ex);
            return null;
        }

        // Calculate average, min and max component deployment times
        log.debug("Component measurements from Influx DB: count={}", componentMeasurementsFromInfluxDbList.size());
        if (componentMeasurementsFromInfluxDbList.size()>0) {
            componentMeasurementsAverage = componentMeasurementsFromInfluxDbList.stream()
                    .mapToDouble(ComponMeasurement::getTimeDepl)
                    .average()
                    .orElseThrow(() -> new IllegalArgumentException("No component measurements"));

            componentMeasurementsMin = componentMeasurementsFromInfluxDbList.stream()
                    .mapToDouble(ComponMeasurement::getTimeDepl)
                    .min().getAsDouble();

            componentMeasurementsMax = componentMeasurementsFromInfluxDbList.stream()
                    .mapToDouble(ComponMeasurement::getTimeDepl)
                    .max().getAsDouble();

            log.info("Component measurements from Influx DB: average={}, min={}, max={}",
                    componentMeasurementsAverage, componentMeasurementsMin, componentMeasurementsMax);
        }

        return componentMeasurementsFromInfluxDbList;
    }

    private PenaltyFunctionResult _evaluatePenaltyFunction(Collection<PenaltyConfigurationElement> actualConfiguration, Collection<PenaltyConfigurationElement> newConfiguration) {

        // Get 'actual configuration' and 'new configuration' differences
        List<PenaltyConfigurationElement> solutionDifferences = calculateConfigurationDifference(actualConfiguration, newConfiguration);

        // Check if there is no difference
        if (solutionDifferences.size() == 0) {
            PenaltyFunctionResult pfResult = new PenaltyFunctionResult(0, 0);
            log.info("-----> No difference between solutions: result will be: {}", pfResult);
            return pfResult;
        } else {
            // There are differences
            log.debug("-----> Difference between solutions: {}", solutionDifferences);
        }


        if (!properties.isSkipComponentDeploymentTimes()) {

            // Get Component deployment times from Influx DB
            List<ComponMeasurement> listComponMeasurements = getComponentMeasurementsFromInfluxDb();

            //check if we have Null Component Deployment Times and act accordingly

            // if we do not have null Component Deployment Times
            if (listComponMeasurements!=null && listComponMeasurements.size()>0) {
                //
                // Calculate penalty when Component Deployment Times are available and not skipped
                //

                return _evaluatePenaltyFunctionWithDeploymentTimes(solutionDifferences, listComponMeasurements);

            } // else use only VM startup times

        } // else use only VM startup times

        //
        // Calculate penalty when no Component Deployment Times are available or they are skipped
        //

        return _evaluatePenaltyFunctionWithStartupTimesOnly(solutionDifferences);
    }

    private PenaltyFunctionResult _evaluatePenaltyFunctionWithDeploymentTimes(
            List<PenaltyConfigurationElement> solutionDifferences,
            List<ComponMeasurement> listComponMeasurements)
    {
        // Initialize deployment time variables - Need to convert them to ???
        double deploymentTimeAverage = componentMeasurementsAverage;
        double deploymentTimeMax = componentMeasurementsMax;
        double deploymentTimeMin = componentMeasurementsMin;
        log.info("------> Component deployment times: count={}, average={}, min={}, max={}",
                listComponMeasurements.size(), deploymentTimeAverage, deploymentTimeMin, deploymentTimeMax);

        // Initialize startup time variables
        double min = vmStartupTimesMin;
        double max = vmStartupTimesMax;
        log.debug("------> VM Startup times: min={}, max={}", min, max);

        double sumOfStartupTimesPerPCE = 0;
        double sumOfEstimatedStartupTimesPerPCE = 0;
        int numOfStartupTimesPerPCE = solutionDifferences.size();

        // For every element in solution differences: get or estimate its startup time
        for (PenaltyConfigurationElement diffElement : solutionDifferences)
        {
            // Get VM name from node candidate
            String hardwareName = diffElement.getNodeCandidate().getHardware().getName()
                    .replace(".", "_");
            log.debug("-----> Diff-Element: {}", hardwareName);

            if (properties.getVmData().containsKey(hardwareName)) {
                log.debug("     Match found for VM: {}", hardwareName);

                // Get VM startup time from properties
                int hardwareStartupTime = properties.getVmData().get(hardwareName).getStartupTime();
                log.trace("DIFF-ELEM: VM={}, STARTUP-TIME={}", hardwareName, hardwareStartupTime);

                // Sum up startup time
                sumOfStartupTimesPerPCE += hardwareStartupTime;
                log.trace("Current startup time sum: {}", sumOfStartupTimesPerPCE);

            } else {
                log.debug("     No match found for VM: {}", hardwareName);

                // Estimate VM startup time based on its characteristics
                int hardwareCores = diffElement.getNodeCandidate().getHardware().getCores();
                long hardwareRam = diffElement.getNodeCandidate().getHardware().getRam();
                double hardwareDisk = diffElement.getNodeCandidate().getHardware().getDisk();
                log.debug("     VM params: cores={}, ram={}, disk={}", hardwareCores, hardwareRam, hardwareDisk);

                double estimatedStartupTime =
                        betaHat[0] + betaHat[1] * hardwareCores + betaHat[2] * hardwareRam + betaHat[3] * hardwareDisk;
                log.debug("Estimated startup time: {}", estimatedStartupTime);

                // An optimization would be to cache the 'hardwareName'-to-'estimated startup time' pair

                // Sum up startup time
                sumOfEstimatedStartupTimesPerPCE += estimatedStartupTime;

                // Update min/max
                if (max < estimatedStartupTime) max = estimatedStartupTime;
                if (min > estimatedStartupTime) min = estimatedStartupTime;
            }
        }

        // Calculate penalty
        double averageStartupTime = (sumOfStartupTimesPerPCE + sumOfEstimatedStartupTimesPerPCE) / numOfStartupTimesPerPCE;
        double averageTotalTime = (averageStartupTime + deploymentTimeAverage) / 2;
        double penaltyValue = (averageTotalTime - min) / (deploymentTimeMax - min);
        log.debug("The max Component Deployment Time value is {}", max / 1000);
        log.debug("------>  sum-startup-time={}, sum-estimated-startup-time={}, num-of-elements={}, total-average={}, min={}, max={} --> penalty={}",
                sumOfStartupTimesPerPCE, sumOfEstimatedStartupTimesPerPCE, numOfStartupTimesPerPCE,
                averageTotalTime, min, deploymentTimeMax, penaltyValue);

        // return penalty function result object
        return new PenaltyFunctionResult(penaltyValue, averageTotalTime);
    }

    private PenaltyFunctionResult _evaluatePenaltyFunctionWithStartupTimesOnly(
            List<PenaltyConfigurationElement> solutionDifferences)
    {
        // Initialize variables
        double min = vmStartupTimesMin;
        double max = vmStartupTimesMax;
        log.debug("------> VM Startup times: min={}, max={}", min, max);

        double sumOfStartupTimesPerPCE = 0;
        double sumOfEstimatedStartupTimesPerPCE = 0;
        int numOfStartupTimesPerPCE = solutionDifferences.size();

        // For every element in solution differences: get or estimate its startup time
        for (PenaltyConfigurationElement diffElement : solutionDifferences) {
            // Get VM name from node candidate
            String hardwareName = diffElement.getNodeCandidate().getHardware().getName()
                    .replace(".", "_");
            log.debug("-----> Diff-Element: {}", hardwareName);

            if (properties.getVmData().containsKey(hardwareName)) {
                log.debug("     Match found for VM: {}", hardwareName);

                // Get VM startup time from properties
                int hardwareStartupTime = properties.getVmData().get(hardwareName).getStartupTime();
                log.trace("DIFF-ELEM: VM={}, STARTUP-TIME={}", hardwareName, hardwareStartupTime);

                // Sum up startup time
                sumOfStartupTimesPerPCE += hardwareStartupTime;
                log.trace("Current startup time sum: {}", sumOfStartupTimesPerPCE);

            } else {
                log.debug("No match found for VM: {}", hardwareName);

                // Estimate VM startup time based on its characteristics
                int hardwareCores = diffElement.getNodeCandidate().getHardware().getCores();
                long hardwareRam = diffElement.getNodeCandidate().getHardware().getRam();
                double hardwareDisk = diffElement.getNodeCandidate().getHardware().getDisk();
                log.debug("     VM params: cores={}, ram={}, disk={}", hardwareCores, hardwareRam, hardwareDisk);

                double estimatedStartupTime =
                        betaHat[0] + betaHat[1] * hardwareCores + betaHat[2] * hardwareRam + betaHat[3] * hardwareDisk;
                log.debug("Estimated startup time: {}", estimatedStartupTime);

                // An optimization would be to cache the 'hardwareName'-to-'estimated startup time' pair

                // Sum up startup time
                sumOfEstimatedStartupTimesPerPCE += estimatedStartupTime;

                // Update min/max
                if (max < estimatedStartupTime) max = estimatedStartupTime;
                if (min > estimatedStartupTime) min = estimatedStartupTime;
            }
        }
        log.trace("----->  new-min={}, new-max={}", min, max);

        // Calculate average VM startup time and its normalized value (aka penalty)
        double averageStartupTime = (sumOfStartupTimesPerPCE + sumOfEstimatedStartupTimesPerPCE) / numOfStartupTimesPerPCE;
        double normalizedValue = (averageStartupTime - min) / (max - min);

        log.info("----->  sum={}, sum-reg={}, num={}, avg={}, min={}, max={} --> resultss={}",
                sumOfStartupTimesPerPCE, sumOfEstimatedStartupTimesPerPCE, numOfStartupTimesPerPCE,
                averageStartupTime, min, max, normalizedValue);

        // return penalty function result object
        return new PenaltyFunctionResult(normalizedValue, averageStartupTime);
    }
}
