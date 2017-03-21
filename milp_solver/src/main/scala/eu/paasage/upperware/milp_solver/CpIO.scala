package eu.paasage.upperware.milp_solver

import eu.paasage.upperware.metamodel.cp.{CpPackage, ConstraintProblem}
import org.eclipse.emf.ecore.resource.{ResourceSet, Resource}
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import java.util.Collections
import java.io.IOException

object CpIO {
  def load(path: String) = {
      val uri = URI.createURI(path);
      CpPackage.eINSTANCE.eClass();
      val reg = Resource.Factory.Registry.INSTANCE;
      val m = reg.getExtensionToFactoryMap();
      m.put("xmi", new XMIResourceFactoryImpl());

      val resSet = new ResourceSetImpl();
      val resource = resSet.getResource(uri, true);

      resource.getContents().get(0).asInstanceOf[ConstraintProblem]
  }

  def store(path: String, cp: ConstraintProblem):Boolean = {
    CpPackage.eINSTANCE.eClass()
    // Register the XMI resource factory for the .xmi extension
    val reg = Resource.Factory.Registry.INSTANCE
    val m = reg.getExtensionToFactoryMap()
    m.put("xmi", new XMIResourceFactoryImpl())

    // Obtain a new resource set
    val resSet = new ResourceSetImpl()

    // create a resource
    val resource = resSet.createResource(URI.createURI(path))

    resource.getContents().add(cp)

    // now save the content.
    try {
      resource.save(Collections.EMPTY_MAP)
      true
    } catch {
      case e: IOException => {
        e.printStackTrace
        false
      }
    }
  }
}
