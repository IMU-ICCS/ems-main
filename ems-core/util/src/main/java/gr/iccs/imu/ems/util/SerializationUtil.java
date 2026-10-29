/*
 * Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Base64;

@Slf4j
public class SerializationUtil {
    /**
     * Write an object to Base64 string.
     */
    public static String serializeToString(Object o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (var out = new ObjectOutputStream(baos)) {
            out.writeObject(o);
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * Read the object from Base64 string.
     */
    public static Object deserializeFromString(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        try (var in = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return in.readObject();
        }
    }
}
