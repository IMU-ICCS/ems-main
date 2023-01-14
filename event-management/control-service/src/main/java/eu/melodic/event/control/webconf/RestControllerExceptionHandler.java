/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.webconf;

import eu.melodic.event.util.StrUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        log.info("RestControllerExceptionHandler initialized");
    }

    @ExceptionHandler(Throwable.class)
    private ResponseEntity<ErrorType> handleAnyException(Throwable ex, WebRequest request) {
        log.warn("RestControllerExceptionHandler: EXCEPTION: {}", ex.getMessage());
        log.debug("RestControllerExceptionHandler: EXCEPTION:\n", ex);
        if (ex instanceof RestControllerException subEx) {
            HttpStatus httpStatus = HttpStatus.resolve(subEx.getStatusCode());
            if (httpStatus!=null) {
                // Return exception-specific response
                ErrorType error = new ErrorType(httpStatus, ex);
                return new ResponseEntity<>(error, error.getReason());
            }
        }

        // Return '400 Bad Request' response
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorType error = new ErrorType(httpStatus, ex);
        return new ResponseEntity<>(error, error.getReason());
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class ErrorType {
        private final int status;
        private final HttpStatus reason;
        private final LocalDateTime timestamp = LocalDateTime.now();
        private final String exception;
        private final String message;
        private final String details;

        public ErrorType(HttpStatus httpStatus, Throwable error) {
            status = httpStatus.value();
            reason = httpStatus;
            exception = error.getClass().getSimpleName();
            message = error.getMessage();
            details = StrUtil.exceptionToDetailsString(error);
        }
    }
}