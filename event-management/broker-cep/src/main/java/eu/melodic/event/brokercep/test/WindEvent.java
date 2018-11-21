/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.test;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter @ToString
@EqualsAndHashCode
public class WindEvent implements Serializable {
	private final int speed;
	private final int direction;
	private final Date timeOfReading;
	
	public WindEvent() {
		this(new Random().nextInt(8)+2, new Random().nextInt(60)+230, new Date());
	}
}