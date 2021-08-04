/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.info;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InfoServiceController {

    private static final int DEFAULT_STREAM_INTERVAL = 30;      // in seconds
    private static final String EVENT_STREAM_NAME = "ems-metrics-event";

    private final IEmsInfoService emsInfoService;

    @GetMapping("/info/metrics/get")
    public Mono<Map<String,Object>> metricsGet(HttpServletRequest request) {
        log.info("metricsGet(): --- client: {}:{}, user: {}",
                request.getRemoteAddr(), request.getRemotePort(), request.getRemoteUser());
        Map<String,Object> message = createMetricsResult(null, -1L);
        log.debug("metricsGet(): message={}", message);
        return Mono.just(message);
    }

    @GetMapping("/info/metrics/stream")
    public Flux<ServerSentEvent<Map<String,Object>>> metricsStream(@QueryParam("interval") Optional<Integer> interval,
                                                                   HttpServletRequest request)
    {
        String sid = UUID.randomUUID().toString();
        log.info("metricsStream(): interval={} --- client: {}:{}, user: {}, Stream-Id: {}",
                interval, request.getRemoteAddr(), request.getRemotePort(), request.getRemoteUser(), sid);
        int intervalInSeconds = interval.orElse(-1);
        if (intervalInSeconds<1) intervalInSeconds = DEFAULT_STREAM_INTERVAL;
        log.debug("metricsStream(): effective-interval={}", intervalInSeconds);

        return Flux.interval(Duration.ofSeconds(intervalInSeconds))
                .onBackpressureDrop()
                .map(sequence -> {
                    Map<String,Object> message = createMetricsResult(sid, sequence);
                    log.debug("metricsStream(): seq={}, id={}, message={}", sequence, sid, message);
                    return ServerSentEvent.<Map<String,Object>> builder()
                            .id(String.valueOf(sequence))
                            .event(EVENT_STREAM_NAME)
                            .data(message)
                            .build();
                });
    }

    public Map<String,Object> createMetricsResult(String sid, long sequence) {
        Map<String, Object> metrics = emsInfoService.getMetricValues();
        metrics.put(".stream-id", sid);
        metrics.put(".sequence", sequence);
        log.trace("createMetricsResult: {}", metrics);
        return metrics;
    }
}
