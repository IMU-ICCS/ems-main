/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.application;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Required Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getFeature <em>Feature</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getProvidedBy <em>Provided By</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.RequiredFeature#isRemote <em>Remote</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.RequiredFeature#isOptional <em>Optional</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.RequiredFeature#isContaiment <em>Contaiment</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getRequiredFeature()
 * 
 * @extends CDOObject
 * 
 */
public interface RequiredFeature extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' attribute.
	 * @see #setFeature(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getRequiredFeature_Feature()
	 *  required="true"
	 * 
	 */
	String getFeature();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getFeature <em>Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' attribute.
	 * @see #getFeature()
	 * 
	 */
	void setFeature(String value);

	/**
	 * Returns the value of the '<em><b>Provided By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided By</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided By</em>' reference.
	 * @see #setProvidedBy(CloudMLElementUpperware)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getRequiredFeature_ProvidedBy()
	 *  required="true"
	 * 
	 */
	CloudMLElementUpperware getProvidedBy();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getProvidedBy <em>Provided By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided By</em>' reference.
	 * @see #getProvidedBy()
	 * 
	 */
	void setProvidedBy(CloudMLElementUpperware value);

	/**
	 * Returns the value of the '<em><b>Remote</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remote</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote</em>' attribute.
	 * @see #setRemote(boolean)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getRequiredFeature_Remote()
	 *  required="true"
	 * 
	 */
	boolean isRemote();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#isRemote <em>Remote</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remote</em>' attribute.
	 * @see #isRemote()
	 * 
	 */
	void setRemote(boolean value);

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see #setOptional(boolean)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getRequiredFeature_Optional()
	 *  required="true"
	 * 
	 */
	boolean isOptional();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#isOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see #isOptional()
	 * 
	 */
	void setOptional(boolean value);

	/**
	 * Returns the value of the '<em><b>Contaiment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contaiment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contaiment</em>' attribute.
	 * @see #setContaiment(boolean)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getRequiredFeature_Contaiment()
	 *  required="true"
	 * 
	 */
	boolean isContaiment();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#isContaiment <em>Contaiment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contaiment</em>' attribute.
	 * @see #isContaiment()
	 * 
	 */
	void setContaiment(boolean value);

} // RequiredFeature
