package eu.paasage.upperware.profiler.rp.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import eu.paasage.camel.CamelModel;
import eu.paasage.upperware.cp.cloner.CDOClientExtended;
import eu.paasage.upperware.cp.cloner.CPCloner;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.rp.RuleProcessor;

/**
 * 
 * @author hopped
 */
public class Utilities {

	/** Logger set to RuleProcessor.class */
	private static Logger log = Logger.getLogger(RuleProcessor.class);

	/**
	 * Prints auto-generate usage information on the command-line.
	 * 
	 * @param options
	 */
	private static void printHelp(Option... options) {
		HelpFormatter formatter = new HelpFormatter();

		Options aggregatedOptions = new Options();
		for (Option option : options) {
			if (option != null) {
				aggregatedOptions.addOption(option);
			}
		}

		formatter.printHelp("RuleProcessor", aggregatedOptions);
	}

	/**
	 * Parses given arguments, which are returned as key-value paris.
	 * 
	 * @param args
	 * @return
	 */
	public static Map<String, String> parseArguments(String[] args) {
		Map<String, String> arguments = new HashMap<String, String>();

		log.info("Parsing command line arguments...");
		Options daemonOptions = new Options();
		Option daemon = Option.builder("d").longOpt("daemon")
				.desc("Activate daemon mode (ZeroMQ service)").build();
		daemonOptions.addOption(daemon);

		Option model = null;
		Option cp = null;

		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(daemonOptions, args, true);
			if (cmd.hasOption("d")) {
				arguments.put("d", "daemon");
			} else {
				model = Option.builder("m")
						.required(true)
						.longOpt("model")
						.hasArgs()
						.numberOfArgs(1)
						.argName("model")
						.desc("CAMEL model ID (e.g., mdhf)")
						.build();
				cp = Option.builder("c")
						.required(true)
						.longOpt("cp_model")
						.hasArgs()
						.numberOfArgs(1)
						.argName("cp_model")
						.desc("CP model ID (e.g., upperware-models/MDPlusHyperflow1447237505755")
						.build();

				Options cmdOptions = new Options();
				cmdOptions.addOption(model);
				cmdOptions.addOption(cp);
				cmd = parser.parse(cmdOptions, args);
				if (cmd.hasOption("m") && cmd.hasOption("c")) {
					arguments.put("m", cmd.getOptionValue("m"));
					arguments.put("c", cmd.getOptionValue("c"));
					log.info("> INPUT [CAMEL model]: " + cmd.getOptionValue("m"));
					log.info("> INPUT [CP model]: " + cmd.getOptionValue("c"));
				} else {
					printHelp(cp, model);
				}
			}

		} catch (ParseException exp) {
			log.error("Parsing failed, because " + exp.getMessage());
			printHelp(cp, daemon, model);
		}

		return arguments;
	}

	private static boolean isValidCPModel(String cpModel) {
		try {
			CDOClientExtended client = CPCloner.createCDOClient();
			return client.existResource(cpModel);
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean isValidCamelModel(String camelModel) {
		try {
			IDatabaseProxy proxy = CDODatabaseProxy.getInstance();
			CamelModel model = proxy.getCamelModel(camelModel);
			return (model != null) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validateArguments(String camelModel, String cpModel) {
		log.info("Checking parameters ...");
		if (!isValidCamelModel(camelModel)) {
			log.error("> ERROR: " + camelModel + " was not found in the CDO database!");
			return false;
		}
		log.info("> " + camelModel + " is available in the CDO database");

		if (!isValidCPModel(cpModel)) {
			log.error("> ERROR: " + cpModel + " was not found in the CDO database!");
			return false;
		}
		log.info("> " + cpModel + " is accessible through the CDO database");

		return true;
	}
}
