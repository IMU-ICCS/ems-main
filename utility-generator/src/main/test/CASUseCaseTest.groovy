/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/


import com.google.common.collect.Lists
import eu.melodic.cloudiator.client.model.Hardware
import eu.melodic.cloudiator.client.model.NodeCandidate
import eu.melodic.upperware.utilitygenerator.UtilityFunctionType
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionFraction
import eu.melodic.upperware.utilitygenerator.model.Component
import eu.melodic.upperware.utilitygenerator.model.Metric
import eu.melodic.upperware.utilitygenerator.model.MetricType
import spock.lang.Ignore
import spock.lang.Specification

//todo to update
@Ignore
class CASUseCaseTest extends Specification {

  CostUtilityFunction costUtilityFunction

  Map<MetricType, Metric[]> metrics
  Metric[] highRamUsage
  Metric[] mediumRamUsage
  Metric[] smallRamUsage

  Component initialDeployment

  def setup(){

    Hardware h = GroovyMock(Hardware)
    h.getName() >> "t2.medium"
    h.getRam() >> 1024

    NodeCandidate initNC = GroovyMock(NodeCandidate)
    initNC.getHardware() >> h
    initNC.getPrice() >> 2.0

    initialDeployment = new Component(initNC, 3)

    costUtilityFunction = new CostUtilityFunctionFraction()
    metrics = new HashMap<>()

    Metric[] ru = new Metric[1]
    ru[0] = new Metric(MetricType.MAX_RAM_USAGE, "",0.8)
    metrics.put(MetricType.MAX_RAM_USAGE, ru)

    smallRamUsage = new Metric[3]
    smallRamUsage[0] = new Metric(MetricType.RAM_USAGE,"t2.medium", 0.05)
    smallRamUsage[1] = new Metric(MetricType.RAM_USAGE, "t2.medium", 0.01)
    smallRamUsage[2] = new Metric(MetricType.RAM_USAGE,"t2.medium", 0.06)

    mediumRamUsage = new Metric[3]
    mediumRamUsage[0] = new Metric(MetricType.RAM_USAGE, "t2.medium", 0.55)
    mediumRamUsage[1] = new Metric(MetricType.RAM_USAGE,"t2.medium",  0.11)
    mediumRamUsage[2] = new Metric(MetricType.RAM_USAGE,"t2.medium", 0.16)

    highRamUsage = new Metric[3]
    highRamUsage[0] = new Metric(MetricType.RAM_USAGE, "t2.medium", 0.99)
    highRamUsage[1] = new Metric(MetricType.RAM_USAGE, "t2.medium", 0.71)
    highRamUsage[2] = new Metric(MetricType.RAM_USAGE,"t2.medium", 0.86)
  }
  //VAR_cardinality * VAR_ram * METRIC_maxRamUsage >= METRIC_currentRamUsage


  def "small ram usage"(){

    setup:
    metrics.put(MetricType.RAM_USAGE, smallRamUsage)

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment), true, UtilityFunctionType.CAS,
        costUtilityFunction)

    when:
    System.out.println("SMALL RAM USAGE ")
    System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    noExceptionThrown()


    System.out.println("utility = " + result)

    where:
    cardinality << [1,2, 3, 4, 5]


  }


  def "medium ram usage"(){

    setup:
    metrics.put(MetricType.RAM_USAGE, mediumRamUsage)

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment), true, UtilityFunctionType.CAS,
        costUtilityFunction)

    when:
    System.out.println("MEDIUM RAM USAGE ")
    System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    noExceptionThrown()

    System.out.println("utility = " + result)

    where:
    cardinality << [1,2, 3, 4, 5]
  }


  def "high ram usage"(){

    setup:
    metrics.put(MetricType.RAM_USAGE, highRamUsage)

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.CAS, costUtilityFunction)

    when:
    System.out.println("HIGH RAM USAGE ")
    System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    noExceptionThrown()

    System.out.println("utility = " + result)

    where:
    cardinality << [1, 2, 3, 4, 5]
  }

}