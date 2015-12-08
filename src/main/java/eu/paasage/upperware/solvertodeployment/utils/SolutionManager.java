package eu.paasage.upperware.solvertodeployment.utils;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.VariableValue;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;
import eu.paasage.upperware.solvertodeployment.lib.SolverToDeployment;

public class SolutionManager {

	private static Logger log = Logger.getLogger(SolverToDeployment.class);

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	CDOClient _cdoClient=null;
	String _cdoResId;

	PaasageConfiguration paasageConfiguration;
	ConstraintProblem constraintProblem;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public SolutionManager(String cdoResId) {
		_cdoResId = cdoResId;
		_cdoClient = new CDOClient();

		log.debug("Reading CP model from CDO...");
//		_cdoClient.registerPackage(ApplicationPackage.eINSTANCE);
		_cdoClient.registerPackage(TypesPackage.eINSTANCE);
//		_cdoClient.registerPackage(TypesPaasagePackage.eINSTANCE);
		_cdoClient.registerPackage(CpPackage.eINSTANCE);
		log.debug("Reading CP model from CDO...done");
	}

	public void close()
	{
		if (_cdoClient!=null)
		{
			_cdoClient.closeSession();
			_cdoClient=null;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void loadFromCDO(CDOTransaction trans)
	{
		log.debug("Loading models from CDO...");
		EList<EObject> contentsPC = trans.getResource(_cdoResId).getContents();
		paasageConfiguration = (PaasageConfiguration) contentsPC.get(0);
		constraintProblem = (ConstraintProblem) contentsPC.get(1);

	}

	////////////////////////////////////////////////////////////////////

	private void addEmptySolution(String param) throws S2DException
	{

		long timestamp;
		if (param.equals("new")) {
			timestamp = System.currentTimeMillis();
			log.info("AddSolution: create timestamp: "+timestamp);
		} else {
			timestamp = Long.parseLong(param);
			log.info("AddSolution: read timestamp: "+timestamp);
		}

		CDOTransaction trans = _cdoClient.openTransaction();

		loadFromCDO(trans);

		log.debug("Creating new solution with timestamp "+timestamp);
		Solution sol = CpFactory.eINSTANCE.createSolution();
		sol.setTimestamp(timestamp);
		constraintProblem.getSolution().add(sol);

		try {
			log.debug("Commiting a new empty Solution...");
			trans.commit();
		} catch (CommitException e) {
			throw new S2DException("Error when commiting an empty solution to CDO");
		}

		log.debug("Closing CDO transaction...");
		trans.close();		
	}

	////////////////////////////////////////////////////////////////////

	private void removeSolution(String param) throws S2DException
	{
		CDOTransaction trans = _cdoClient.openTransaction();

		loadFromCDO(trans);
		EList<Solution> sols = constraintProblem.getSolution();

		// Checks
		if (sols.isEmpty())
		{
			log.info("Empty solution -- doing nothing");
			return;
		}
		// Doing the work
		switch(param.toLowerCase())
		{
		case "all":
			log.info("Going to remove all solutions -- found: "+sols.size());			
			for(Solution sol : sols)
			{
//				sol.getMetricVariableValue().clear();
				ArrayList<VariableValue> toDel = new ArrayList<VariableValue>();					
				for(VariableValue vv : sol.getVariableValue()) {
					toDel.add(vv);
				}
				for(VariableValue vv : toDel) {
					_cdoClient.deleteObject(vv, trans, false);
					break;
				}
			}
			_cdoClient.deleteObject(sols.get(0), trans, false);
//			sols.clear();
			break;
		case "last":
			log.info("Going to remove last solution -- found: "+sols.size());
			sols.remove(sols.size()-1);
			break;
		default: // shall be a timestamp
			long timestamp = Long.parseLong(param);
			log.info("Going to remove Solution with timestamp: "+timestamp);
			Solution solToDel=null;
			for(Solution sol : sols)
			{
				if (sol.getTimestamp() == timestamp)
				{
					solToDel = sol; break;
				}
			}
			sols.remove(solToDel);
		}

		try {
			log.info("Commiting...");
			trans.commit();
		} catch (CommitException e) {
			throw new S2DException("Error when commiting remove solution to CDO.\n"+e.getMessage());
		}

		log.info("Closing CDO transaction & Client...");
		trans.close();

	}

	////////////////////////////////////////////////////////////////////

	final int LIST_MAX_LEVEL=3;

	private void listSolution(Solution sol, int level)
	{
		log.info("  Solution timestamp: "+sol.getTimestamp()+" #VariableValue: "+sol.getVariableValue().size()+" #MetricVariableValue: "+sol.getMetricVariableValue().size());
		if (level>0) 
		{
			log.info("    #VariableValue: "+sol.getVariableValue().size());
			if (level>1)
				for(VariableValue vv : sol.getVariableValue())
				{
					log.info("    "+vv.getVariable().getId()+" = "+vv.getValue().toString());
				}
			log.info("    #MetricVariableValue: "+sol.getMetricVariableValue().size());
			if (level>1)
				for(MetricVariableValue mvv : sol.getMetricVariableValue())
				{
					log.info("    "+mvv.getVariable().getId()+" = "+mvv.getValue().toString());
				}
		}
	}

	private void listSolutions(String slevel)
	{
		int level = Integer.parseInt(slevel);
		if ((level<0)||(level>LIST_MAX_LEVEL))
		{
			log.fatal("List level has to be between 0 and "+LIST_MAX_LEVEL);
		}

		CDOTransaction trans = _cdoClient.openTransaction();

		loadFromCDO(trans);
		EList<Solution> sols = constraintProblem.getSolution();

		log.info("Listing #Solution: "+sols.size());
		for(Solution sol : sols)
			listSolution(sol, level);


		trans.close();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private enum SOLUTION_MANAGER_ARGS_CMD { DEFAULT, LIST, ADD, DEL};

	private static void usage()
	{
//		System.err.println("Usage: SolutionManager CPid [add timeStamp / new ]* [del timeStamp / all / last]* [list level (0-2)]* [ls == list 2]*");
		System.err.println("Usage: SolutionManager CPid [add timeStamp / new ]* [list level (0-2)]* [ls == list 2]*");
		System.exit(-1);
		
	}
	
	public static void main(String args[])
	{
		if (args.length<2) {
			usage();
		}

		String paasageConfigurationID = args[0];		
		SolutionManager gen = new SolutionManager(paasageConfigurationID);

		try {
			SOLUTION_MANAGER_ARGS_CMD next_op=SOLUTION_MANAGER_ARGS_CMD.DEFAULT;
			for(int i=1; i<args.length; i++) 
			{
				String a = args[i].toLowerCase();
				log.info("arg: "+a);
				switch (next_op) {
				case LIST: next_op = SOLUTION_MANAGER_ARGS_CMD.DEFAULT; gen.listSolutions(a); break;
				case ADD:  next_op = SOLUTION_MANAGER_ARGS_CMD.DEFAULT; gen.addEmptySolution(a);break;
				case DEL:  next_op = SOLUTION_MANAGER_ARGS_CMD.DEFAULT; gen.removeSolution(a);break;

				default:
					if (a.equals("list")) 	  next_op = SOLUTION_MANAGER_ARGS_CMD.LIST;
					else if (a.equals("ls"))  gen.listSolutions("2"); // shortcut
					else if (a.equals("add")) next_op = SOLUTION_MANAGER_ARGS_CMD.ADD;
//					else if (a.equals("del")) next_op = SOLUTION_MANAGER_ARGS_CMD.DEL;
					else {
						log.fatal("Unknown parameter: "+a);
						gen.close();
						System.exit(-2);
					}
				}
			}
			if (next_op!=SOLUTION_MANAGER_ARGS_CMD.DEFAULT) // something gone wrong;
				usage();

		}
		catch (S2DException e)
		{
			log.fatal("Got an S2D Exception: "+e.getMessage());
			System.exit(-1);
		}
		
		gen.close();
		log.info("All done.");
	}
}
