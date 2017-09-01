/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 * <p>
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
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
public class GwdgPriceEstimator extends ProviderPriceEstimator {


    private Map<String, Double> vmsMap = new HashMap<>();

    protected double computeVmsPrice(Feature vm, ProviderModel fm, Variable variable) {
        log.debug("GwdgPriceEstimator - computeVmsPrice- Computing the price... ");

        String flavourName = variable.getFlavourName();

        log.debug("GwdgPriceEstimator - computeVmsPrice- Computing the price for vm: {}", flavourName);

        Double rate = getValue(vmsMap, flavourName, DEFAULT_PRICE_VM, "GwdgPriceEstimator - computeVmsPrice- The rate for vm: {} cannot be found. The default value will be used");
        return rate;
    }


    /**
     * Format of the input:
     * comments
     * vmSize;price
     * # Indicates comments
     * Example:
     M1.MICRO;0.070
     M1.TINY;0.080
     M1.SMALL;0.090
     M1.MEDIUM;0.100
     M1.LARGE;0.110
     M1.XLARGE;0.120
     M1.XXLARGE;0.130
     M2.SMALL;0.135
     M2.MEDIUM;0.140
     M2.LARGE;0.145
     M2.XLARGE;0.150
     C1.SMALL;0.155
     C1.MEDIUM;0.160
     C1.LARGE;0.165
     C1.XLARGE;0.170
     C1.XXLARGE;0.175
     **/
    public void loadLocationRates(BufferedReader br) {
        try {
            String line = br.readLine();

            if (line.startsWith("#")) {
                return;
            }
            log.debug("GwdgPriceEstimationTool - loadLocationRates - processing line {}", line);

            String[] infos = line.split(ProviderModelTool.LINE_INFOS_SEPARATOR);

            if (infos.length == 2) {
                vmsMap.put(infos[0], Double.parseDouble(infos[1]));
            } else
                log.error("GwdgPriceEstimationTool - loadLocationRates - The line {} does not have the correct format. The price will be not loaded!", line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reset() {
        this.vmsMap = new HashMap<>();
    }
}
