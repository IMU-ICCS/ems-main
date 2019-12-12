/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.util;

import eu.passage.upperware.commons.passwords.IdentityPasswordEncoder;
import eu.passage.upperware.commons.passwords.PasswordEncoder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  CredentialsMap is a HashMap with toString() method overidden in order to password encodes entry values.
 *  Used to store credentials
 */
@Slf4j
public class CredentialsMap extends HashMap<String,String> {
    @Getter
    private PasswordEncoder passwordEncoder;

    public CredentialsMap() { this(new IdentityPasswordEncoder()); }
    public CredentialsMap(PasswordEncoder pe) { this.passwordEncoder = pe; }

    public String toString() {
        return entrySet()
                .stream()
                .collect(Collectors.toMap(Entry::getKey, e -> passwordEncoder.encode(e.getValue())))
                .toString();
    }
}
