package eu.melodic.upperware.adapter.service;

import camel.core.Feature;

public interface CamelEnricherService {

    <T extends Feature> void enrich(T feature, String key, String value);

    <T extends Feature> String fetch(String key, T feature);

}