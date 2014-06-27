/**
 */
package camel.scalability;

import camel.TimeIntervalUnit;

import java.util.Date;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Schedule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.Schedule#getStart <em>Start</em>}</li>
 *   <li>{@link camel.scalability.Schedule#getEnd <em>End</em>}</li>
 *   <li>{@link camel.scalability.Schedule#getType <em>Type</em>}</li>
 *   <li>{@link camel.scalability.Schedule#getUnit <em>Unit</em>}</li>
 *   <li>{@link camel.scalability.Schedule#getRepetitions <em>Repetitions</em>}</li>
 *   <li>{@link camel.scalability.Schedule#getInterval <em>Interval</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getSchedule()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Schedule_Start_Before_End Schedule_Correct_values'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Schedule_Start_Before_End='\n\t\t\t\t\tcheckStartEndDates(self)' Schedule_Correct_values='\n\t\t\t\t\t(self.type <> ScheduleType::SINGLE_EVENT implies (self.interval > 0 and self.unit <> null) and checkIntervalRepetitions(self)) and ((self.type = ScheduleType::SINGLE_EVENT implies (interval = 0 and start = null and end = null and unit = null)))'"
 * @extends CDOObject
 * @generated
 */
public interface Schedule extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start</em>' attribute.
	 * @see #setStart(Date)
	 * @see camel.scalability.ScalabilityPackage#getSchedule_Start()
	 * @model
	 * @generated
	 */
	Date getStart();

	/**
	 * Sets the value of the '{@link camel.scalability.Schedule#getStart <em>Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start</em>' attribute.
	 * @see #getStart()
	 * @generated
	 */
	void setStart(Date value);

	/**
	 * Returns the value of the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End</em>' attribute.
	 * @see #setEnd(Date)
	 * @see camel.scalability.ScalabilityPackage#getSchedule_End()
	 * @model
	 * @generated
	 */
	Date getEnd();

	/**
	 * Sets the value of the '{@link camel.scalability.Schedule#getEnd <em>End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End</em>' attribute.
	 * @see #getEnd()
	 * @generated
	 */
	void setEnd(Date value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.scalability.ScheduleType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see camel.scalability.ScheduleType
	 * @see #setType(ScheduleType)
	 * @see camel.scalability.ScalabilityPackage#getSchedule_Type()
	 * @model required="true"
	 * @generated
	 */
	ScheduleType getType();

	/**
	 * Sets the value of the '{@link camel.scalability.Schedule#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see camel.scalability.ScheduleType
	 * @see #getType()
	 * @generated
	 */
	void setType(ScheduleType value);

	/**
	 * Returns the value of the '<em><b>Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' reference.
	 * @see #setUnit(TimeIntervalUnit)
	 * @see camel.scalability.ScalabilityPackage#getSchedule_Unit()
	 * @model required="true"
	 * @generated
	 */
	TimeIntervalUnit getUnit();

	/**
	 * Sets the value of the '{@link camel.scalability.Schedule#getUnit <em>Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' reference.
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(TimeIntervalUnit value);

	/**
	 * Returns the value of the '<em><b>Repetitions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repetitions</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repetitions</em>' attribute.
	 * @see #setRepetitions(int)
	 * @see camel.scalability.ScalabilityPackage#getSchedule_Repetitions()
	 * @model
	 * @generated
	 */
	int getRepetitions();

	/**
	 * Sets the value of the '{@link camel.scalability.Schedule#getRepetitions <em>Repetitions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repetitions</em>' attribute.
	 * @see #getRepetitions()
	 * @generated
	 */
	void setRepetitions(int value);

	/**
	 * Returns the value of the '<em><b>Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interval</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interval</em>' attribute.
	 * @see #setInterval(long)
	 * @see camel.scalability.ScalabilityPackage#getSchedule_Interval()
	 * @model required="true"
	 * @generated
	 */
	long getInterval();

	/**
	 * Sets the value of the '{@link camel.scalability.Schedule#getInterval <em>Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interval</em>' attribute.
	 * @see #getInterval()
	 * @generated
	 */
	void setInterval(long value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model thisRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"CHECKING Schedule_Start_Before_End: \" + this + \" \" + this.getStart() + \" \" + this.getEnd()); java.util.Date date1 = this.getStart(); java.util.Date date2 = this.getEnd(); if ((this.getType() != ScheduleType.SINGLE_EVENT) && (date1 == null || date2 == null)) return Boolean.FALSE; if (date1 == null || date2 == null || (date1 != null && date2 != null && date1.before(date2))) return Boolean.TRUE; return Boolean.FALSE;'"
	 * @generated
	 */
	boolean checkStartEndDates(Schedule this_);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model sRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"Schedule_correct_rep: \" + s + \" \" + s.getStart() + \" \" + s.getEnd() + \" \" + s.getInterval() + \" \" + s.getRepetitions());\r\n\t\tDate d1 = s.getStart();\r\n\t\tDate d2 = s.getEnd();\r\n\t\tint reps = s.getRepetitions();\r\n\t\tlong interval = s.getInterval();\r\n\t\tcamel.TimeIntervalUnit unit = s.getUnit();\r\n\t\tdouble diff = d2.getTime()-d1.getTime();\r\n\t\tif (d1 != null && d2 != null && interval != 0){\r\n\t\t\tif (unit.equals(camel.UnitType.SECONDS)){\r\n\t\t\t\tdiff = diff / 1000.0;\r\n\t\t\t}\r\n\t\t\telse if (unit.equals(camel.UnitType.MINUTES)){\r\n\t\t\t\tdiff = diff / (60 * 1000.0);\r\n\t\t\t}\r\n\t\t\telse if (unit.equals(camel.UnitType.HOURS)){\r\n\t\t\t\tdiff = diff / (60 * 60 * 1000.0);\r\n\t\t\t}\r\n\t\t\telse if (unit.equals(camel.UnitType.DAYS)){\r\n\t\t\t\tdiff = diff / (24 * 60 * 60 * 1000.0);\r\n\t\t\t}\r\n\t\t\telse if (unit.equals(camel.UnitType.WEEKS)){\r\n\t\t\t\tdiff = diff / (7 * 24 * 60 * 60 * 1000.0);\r\n\t\t\t}\r\n\t\t\telse if (unit.equals(camel.UnitType.MONTHS)){\r\n\t\t\t\tdiff = diff / (30 * 24 * 60 * 60 * 1000.0);\r\n\t\t\t}\r\n\t\t\tif (diff >= interval){\r\n\t\t\t\tif (reps == 0 || (reps != 0 && reps * interval <= diff)) return Boolean.TRUE;\r\n\t\t\t}\r\n\t\t}\r\n\t\treturn Boolean.FALSE;'"
	 * @generated
	 */
	boolean checkIntervalRepetitions(Schedule s);

} // Schedule
