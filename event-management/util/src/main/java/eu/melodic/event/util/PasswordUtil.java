/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.util;

import eu.passage.upperware.commons.passwords.IdentityPasswordEncoder;
import eu.passage.upperware.commons.passwords.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@Slf4j
public class PasswordUtil implements InitializingBean {
    @Value("${control.password-encoder}")
    private String passwordEncoderClassName;
    private PasswordEncoder passwordEncoder;

    @Override
    public void afterPropertiesSet() {
        log.debug("PasswordUtil: password-encoder-class: {}", passwordEncoderClassName);
        if (passwordEncoderClassName!=null) {
            this.setPasswordEncoder(passwordEncoderClassName);
        }
    }

    public String encodePassword(String password) {
        return getPasswordEncoder().encode(password);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder pe) {
        passwordEncoder = pe;
        log.info("PasswordUtil.setPasswordEncoder(): PasswordEncoder set to: {}", passwordEncoder.getClass().getName());
    }

    public void setPasswordEncoder(String passwordEncoderClassName) {
        setPasswordEncoder(createPasswordEncoder(passwordEncoderClassName));
    }

    public PasswordEncoder createPasswordEncoder(String passwordEncoderClassName) {
        Supplier<PasswordEncoder> passwordEncoderSupplier = IdentityPasswordEncoder::new;
        if (StringUtils.isBlank(passwordEncoderClassName)) {
            log.info("Password encoder class name is empty. Default instance of PasswordEncoder will be created");
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
