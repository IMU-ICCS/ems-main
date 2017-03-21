/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp;

import java.util.Iterator;
import java.util.UUID;

import org.eclipse.emf.common.util.EList;

import eu.paasage.upperware.loadPaaSageInstance.ConfigurationLoad;
import eu.paasage.upperware.loadPaaSageInstance.ConstraintProblemLoad;
import eu.paasage.upperware.metamodel.application.*;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.cp.impl.*;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.OS;

public class ModelData {
	private String appId_;
	private String fileLoc_;
	private PaasageConfiguration config_;
	private ConstraintProblem cpModel_;

	public ModelData() {
		appId_ = null;
		fileLoc_ = null;
		config_ = null;
		cpModel_ = null;
	}

	public ModelData(String appId) {
		appId_ = appId;
		fileLoc_ = null;
		config_ = null;
		cpModel_ = null;
	}

	public boolean setAppId(String id) {
		if (id == null) {
			return false;
		}

		appId_ = id;
		return true;
	}

	public String getAppId() {
		return appId_;
	}

	public boolean setFileLocation(String loc) {
		if (loc == null) {
			return false;
		}

		fileLoc_ = loc;
		return true;
	}

	public String getFileLocation() {
		return fileLoc_;
	}

	public boolean setPaasageConfiguration(PaasageConfiguration config) {
		if (config == null) {
			return false;
		}

		config_ = config;
		return true;
	}

	public PaasageConfiguration getPaasageConfiguration() {
		return config_;
	}

	public boolean setConstraintProblem(ConstraintProblem cpModel) {
		if (cpModel == null) {
			return false;
		}

		cpModel_ = cpModel;
		return true;
	}

	public ConstraintProblem getConstraintProblem() {
		return cpModel_;
	}

	public void printConstraintProblem() {
		if (cpModel_ == null) {
			System.out.println("MessageData.printConstraintProblem(): Warning - empty CP Model");
			return;
		}

		System.out.println("MessageData.printConstraintProblem(): Printing constraint problems\n");
		Iterator<Variable> varIt = cpModel_.getVariables().iterator();
		while (varIt.hasNext()) {
			Variable v = varIt.next();
			System.out.println("PaaSage variable: " + v.getId());
		}

		System.out.println();
		System.out.println("Goals: ");
		Iterator<Goal> it2 = cpModel_.getGoals().iterator();
		while (it2.hasNext()) {
			Goal g = it2.next();
			System.out.println("* ID: " + g.getId());
			System.out.println("  Type: " + g.getGoalType().getName() + " ("
					+ g.getGoalType().getValue() + ") - literal: "
					+ g.getGoalType().getLiteral());

			// NumericExpression can be a variable, a constant or a composed expression
			Object ce = g.getExpression();
			if (ce instanceof eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl) {
				ComposedExpressionImpl cExp = (ComposedExpressionImpl) ce;
				System.out.println("  Exp Id: " + cExp.getId());
				System.out.print("  -- Composed expressions:");

				Iterator<NumericExpression> ceIt = cExp.getExpressions()
						.iterator();
				while (ceIt.hasNext()) {
					NumericExpression ne = ceIt.next();
					System.out.print(" " + ne.getId());
				}
				System.out.println();
			}
		}

		System.out.println();
		System.out.println("Constants: ");
		Iterator<Constant> it3 = cpModel_.getConstants().iterator();
		while (it3.hasNext()) {
			Constant co = it3.next();
			String val = null;
			int type = co.getType().getValue();
			if (type == BasicTypeEnum.INTEGER_VALUE) {
				val = "" + ((IntegerValueUpperware) co.getValue()).getValue();
			} else if (type == BasicTypeEnum.DOUBLE_VALUE) {
				val = "" + ((DoubleValueUpperware) co.getValue()).getValue();
			}

			System.out.println("* ID: " + co.getId());
			System.out.print("    Value: " + val);
			System.out.print(" -- Type: " + co.getType().getName() + " ("
					+ co.getType().getValue() + ") - literal: "
					+ co.getType().getLiteral());
			System.out.println();
		}

		System.out.println();
		System.out.println("Constraints: ");
		Iterator<ComparisonExpression> it4 = cpModel_.getConstraints()
				.iterator();
		while (it4.hasNext()) {
			ComparisonExpression ce = it4.next();
			System.out.print("* " + ce.getId());
			System.out.print(" = " + ce.getExp1().getId());
			System.out.print(" -- " + ce.getComparator().getName());
			System.out.print(" (" + ce.getComparator().getValue());
			System.out.println(") -- " + ce.getExp2().getId());
		}

		System.out.println();
		System.out.println("Aux Expressions: ");
		Iterator<Expression> it5 = cpModel_.getAuxExpressions().iterator();
		while (it5.hasNext()) {
			Expression ex = it5.next();
			System.out.print("* " + ex.getId());

			if (ex instanceof eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl) {
				ComposedExpressionImpl cExp = (ComposedExpressionImpl) ex;
				System.out
						.println(" has the following variables and/or expressions:");
				System.out.print("  -");
				Iterator<NumericExpression> neIt = cExp.getExpressions()
						.iterator();
				while (neIt.hasNext()) {
					NumericExpression ne = neIt.next();
					System.out.print(" " + ne.getId() + "  ");
				}
				System.out.println();
			} else if (ex instanceof eu.paasage.upperware.metamodel.cp.impl.ConstantImpl) {
				System.out.println(" is a constant expression.");
			}
			System.out.println();
		}
	}

