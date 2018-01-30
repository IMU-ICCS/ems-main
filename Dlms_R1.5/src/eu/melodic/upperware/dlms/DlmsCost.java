package eu.melodic.upperware.dlms;

/*
 * Copyright (C) 2018 Simula.no
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

import java.util.Random;

import eu.melodic.upperware.dlms.CandidateNodeList.NodeList;

public class DlmsCost implements NodeList {
	private int item;
	private DlmsCost next;

	@Override
	public int getItem() {
		return item;
	}

	public void setItem(int si) {
		item = si;
	}

	@Override
	public NodeList nextNode() {
		return this.next;
	}

	public void setNext(DlmsCost n) {
		this.next = n;
	}

	// Now calculating the data related cost
	public double cost(double min, double max) {

		Random random = new Random();
		return (random.nextInt((int) ((max - min) * 10 + 1)) + min * 10) / 10.0;
	}

	public static void main(String[] args) {
		// Initialization block

		DlmsCost dataCost = new DlmsCost();
		double dataMovCost;
		dataMovCost = dataCost.cost(1.0, 9.0);
		System.out.println(dataMovCost);
	}
}