/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;

import java.io.PrintStream;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class LogPrintStream extends PrintStream {
    private Level logLevel;
    private String name;
    private AtomicBoolean firstCall = new AtomicBoolean(true);

    public LogPrintStream(PrintStream stream, Level level, String name) {
        super(stream);
        if (level==null) throw new IllegalArgumentException("Level cannot be null");
        this.logLevel = level;
        this.name = name;
    }

    @Override
    public void print(String s) {
        logStr(s);
        super.print(s);
    }

    @Override
    public void println(String s) {
        logStr(s);
        super.println(s);
    }

    @Override
    public void println() {
        logStr("");
        super.println();
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        String s = String.format(format, args);
        this.print(s);
        return this;
    }

    @Override
    public PrintStream printf(Locale locale, String format, Object... args) {
        String s = String.format(locale, format, args);
        this.print(s);
        return this;
    }

    private void logStr(String s) {
        if (logLevel==Level.TRACE)
            log.trace("{}> {}", name, s);
        else if (logLevel==Level.DEBUG)
            log.debug("{}> {}", name, s);
        else if (logLevel==Level.INFO)
            log.info("{}> {}", name, s);
        else if (logLevel==Level.WARN)
            log.warn("{}> {}", name, s);
        else if (logLevel==Level.ERROR)
            log.error("{}> {}", name, s);
    }
}