	public void printPaasageConfiguration() {
		if (config_ == null) {
			System.out.println("MessageData.printPaasageConfiguration(): Warning - empty application configuration");
			return;
		}

		System.out.println("MessageData.printPaasageConfiguration(): Display the application configuration\n");

		System.out.println("App ID: " + config_.getId());

		// Lets see what info the application components has
		Iterator<ApplicationComponent> iterator = config_.getComponents().iterator();
		while (iterator.hasNext()) {
			ApplicationComponent comp = iterator.next();
			System.out.println("App Component Name: " + comp.getCloudMLId());

			EList<LocationUpperware> list = comp.getPreferredLocations();
			for (int i = 0; i < list.size(); i++) {
				System.out.println("- preferred location: "
						+ list.get(i).getName());
			}

			EList<VirtualMachineProfile> vmList = comp.getRequiredProfile();
			for (int i = 0; i < vmList.size(); i++) {
				System.out.println("- preferred VM Profile: "
						+ vmList.get(i).getCloudMLId());
			}
		}

		// for variables
		System.out.println();
		Iterator<PaaSageVariable> itVar = config_.getVariables().iterator();
		while (itVar.hasNext()) {
			PaaSageVariable pvar = itVar.next();
			System.out.println("PaaSage variable: " + pvar.getCpVariableId());
		}

		// for providers
		System.out.println();
		Iterator<Provider> it2 = config_.getProviders().iterator();
		while (it2.hasNext()) {
			Provider p = it2.next();
			System.out.println("Cloud provider: " + p.getId());
			System.out.println("- type: " + p.getType().getId());
			System.out.println("- type ID: " + p.getTypeId());
		}

		// for vm profiles
		System.out.println();
		System.out.println("VM Profile:");
		Iterator<VirtualMachineProfile> it3 = config_.getVmProfiles()
				.iterator();
		while (it3.hasNext()) {
			VirtualMachineProfile vmProfile = it3.next();
			int RAM = ((IntegerValueUpperware) vmProfile.getMemory().getValue())
					.getValue();
			System.out.println("- ID = " + vmProfile.getCloudMLId());
			System.out.println("  RAM = " + RAM);
			System.out.println("  num of CPU = "
					+ vmProfile.getCpu().getCores());
			int storage = ((IntegerValueUpperware) vmProfile.getStorage()
					.getValue()).getValue();
			System.out.println("  storage = " + storage);
			
			OS os = vmProfile.getOs();
			if (os != null) {
				System.out.println("  OS = " + os.getName());
				System.out.println("  OS ver = " + os.getVers());
				System.out.println("  OS arch = "
						+ os.getArchitecture().getName() + " ("
						+ os.getArchitecture().getValue()
						+ ") - literal: "
						+ os.getArchitecture().getLiteral());	
			}
			
			System.out.println();
		}

		System.out.println("Goals:");
		Iterator<PaaSageGoal> it4 = config_.getGoals().iterator();
		while (it4.hasNext()) {
			PaaSageGoal goal = it4.next();
			System.out.println("- ID = " + goal.getId());
			System.out.println("- function type ID = "
					+ goal.getFunction().getId());
		}
	}

	public static void main(String[] args) {
		String fileName = "paasageConfigurationModel.xmi";
		String constraint = "cpModel.xmi";
		if (args.length >= 2) {
			fileName = args[0];
			constraint = args[1];
		}
		String id = UUID.randomUUID().toString();
		ModelData data = new ModelData(id);

		// Loading the app config
		ConfigurationLoad loader = new ConfigurationLoad(fileName);
		PaasageConfiguration myApp = loader.load();
		data.setPaasageConfiguration(myApp);

		// Loading the CP Model
		ConstraintProblemLoad cpLoader = new ConstraintProblemLoad(constraint);
		ConstraintProblem cp = cpLoader.load();
		data.setConstraintProblem(cp);

		// Print the results
		data.printPaasageConfiguration();
		System.out.println("------------------------------------------------");
		data.printConstraintProblem();
		System.out.println();
	}
}
