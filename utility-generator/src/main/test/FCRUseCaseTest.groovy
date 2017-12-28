///* * Copyright (C) 2017 7bulls.com
//*
//* This Source Code Form is subject to the terms of the
//* Mozilla Public License, v. 2.0. If a copy of the MPL
//* was not distributed with this file, You can obtain one at
//* http://mozilla.org/MPL/2.0/.
//*/
//
//
//import com.google.common.collect.Lists
//import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction
//import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExample
//import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExampleV2
//import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionWithAbsoluteCost
//import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluator
//import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluatorFCR
//import eu.melodic.upperware.utilitygenerator.model.Metric
//import eu.melodic.upperware.utilitygenerator.model.MetricType
//import spock.lang.Ignore
//import spock.lang.Shared
//import spock.lang.Specification
//@Ignore
//class FCRUseCaseTest extends Specification {
//
//  VirtualMachine vm1_1inst
//  VirtualMachine vm1_2inst
//  VirtualMachine vm1_3inst
//  VirtualMachine vm2
//  @Shared
//  Collection<VirtualMachine> initialConfiguration
//  @Shared
//  Collection<VirtualMachine> newConfigurationWithLessMachines
//  Collection<VirtualMachine> newConfigurationWithMoreMachines
//  @Shared
//  UtilityFunctionEvaluator evaluator
//  @Shared
//  Map<MetricType, Metric[]> metrics
//
//  CostUtilityFunction costUtilityFunction
//  @Shared
//  CostUtilityFunction costUtilityFunction_1 = new CostUtilityFunctionExample(true)
//  @Shared
//  CostUtilityFunction costUtilityFunction_2 = new CostUtilityFunctionExampleV2(true)
//  @Shared
//  CostUtilityFunction costUtilityFunctionAbs = new CostUtilityFunctionWithAbsoluteCost(10)
//
//  def setup(){
//    vm1_1inst = new VirtualMachine("mBig", 3, 4, 1)
//    vm1_2inst = new VirtualMachine("mBig", 3, 4, 2)
//    vm1_3inst = new VirtualMachine("mBig", 3, 4, 3)
//    vm2 = new VirtualMachine("mXXL", 10, 16, 0)
//
//    initialConfiguration = Lists.newArrayList(vm1_2inst, vm2)
//    newConfigurationWithLessMachines = Lists.newArrayList(vm1_1inst, vm2)
//    newConfigurationWithMoreMachines = Lists.newArrayList(vm1_3inst, vm2)
//
//    metrics = new HashMap<>()
//    metrics.put(MetricType.MAX_RESPONSE_TIME, new Metric[]{new Metric(MetricType.MAX_RESPONSE_TIME, "",30)})
//    metrics.put(MetricType.NOM_RESPONSE_TIME, new Metric[]{new Metric(MetricType.NOM_RESPONSE_TIME, "",20)})
//    metrics.put(MetricType.COST_WEIGHT, new Metric[]{new Metric(MetricType.COST_WEIGHT, "",0.5)})
//
//  }
//
//  def "avg response time=3, more machines"(){
//
//    setup:
//    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, "", 3))
//    evaluator = UtilityFunctionEvaluatorFCR(metrics, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithMoreMachines)
//
//    then:
//    //Out[40]= 0.33334
//    System.out.println(result)
//    result >= 0.28 && result <= 0.38
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
//  }
//
//
//  def "avg response time=3, less machines"(){
//    setup:
//    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, "", 3))
//    evaluator = new UtilityFunctionEvaluatorFCR(metrics, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithLessMachines)
//
//    then:
//    //0.503253
//    System.out.println(result)
//
//    result >= 0.45 && result <= 0.55
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
//  }
//
//  def "avg response time=3, no changes"(){
//    setup:
//    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, "", 3))
//    evaluator = new UtilityFunctionEvaluatorFCR(metrics, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(initialConfiguration)
//
//    then:
//    //0.500068
//    System.out.println(result)
//
//    result >= 0.45 && result <= 0.55
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
//  }
//
//  def "avg response time=25, more machines"(){
//    setup:
//    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, "", 25))
//    evaluator = new UtilityFunctionEvaluatorFCR(metrics, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithMoreMachines)
//
//    then:
//    //0.677073
//    System.out.println(result)
//    result >= 0.62 && result <= 0.72
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
//  }
//
//  def "avg response time=25, less machines"(){
//    setup:
//    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, "", 25))
//    evaluator = new UtilityFunctionEvaluatorFCR(metrics, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithLessMachines)
//
//    then:
//    //0.5
//    System.out.println(result)
//    result >= 0.45 && result <= 0.55
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
//
//  }
//  def "avg response time=25, no changes"(){
//    setup:
//    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, "", 25))
//    evaluator = new UtilityFunctionEvaluatorFCR(metrics, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(initialConfiguration)
//
//    then:
//    //0.837175
//    System.out.println(result)
//    result >= 0.77 && result <= 0.87
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
//  }
//
//  def "avg response time=28, more machines"(){
//    setup:
//    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, "", 28))
//    evaluator = new UtilityFunctionEvaluatorFCR(metrics, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithMoreMachines)
//
//    then:
//    //0.785278
//    System.out.println(result)
//    result >= 0.73 && result <= 0.83
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
//  }
//
//  def "avg response time=28, less machines"(){
//    setup:
//    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, "", 28))
//    evaluator = new UtilityFunctionEvaluatorFCR(metrics, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithLessMachines)
//
//    then:
//    //0.5
//    System.out.println(result)
//    result >= 0.45 && result <= 0.55
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
//
//  }
//  def "avg response time=28, no changes"(){
//    setup:
//    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, "", 28))
//    evaluator = new UtilityFunctionEvaluatorFCR(metrics, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(initialConfiguration)
//
//    then:
//    //0.567346
//    System.out.println(result)
//    result >= 0.51 && result <= 0.61
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
//  }
//
//  def "avg response time=25, zeta = 0.05, more machines"(){
//    setup:
//    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, "", 25))
//    metrics.put(MetricType.COST_WEIGHT, new Metric(MetricType.COST_WEIGHT, "", 0.05))
//    evaluator = new UtilityFunctionEvaluatorFCR(metrics, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithMoreMachines)
//
//    then:
//    //0.686438
//    System.out.println(result)
//    result >= 0.63 && result <= 0.73
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
//  }
//
//  def "avg response time=25, zeta = 0.05, less machines"(){
//    setup:
//    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, "", 25))
//    metrics.put(MetricType.COST_WEIGHT, new Metric(MetricType.COST_WEIGHT, "", 0.05))
//    evaluator = new UtilityFunctionEvaluatorFCR(metrics, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithLessMachines)
//
//    then:
//    //0.05
//    System.out.println(result)
//    result >= 0.0 && result <= 0.1
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
//
//  }
//  def "avg response time=25, zeta = 0.05, no changes"(){
//    setup:
//    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, "", 25))
//    metrics.put(MetricType.COST_WEIGHT, new Metric(MetricType.COST_WEIGHT, "", 0.05))
//    evaluator = new UtilityFunctionEvaluatorFCR(metrics, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(initialConfiguration)
//
//    then:
//    //0.690632
//    System.out.println(result)
//    result >= 0.64 && result <= 0.74
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
//  }
//
//}