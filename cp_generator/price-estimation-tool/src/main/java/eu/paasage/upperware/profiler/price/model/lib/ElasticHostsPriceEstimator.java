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

import eu.paasage.camel.provider.*;
import eu.paasage.camel.type.IntegerValue;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.profiler.price.tools.ProviderModelTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Component
@Slf4j
public class ElasticHostsPriceEstimator extends ProviderPriceEstimator {
    
    private Map<String, Map<String, Map<String, ResourcePrice>>> locationsMap = new HashMap<>();

    public void loadLocationRates(BufferedReader br) {
        try {
            String line = br.readLine();

            String[] infos = line.split(ProviderModelTool.LINE_INFOS_SEPARATOR);

            if (infos.length == 2) {
                String location = infos[0];
                int numberOfResources = Integer.parseInt(infos[1]);

                Map<String, Map<String, ResourcePrice>> resourcesMap = new Hashtable<>();
                for (int i = 0; i < numberOfResources; i++) {
                    loadResourceRates(br, resourcesMap);
                }
                locationsMap.put(location, resourcesMap);
            } else
                log.error("ElasticHostsPriceEstimator - loadLocationRates - The line {} does not have the correct format!", line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadResourceRates(BufferedReader br, Map<String, Map<String, ResourcePrice>> resourcesMap) throws IOException {
        //<resource>;<pricingModel>;<quantity>;<price>
        String line = br.readLine();

        String[] infos = line.split(ProviderModelTool.LINE_INFOS_SEPARATOR);

        if (infos.length == 4) {
            String resource = infos[0];
            String pricingModel = infos[1];
            int quantity = Integer.parseInt(infos[2]);
            double price = Double.parseDouble(infos[3]);

            Map<String, ResourcePrice> pricingModelsMap = resourcesMap.computeIfAbsent(resource, k -> new Hashtable<>());
            pricingModelsMap.put(pricingModel, new ResourcePrice(quantity, price));

            log.debug("ElasticHostsPriceEstimator - loadResourceRates - Rate {} added!", line);
        } else
            log.error("ElasticHostsPriceEstimator - loadResourceRates - The line {} does not have the correct format!", line);


    }

    public double estimatePrice(ProviderModel fm) {
        double price = 0;

        Exclusive pricingModelFeature = (Exclusive) ProviderModelTool.getFeatureByName(fm, ProviderModelTool.PRICING_MODEL_FEATURE);

        log.debug("ElasticHostsPriceEstimator - estimatePrice - pricingModelFeature name {}", pricingModelFeature.getName());

        Feature selectedFeature = ProviderModelTool.getSelectedFeatureFromList(pricingModelFeature.getVariants());

        log.debug("ElasticHostsPriceEstimator - estimatePrice - Selected Price Model {}", selectedFeature);

        String pricingModel = ProviderModelTool.HOUR;

        if (selectedFeature != null)
            pricingModel = ProviderModelTool.getPricingModelFromFeatureName(selectedFeature.getName());

        price = computeResourcesPrice(fm, pricingModel);

        return price;
    }

    @Override
    protected double computeVmsPrice(Feature vm, ProviderModel fm, Variable variable) {
        return 0;
    }


    public double computeResourcesPrice(ProviderModel fm, String pricingModel) {
        log.debug("ElasticHostsPriceEstimator - computeResourcesPrice- Computing the price... ");
        double price = 0;

        Feature vm = ProviderModelTool.getFeatureByName(fm, ProviderModelTool.VIRTUAL_MACHINE_FEATURE);

        if (vm != null) {
            Alternative locationGroup = (Alternative) ProviderModelTool.getFeatureByName(fm.getRootFeature(), ProviderModelTool.LOCATION_FEATURE);


            if (locationGroup != null) {
                Feature location = ProviderModelTool.getSelectedFeatureFromList(locationGroup.getVariants());

                if (location != null) {
                    //location.get
                    Map<String, Map<String, ResourcePrice>> resourcesMap = locationsMap.get(location.getName());

                    if (resourcesMap != null) {
                        Map<String, ResourcePrice> pricingModelsRAMMap = resourcesMap.get("memory");
                        ResourcePrice ramPrice = pricingModelsRAMMap.get(pricingModel);


                        Map<String, ResourcePrice> pricingModelsStorageMap = resourcesMap.get("storage");
                        ResourcePrice storagePrice = pricingModelsStorageMap.get(pricingModel);


                        Map<String, ResourcePrice> pricingModelsCPUMap = resourcesMap.get("CPU");
                        ResourcePrice cpuPrice = pricingModelsCPUMap.get(pricingModel);


                        Attribute memoryAtt = ProviderModelTool.getAttributeByName(vm, ProviderModelTool.VIRTUAL_MACHINE_MEMORY_ATTRIBUTE);
                        int memory = ((IntegerValue) (memoryAtt.getValue())).getValue();
                        double ramPriceForQuantity = ramPrice.getResourcePriceForQuantity(memory);

                        log.debug("ElasticHostsPriceEstimator - computeResourcesPrice - Memory {} with Memory Price {}", memory, ramPriceForQuantity);

                        Attribute cpuAtt = ProviderModelTool.getAttributeByName(vm, ProviderModelTool.VIRTUAL_MACHINE_CPU_CORE_ATTRIBUTE);
                        int cpu = ((IntegerValue) (cpuAtt.getValue())).getValue();
                        double cpuPriceForQuantity = cpuPrice.getResourcePriceForQuantity(cpu);

                        log.debug("ElasticHostsPriceEstimator - computeResourcesPrice - CPU {} with CPU Price {}", cpu, cpuPriceForQuantity);

                        Attribute storageAtt = ProviderModelTool.getAttributeByName(vm, ProviderModelTool.VIRTUAL_MACHINE_STORAGE_ATTRIBUTE);
                        int storage = ((IntegerValue) (storageAtt.getValue())).getValue();
                        double storagePriceForQuantity = storagePrice.getResourcePriceForQuantity(storage);

                        log.debug("ElasticHostsPriceEstimator - computeResourcesPrice - Storage{} with Storage Price {}", storage, storagePriceForQuantity);

                        price = (ramPriceForQuantity + storagePriceForQuantity + cpuPriceForQuantity);

                    } else
                        log.error("ElasticHostsPriceEstimator - computeResourcesPrice- The location {} does not exist. The price will be not computed!", location.getName());

                } else
                    log.error("ElasticHostsPriceEstimator - computeResourcesPrice- The location is not selected. The price will be not computed!");
            } else
                log.error("ElasticHostsPriceEstimator - computeResourcesPrice- The location feature group does not exist. The price will be not computed!");
        } else
            log.error("ElasticHostsPriceEstimator - computeResourcesPrice- The virtual machine feature does not exist. The price will be not computed!");

        return price;
    }

    @Override
    public void reset() {
        this.locationsMap = new HashMap<>();
    }

}
