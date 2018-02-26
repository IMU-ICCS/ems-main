package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.profiler.generator.service.camel.NodeCandidatesService;
import io.github.cloudiator.rest.model.Hardware;
import io.github.cloudiator.rest.model.Image;
import io.github.cloudiator.rest.model.NodeCandidate;
import io.github.cloudiator.rest.model.OperatingSystem;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class NodeCandidateServiceImpl implements NodeCandidatesService {

    @Override
    public Pair<Integer, Integer> getRangeForProviders(List<NodeCandidate> nodeCandidates) {
        List<String> collect = getAvalibleProviderNames(nodeCandidates);
        return ImmutablePair.of(0, collect.size());
    }

    @Override
    public Map<String, List<NodeCandidate>> groupByProviders(List<NodeCandidate> nodeCandidates) {
        return nodeCandidates.stream()
                .collect(sortedGroupingBy(nodeCandidate -> nodeCandidate.getHardware().getProviderId()));
    }

    @Override
    public Pair<Long, Long> getRangeForRam(List<NodeCandidate> nodeCandidates) {
        Optional<Long> minValue = getHardwareMinValue(nodeCandidates, Hardware::getRam);
        Optional<Long> maxValue = getHardwareMaxValue(nodeCandidates, Hardware::getRam);
        return ImmutablePair.of(minValue.orElse(0l), maxValue.orElse(0l));
    }

    @Override
    public Pair<Double, Double> getRangeForStorage(List<NodeCandidate> nodeCandidates) {
        Optional<Double> minValue = getHardwareMinValue(nodeCandidates, Hardware::getDisk);
        Optional<Double> maxValue = getHardwareMaxValue(nodeCandidates, Hardware::getDisk);
        return ImmutablePair.of(minValue.orElse(0.0), maxValue.orElse(0.0));
    }

    @Override
    public Pair<Integer, Integer> getRangeForCores(List<NodeCandidate> nodeCandidates) {
        Optional<Integer> minValue = getHardwareMinValue(nodeCandidates, Hardware::getCores);
        Optional<Integer> maxValue = getHardwareMaxValue(nodeCandidates, Hardware::getCores);
        return ImmutablePair.of(minValue.orElse(0), maxValue.orElse(0));
    }

    @Override
    public Pair<Integer, Integer> getRangeForOs(List<NodeCandidate> nodeCandidates) {
        Optional<Integer> minValue = getValueForOsFamily(nodeCandidates, Collectors.minBy(Comparator.naturalOrder()));
        Optional<Integer> maxValue = getValueForOsFamily(nodeCandidates, Collectors.maxBy(Comparator.naturalOrder()));
        return ImmutablePair.of(minValue.orElse(0), maxValue.orElse(0));
    }


    @Override
    public List<Integer> getValuesForProviders(Map<Integer, List<NodeCandidate>> nodeCandidatesMap) {
        return nodeCandidatesMap.keySet().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public List<Long> getValuesForRam(Map<Integer, List<NodeCandidate>> nodeCandidatesMap) {
        return getPossibleValues(nodeCandidatesMap, Hardware::getRam);
    }

    @Override
    public List<Double> getValuesForStorage(Map<Integer, List<NodeCandidate>> nodeCandidatesMap) {
        return getPossibleValues(nodeCandidatesMap, Hardware::getDisk);
    }

    @Override
    public List<Integer> getValuesForCores(Map<Integer, List<NodeCandidate>> nodeCandidatesMap) {
        return getPossibleValues(nodeCandidatesMap, Hardware::getCores);
    }

    @Override
    public List<Integer> getValuesForOsFamily(Map<Integer, List<NodeCandidate>> nodeCandidatesMap) {

        return nodeCandidatesMap.keySet().stream()
                .map(nodeCandidatesMap::get)
                .flatMap(List::stream)
                        .map(NodeCandidate::getImage)
                        .map(Image::getOperatingSystem)
                        .map(OperatingSystem::getOperatingSystemFamily)
                        .map(Enum::ordinal)
                .distinct().sorted().collect(Collectors.toList());
    }


    private Optional<Integer> getValueForOsFamily(List<NodeCandidate> nodeCandidates, Collector<Integer, ?, Optional<Integer>> integerOptionalCollector) {
        return nodeCandidates.stream()
                        .map(NodeCandidate::getImage)
                        .map(Image::getOperatingSystem)
                        .map(OperatingSystem::getOperatingSystemFamily)
                        .map(Enum::ordinal)
                .collect(integerOptionalCollector);
    }

    @Override
    public List<Long> getValuesForRam(List<NodeCandidate> nodeCandidates) {
        return getPossibleValues(nodeCandidates, Hardware::getRam);
    }

    @Override
    public List<Double> getValuesForStorage(List<NodeCandidate> nodeCandidates) {
        return getPossibleValues(nodeCandidates, Hardware::getDisk);
    }

    @Override
    public List<Integer> getValuesForCores(List<NodeCandidate> nodeCandidates) {
        return getPossibleValues(nodeCandidates, Hardware::getCores);
    }

    private List<String> getAvalibleProviderNames(List<NodeCandidate> nodeCandidates) {
        return getPossibleValues(nodeCandidates, Hardware::getProviderId);
    }

    private <T> List<T> getPossibleValues(List<NodeCandidate> nodeCandidates, Function<Hardware, T> mapper){
        return nodeCandidates.stream().map(NodeCandidate::getHardware).map(mapper).distinct().sorted().collect(Collectors.toList());
    }

    private <T> List<T> getPossibleValues(Map<Integer, List<NodeCandidate>> nodeCandidatesMap, Function<Hardware, T> mapper){
        List<NodeCandidate> nodeCandidates = nodeCandidatesMap.keySet().stream().map(nodeCandidatesMap::get).flatMap(Collection::stream).collect(Collectors.toList());
        return getPossibleValues(nodeCandidates, mapper);
    }

    private <T extends Comparable> Optional<T> getHardwareMinValue(Collection<NodeCandidate> collection, Function<Hardware, T> function) {
        return getHardwareValue(collection, function, Collectors.minBy(Comparator.naturalOrder()));
    }

    private <T extends Comparable> Optional<T> getHardwareMaxValue(Collection<NodeCandidate> collection, Function<Hardware, T> function) {
        return getHardwareValue(collection, function, Collectors.maxBy(Comparator.naturalOrder()));
    }

    private <T extends Comparable> Optional<T> getHardwareValue(Collection<NodeCandidate> collection, Function<Hardware, T> function,
                                                                Collector<T, ?, Optional<T>> tOptionalCollector) {
        return collection.stream()
                .map(NodeCandidate::getHardware)
                .map(function)
                .collect(tOptionalCollector);
    }

    private static <T, K extends Comparable<K>> Collector<T, ?, TreeMap<K, List<T>>> sortedGroupingBy(Function<T, K> function) {
        return Collectors.groupingBy(function,
                TreeMap::new, Collectors.toList());
    }

}