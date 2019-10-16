/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate;

public class CamelToEplTranslationException extends RuntimeException {
    public CamelToEplTranslationException(String message) {
        super(message);
    }

    public CamelToEplTranslationException(Throwable th) {
        super(th);
    }

    public CamelToEplTranslationException(String message, Throwable th) {
        super(message, th);
    }
}
