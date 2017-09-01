/**
 * Saloon PaasSage
 * Copyright (C) 2014 INRIA, Universite  Lille 1
 * <p>
 * Contacts: daniel.romero@inria.fr & clement.quinton@inria.fr & laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 03/2014
 */

package eu.paasage.upperware.profiler.price.model.lib;

import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.profiler.price.tools.ProviderModelTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TryStackPriceEstimator extends ProviderPriceEstimator {


    private Map<String, Double> vmsMap = new HashMap<>();

    protected double computeVmsPrice(Feature vm, ProviderModel fm, Variable variable) {
        log.debug("TryStackPriceEstimator - computeVmsPrice- Computing the price... ");

        String flavourName = variable.getFlavourName();

        log.debug("TryStackPriceEstimator - computeVmsPrice- Computing the price for vm: {}", flavourName);

        Double rate = getValue(vmsMap, flavourName, DEFAULT_PRICE_VM, "TryStackPriceEstimator - computeVmsPrice- The rate for vm: {} cannot be found. The default value will be used");
        return rate;
    }


    /**
     * Format of the input:
     * comments
     * vmSize;price
     * # Indicates comments
     * Example:
     * LOWEST;0.084
     * LOWER;0.094
     * LOW;0.104
     * MEDIUM;0.114
     * HIGH;0.124
     * HIGHER;0,134
     * HIGHEST;0,144
     **/
    public void loadLocationRates(BufferedReader br) {
        try {
            String line = br.readLine();
            if (line.startsWith("#")) {
                return;
            }

            log.debug("TryStackPriceEstimator - loadLocationRates - processing line {}", line);

            String[] infos = line.split(ProviderModelTool.LINE_INFOS_SEPARATOR);

            if (infos.length == 2) {
                vmsMap.put(infos[0], Double.parseDouble(infos[1]));
            } else
                log.error("TryStackPriceEstimator - loadLocationRates - The line {} does not have the correct format. The price will be not loaded!", line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reset() {
        this.vmsMap = new HashMap<>();
    }
}
