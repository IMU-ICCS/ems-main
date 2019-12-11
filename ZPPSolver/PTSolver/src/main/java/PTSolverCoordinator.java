public class PTSolverCoordinator {

    public void prepareProblem() {
        // create data
        //TSPData data = new TSPData(dist);
// create objective
        //TSPObjective obj = new TSPObjective();
// create random solution generator
        //RandomSolutionGenerator<TSPSolution, TSPData> rsg = ... // see before

// wrap in generic problem
        //Problem<TSPSolution> problem = new GenericProblem<>(data, obj, rsg);
    }

    public void preparePTSolver() {
        // set temperature range, scaled according to average
// distance between cities and their nearest neighbours
       /* double scale = computeAvgNearestNeighbourDistance(data);
        double minTemp = scale * 1e-8;
        double maxTemp = scale * 0.6;
// create parallel tempering search with TSP neighbourhood
        int numReplicas = 10;
        ParallelTempering<TSPSolution> parallelTempering = new ParallelTempering<>(
                problem,
                new TSP2OptNeighbourhood(),
                numReplicas, minTemp, maxTemp*/

    }
}
