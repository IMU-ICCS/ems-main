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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InfoServiceController {

    private final ControlServiceProperties properties;
    private final IEmsInfoService emsInfoService;

    @GetMapping("/info/metrics/get")
    public Mono<Map<String,Object>> serverMetricsGet(HttpServletRequest request) {
        log.info("serverMetricsGet(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        Map<String,Object> message = createServerMetricsResult(null, -1L);
        log.debug("serverMetricsGet(): message={}", message);
        return Mono.just(message);
    }

    @GetMapping("/info/metrics/stream")
    public Flux<ServerSentEvent<Map<String,Object>>> serverMetricsStream(
            @QueryParam("interval") Optional<Integer> interval, HttpServletRequest request)
    {
        String sid = UUID.randomUUID().toString();
        log.info("serverMetricsStream(): interval={} --- client: {}:{}, Stream-Id: {}",
                interval, request.getRemoteAddr(), request.getRemotePort(), sid);
        int intervalInSeconds = interval.orElse(-1);
        if (intervalInSeconds<1) intervalInSeconds = properties.getMetricsStreamUpdateInterval();
        log.debug("serverMetricsStream(): effective-interval={}", intervalInSeconds);

        return Flux.interval(Duration.ofSeconds(intervalInSeconds))
                .onBackpressureDrop()
                .map(sequence -> {
                    Map<String,Object> message = createServerMetricsResult(sid, sequence);
                    log.debug("serverMetricsStream(): seq={}, id={}, message={}", sequence, sid, message);
                    return ServerSentEvent.<Map<String,Object>> builder()
                            .id(String.valueOf(sequence))
                            .event(properties.getMetricsStreamEventName())
                            .data(message)
                            .build();
                });
    }

    @GetMapping("/info/metrics/clear")
    public String serverMetricsClear(HttpServletRequest request) {
        log.info("serverMetricsClear(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        emsInfoService.clearServerMetricValues();
        emsInfoService.clearClientMetricValues();
        return "CLEARED-SERVER-METRICS";
    }

    // ------------------------------------------------------------------------

    @GetMapping("/info/client-metrics/get/{clientIds}")
    public Mono<Map<String,Object>> clientMetricsGet(
            @PathVariable("clientIds") List<String> clientIds, HttpServletRequest request)
    {
        log.info("clientMetricsGet(): baguette-client-ids={} --- client: {}:{}", clientIds, request.getRemoteAddr(), request.getRemotePort());
        Map<String,Object> message = createClientMetricsResult(null, -1L, clientIds);
        log.debug("clientMetricsGet(): message={}", message);
        return Mono.just(message);
    }

    @GetMapping("/info/client-metrics/stream/{clientIds}")
    public Flux<ServerSentEvent<Map<String,Object>>> clientMetricsStream(
            @PathVariable("clientIds") List<String> clientIds,
            @QueryParam("interval") Optional<Integer> interval,
            HttpServletRequest request)
    {
        String sid = UUID.randomUUID().toString();
        log.info("clientMetricsStream(): interval={}, baguette-client-ids={} --- client: {}:{}, Stream-Id: {}",
                interval, clientIds, request.getRemoteAddr(), request.getRemotePort(), sid);
        int intervalInSeconds = interval.orElse(-1);
        if (intervalInSeconds<1) intervalInSeconds = properties.getMetricsStreamUpdateInterval();
        log.debug("clientMetricsStream(): effective-interval={}", intervalInSeconds);

        return Flux.interval(Duration.ofSeconds(intervalInSeconds))
                .onBackpressureDrop()
                .map(sequence -> {
                    Map<String,Object> message = createClientMetricsResult(sid, sequence, clientIds);
                    log.debug("clientMetricsStream(): seq={}, id={}, message={}", sequence, sid, message);
                    return ServerSentEvent.<Map<String,Object>> builder()
                            .id(String.valueOf(sequence))
                            .event(properties.getMetricsStreamEventName())
                            .data(message)
                            .build();
                });
    }

    @GetMapping("/info/client-metrics/clear/{clientIds}")
    public String clientMetricsClear(@PathVariable("clientIds") List<String> clientIds, HttpServletRequest request) {
        log.info("clientMetricsClear(): baguette-client-ids={} --- client: {}:{}",
                clientIds, request.getRemoteAddr(), request.getRemotePort());
        emsInfoService.clearClientMetricValues();
        return "CLEARED-CLIENT-METRICS";
    }

    // ------------------------------------------------------------------------

    public Map<String,Object> createServerMetricsResult(String sid, long sequence) {
        log.trace("createServerMetricsResult: BEGIN: sid={}, seq={}", sid, sequence);
        Map<String, Object> metrics = new LinkedHashMap<>(emsInfoService.getServerMetricValues());
        metrics.put(".stream-id", sid);
        metrics.put(".sequence", sequence);
        log.trace("createMetricsResult: {}", metrics);
        log.trace("createServerMetricsResult: END: sid={}, seq={} ==> {}", sid, sequence, metrics);
        return metrics;
    }

    public Map<String,Object> createClientMetricsResult(String sid, long sequence, @NonNull List<String> clientIds) {
        log.trace("createClientMetricsResult: BEGIN: sid={}, seq={}, client-ids={}", sid, sequence, clientIds);
        Map<String, Object> metrics = emsInfoService.getClientMetricValues();
        log.trace("createClientMetricsResult: metrics: {}", metrics);
        if (metrics!=null && clientIds.size()>0 && !clientIds.contains("*")) {
            clientIds = clientIds.stream()
                    .filter(StringUtils::isNotBlank)
                    .map(s->s.startsWith("#") ? s : "#"+s)
                    .collect(Collectors.toList());
            log.trace("createClientMetricsResult(): CLIENT-FILTER: PREPARE: client-ids: {}", clientIds);
            metrics = new LinkedHashMap<>(metrics);
            log.trace("createClientMetricsResult(): CLIENT-FILTER: BEFORE: metrics: {}", metrics);
            metrics.keySet().retainAll(clientIds);
            log.trace("createClientMetricsResult(): CLIENT-FILTER: AFTER: metrics: {}", metrics);
        }
        Map<String,Object> clientMetrics = new LinkedHashMap<>();
        clientMetrics.put("client-metrics", metrics);
        clientMetrics.put(".stream-id", sid);
        clientMetrics.put(".sequence", sequence);
        log.trace("createClientMetricsResult: END: sid={}, seq={} ==> {}", sid, sequence, clientMetrics);
        return clientMetrics;
    }
}
