/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.util.Set;

import org.eclipse.emf.cdo.common.id.CDOID;

public interface MetricCollection {
	//Read metric definitions based on their ID & start measuring
	public void readMetrics(Set<CDOID> metricIDs, CDOID execContextId);
	
	//Read updated metric definitions based on their ID & change measurement process
	public void updateMetrics(Set<CDOID> metricIDs, CDOID execContextId);
	
	//Stop measuring metrics mapping to the specific execution context id
	public void deleteMetrics(CDOID execContextId);
	
	//Terminate everything - all execution context & metric handlers plus any other
	//Thread will be terminated
	public void terminate();
}
