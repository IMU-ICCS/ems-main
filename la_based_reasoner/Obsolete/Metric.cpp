/*=============================================================================
 Metrics

 The only thing we need to store for the metrics is the global static variable
 indicating whether the changing value should be reported or kept fixed during
 a mathematical algorithm optimisation run.

  Copyright (c) 2014 Geir Horn, Geir.Horn@mn.uio.no
  University of Oslo
  
  Licence:
  This Source Code Form is subject to the terms of the Mozilla Public License, 
  v. 2.0. If a copy of the MPL was not distributed with this file, You can 
  obtain one at http://mozilla.org/MPL/2.0/.
=============================================================================*/

#include "Interface.hpp"

using namespace Solver;

// ===> NOTE: All static variables of the metrics class are defined in the 
// ===> Interface.cpp file
// ===> Please see the explaination in the Interface.hpp file for the 
// ===> metrics...

/*****************************************************************************
 Register metric
 
 In order to receive new metric values, the metric must subscribe to the 
 Metric Collector. This is done from the constructor of the metric after the
 initialisation of the atomic value object.
 
******************************************************************************/

// Currently not implemented - only the registration with the global map of 
// metrices. 

void MetricBase::RegisterMetric( MetricBase * TheMetric )
{
  Metrics.insert( std::pair< MetricsID, MetricBase * >( ID, TheMetric ) );
};