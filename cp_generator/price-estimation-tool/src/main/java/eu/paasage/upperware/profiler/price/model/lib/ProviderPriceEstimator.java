package eu.paasage.upperware.profiler.price.model.lib;

import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.profiler.price.api.IProviderPriceEstimator;
import eu.paasage.upperware.profiler.price.tools.ProviderModelTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public abstract class ProviderPriceEstimator implements IProviderPriceEstimator {

    public double estimatePrice(ProviderModel fm, Variable variable) {
        double price = 0;
        Feature vm = ProviderModelTool.getFeatureByFirstName(fm, ProviderModelTool.VIRTUAL_MACHINE_FEATURE, ProviderModelTool.VIRTUAL_MACHINE_FEATURE_ALT);
        if (vm != null) {
            price = computeVmsPrice(vm, fm, variable);
        }
        return price;
    }

    protected abstract double computeVmsPrice(Feature vm, ProviderModel fm, Variable variable);

    protected <T> T getValue(Map<String, T> map, String key, T defaultValue, String missingLogStr) {
        if (!map.containsKey(key)) {
            log.warn(missingLogStr, key);
        }

        return map.getOrDefault(key, defaultValue);
    }

}
