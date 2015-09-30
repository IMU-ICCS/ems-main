# MILP Solver

MILP solver performs optimization of given CP problem using mixed integer linear solver. Only subset of CP is supported. The data solver is working on is fetched from and stored in CDO server.

## Requirements

* [Scala 2.11](http://www.scala-lang.org)
* [SBT](http://www.scala-sbt.org)
* [CMPL](http://www.coliop.org/download.html) (includes Cbc solver) installed
* Maven dependencies (available in either in public or PaaSage repositories, see [build.sbt](build.sbt))

## Building

To build standalone JAR to `target/scala-2.11/milp-solver-assembly-2015.04-SNAPSHOT.jar`:

```shell
$ sbt assembly
```

## Configuration

There are no options specific to the component.

CDO server host and port should be set in `$PAASAGE_CONFIG_DIR/eu.paasage.cdo.client.properties`


## Usage

Component provides command line interface:

```shell
$ java -jar milp-solver-assembly-2015.04-SNAPSHOT.jar resourceName
```

**Input:** MILP solver reads CP problem directly from CDO resource. `resourceName` in CDO is a two element list. The first element is CAMEL model, the second is CP problem.

**Output**: Solver adds new solution to the CP problem stored in CDO. CAMEL model is not modified.


## Helpers

In addition to it's main function it also provides debugging/helper tools:

1. **File-based interface** ([example input](examples/PaaSageConfiguration1ConstraintProblem.xmi))
  `$ java -cp milp-solver-assembly-2015.04-SNAPSHOT.jar eu.paasage.upperware.milp_solver.exec.MainFile inFile.xmi outFile.xmi`
1. **CDO to/from file**
  * Read CP from XMI file and store it in CDO: `$ java -cp milp-solver-assembly-2015.04-SNAPSHOT.jar eu.paasage.upperware.milp_solver.exec.StoreCp resourceName inFile.xmi`
  * Retreive CP from CDO and save it in XMI file: `$ java -cp milp-solver-assembly-2015.04-SNAPSHOT.jar eu.paasage.upperware.milp_solver.exec.RetrieveCp resourceName outFile.xmi`
1. **Display CP in human readable format**
  `$ java -cp milp-solver-assembly-2015.04-SNAPSHOT.jar eu.paasage.upperware.milp_solver.exec.HumanCP resourceName`
1. **Display solutions stored in CDO**
  `$ java -cp milp-solver-assembly-2015.04-SNAPSHOT.jar eu.paasage.upperware.milp_solver.exec.ShowVars resourceName`


## Limitations

* No (yet) metrics support
* `ListDomain` and `MultiRangeDomain` variable domains are NOT supported (and will not be).
* Non-linear expressions in general are not supported. In some cases it may be accepted if they can be automatically reformulated by CMPL (see CMPL Manual section 2.7), this applies in particular to integer and binary variable. Reformulation involves increasing number of internal variables to back-end solver, so it may not be very efficient to rely on it.

