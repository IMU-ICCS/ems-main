/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.info;

import eu.melodic.event.control.properties.ControlServiceProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InfoServiceController {

    private final ControlServiceProperties properties;
    private final IEmsInfoService emsInfoService;

    @GetMapping("/info/metrics/get")
    public Mono<Map<String,Object>> metricsGet(
            @RequestParam(name="clients", required=false) List<String> clientIds, HttpServletRequest request) {
        log.info("metricsGet(): baguette-client-ids={} --- client: {}:{}", clientIds, request.getRemoteAddr(), request.getRemotePort());
        Map<String,Object> message = getMetricValues(clientIds);
        log.debug("metricsGet(): message={}", message);
        return Mono.just(message);
    }

    public Map<String,Object> getMetricValues(List<String> clientIds) {
        return createMetricsResult(null, -1L, clientIds);
    }

    @GetMapping("/info/metrics/stream")
    public Flux<ServerSentEvent<Map<String,Object>>> metricsStream(
            @QueryParam("interval") Optional<Integer> interval,
            @RequestParam(name="clients", required=false) List<String> clientIds,
            HttpServletRequest request)
    {
        String sid = UUID.randomUUID().toString();
        log.info("metricsStream(): interval={}, baguette-client-ids={} --- client: {}:{}, Stream-Id: {}",
                interval, clientIds, request.getRemoteAddr(), request.getRemotePort(), sid);
        int intervalInSeconds = interval.orElse(-1);
        if (intervalInSeconds<1) intervalInSeconds = properties.getMetricsStreamUpdateInterval();
        log.debug("metricsStream(): effective-interval={}", intervalInSeconds);

        return Flux.interval(Duration.ofSeconds(intervalInSeconds))
                .onBackpressureDrop()
                .map(sequence -> {
                    Map<String,Object> message = createMetricsResult(sid, sequence, clientIds);
                    log.debug("metricsStream(): seq={}, id={}, message={}", sequence, sid, message);
                    return ServerSentEvent.<Map<String,Object>> builder()
                            .id(String.valueOf(sequence))
                            .event(properties.getMetricsStreamEventName())
                            .data(message)
                            .build();
                });
    }

    @GetMapping("/info/metrics/clear")
    public String metricsClear(
            @RequestParam(name="clients", required=false) List<String> clientIds,
            HttpServletRequest request) {
        log.info("metricsClear(): baguette-client-ids={} --- client: {}:{}",
                clientIds, request.getRemoteAddr(), request.getRemotePort());
        emsInfoService.clearMetricValues();
        emsInfoService.clearClientMetricValues();
        return "CLEARED";
    }

    public Map<String,Object> createMetricsResult(String sid, long sequence, List<String> clientIds) {
        Map<String, Object> metrics = emsInfoService.getMetricValues();
        Map<String, Map<String, Object>> clientMetrics = null;
        if (clientIds!=null && clientIds.size()>0) {
            clientMetrics = clientIds.stream()
                    .filter(StringUtils::isNotBlank)
                    .map(id -> new Pair<>(id, emsInfoService.getClientMetricValues(id)))
                    .filter(p -> p.getValue() != null)
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
            metrics.put("client-metrics", clientMetrics);
        }
        metrics.put(".stream-id", sid);
        metrics.put(".sequence", sequence);
        log.trace("createMetricsResult: {}", metrics);
        return metrics;
    }

    @Data
    private static class Pair<T,U> {
        private final T key;
        private final U value;
    }
}
