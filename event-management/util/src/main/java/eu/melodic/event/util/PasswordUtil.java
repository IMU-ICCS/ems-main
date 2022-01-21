/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.util;

import eu.melodic.event.util.password.AsterisksPasswordEncoder;
import eu.melodic.event.util.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Slf4j
@Service
public class PasswordUtil implements InitializingBean {
    @Value("${password-encoder-class:}")
    private String passwordEncoderClassName;
    private PasswordEncoder passwordEncoder;

    @Override
    public void afterPropertiesSet() {
        log.debug("PasswordUtil: password-encoder-class: {}", passwordEncoderClassName);
        this.setPasswordEncoder(passwordEncoderClassName.trim());
    }

    public String encodePassword(String password) {
        return getPasswordEncoder().encode(password);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder!=null
                ? passwordEncoder : (passwordEncoder=createPasswordEncoder(null));
    }

    public void setPasswordEncoder(PasswordEncoder pe) {
        passwordEncoder = pe;
        log.info("PasswordUtil.setPasswordEncoder(): PasswordEncoder set to: {}", passwordEncoder.getClass().getName());
    }

    public void setPasswordEncoder(String passwordEncoderClassName) {
        setPasswordEncoder(createPasswordEncoder(passwordEncoderClassName));
    }

    public static PasswordEncoder createPasswordEncoder(String passwordEncoderClassName) {
        Supplier<PasswordEncoder> passwordEncoderSupplier = AsterisksPasswordEncoder::new;
        if (StringUtils.isBlank(passwordEncoderClassName)) {
            log.warn("Password encoder class name is empty. Default instance of PasswordEncoder will be created");
            return passwordEncoderSupplier.get();
        }

        try {
            Class<?> passwordEncoderClass = Class.forName(passwordEncoderClassName);
            return (PasswordEncoder) passwordEncoderClass.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            log.warn("Could not instantiate PasswordEncoder instance of {}. Default instance of PasswordEncoder will be created", passwordEncoderClassName);
            return passwordEncoderSupplier.get();
        }
    }
}
