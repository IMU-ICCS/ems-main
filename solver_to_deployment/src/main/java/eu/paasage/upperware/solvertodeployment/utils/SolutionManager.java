package eu.paasage.upperware.solvertodeployment.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;
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
import eu.paasage.upperware.metamodel.types.BooleanValueUpperware;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.FloatValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.metamodel.types.StringValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.ValueUpperware;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;
import eu.paasage.upperware.solvertodeployment.lib.SolverToDeployment;

@Slf4j
public class SolutionManager {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	CDOClient _cdoClient=null;
	String _cdoResId;

	PaasageConfiguration paasageConfiguration; // set by loadFromCDO
	ConstraintProblem constraintProblem; // set by loadFromCDO

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public SolutionManager(String cdoResId) {
		_cdoResId = cdoResId;
		_cdoClient = new CDOClient();

		log.debug("Reading CP model from CDO...");
		_cdoClient.registerPackage(TypesPackage.eINSTANCE);
		_cdoClient.registerPackage(CpPackage.eINSTANCE);
		log.debug("Reading CP model from CDO...done");
	}

	public void close() {
		if (_cdoClient!=null) {
			_cdoClient.closeSession();
			_cdoClient=null;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void loadFromCDO(CDOTransaction trans) {
		log.debug("Loading models from CDO...");
		EList<EObject> contentsPC = trans.getResource(_cdoResId).getContents();
		paasageConfiguration = (PaasageConfiguration) contentsPC.get(0);
		constraintProblem = (ConstraintProblem) contentsPC.get(1);
	}

	////////////////////////////////////////////////////////////////////

	private void addEmptySolution(String param) throws S2DException {
		long timestamp;
		if (param.equals("new")) {
			timestamp = System.currentTimeMillis();
			log.info("AddSolution: create timestamp: {}", timestamp);
		} else {
			timestamp = Long.parseLong(param);
			log.info("AddSolution: read timestamp: {}", timestamp);
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

	private void removeSolution(String param) throws S2DException {
		CDOTransaction trans = _cdoClient.openTransaction();

		loadFromCDO(trans);
		EList<Solution> sols = constraintProblem.getSolution();

		// Checks
		if (sols.isEmpty()) {
			log.info("Empty solution -- doing nothing");
			return;
		}
		// Doing the work
		switch(param.toLowerCase()) {
		case "all":
			log.info("Going to remove all solutions -- found: "+sols.size());			
			for(Solution sol : sols) {
				for(VariableValue vv : sol.getVariableValue()) {
					_cdoClient.deleteObject(vv, trans, false);
				}
			}
			_cdoClient.deleteObject(sols.get(0), trans, false);

			break;
		case "last":
			log.info("Going to remove last solution -- found: "+sols.size());
			sols.remove(sols.size()-1);
			break;
		default: // shall be a timestamp
			long timestamp = Long.parseLong(param);
			log.info("Going to remove Solution with timestamp: "+timestamp);
			Solution solToDel= getSolutionByTimestamp(sols, timestamp);
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

	private Solution getSolutionByTimestamp(EList<Solution> sols, long timestamp) {
		return sols.stream().filter(sol -> sol.getTimestamp() == timestamp).findFirst().orElse(null);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void write(Solution sol, String filename) throws S2DException {

		try (Scanner scan = new Scanner(new File(filename))) {
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				String[] res = line.split(" = ");
				String id = res[0];
				String val = res[1];
				log.info("id: {} val: {}", id, val);
				for (VariableValue vv : sol.getVariableValue()) {
					if (id.compareTo(vv.getVariable().getId()) == 0) {
						log.info("=> need to overwrite {} wiht {}", vv.getVariable().getId(), val);
						setValueUpperware(vv.getValue(), val);
						log.info("   new value is: {}", vv.getValue());
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw new S2DException("File of solution not found: "+filename);
		}
	}
	
	private void writeSolution(String param, String filename) throws S2DException {
		CDOTransaction trans = _cdoClient.openTransaction();

		loadFromCDO(trans);
		EList<Solution> sols = constraintProblem.getSolution();
		Solution solToWrite=null;

		// Checks
		if (sols.isEmpty()) {
			log.info("Empty solution -- doing nothing");
			return;
		}
		// Doing the work
		switch (param.toLowerCase()) {
			case "first":
				log.info("Going to write the first solution -- found: {}", sols.size());
				solToWrite = sols.get(0);
				break;
			case "last":
				log.info("Going to write the last solution -- found: {}", sols.size());
				solToWrite = sols.get(sols.size() - 1);
				break;
			default: // shall be a timestamp
				long timestamp = Long.parseLong(param);
				log.info("Going to write Solution with timestamp: {}", timestamp);
				solToWrite = getSolutionByTimestamp(sols, timestamp);
		}

		this.write(solToWrite, filename);

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

	private void dump(Solution sol, String filename) throws S2DException {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)))) {
			for(VariableValue vv : sol.getVariableValue()) {
				writer.write(vv.getVariable().getId()+" = "+getValueUpperware(vv.getValue())+"\n");
			}
		} catch (IOException e) {
			throw new S2DException("Error when dumping to file "+filename);
		}
	}
	
	private void dumpSolution(String param, String filename) throws S2DException {
		CDOTransaction trans = _cdoClient.openTransaction();

		loadFromCDO(trans);
		EList<Solution> sols = constraintProblem.getSolution();
		Solution solToDump=null;

		// Checks
		if (sols.isEmpty()) {
			log.info("Empty solution -- doing nothing");
			return;
		}
		// Doing the work
		switch (param.toLowerCase()) {
			case "first":
				log.info("Going to dump the first solution -- found: " + sols.size());
				solToDump = sols.get(0);
				break;
			case "last":
				log.info("Going to dump the last solution -- found: " + sols.size());
				solToDump = sols.get(sols.size() - 1);
				break;
			default: // shall be a timestamp
				long timestamp = Long.parseLong(param);
				log.info("Going to dump Solution with timestamp: " + timestamp);
				solToDump = getSolutionByTimestamp(sols, timestamp);
		}

		this.dump(solToDump, filename);

		log.info("Closing CDO transaction & Client...");
		trans.close();

	}
	
	////////////////////////////////////////////////////////////////////

	final int LIST_MAX_LEVEL=3;

	private String getValueUpperware(ValueUpperware val) throws S2DException {
		String res;
		if      (val instanceof StringValueUpperware)  res = ((StringValueUpperware) val).getValue();
		else if (val instanceof BooleanValueUpperware) res = Boolean.toString(((BooleanValueUpperware) val).isValue());
		else if (val instanceof IntegerValueUpperware) res = Integer.toString(((IntegerValueUpperware) val).getValue());
		else if (val instanceof LongValueUpperware)    res = Long.toString   (((LongValueUpperware)    val).getValue());
		else if (val instanceof FloatValueUpperware)   res = Float.toString  (((FloatValueUpperware)   val).getValue());
		else if (val instanceof DoubleValueUpperware)  res = Double.toString (((DoubleValueUpperware)  val).getValue());
		else throw new S2DException("Unsupported NumericValueUpperware type: "+val);
		return res;
	}
	
	private void setValueUpperware(ValueUpperware val, String value) throws S2DException {
		if      (val instanceof StringValueUpperware)  ((StringValueUpperware) val).setValue(value);
		else if (val instanceof BooleanValueUpperware) ((BooleanValueUpperware) val).setValue(Boolean.valueOf(value));
		else if (val instanceof IntegerValueUpperware) ((IntegerValueUpperware) val).setValue(Integer.valueOf(value));
		else if (val instanceof LongValueUpperware)    ((LongValueUpperware)    val).setValue(Long.valueOf(value));
		else if (val instanceof FloatValueUpperware)   ((FloatValueUpperware)   val).setValue(Float.valueOf(value));
		else if (val instanceof DoubleValueUpperware)  ((DoubleValueUpperware)  val).setValue(Double.valueOf(value));
		else throw new S2DException("Unsupported NumericValueUpperware type: "+val);
	}

	////////////////////////////////////////////////////////////////////

	private void listSolution(Solution sol, int level) throws S2DException {
		System.out.println("  Solution timestamp: "+sol.getTimestamp()+" #VariableValue: "+sol.getVariableValue().size()+" #MetricVariableValue: "+sol.getMetricVariableValue().size());
		if (level>0) {
			System.out.println("    #VariableValue: "+sol.getVariableValue().size());
			if (level>1)
				for(VariableValue vv : sol.getVariableValue()) {
					System.out.println("    "+vv.getVariable().getId()+" = "+getValueUpperware(vv.getValue()));
				}
			System.out.println("    #MetricVariableValue: "+sol.getMetricVariableValue().size());
			if (level>1){
				for(MetricVariableValue mvv : sol.getMetricVariableValue()) {
					System.out.println("    "+mvv.getVariable().getId()+" = "+this.getValueUpperware(mvv.getValue()));
				}
			}
		}
	}

	private void listSolutions(String slevel) throws S2DException {
		int level = Integer.parseInt(slevel);
		if ((level<0)||(level>LIST_MAX_LEVEL)) {
			log.error("List level has to be between 0 and "+LIST_MAX_LEVEL);
		}

		CDOTransaction trans = _cdoClient.openTransaction();

		loadFromCDO(trans);
		EList<Solution> sols = constraintProblem.getSolution();

		log.info("Listing #Solution: "+sols.size());

		for(Solution sol : sols){
			listSolution(sol, level);
		}

		trans.close();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private enum SOLUTION_MANAGER_ARGS_CMD { DEFAULT, LIST, ADD, DEL, DUMPFILE, _DUMPFILE, WRITE, _WRITE};

	private static void usage() {
//		System.err.println("Usage: SolutionManager CPid [add timeStamp / new ]* [del timeStamp / all / last]* [list level (0-2)]* [ls == list 2]*");
		System.err.println("Usage: SolutionManager CPid [add timeStamp / new ]* [list level (0-2)]* [ls == list 2]*");
		System.err.println("Usage: SolutionManager CPid [dump <timeStamp/first/last> filename (create a file that contains current solutio)]");
		System.err.println("Usage: SolutionManager CPid [write <timeStamp/first/last> filename (filename: each line is in the form of 'variable name' = 'value' -- without quotes)]");
		System.exit(-1);
		
	}
	
	public static void main(String args[]) {
		if (args.length<2) {
			usage();
		}

		String paasageConfigurationID = args[0];		
		SolutionManager gen = new SolutionManager(paasageConfigurationID);

		try {
			String _tmp=null;
			SOLUTION_MANAGER_ARGS_CMD next_op=SOLUTION_MANAGER_ARGS_CMD.DEFAULT;
			for(int i=1; i<args.length; i++) {
				String a = args[i].toLowerCase();
				log.info("arg: "+a);
				switch (next_op) {
					case LIST:
						next_op = SOLUTION_MANAGER_ARGS_CMD.DEFAULT;
						gen.listSolutions(a);
						break;
					case ADD:
						next_op = SOLUTION_MANAGER_ARGS_CMD.DEFAULT;
						gen.addEmptySolution(a);
						break;
					case DEL:
						next_op = SOLUTION_MANAGER_ARGS_CMD.DEFAULT;
						gen.removeSolution(a);
						break;
					case DUMPFILE:
						next_op = SOLUTION_MANAGER_ARGS_CMD._DUMPFILE;
						_tmp = a;
						break;
					case _DUMPFILE:
						next_op = SOLUTION_MANAGER_ARGS_CMD.DEFAULT;
						gen.dumpSolution(_tmp, a);
						break;
					case WRITE:
						next_op = SOLUTION_MANAGER_ARGS_CMD._WRITE;
						_tmp = a;
						break;
					case _WRITE:
						next_op = SOLUTION_MANAGER_ARGS_CMD.DEFAULT;
						gen.writeSolution(_tmp, a);
						break;

					default:
						if (a.equals("list")) next_op = SOLUTION_MANAGER_ARGS_CMD.LIST;
						else if (a.equals("ls")) gen.listSolutions("2"); // shortcut
						else if (a.equals("add")) next_op = SOLUTION_MANAGER_ARGS_CMD.ADD;
//					else if (a.equals("del")) next_op = SOLUTION_MANAGER_ARGS_CMD.DEL;
						else if (a.equals("dump")) next_op = SOLUTION_MANAGER_ARGS_CMD.DUMPFILE;
						else if (a.equals("write")) next_op = SOLUTION_MANAGER_ARGS_CMD.WRITE;
						else {
							log.error("Unknown parameter: " + a);
							gen.close();
							System.exit(-2);
						}
				}
			}
			if (next_op!=SOLUTION_MANAGER_ARGS_CMD.DEFAULT) // something gone wrong;
				usage();

		} catch (S2DException e) {
			log.error("Got an S2D Exception: "+e.getMessage());
			System.exit(-1);
		}
		
		gen.close();
		log.info("All done.");
	}
}
