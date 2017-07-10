/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.mddb.cdo.client;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class ContainmentChain {
	private List<EReference> features;
	private List<Integer> positions;
	
	public ContainmentChain(EObject object){
		features = new ArrayList<EReference>();
		positions = new ArrayList<Integer>();
		createChain(object);
	}
	
	public List<EReference> getFeatures(){
		return features;
	}
	
	public List<Integer> getPositions(){
		return positions;
	}
	
	private void createChain(EObject object){
		EObject parent = object.eContainer();
		//System.out.println("Parent is: " + parent);
		if (parent != null){
			EReference ref = object.eContainmentFeature();
			Object o = parent.eGet(ref);
			int position = -2;
			if (o instanceof List){
				List l = (List)o;
				position = l.indexOf(object);
			}
			createChain(parent);
			features.add(ref);
			positions.add(position);
		}
		return;
	}
	
	/*private EReference getRightRef(String name, EObject object){
		for (EReference ref: object.eClass().getEAllReferences()){
			if (ref.getName().equals(name)) return ref;
		}
		return null;
	}*/
	
	public EObject getObjectSubstitute(EObject object){
		int i = 0;
		for (EReference ref: features){
			//System.out.println("Feature is: " + ref + " " + ref.getEReferenceType() + " " + ref.getFeatureID());
			//System.out.println("object has the following content: " + object.eClass().getEAllReferences());
			Object o = null;
			try{
				o = object.eGet(ref);
			}
			catch(Exception e){
				e.printStackTrace();
				//o = object.eGet(getRightRef(ref.getName(),object));
			}
			int pos = positions.get(i);
			if (pos == -2) object = (EObject)o;
			else{
				List l = (List)o;
				if (pos != -1)
					object = (EObject)(l.get(pos));
				else return null;
			}
			i++;
			//System.out.println("Got object: " + object);
		}
		return object;
	}
}
