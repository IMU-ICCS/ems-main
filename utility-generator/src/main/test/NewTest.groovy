/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/


import com.google.common.collect.Lists
import eu.melodic.cloudiator.client.model.NodeCandidate
import eu.melodic.upperware.utilitygenerator.UtilityFunctionType
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExample
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExampleV2
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionWithAbsoluteCost
import eu.melodic.upperware.utilitygenerator.model.Component
import eu.melodic.upperware.utilitygenerator.model.Metric
import eu.melodic.upperware.utilitygenerator.model.MetricType
import spock.lang.Shared
import spock.lang.Specification


class NewTest extends Specification {

  CostUtilityFunction costUtilityFunction
  @Shared
  CostUtilityFunction costUtilityFunction_1 = new CostUtilityFunctionExample(true)
  @Shared
  CostUtilityFunction costUtilityFunction_2 = new CostUtilityFunctionExampleV2(true)
  @Shared
  CostUtilityFunction costUtilityFunctionAbs = new CostUtilityFunctionWithAbsoluteCost(10)

  def "tmp"(){

    setup:

    Metric[] rt = new Metric[1]
    rt[0] = new Metric(MetricType.MAX_RESPONSE_TIME, "",30)
    Metric[] nt = new Metric[1]
    nt[0] = new Metric(MetricType.NOM_RESPONSE_TIME, "",20)
    Metric[] cw = new Metric[1]
    cw[0] = new Metric(MetricType.COST_WEIGHT, "",0.5)
    Metric[] avgrt = new Metric[1]
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",5)

    Component c = new Component(GroovyMock(NodeCandidate), 2)
    c.getNodeCandidate().getPrice() >> 7.0


    Map<MetricType, Metric[]> metrics = new HashMap<>()
    metrics.put(MetricType.MAX_RESPONSE_TIME, rt)
    metrics.put(MetricType.NOM_RESPONSE_TIME, nt)
    metrics.put(MetricType.COST_WEIGHT, cw)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(c), true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    double result = utilityGenerator.evaluate(3)

    then:
    noExceptionThrown()
    System.out.println(result)

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]


  }

}