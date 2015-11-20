/**
 * 
 */
package eu.paasage.upperware.profiler.rp.algebra;

import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.cp.impl.CpFactoryImpl;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.impl.TypesFactoryImpl;

/**
 * @author hopped
 *
 */
public class ConditionalUtils {

	private static CpFactoryImpl factory = null;
	private static TypesFactoryImpl typesFactory = null;

	static {
		factory = new CpFactoryImpl();
		typesFactory = new TypesFactoryImpl();
	}
	
	public static ComparisonExpression createConstraint(
			String variableName,
			ComparatorEnum comparator,
			int constant
	) {
		Variable instances = factory.createVariable();
		instances.setId(variableName);

		Constant c = factory.createConstant();
		IntegerValueUpperware iv = typesFactory.createIntegerValueUpperware();
		iv.setValue(constant);
		c.setValue(iv);

		ComparisonExpression exp = factory.createComparisonExpression();
		exp.setComparator(comparator);
		exp.setExp1(instances);
		exp.setExp2(c);

		return exp;
	}
	
	public static ComparisonExpression createRelation(
			String leftVariableName,
			ComparatorEnum comparator,
			String rightVariableName
	) {
		Variable left = factory.createVariable();
		left.setId(leftVariableName);

		Variable right = factory.createVariable();
		right.setId(rightVariableName);

		ComparisonExpression exp = factory.createComparisonExpression();
		exp.setComparator(comparator);
		exp.setExp1(left);
		exp.setExp2(right);

		return exp;
	}

}
