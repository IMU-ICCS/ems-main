/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.adapter;

import de.uniulm.omi.cloudiator.colosseum.client.entities.MeasurementWindow;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessor;
import org.ow2.paasage.camel.srl.adapter.utils.Convert;
import eu.paasage.camel.metric.Window;
import eu.paasage.camel.metric.WindowSizeType;

/**
 * Created by Frank on 07.09.2015.
 */
public class WindowsAdapter extends AbstractAdapter {
    private final Window window;

    public WindowsAdapter(CommandLinePropertiesAccessor config, FrontendCommunicator fc, Window window) {
        super(config, fc);
        this.window = window;
    }

    @Override
    public void adapt() {
        /* TODO implement other types of windows, measurement sizes..., etc. */
        if (window.getSizeType().equals(WindowSizeType.TIME_ONLY) || window
                .getSizeType().equals(WindowSizeType.BOTH_MATCH)) {


            logger.info("Save time window to colosseum: " + window.getName());

            getFc().saveTimeWindow(window.getTimeSize(), Convert.toJavaTimeUnit(window.getUnit()));
        } else if (window.getSizeType().equals(WindowSizeType.MEASUREMENTS_ONLY)) {
            MeasurementWindow mw = (MeasurementWindow) window;

            getFc().saveMeasurementWindow(mw.getMeasurements());
        } else {
            throw new RuntimeException("WindowSizeType not implemented!");
        }
    }
}
