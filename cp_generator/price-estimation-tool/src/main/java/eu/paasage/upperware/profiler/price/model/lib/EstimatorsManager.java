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

import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.profiler.price.api.IProviderPriceEstimator;
import eu.paasage.upperware.profiler.price.tools.ProviderModelTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
@Slf4j
public class EstimatorsManager {

    private List<IProviderPriceEstimator> estimators;

    private Map<String, IProviderPriceEstimator> usedEstimators = new HashMap<>();

    @Autowired
    public EstimatorsManager(List<IProviderPriceEstimator> estimators) throws IOException {
        this.estimators = estimators;
        initEstimator();
    }

    private void loadPriceEstimators(InputStream cloudRatesFile) {

        if (cloudRatesFile != null) {

            try (BufferedReader br = new BufferedReader(new InputStreamReader(cloudRatesFile))) {
                String line = br.readLine();
                while (line != null) {
                    log.debug("EstimatorsManager - loadPriceEstimators - processing line {}", line);

                    //Provider;class-computing-pricing;number-of-locations
                    String[] infos = line.split(ProviderModelTool.LINE_INFOS_SEPARATOR);

                    log.debug("EstimatorsManager - loadPriceEstimators - infos length {}", infos.length);

                    if (infos.length == 3) {
                        String provider = infos[0];
                        String className = infos[1];
                        int numberOfLocations = Integer.parseInt(infos[2]);

                        Class clazz = Class.forName(className);

                        Optional<IProviderPriceEstimator> estimatorOpt = estimators.stream().filter(clazz::isInstance).findFirst();
                        if (estimatorOpt.isPresent()){
                            IProviderPriceEstimator estimator = estimatorOpt.get();
                            estimator.reset();

                            for (int i = 0; i < numberOfLocations; i++)
                                estimator.loadLocationRates(br);

                            usedEstimators.put(provider, estimator);
                        } else {
                            log.error("Price estimator for {} could not be found", clazz.getName());
                        }

                        line = br.readLine();
                    } else {
                        log.error("EstimatorsManager - loadPriceEstimators - The line " + line + " does not have the correct format. The Estimators will be not loaded!");
                        line = null;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else
            log.error("EstimatorsManager - loadPriceEstimators - The file with rates does not exist. The Estimators will be not loaded!");

    }

    private void initEstimator() throws IOException {
        String configDir = System.getenv("MELODIC_CONFIG_DIR");
        FileSystemResource fileSystemResource = new FileSystemResource(configDir + "/cloudPricing.txt");

        try (InputStream inputStream = fileSystemResource.getInputStream()) {
            loadPriceEstimators(inputStream);
        }
    }

    public double estimatePrice(ProviderModel configuration, Variable variable) {

        String key = configuration.getRootFeature().getName();
        IProviderPriceEstimator estimator = usedEstimators.get(key);

        double price = estimate(estimator, () -> estimator.estimatePrice(configuration, variable),
                s -> log.error("EstimatorsManager - estimatePricePerYear - There is not estimator for the {} provider!"), key);
        return price;
    }

    private double estimate(IProviderPriceEstimator estimator, Supplier<Double> priceSupplier,
                            Consumer<String> logConsumer, String key){
        double price = 0;
        if (estimator != null)
            price = priceSupplier.get();
        else {
            logConsumer.accept(key);
        }
        return price;
    }

}


