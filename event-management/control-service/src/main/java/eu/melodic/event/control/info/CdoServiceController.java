/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.info;

import eu.melodic.event.control.properties.InfoServiceProperties;
import eu.melodic.event.translate.util.CdoCpModelUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CdoServiceController {

    private final static String MAPPING_PREFIX = "/info/cdo";

    private final InfoServiceProperties properties;
    private final CdoCpModelUtil cdoCpModelUtil;

    @GetMapping(MAPPING_PREFIX)
    public Mono<Map<String,String>> cdoTree(HttpServletRequest request, @RequestParam(defaultValue="true") boolean filter) {
        log.debug("cdoTree(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        Map<String,String> resources = cdoCpModelUtil.getResourceTree(filter);
        log.info("cdoTree(): --- Retrieved resource tree");
        log.debug("cdoTree(): --- Resource tree:\n{}", resources);
        return Mono.just(resources);
    }

    @GetMapping(value = MAPPING_PREFIX + "/**", produces = MediaType.TEXT_XML_VALUE)
    public Mono<String> cdoGet(HttpServletRequest request) {
        String resourceId = StringUtils.removeStart(request.getRequestURI(), request.getContextPath()+MAPPING_PREFIX);
        log.debug("cdoGet(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        log.debug("cdoGet(): --- Resource path: {}", resourceId);
        try {
            String modelStr = cdoCpModelUtil.getResource(resourceId.trim());
            log.info("cdoGet(): --- Retrieved resource: {}", resourceId);
            log.debug("cdoGet(): --- Resource contents:\n{}", modelStr);
            return Mono.just(modelStr);
        } catch (Exception e) {
            log.error("cdoGet(): --- Exception \n", e);
            return Mono.error(e);
        }
    }

    @RequestMapping(value = MAPPING_PREFIX + "/**", consumes = MediaType.TEXT_XML_VALUE, method = {POST, PUT})
    public Mono<String> cdoPostPut(@RequestBody String contentsStr, HttpServletRequest request) {
        String resourceId = StringUtils.removeStart(request.getRequestURI(), request.getContextPath()+MAPPING_PREFIX);
        log.debug("cdoPostPut(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        log.debug("cdoPostPut(): --- Resource path: {} {}", request.getMethod(), resourceId);
        log.debug("cdoPostPut(): --- Resource content:\n{}", contentsStr);
        try {
            CdoCpModelUtil.IMPORT_OP op = request.getMethod().equalsIgnoreCase("POST")
                    ? CdoCpModelUtil.IMPORT_OP.GET_OR_CREATE : CdoCpModelUtil.IMPORT_OP.CREATE;
            cdoCpModelUtil.importResourceFromString(contentsStr, resourceId, op);
            log.info("cdoPostPut(): --- Resource saved: {}", resourceId);
            return Mono.just("OK");
        } catch (Exception e) {
            log.error("cdoPostPut(): --- Exception \n", e);
            return Mono.error(e);
        }
    }

    @DeleteMapping(value = MAPPING_PREFIX + "/**")
    public Mono<String> cdoDelete(HttpServletRequest request, @RequestParam(defaultValue="false") boolean force) {
        String resourceId = StringUtils.removeStart(request.getRequestURI(), request.getContextPath()+MAPPING_PREFIX);
        log.debug("cdoDelete(): --- client: {}:{}", request.getRemoteAddr(), request.getRemotePort());
        log.debug("cdoDelete(): --- Resource path: {}", resourceId);
        try {
            boolean result = cdoCpModelUtil.deleteResource(resourceId, force);
            log.info("cdoDelete(): --- Resource deleted: {}", resourceId);
            return Mono.just( result ? "OK" : "NOT DELETED");
        } catch (Exception e) {
            log.error("cdoDelete(): --- Exception \n", e);
            return Mono.error(e);
        }
    }
}
