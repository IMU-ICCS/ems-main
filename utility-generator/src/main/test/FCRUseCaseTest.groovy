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
import eu.melodic.upperware.utilitygenerator.costfunction.*
import eu.melodic.upperware.utilitygenerator.model.Component
import eu.melodic.upperware.utilitygenerator.model.Metric
import eu.melodic.upperware.utilitygenerator.model.MetricType
import spock.lang.Shared
import spock.lang.Specification

class FCRUseCaseTest extends Specification {

  CostUtilityFunction costUtilityFunction

  @Shared
  CostUtilityFunction costUtilityFunction_1 = new CostUtilityFunctionExample(true)
  @Shared
  CostUtilityFunction costUtilityFunction_2 = new CostUtilityFunctionExampleV2(true)
  @Shared
  CostUtilityFunction costUtilityFunctionAbs = new CostUtilityFunctionWithAbsoluteCost(10)
  @Shared
  CostUtilityFunction costUtilityFunctionFraction = new CostUtilityFunctionFraction()

  Map<MetricType, Metric[]> metrics

  Component initialDeployment

  Metric[] avgrt = new Metric[1]

  def setup(){
    metrics = new HashMap<>()

    NodeCandidate initNC = GroovyMock(NodeCandidate)
    initNC.getPrice() >> 2.0

    initialDeployment = new Component(initNC, 3)
    metrics = new HashMap<>()

    Metric[] rt = new Metric[1]
    rt[0] = new Metric(MetricType.MAX_RESPONSE_TIME, "",30)
    metrics.put(MetricType.MAX_RESPONSE_TIME, rt)

    Metric[] nrt = new Metric[1]
    nrt[0] = new Metric(MetricType.NOM_RESPONSE_TIME, "",20)
    metrics.put(MetricType.NOM_RESPONSE_TIME, nrt)

    Metric[] cw = new Metric[1]
    cw[0] = new Metric(MetricType.COST_WEIGHT, "",0.5)
    metrics.put(MetricType.COST_WEIGHT, cw)

  }

  def "avg response time=3, less machines"(){
    setup:
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",3)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    int cardinality = 2

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    //System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    //0.503253
    noExceptionThrown()
    System.out.println("utility = " + result)

    result >= 0.45 && result <= 0.55

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                            costUtilityFunctionAbs, costUtilityFunctionFraction]
  }

  def "avg response time=3, no changes"(){
    setup:
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",3)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    int cardinality = 3

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    //System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    //0.500068
    noExceptionThrown()
    System.out.println("utility = " + result)

    result >= 0.45 && result <= 0.55

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                            costUtilityFunctionAbs, costUtilityFunctionFraction]
  }

  def "avg response time=3, more machines"(){

    setup:
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",3)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    int cardinality = 4

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    //System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    noExceptionThrown()
    System.out.println("utility = " + result)

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                            costUtilityFunctionAbs, costUtilityFunctionFraction]
  }

  def "avg response time=25, less machines"(){
    setup:
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",25)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    int cardinality = 2

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    //System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    //0.5
    noExceptionThrown()
    System.out.println("utility = " + result)
    result >= 0.45 && result <= 0.55

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                            costUtilityFunctionAbs, costUtilityFunctionFraction]

  }

  def "avg response time=25, no changes"(){
    setup:
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",25)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    int cardinality = 3

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    //System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    //0.837175
    noExceptionThrown()
    System.out.println("utility = " + result)
    result >= 0.77 && result <= 0.87

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                            costUtilityFunctionAbs, costUtilityFunctionFraction]
  }



  def "avg response time=25, more machines"(){
    setup:
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",25)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    int cardinality = 4

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    //System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    //0.677073
    noExceptionThrown()
    System.out.println("utility = " + result)
    result >= 0.62 && result <= 0.72

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                            costUtilityFunctionAbs, costUtilityFunctionFraction]
  }


  def "avg response time=28, less machines"(){
    setup:
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",28)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    int cardinality = 2

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    //System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    //0.5
    noExceptionThrown()
    System.out.println("utility = " + result)
    result >= 0.45 && result <= 0.55

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                            costUtilityFunctionAbs, costUtilityFunctionFraction]

  }
  def "avg response time=28, no changes"(){
    setup:
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",28)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    int cardinality = 3

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    //System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    //0.567346
    noExceptionThrown()
    System.out.println("utility = " + result)
    result >= 0.51 && result <= 0.61

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                            costUtilityFunctionAbs, costUtilityFunctionFraction]
  }

  def "avg response time=28, more machines"(){
    setup:
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",28)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    int cardinality = 4

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    //System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    //0.785278
    noExceptionThrown()
    System.out.println("utility = " + result)
    result >= 0.73 && result <= 0.83

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                            costUtilityFunctionAbs, costUtilityFunctionFraction]
  }




  def "avg response time=25, zeta = 0.05, less machines"(){
    setup:
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",25)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    Metric[] cw = new Metric[1]
    cw[0] = new Metric(MetricType.COST_WEIGHT, "", 0.05)
    metrics.put(MetricType.COST_WEIGHT, cw)

    int cardinality = 2

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    //System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    //0.05
    noExceptionThrown()
    System.out.println("utility = " + result)
    result >= 0.0 && result <= 0.1

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                            costUtilityFunctionAbs, costUtilityFunctionFraction]

  }
  def "avg response time=25, zeta = 0.05, no changes"(){
    setup:
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",25)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    Metric[] cw = new Metric[1]
    cw[0] = new Metric(MetricType.COST_WEIGHT, "", 0.05)
    metrics.put(MetricType.COST_WEIGHT, cw)

    int cardinality = 3

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    //System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    //0.690632
    noExceptionThrown()
    System.out.println("utility = " + result)
    result >= 0.64 && result <= 0.74

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                            costUtilityFunctionAbs, costUtilityFunctionFraction]
  }

  def "avg response time=25, zeta = 0.05, more machines"(){
    setup:
    avgrt[0] = new Metric(MetricType.AVG_RESPONSE_TIME, "",25)
    metrics.put(MetricType.AVG_RESPONSE_TIME, avgrt)

    Metric[] cw = new Metric[1]
    cw[0] = new Metric(MetricType.COST_WEIGHT, "", 0.05)
    metrics.put(MetricType.COST_WEIGHT, cw)

    int cardinality = 4

    UtilityGeneratorApplication utilityGenerator =
      new UtilityGeneratorApplication(metrics, Lists.newArrayList(initialDeployment),
        true, UtilityFunctionType.FCR, costUtilityFunction)

    when:
    //System.out.println("CARDINALITY = " + cardinality)
    double result = utilityGenerator.evaluate(cardinality)

    then:
    //0.686438
    noExceptionThrown()
    System.out.println("utility = " + result)
    result >= 0.63 && result <= 0.73

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2,
                            costUtilityFunctionAbs, costUtilityFunctionFraction]
  }

}