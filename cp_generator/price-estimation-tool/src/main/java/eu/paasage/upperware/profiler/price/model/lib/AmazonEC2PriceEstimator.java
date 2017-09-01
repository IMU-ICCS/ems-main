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
import org.apache.commons.collections4.MapUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AmazonEC2PriceEstimator extends ProviderPriceEstimator {

    private Map<String, Map<String, Map<String, Double>>> locationMap = new HashMap<>();

    @Override
    protected double computeVmsPrice(Feature vm, ProviderModel fm, Variable variable) {
        double price = DEFAULT_PRICE_VM;

        String flavourName = variable.getFlavourName();
        String osImageId = variable.getOsImageId();
        EList<String> locationIds = variable.getLocationIds();

        Map<String, Map<String, Double>> mapByLocation = findByLocation(locationIds);
        if (MapUtils.isNotEmpty(mapByLocation) && mapByLocation.containsKey(flavourName)) {
            Map<String, Double> mapByFlavour = mapByLocation.get(flavourName);
            if (MapUtils.isNotEmpty(mapByFlavour) && mapByFlavour.containsKey(osImageId)) {
                Double vmPrice = mapByFlavour.get(osImageId);
                if (vmPrice != null) {
                    price = vmPrice;
                    log.info("Computed price: {}", price);
                } else {
                    log.warn("Could not find value for osImageId {}. The default value will be used!", osImageId);
                }
            } else {
                log.warn("Could not find value for flavour {}. The default value will be used!", flavourName);
            }
        } else {
            log.warn("Could not find value for location location {}. The default value will be used!", locationIds.stream().collect(Collectors.joining(", ")));
        }
        return price;
    }

    private Map<String, Map<String, Double>> findByLocation(List<String> locations){
        for (String location: locations) {
            if (locationMap.containsKey(location)) {
                Map<String, Map<String, Double>> map = locationMap.get(location);
                if (MapUtils.isNotEmpty(map)){
                    return map;
                }
            }
        }
        return new HashMap<>();
    }


    /**
     * Format of the input:
     * comments
     * LocationName;number_of_profiles
     * vmSize;OS;price
     * # Indicates comments
     * Example:
     US East;12
     #Resources- VM in the case of amazon- Size;OS;price_per_hour_euros
     M;Ubuntu;0.084
     L;Ubuntu;0.17
     X;Ubuntu;0.33
     XXL;Ubuntu;0.67
     M;WindowsServer;0.13
     L;WindowsServer;0.26
     X;WindowsServer;0.52
     XXL;WindowsServer;1.04
     M;RedHatEnterpriseLinux;0.13
     L;RedHatEnterpriseLinux;0.21
     X;RedHatEnterpriseLinux;0.38
     XXL;RedHatEnterpriseLinux;0.76
     **/
    public void loadLocationRates(BufferedReader br) {
        try {
            String line = br.readLine();

            log.debug("AmazonEC2PriceEstimator - loadLocationRates - processing line {}", line);

            String[] infos = line.split(ProviderModelTool.LINE_INFOS_SEPARATOR);

            if (infos.length == 2) {
                String location = infos[0];
                int numOfProfiles = Integer.parseInt(infos[1]);

                Map<String, Map<String, Double>> locationSizes = new Hashtable<>();
                for (int i = 0; i < numOfProfiles; i++) {
                    line = br.readLine();
                    loadVMSizeRates(line, locationSizes);
                }

                locationMap.put(location, locationSizes);
                log.debug("AmazonEC2PriceEstimator - loadLocationRates - rates for {} location added!", location);
            } else
                log.error("AmazonEC2PriceEstimator - loadLocationRates - The line {} does not have the correct format. The prices will be not loaded!", line);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadVMSizeRates(String infos, Map<String, Map<String, Double>> locationPrices) {
        String[] rateInfos = infos.split(ProviderModelTool.LINE_INFOS_SEPARATOR);

        if (rateInfos.length == 3) {
            //<vmSize>;<OS>;<price>
            String size = rateInfos[0];
            String os = rateInfos[1];
            String priceString = rateInfos[2];
            Double price = Double.parseDouble(priceString);

            Map<String, Double> osPrices = locationPrices.computeIfAbsent(size, k -> new Hashtable<>());
            log.debug("AmazonEC2PriceEstimator - loadVMSizeRates- prices added: {}", infos);

            osPrices.put(os, price);
        } else
            log.error("AmazonEC2PriceEstimator - loadVMSizeRates- The line {} does not have the correct format!", infos);

    }

    @Override
    public void reset() {
        locationMap = new HashMap<>();
    }
}
