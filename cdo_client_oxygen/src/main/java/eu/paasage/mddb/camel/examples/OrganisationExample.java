package eu.paasage.mddb.camel.examples;

import java.text.SimpleDateFormat;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import camel.core.CamelModel;
import camel.core.CoreFactory;
import camel.organisation.CloudProvider;
import camel.organisation.ExternalIdentifier;
import camel.organisation.OrganisationFactory;
import camel.organisation.OrganisationModel;
import camel.organisation.PlatformCredentials;
import camel.organisation.Role;
import camel.organisation.RoleAssignment;
import camel.organisation.User;
import camel.organisation.UserGroup;

public class OrganisationExample {

	/* This method is used to create a particular model based on the CERIF
	 * meta-model in order to be able to test the functionality of the
	 * CDOClient in terms of storing and querying about the objects defined
	 * by this model.
	 */
	public static EObject createExampleOrgModel(){
		CamelModel cm = CoreFactory.eINSTANCE.createCamelModel();
		cm.setName("MY CAMEL MODEL");
		OrganisationModel om = OrganisationFactory.eINSTANCE.createOrganisationModel();
		om.setName("MY ORGANISATION MODEL");
		cm.getOrganisationModels().add(om);
		EList<User> users = om.getUsers();
		EList<UserGroup> ugroups = om.getUserGroups();
		EList<Role> roles = om.getRoles();
		EList<RoleAssignment> assigns = om.getRoleAssigments();
		EList<ExternalIdentifier> ids = om.getExternalIdentifiers();


		ExternalIdentifier id1 = OrganisationFactory.eINSTANCE.createExternalIdentifier();
		id1.setIdentifier("ID1");
		id1.setName("ID1");
		ids.add(id1);

		ExternalIdentifier id2 = OrganisationFactory.eINSTANCE.createExternalIdentifier();
		id2.setIdentifier("ID2");
		id2.setName("ID2");
		ids.add(id2);

		ExternalIdentifier id3 = OrganisationFactory.eINSTANCE.createExternalIdentifier();
		id3.setIdentifier("ID3");
		id3.setName("ID3");
		ids.add(id3);

		User user1 = OrganisationFactory.eINSTANCE.createUser();
		user1.setLastName("User");
		user1.setFirstName("User1");
		user1.setName("User1");
		user1.setEmail("user@user1");
		EList<ExternalIdentifier> exIDs1 = user1.getExternalIdentifiers();
		exIDs1.add(id1);
		exIDs1.add(id2);
		users.add(user1);

		PlatformCredentials pc1 = OrganisationFactory.eINSTANCE.createPlatformCredentials();
		pc1.setName("User1_Creds");
		pc1.setPassword("user1");
		user1.setPlatformCredentials(pc1);

		User user2 = OrganisationFactory.eINSTANCE.createUser();
		user2.setFirstName("User2");
		user2.setLastName("User");
		user2.setName("User2");
		user2.setEmail("user2@User");
		users.add(user2);
		exIDs1 = user2.getExternalIdentifiers();
		//exIDs1.add(id2);
		exIDs1.add(id3);

		PlatformCredentials pc2 = OrganisationFactory.eINSTANCE.createPlatformCredentials();
		pc2.setName("User2_PlatformCreds");
		pc2.setPassword("user2");
		user2.setPlatformCredentials(pc2);

		CloudProvider org1 = OrganisationFactory.eINSTANCE.createCloudProvider();
		org1.setEmail("email2");
		org1.setName("Org2");
		org1.setWww("www2");
		org1.setPublic(true);
		om.setOrganisation(org1);

		UserGroup ug1 = OrganisationFactory.eINSTANCE.createUserGroup();
		ug1.setName("ug1");
		EList<User> members = ug1.getUsers();
		members.add(user1);
		ugroups.add(ug1);

		Role r1 = OrganisationFactory.eINSTANCE.createRole();
		r1.setName("role1");
		roles.add(r1);

		Role r2 = OrganisationFactory.eINSTANCE.createRole();
		r2.setName("role2");
		roles.add(r2);

		RoleAssignment ra1 = OrganisationFactory.eINSTANCE.createRoleAssignment();
		ra1.setName("MY_ROLE_ASSIGNMENT");
		ra1.setRole(r1);
		ra1.setUser(user1);
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		try{
			ra1.setAssignmentTime(ft.parse("1976-12-16"));
			ra1.setStartTime(ft.parse("1977-12-16"));
			ra1.setEndTime(ft.parse("1978-12-16"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		assigns.add(ra1);

		return cm;
	}

	
}
