package eu.melodic.upperware.solvertodeployment.utils;

import camel.core.Feature;

public interface ProviderEnricherService {

    <T extends Feature> void enrich(T feature, String key, String value);

}