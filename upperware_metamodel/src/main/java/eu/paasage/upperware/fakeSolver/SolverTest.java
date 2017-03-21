package eu.paasage.upperware.fakeSolver;

public class SolverTest {
	public static void main(String[] args) {
		// create a solver
		Solver fakesolver = new Solver("src/main/resources/models/SimpleConfiguration.xmi", "src/main/resources/models/SimpleConfigurationConstraintProblem.xmi");
		
        fakesolver.solve("src/main/resources/models/SimpleConfigurationSolution.xmi");
        
	}

}
