/*
 * Copyright (C) 2019 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.penaltycalculator;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PenaltyFunction {

    @Autowired
    @Getter
    @Setter
    private PenaltyFunctionProperties properties;

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

        // find the elements in actual-current config. but not in new configuration
        // these elements will be deleted.
        for (PenaltyConfigurationElement s : actualConfiguration) {
            //log.debug("LOOP-1: checking CE: {}", toString(s));
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
        String str1 = "";
        String str2 = "";
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

        // connect to Daniel's InfluxDB and created database with queries...unused yet


        String arr = null;
        log.info(arr);

        //check if we have Null Component Deployment Times and act accordingy +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        double resultss = 0;
        double result = 0;
        int value1 = 0;
        double value2 = 0;
        double result2 = 0;

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
            log.info("cnt={}", cnt);
            log.info("avg={}", avg);


            //Find the maximum Component Deployment time
            double maxx = 0;
        /*
        for (ComponMeasurement cmm : ComponMeasurements) {

            if (maxx < cmm.timeDepl()) {
                maxx = cmm.timeDepl(); //swapping

            }

        }
        */

            log.info("The max Component Deployment Time value is " + maxx);


            //Close connection to Influx DB

            //influxDB.close();


            HashMap<String, String> hm = new HashMap<String, String>();


            // load old first properties file DATA
            //IPATINI:
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


            double avgTime = ((((result + result2) / value1) + avg) / 2) - min;
            resultss = avgTime / (maxx - min);
            log.info("!!!!!!!!!!!!!  result={}, result2={}, value1={}, avg={}, min={}, max={} --> resultss={}", result, result2, value1, avg, min, max, resultss);
            //return resultss;

            PenaltyFunctionResult pfResult = new PenaltyFunctionResult(resultss, avgTime);

            return pfResult;

        } else {
            //do appropriate things for only VM startup times existing
            //XXX: HashMap<String, String> hm = new HashMap<String, String>();
            log.info("-----> Point A");

            // load first properties file--REMOVED


            // load old first properties file DATA
            //IPAT:
            /*XXX: Map<String, String> prop = properties.getStartupTimes();
            prop.forEach((key, value) -> mcc.set(String.valueOf(key), String.valueOf(value)));
            prop.forEach((key, value) -> hm.put((String) key, (String) value));
            log.info(">>>>>>>>>: hm: {}", hm);*/

            log.info("-----> Adding VM data to Memcache");
            properties.getVmData().entrySet()
                    .forEach(entry -> mcc.set(entry.getKey(), Integer.toString(entry.getValue().getStartupTime())));
            log.info("-----> Point B");

            // get the values of the HashMap hm returned as an Array
            //XXX: String[] yy = hm.values().toArray(new String[0]);

            //XXX: log.info(Arrays.toString(yy));

            //Instantiate data for train of OLSMulitple regression algorithm
            //convert String Array to double Array
            double[] y = properties.getVmData().values().stream()
                    .mapToDouble(data -> data.getStartupTime()).toArray();
            log.info("-----> VM startup times (y-data): {}", y);
            //XXX: double[] y = Arrays.stream(yy).mapToDouble(Double::parseDouble).toArray();

            //Find the maximum VM Startup time
            double max = Arrays.stream(y).max().getAsDouble();
            /*XXX: double max = y[0];
            for (int i = 1; i < y.length; i++) {
                if (max < y[i]) {
                    max = y[i]; //swapping
                    y[i] = y[0];
                }
            }*/
            log.info("The max VM Startup value is " + max);


            //Find the mimimum VM Startup time
            double min = Arrays.stream(y).min().getAsDouble();
            /*XXX: double min = y[0];
            for (int i = 1; i < y.length; i++) {
                if (min > y[i]) {
                    min = y[i]; //swapping
                    y[i] = y[0];
                }
            }*/
            log.info("The min VM Startup value is " + min);


            /*XXX: log.info(">>>>>>>>>: y: {}", y);


            log.info(">>>>>>>>>: y.length: ", y.length);

            tableStringLength = y.length;

            log.info(">>>>>>>>>: tableStringLength: ", tableStringLength);

            log.info("y={}", y.length);*/


            //instantiate the double array
            //XXX: double[][] xx = new double[tableStringLength][3];
            double[][] xx = properties.getVmData().values().stream()
                    .map(PenaltyFunctionProperties.VmData::getX)
                    .collect(Collectors.toList())
                    .toArray(new double[0][0]);
            log.info("------> VM core/ram/disk (x-data): {}", Arrays.deepToString(xx));

            //XXX: log.info("xx.len={}", xx.length);

            log.info("------> Point C");
            OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();


/*XXX:
            //  load second properties files ----- REMOVED

            // load old second properties file DATA : STEFANIDI

            String[] a = properties.getStateInfo().split(";");


            //create the two dimensional array with correct size
            String[][] array = new String[a.length][];

            //combine the arrays split by semicolin and comma
            for (int i = 0; i < a.length; i++) {
                array[i] = a[i].split(",");
            }

            //Convert two dimensions String Array to two dimensions Double Array

            log.info("a={}", a.length);
            log.info("xx.len={}", xx.length);
            log.info("array: " + java.util.Arrays.deepToString(array));
            log.info("xx: " + java.util.Arrays.deepToString(xx));
            for (int k = 0; k < tableStringLength; k++) {
                for (int j = 0; j < 3; j++) {

                    xx[k][j] = Double.parseDouble(array[k][j]);

                }
            }

            log.info("array: " + java.util.Arrays.deepToString(array));
            log.info("xx_after_fill: " + java.util.Arrays.deepToString(xx));

            log.info(">>>>>>>>>: xx: {}", xx);
*/

            log.info("-----> Calculating regression parameters...");
            regression.newSampleData(y, xx);
            regression.setNoIntercept(true);

            // Get the regression parameters and residuals
            double[] betaHat = regression.estimateRegressionParameters();
            double[] residuals = regression.estimateResiduals();
            double rSquared = regression.calculateRSquared();

            //print them
            log.info("-----> Regression parameters: {}", Arrays.toString(betaHat));
            /*XXX: for (int i = 0; i < betaHat.length; i++) {
                log.info("betaHat[{}]={}", i, betaHat[i]);
            }*/

            log.info("-----> Residual parameter: {}", Arrays.toString(residuals));
            /*XXX: for (int i = 0; i < residuals.length; i++) {
                log.info("residuals[{}]={}", i, residuals[i]);
            }*/

            //log.info("residual: " + residuals);
            log.info("rSquared: {}", rSquared);


/*            for (String key : hm.keySet()) {
                int value = 0;


                // value=((Integer) hm.get(key)).intValue();//here is an ERROR
                value = Integer.parseInt((String) hm.get(key));
                for (PenaltyConfigurationElement s33 : results) {
                    log.info("KEY: {},  s33: {}", key, s33.getNodeCandidate().getHardware().getName());

                    if (key.equals(s33.getNodeCandidate().getHardware().getName())) {
                        log.info("     MATCH FOUND: {}", key);

                        //value = Integer.valueOf((String) hm.get(key));
                        result += value;
                        log.info("RESULT:{}", result);
                        value1 = value1 + 1;
                        log.info("KEY:" + key + " VALUE:" + hm.get(key));
                    }

                    if (!(hm.containsKey(s33.getNodeCandidate().getHardware().getName()))) {
                        log.info("     NO MATCH FOUND FOR: {}", s33.getNodeCandidate().getHardware().getName());

                        value2 = betaHat[0] + betaHat[1] * (s33.getNodeCandidate().getHardware().getCores()) + betaHat[2] * (s33.getNodeCandidate().getHardware().getRam()) + betaHat[3] * (s33.getNodeCandidate().getHardware().getDisk());
                        log.info("value custom:" + value2);
                        result2 += value2;
                        value1 = value1 + 1;
                    }


                }


            }*/

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

            double averageStartupTime = (sumOfStartupTimesPerPCE+sumOfEstimatedStartupTimesPerPCE)/numOfStartupTimesPerPCE;
            double normalizedValue = (averageStartupTime - min) / (max - min);

            log.info("----->  sum={}, sum-reg={}, num={}, avg={}, min={}, max={} --> resultss={}",
                    sumOfStartupTimesPerPCE, sumOfEstimatedStartupTimesPerPCE, numOfStartupTimesPerPCE,
                    averageStartupTime, min, max, normalizedValue);

            // prepare results object
            PenaltyFunctionResult pfResult = new PenaltyFunctionResult(normalizedValue, averageStartupTime);

            return pfResult;

            /*resultss = ((((result + result2) / value1) - min) / (max - min));
            log.info("!!!!!!!!!!!!!  result={}, result2={}, value1={}, min={}, max={} --> resultss={}", result, result2, value1, min, max, resultss);

            return resultss;*/
        }
    }


    public static boolean containsEquivalent(Collection<PenaltyConfigurationElement> collection, PenaltyConfigurationElement element) {
        for (PenaltyConfigurationElement ce : collection) {
            //log.debug("containsEquivalent: comparing col. elem. to given elem.: \n\t{}\n\t{}", toString(ce), toString(element));
            if (isEquivalent(ce, element)) {
                //log.debug("containsEquivalent: ARE EQUIV");
                return true;
            }
        }
        //log.debug("containsEquivalent: NO EQUIV FOUND");
        return false;
    }

    public static boolean isEquivalent(PenaltyConfigurationElement a, PenaltyConfigurationElement b) {
		/*log.debug("isEquivalent:              checking: {} <--> {}", toString(a), toString(b));
		log.debug("isEquivalent:                  ram:   {} <--> {}", a.getNodeCandidate().getHardware().getRam()-b.getNodeCandidate().getHardware().getRam()==0);
		log.debug("isEquivalent:                  cores: {} <--> {}", a.getNodeCandidate().getHardware().getCores()-b.getNodeCandidate().getHardware().getCores()==0);
		log.debug("isEquivalent:                  disk:  {} <--> {}", a.getNodeCandidate().getHardware().getDisk()-b.getNodeCandidate().getHardware().getDisk()==0);*/
        if (a.getNodeCandidate().getHardware().getRam() - b.getNodeCandidate().getHardware().getRam() == 0) {
            //log.debug("isEquivalent:                  PASS-1");
            if (a.getNodeCandidate().getHardware().getCores() - b.getNodeCandidate().getHardware().getCores() == 0) {
                //log.debug("isEquivalent:                  PASS-2");
                if (a.getNodeCandidate().getHardware().getName().equals( b.getNodeCandidate().getHardware().getName() )) {
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

        //log.debug("isEquivalent:              checking: NOT EQUIV");
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
}
