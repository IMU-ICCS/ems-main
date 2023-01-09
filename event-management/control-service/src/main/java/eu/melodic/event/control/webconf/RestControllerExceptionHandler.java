/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.webconf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        log.info("RestControllerExceptionHandler initialized");
    }

    //@ExceptionHandler(value = { Throwable.class, Exception.class, NullPointerException.class })
    protected ResponseEntity<Object> handleMethodArgumentNotValid(RuntimeException ex, WebRequest request) {
        log.error("Returning error response: Invalid request: {}", ex.getMessage());
        log.debug("Returning error response: Invalid request: EXCEPTION:\n", ex);
        String bodyOfResponse = "Invalid request: "+ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}