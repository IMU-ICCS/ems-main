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
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.log4j.BasicConfigurator;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PenaltyFunction {

    @Autowired
    @Getter
    @Setter
    private PenaltyFunctionProperties properties;

    public static boolean containsEquivalent(Collection<PenaltyConfigurationElement> collection, PenaltyConfigurationElement element) {
        for (PenaltyConfigurationElement ce : collection) {
            if (isEquivalent(ce, element)) {
  
                return true;
            }
        }
        
        return false;
    }

    public static boolean isEquivalent(PenaltyConfigurationElement a, PenaltyConfigurationElement b) {
		
        if (a.getNodeCandidate().getHardware().getRam() - b.getNodeCandidate().getHardware().getRam() == 0) {
           
            if (a.getNodeCandidate().getHardware().getCores() - b.getNodeCandidate().getHardware().getCores() == 0) {
                
                if (a.getNodeCandidate().getHardware().getName().equals(b.getNodeCandidate().getHardware().getName())) {
                    
                    if (a.getNodeCandidate().getHardware().getDisk() - b.getNodeCandidate().getHardware().getDisk() == 0) {
                        
                        return true;
                    }
                }
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

    public PenaltyFunctionResult evaluatePenaltyFunction(Collection<PenaltyConfigurationElement> actualConfiguration, Collection<PenaltyConfigurationElement> newConfiguration) {
        try {
            return _evaluatePenaltyFunction(actualConfiguration, newConfiguration);
        } catch (Throwable t) {
            log.error("-----> EXCEPTION: ", t);
            throw t;
        }
    }

    private PenaltyFunctionResult _evaluatePenaltyFunction(Collection<PenaltyConfigurationElement> actualConfiguration, Collection<PenaltyConfigurationElement> newConfiguration) {
        log.info("PROPERTIES: startup times:\n{}", properties.getStartupTimes());
        log.info("PROPERTIES: state info:\n{}", properties.getStateInfo());
        log.info("PROPERTIES: Memcached Port operation info:\n{}", properties.getPort());
        log.info("PROPERTIES: Memcached Host operattion info:\n{}", properties.getHost());

        // ........
        List<PenaltyConfigurationElement> toBeDeleted = new ArrayList<>();
        List<PenaltyConfigurationElement> toBeAdded = new ArrayList<>();
        List<PenaltyConfigurationElement> toBeChanged = new ArrayList<>();
		
		//Init Memchache variable info here
        String str1 = "";
        String str2 = "";
		//Init  InfluxDB variable info here
		String str3 = "";
        String str4 = "";
		String str5 = "";
		String str6 = "";
		String str7 = "";

		// Init variables for OLS algorithm 
		double resultss = 0;
        double result = 0;
        int value1 = 0;
        double value2 = 0;
        double result2 = 0;

        // find the elements in actual-current config. but not in new configuration
        // these elements will be deleted.
        for (PenaltyConfigurationElement s : actualConfiguration) {
            
            if (!containsEquivalent(newConfiguration, s)) {

                toBeDeleted.add(s);
                log.info(">>>>>>>>>: mcc: {}", toBeDeleted);
            }
        }


        // find the elelements that exist in new configuration but not in actual-current configuration
        // these elements will be added.
        for (PenaltyConfigurationElement s : newConfiguration) {
            if (!containsEquivalent(actualConfiguration, s)) {
                toBeAdded.add(s);
                log.info(">>>>>>>>>: mcc: {}", toBeAdded);

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


        //The results we need are: 'toBeAdded'&'toBeChanged'
        List<PenaltyConfigurationElement> results = new ArrayList<PenaltyConfigurationElement>(toBeChanged);
        results.addAll(toBeAdded);

        log.info("----------------------------------------------------------------------");
        log.info("Uncommon elements:\n{}", PenaltyFunction.toString(results));
        log.info("Penalty: ++++++");

        // Check if there is no difference
        if (results.size() == 0) {
            PenaltyFunctionResult pfResult = new PenaltyFunctionResult(0, 0);
            log.warn("-----> No difference between solutions: result will be: {}", pfResult);
            return pfResult;
        }


        // Get memcached connection info from properties file
        str1 = properties.getHost();
        str2 = properties.getPort();
		


        //initialize the SockIOPool that maintains the Memcached Server Connection Pool

        BasicConfigurator.configure();
        String[] servers = {str1 + ":" + str2};
        SockIOPool pool = SockIOPool.getInstance("Test2");
        log.info("servers: {}", Arrays.toString(servers));
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

        // connect to  InfluxDB --specify DB name 

        

        log.info("INFLUX point A - START");
        // Start info for Connection session to Influx DB 
		str3 = properties.getDBHost();
        str4 = properties.getDBPort();
		str5 = properties.getUser();
		str6 = properties.getPasswd();
		str7 = properties.getName();
		
		String dbName = str7;
		InfluxDB influxDB = InfluxDBFactory.connect(str3 + ":" + str4,str5,str6);
        log.info("INFLUX point B - connected");


        // querying from Daniel DB

        InfluxDBMapper influxDBMapper = new InfluxDBMapper(influxDB);
        
        log.info("INFLUX point D - InfluxDB Mapper initialized");

        //query to Influx DB for Component Deployment Times 
        Query query1 = new Query("SELECT \"time\" AS \"time\", \"task\" AS \"ComponentName\", \"value\" AS \"timeDepl\" FROM \"cloudiator\".\"autogen\".\"process-start-time\" WHERE \"task\"='database'", dbName);
        QueryResult queryResult1 = influxDB.query(query1);
        //log.info("INFLUX point E-2 - Results: {}", queryResult1);
        List<ComponMeasurement> listComponMeasurements = new ArrayList<>();
        QueryResult.Series series = queryResult1.getResults().get(0).getSeries().get(0);
        String seriesName = series.getName();
        List<String> seriesColumns = series.getColumns();
        log.info("#################  series: name={}, columns={}", seriesName, seriesColumns);
        List<List<Object>> seriesValues = series.getValues();
        //log.info("#################  values: {}", seriesValues);

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
        log.info("INFLUX point E-new - Query ok: size={}", listComponMeasurements.size());
        log.info("ComponMeasurements: {}", listComponMeasurements);

        log.info("INFLUX point F - Query results listed");
        
		
        String arr = listComponMeasurements.toString();
        log.info("INFLUX point G - arr: {}", arr);

        //String arr = null;
        log.info(arr);


        //check if we have Null Component Deployment Times and act accordingy 
		
        //if we do not have null Componennt Deployment Times:
        if (arr != null && !arr.isEmpty()) {


            //Find the Average Component Deployment Time ==>  avg
            // cnt are the number of Components Deployed along with their times
            double sum = 0;
            int cnt = 0;
            double avg = 0;

            for (ComponMeasurement cmmm : listComponMeasurements) {

                sum += cmmm.timeDepl();
                cnt++;
            }

            if (cnt > 0) {
                avg = sum / cnt;
            } else
                throw new RuntimeException("some error message");
            log.info("cnt={}", cnt);
            log.info("avg={}", avg);


            //Find the maximum Component Deployment time
            double maxx = 0;

            for (ComponMeasurement cmm : listComponMeasurements) {

                if (maxx < cmm.timeDepl()) {
                    maxx = cmm.timeDepl(); //swapping

                }

            }


            log.info("The max Component Deployment Time value is " + maxx);


            //Close connection to Influx DB

            influxDB.close();


            HashMap<String, String> hm = new HashMap<String, String>();


            // load from properties file DATA of predefined types
            
            Map<String, String> prop = properties.getStartupTimes();
            prop.forEach((key, value) -> mcc.set(String.valueOf(key), String.valueOf(value)));
            prop.forEach((key, value) -> hm.put((String) key, (String) value));
            log.info(">>>>>>>>>: hm: {}", hm);


            // get the values of the HashMap hm returned as an Array
            String[] yy = hm.values().toArray(new String[0]);

            log.info(Arrays.toString(yy));

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
            log.info("The max VM Startup value is " + max);


            //Find the mimimum VM Startup time
            double min = y[0];
            for (int i = 1; i < y.length; i++) {
                if (min > y[i]) {
                    min = y[i]; //swapping
                    y[i] = y[0];
                }
            }
            log.info("The min VM Startup value is " + min);


            log.info(">>>>>>>>>: y: {}", y);


            log.info(">>>>>>>>>: y.length: ", y.length);

            int tableStringLength = y.length;

            log.info(">>>>>>>>>: tableStringLength: ", tableStringLength);


            //instantiate the double array
            double[][] xx = new double[tableStringLength][3];

            log.info("xx.len={}", xx.length);

            OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();


            // load old second properties file DATA : STEFANIDI

            String[] a = properties.getStateInfo().split(";");


            //create the two dimensional array with correct size
            String[][] array = new String[a.length][a.length];

            //combine the arrays split by semicolin and comma
            for (int i = 0; i < a.length; i++) {
                array[i] = a[i].split(",");
            }

            //Convert two dimensions String Array to two dimensions Double Array

            log.info("a.len={}", a.length);
            log.info("xx.len={}", xx.length);
            log.info("array: " + java.util.Arrays.deepToString(array));
            log.info("xx: " + java.util.Arrays.deepToString(xx));
            for (int k = 0; k < tableStringLength; k++) {
                for (int j = 0; j < 3; j++) {
                    //tableDouble[k][j]= Double.parseDouble(tableString[k][j]);
                    //xx[k][j]= Double.valueOf(array[k][j]).doubleValue();
                    xx[k][j] = Double.parseDouble(array[k][j]);

                }
            }

            log.info("array: " + java.util.Arrays.deepToString(array));
            log.info("xx_after_fill: " + java.util.Arrays.deepToString(xx));

            log.info(">>>>>>>>>: xx: {}", xx);


            regression.newSampleData(y, xx);
            regression.setNoIntercept(true);
            // Get the regression parameters and residuals
            double[] betaHat = regression.estimateRegressionParameters();
            double[] residuals = regression.estimateResiduals();
            double rSquared = regression.calculateRSquared();
            //print them

            log.info("Regression parameters: ");
            for (int i = 0; i < betaHat.length; i++) {
                log.info("beta[{}]={}", i, betaHat[i]);
            }

            log.info("Residual parameter:");
            for (int i = 0; i < residuals.length; i++) {
                log.info("residual[{}]={}", i, residuals[i]);
            }

            //log.info("residual: " + residuals);
            log.info("rSquared: " + rSquared);


            for (String key : hm.keySet()) {
                int value = 0;


                // value=((Integer) hm.get(key)).intValue();//here is an ERROR
                value = Integer.parseInt((String) hm.get(key));
                for (PenaltyConfigurationElement s33 : results) {
                    log.info("KEY: {},  s33: {}", key, s33.getNodeCandidate().getHardware().getName());

                    if (key.equals(s33.getNodeCandidate().getHardware().getName())) {

                        //value = Integer.valueOf((String) hm.get(key));
                        result += value;
                        log.info("RESULT:{}", result);
                        value1 = value1 + 1;
                        log.info("KEY:" + key + " VALUE:" + hm.get(key));
                    }

                    if (!(hm.containsKey(s33.getNodeCandidate().getHardware().getName()))) {

                        value2 = betaHat[0] + betaHat[1] * (s33.getNodeCandidate().getHardware().getCores()) + betaHat[2] * (s33.getNodeCandidate().getHardware().getRam()) + betaHat[3] * (s33.getNodeCandidate().getHardware().getDisk());
                        log.info("value custom:" + value2);
                        result2 += value2;
                        value1 = value1 + 1;
                    }


                }


            }


            double avgTime = ((((result + result2) / value1) + (avg / 1000)) / 2) - min;
            resultss = avgTime / ((maxx / 1000) - min);
            log.info("The max Component Deployment Time value is " + maxx / 1000);
            log.info("!!!!!!!!!!!!!  result={}, result2={}, value1={}, avg={}, min={}, max={} --> resultss={}", result, result2, value1, avg / 1000, min, maxx / 1000, resultss);
            //return resultss;

            PenaltyFunctionResult pfResult = new PenaltyFunctionResult(resultss, avgTime);

            return pfResult;

        } else {
            //do appropriate things for only VM startup times existing and NOT any Component Deployment Times
           
            log.info("-----> Point A");

            //add VM data to Memcache

            log.info("-----> Adding VM data to Memcache");
            properties.getVmData().entrySet()
                    .forEach(entry -> mcc.set(entry.getKey(), Integer.toString(entry.getValue().getStartupTime())));
            log.info("-----> Point B");

            // get the values of the HashMap hm returned as an Array
           
            //Instantiate data for train of OLSMulitple regression algorithm
            //convert String Array to double Array
            double[] y = properties.getVmData().values().stream()
                    .mapToDouble(data -> data.getStartupTime()).toArray();
            log.info("-----> VM startup times (y-data): {}", y);
            

            //Find the maximum VM Startup time
            double max = Arrays.stream(y).max().getAsDouble();
            log.info("The max VM Startup value is " + max);


            //Find the mimimum VM Startup time
            double min = Arrays.stream(y).min().getAsDouble();   
            log.info("The min VM Startup value is " + min);



            //instantiate the double array
            
            double[][] xx = properties.getVmData().values().stream()
                    .map(PenaltyFunctionProperties.VmData::getX)
                    .collect(Collectors.toList())
                    .toArray(new double[0][0]);
            log.info("------> VM core/ram/disk (x-data): {}", Arrays.deepToString(xx));

         

            log.info("------> Point C");
            OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

            //Calculate regression parameters 

            log.info("-----> Calculating regression parameters...");
            regression.newSampleData(y, xx);
            regression.setNoIntercept(true);

            // Get the regression parameters and residuals
            double[] betaHat = regression.estimateRegressionParameters();
            double[] residuals = regression.estimateResiduals();
            double rSquared = regression.calculateRSquared();

            //print them
            log.info("-----> Regression parameters: {}", Arrays.toString(betaHat));
            

            log.info("-----> Residual parameter: {}", Arrays.toString(residuals));
            

            //log.info("residual: " + residuals);
            log.info("rSquared: {}", rSquared);

            log.info("-----> Point D");
            int sumOfStartupTimesPerPCE = 0;
            int sumOfEstimatedStartupTimesPerPCE = 0;
            int numOfStartupTimesPerPCE = results.size();

            for (PenaltyConfigurationElement pce : results) {
                String hardwareName = pce.getNodeCandidate().getHardware().getName()
                        .replace(".", "_");
                log.info("-----> PCE: {}", hardwareName);

                if (properties.getVmData().containsKey(hardwareName)) {
                    log.info("     MATCH FOUND: {}", hardwareName);
                    int hardwareStartupTime = properties.getVmData().get(hardwareName).getStartupTime();

                    sumOfStartupTimesPerPCE += hardwareStartupTime;
                    log.info("RESULT:{}", sumOfStartupTimesPerPCE);
                    log.info("PCE:" + hardwareName + " VALUE:" + hardwareStartupTime);
                } else {
                    log.info("     NO MATCH FOUND FOR: {}", hardwareName);

                    int hardwareCores = pce.getNodeCandidate().getHardware().getCores();
                    long hardwareRam = pce.getNodeCandidate().getHardware().getRam();
                    double hardwareDisk = pce.getNodeCandidate().getHardware().getDisk();
                    log.info("     VM params: cores={}, ram={}, disk={}", hardwareCores, hardwareRam, hardwareDisk);
                    double estimatedStartupTime = betaHat[0] + betaHat[1] * hardwareCores + betaHat[2] * hardwareRam + betaHat[3] * hardwareDisk;
                    log.info("value custom:" + estimatedStartupTime);
                    sumOfEstimatedStartupTimesPerPCE += estimatedStartupTime;

                    // update min/max
                    if (max < estimatedStartupTime) max = estimatedStartupTime;
                    if (min > estimatedStartupTime) min = estimatedStartupTime;
                }
            }
            log.info("----->  new-min={}, new-max={}", min, max);

            double averageStartupTime = (sumOfStartupTimesPerPCE + sumOfEstimatedStartupTimesPerPCE) / numOfStartupTimesPerPCE;
            double normalizedValue = (averageStartupTime - min) / (max - min);

            log.info("----->  sum={}, sum-reg={}, num={}, avg={}, min={}, max={} --> resultss={}",
                    sumOfStartupTimesPerPCE, sumOfEstimatedStartupTimesPerPCE, numOfStartupTimesPerPCE,
                    averageStartupTime, min, max, normalizedValue);

            // prepare results object
            PenaltyFunctionResult pfResult = new PenaltyFunctionResult(normalizedValue, averageStartupTime);

            return pfResult;

           
        }
    }
}
