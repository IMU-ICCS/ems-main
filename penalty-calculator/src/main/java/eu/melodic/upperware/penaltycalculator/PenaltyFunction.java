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

@Slf4j
@Service
@RequiredArgsConstructor
public class PenaltyFunction {

    private final PenaltyFunctionProperties properties;

    private MemCachedClient memCachedClient;

    private double[] vmStartupTimesArray;
    private double vmStartupTimesMax;
    private double vmStartupTimesMin;
    private double[][] vmDataArray;

    private List<ComponMeasurement> componentMeasurementsFromInfluxDbList;
    private long componentMeasurementsFromInfluxDbTimestamp = -1;
    private double componentMeasurementsAverage;
    private double componentMeasurementsMin;
    private double componentMeasurementsMax;

    private double[] betaHat;
    private double[] residuals;
    private double rSquared;

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

    public static boolean containsEquivalent(Collection<PenaltyConfigurationElement> collection, PenaltyConfigurationElement element) {
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

        log.info("Connecting to Memcache servers: {}", Arrays.toString(servers));

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
        memCachedClient = new MemCachedClient("Test2");

        /*// Add VM startup time from properties to Memcache
        log.info("-----> Adding VM startup times to Memcache");
        properties.getVmData().forEach(
                (vmName, vmData) -> memCachedClient.set(vmName, Integer.toString(vmData.getStartupTime())));*/

        log.info("Memcache client initialized");
    }

    private void loadVmStartupTimesFromMemcache() {
        vmStartupTimesArray = null;
    }

    private void loadVmStartupTimesFromProperties() {

        // Load VM startup times from properties
        vmStartupTimesArray = properties.getVmData().values().stream()
                .mapToDouble(PenaltyFunctionProperties.VmData::getStartupTime).toArray();
        log.debug("-----> VM startup times from properties: {}", vmStartupTimesArray);

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
        log.info("------> VM core/ram/disk from properties: {}", Arrays.deepToString(vmDataArray));
    }

    private synchronized void calculateRegressionParameters() {
        // Create OSL Multiple Linear regression instance
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

        // Calculate regression parameters
        log.info("-----> Calculating regression parameters...");
        regression.newSampleData(vmStartupTimesArray, vmDataArray);
        regression.setNoIntercept(true);

        // Get the regression parameters and residuals
        betaHat = regression.estimateRegressionParameters();
        residuals = regression.estimateResiduals();
        rSquared = regression.calculateRSquared();

        // print regression results
        log.info("-----> Regression parameters: {}", Arrays.toString(betaHat));
        log.info("-----> Residual parameter: {}", Arrays.toString(residuals));
        log.info("-----> rSquared: {}", rSquared);
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
                        "FROM \"cloudiator\".\"autogen\".\"process-start-time\" " +
                        "WHERE \"task\"='database'";
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
        log.info("Querying InfluxDB for component measurements...");
        try {
            componentMeasurementsFromInfluxDbList = queryInfluxDbForComponentMeasurements();
            componentMeasurementsFromInfluxDbTimestamp = System.currentTimeMillis();
        } catch (Exception ex) {
            log.error("Exception while querying InfluxDB: ", ex);
            return null;
        }

        // Calculate average, min and max component deployment times
        log.info("Component measurements from Influx DB: count={}", componentMeasurementsFromInfluxDbList.size());
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
        double deploymentTimeAverage = componentMeasurementsAverage / 1000;
        double deploymentTimeMax = componentMeasurementsMax / 1000;
        double deploymentTimeMin = componentMeasurementsMin / 1000;
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
            log.info("-----> Diff-Element: {}", hardwareName);

            if (properties.getVmData().containsKey(hardwareName)) {
                log.info("     Match found for VM: {}", hardwareName);

                // Get VM startup time from properties
                int hardwareStartupTime = properties.getVmData().get(hardwareName).getStartupTime();
                log.trace("DIFF-ELEM: VM={}, STARTUP-TIME={}", hardwareName, hardwareStartupTime);

                // Sum up startup time
                sumOfStartupTimesPerPCE += hardwareStartupTime;
                log.trace("Current startup time sum: {}", sumOfStartupTimesPerPCE);

            } else {
                log.info("     No match found for VM: {}", hardwareName);

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
            log.info("-----> Diff-Element: {}", hardwareName);

            if (properties.getVmData().containsKey(hardwareName)) {
                log.info("     Match found for VM: {}", hardwareName);

                // Get VM startup time from properties
                int hardwareStartupTime = properties.getVmData().get(hardwareName).getStartupTime();
                log.trace("DIFF-ELEM: VM={}, STARTUP-TIME={}", hardwareName, hardwareStartupTime);

                // Sum up startup time
                sumOfStartupTimesPerPCE += hardwareStartupTime;
                log.trace("Current startup time sum: {}", sumOfStartupTimesPerPCE);

            } else {
                log.info("     No match found for VM: {}", hardwareName);

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
