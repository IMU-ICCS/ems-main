/*
 * Copyright (c) 2015 INRIA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.test;

import java.util.regex.Pattern;

import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.clientdriver.RestClientDriver;
import com.github.restdriver.clientdriver.ClientDriverRequest.Method;

import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;

/**
 * This class contains the various request and response Strings for simulating the BeWan App using Client Driver
 * @author Arnab Sinha
 *
 */

public class RedeploymentScenario3 {
	
	private static final String token = "bo6t3h9fl1i6ie7fu2l1k1bafnsr0jv1qo5ampb2qlt1u1q2o12674qoad8qa1sh306d1riffu471nr055tv3ftqm0u07camoo9j2luut1pqm3oosb62sqgheh0aak0bm2266n730sa62eifj78qipj3elipdnt74mc1tas8h1qv60cld5nrn2f8gttfqf5r35ol4b0obcdos";
	
	private final String TEST_INPUTFILE_ReDeploymentScenario3 = System
			.getProperty("user.dir") + "/src/test/resources/CAMEL_test_Redeployment3.xmi";
	
	//private final String TEST_INPUTFILE_ReDeploymentScenario3 = "/PaaSage/CAMEL_676790ec61a8ae90a627eec0323eee12_namesChangedALL.xmi";
	
	/**
	 * Request Strings
	 */
	public static final String API_LOGIN1 = "/api/login";
	
	/**
	 * Response Strings
	 */
	
	/*public static final String RESP_POST_APP = "{\"name\":\"BewanApplication\",\"link\":[{\"href\":\"http://localhost:9000/api/application/1\",\"rel\":\"self\"}]}";
	public static final String RESP_POST_APPInst = "{\"application\":1,\"link\":[{\"href\":\"http://localhost:9000/api/applicationInstance/1\",\"rel\":\"self\"}]}";*/
	
	public static final String RESP_POST_VMT3 = "{\"cloud\":1,\"image\":158,\"location\":3,\"hardware\":4,\"templateOptions\":null,\"link\":[{\"href\":\"http://localhost:9000/api/vmt/3\",\"rel\":\"self\"}]}";
	public static final String RESP_POST_VMT4 = "{\"cloud\":1,\"image\":158,\"location\":3,\"hardware\":4,\"templateOptions\":null,\"link\":[{\"href\":\"http://localhost:9000/api/vmt/4\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_LCCOMP4 = Pattern.compile(".*\"name\":\"WebApplication_new\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_LCCOMP4 = "{\"name\":\"WebApplication_new\",\"init\":null,\"preInstall\":\"apt-get install -y git && cd /usr && git clone http://paasage_demo:PaaSageReview@git.cetic.be/paasage/enterprise_service_application.git\",\"install\":null,\"postInstall\":\"cd /usr/enterprise_service_application && bash deploy/scripts/webApplication.sh -e prod\",\"preStart\":null,\"start\":\"apachectl -DFOREGROUND\",\"startDetection\":null,\"stopDetection\":null,\"postStart\":null,\"preStop\":null,\"stop\":null,\"postStop\":null,\"shutdown\":null,\"link\":[{\"href\":\"http://localhost:9000/api/lifecycleComponent/4\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_LCCOMP5 = Pattern.compile(".*\"name\":\"LoadBalancer_new\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_LCCOMP5 = "{\"name\":\"LoadBalancer_new\",\"init\":null,\"preInstall\":\"apt-get install -y git && cd /usr && git clone http://paasage_demo:PaaSageReview@git.cetic.be/paasage/enterprise_service_application.git\",\"install\":null,\"postInstall\":\"cd /usr/enterprise_service_application && bash deploy/scripts/loadBalancer.sh -e prod\",\"preStart\":null,\"start\":\"apachectl -DFOREGROUND\",\"startDetection\":null,\"stopDetection\":null,\"postStart\":null,\"preStop\":null,\"stop\":null,\"postStop\":null,\"shutdown\":null,\"link\":[{\"href\":\"http://localhost:9000/api/lifecycleComponent/5\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_LCCOMP6 = Pattern.compile(".*\"name\":\"DatabaseComp_new\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_LCCOMP6 = "{\"name\":\"DatabaseComp_new\",\"init\":null,\"preInstall\":\"apt-get install -y git && cd /usr && git clone http://paasage_demo:PaaSageReview@git.cetic.be/paasage/enterprise_service_application.git\",\"install\":null,\"postInstall\":\"cd /usr/enterprise_service_application && bash deploy/scripts/databaseComp.sh -e prod\",\"preStart\":null,\"start\":\"mysqld_safe --log-error=/var/log/mysql.err\",\"startDetection\":null,\"stopDetection\":null,\"postStart\":null,\"preStop\":null,\"stop\":null,\"postStop\":null,\"shutdown\":null,\"link\":[{\"href\":\"http://localhost:9000/api/lifecycleComponent/6\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_AC4 = Pattern.compile(".*\"component\":4.*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_AC4 = "{\"application\":1,\"component\":4,\"virtualMachineTemplate\":3,\"link\":[{\"href\":\"http://localhost:9000/api/ac/4\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_AC5 = Pattern.compile(".*\"component\":5.*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_AC5 = "{\"application\":1,\"component\":5,\"virtualMachineTemplate\":4,\"link\":[{\"href\":\"http://localhost:9000/api/ac/5\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_AC6 = Pattern.compile(".*\"component\":6.*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_AC6 = "{\"application\":1,\"component\":6,\"virtualMachineTemplate\":4,\"link\":[{\"href\":\"http://localhost:9000/api/ac/6\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_PORTREQ6 = Pattern.compile(".*(\"applicationComponent\":4|\"name\":\"DATABASECOMPREQ_new\").*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_PORTREQ6 = "{\"name\":\"DATABASECOMPREQ_new\",\"applicationComponent\":4,\"updateAction\":\"null\",\"isMandatory\":true,\"link\":[{\"href\":\"http://localhost:9000/api/portReq/6\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_PORTREQ9 = Pattern.compile(".*(\"applicationComponent\":5|\"name\":\"WEBAPPLICATIONREQ_new\").*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_PORTREQ9 = "{\"name\":\"WEBAPPLICATIONREQ_new\",\"applicationComponent\":5,\"updateAction\":\"cd /usr/enterprise_service_application && bash deploy/scripts/startCommunicationCommand.sh -l /tmp/start.log -e prod\",\"isMandatory\":true,\"link\":[{\"href\":\"http://localhost:9000/api/portReq/9\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_PORTPROV7 = Pattern.compile(".*(\"applicationComponent\":4|\"name\":\"WEBAPPLICATIONPROV_new\").*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_PORTPROV7 = "{\"name\":\"WEBAPPLICATIONPROV_new\",\"applicationComponent\":4,\"port\":80,\"link\":[{\"href\":\"http://localhost:9000/api/portProv/7\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_PORTPROV8 = Pattern.compile(".*(\"applicationComponent\":5|\"name\":\"LOADBALANCERPROV_new\").*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_PORTPROV8 = "{\"name\":\"LOADBALANCERPROV_new\",\"applicationComponent\":5,\"port\":80,\"link\":[{\"href\":\"http://localhost:9000/api/portProv/8\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_PORTPROV10 = Pattern.compile(".*(\"applicationComponent\":6|\"name\":\"DATABASECOMPPROV_new\").*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_PORTPROV10 = "{\"name\":\"DATABASECOMPPROV_new\",\"applicationComponent\":6,\"port\":3306,\"link\":[{\"href\":\"http://localhost:9000/api/portProv/10\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_COMM3 = Pattern.compile(".*(\"requiredPort\":9|\"providedPort\":7).*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_COMM3 = "{\"requiredPort\":9,\"providedPort\":7,\"link\":[{\"href\":\"http://localhost:9000/api/communication/3\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_COMM4 = Pattern.compile(".*(\"requiredPort\":6|\"providedPort\":10).*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_COMM4 = "{\"requiredPort\":6,\"providedPort\":10,\"link\":[{\"href\":\"http://localhost:9000/api/communication/4\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_VIRTUALMACHINE65539 = Pattern.compile(".*\"name\":\"MediumComputeLowStorageUbuntuVMInstance_4_new\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_VIRTUALMACHINE65539 = "{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98/d9cef10b-bd4d-3d8e-83c9-225fba5f9406\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98/d9cef10b-bd4d-3d8e-83c9-225fba5f9406\",\"remoteState\":\"OK\",\"name\":\"MediumComputeLowStorageUbuntuVMInstance_4_new\",\"cloud\":1,\"image\":158,\"hardware\":4,\"location\":3,\"templateOptions\":null,\"link\":[{\"href\":\"http://localhost:9000/api/virtualMachine/65539\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_VIRTUALMACHINE65540 = Pattern.compile(".*\"name\":\"MediumComputeMediumStorageUbuntuVMInstance_9_new\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_VIRTUALMACHINE65540 = "{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98/eadf0541-444d-3b59-aeb4-187cf27ead92\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98/eadf0541-444d-3b59-aeb4-187cf27ead92\",\"remoteState\":\"OK\",\"name\":\"MediumComputeMediumStorageUbuntuVMInstance_9_new\",\"cloud\":1,\"image\":158,\"hardware\":4,\"location\":3,\"templateOptions\":null,\"link\":[{\"href\":\"http://localhost:9000/api/virtualMachine/65540\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_VIRTUALMACHINE65541 = Pattern.compile(".*\"name\":\"MediumComputeLowStorageUbuntuVMInstance_15_new\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_VIRTUALMACHINE65541 = "{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98/b22e250b-ba28-3352-ae12-41b95b3ec6e0\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98/b22e250b-ba28-3352-ae12-41b95b3ec6e0\",\"remoteState\":\"OK\",\"name\":\"MediumComputeLowStorageUbuntuVMInstance_15_new\",\"cloud\":1,\"image\":158,\"hardware\":4,\"location\":3,\"templateOptions\":null,\"link\":[{\"href\":\"http://localhost:9000/api/virtualMachine/65541\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_INSTANCE65542 = Pattern.compile(".*\"virtualMachine\":\"65539\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_INSTANCE65542 = "{\"remoteId\":null,\"cloudProviderId\":null,\"remoteState\":\"OK\",\"applicationComponent\":4,\"applicationInstance\":1,\"virtualMachine\":65539,\"link\":[{\"href\":\"http://localhost:9000/api/instance/65542\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_INSTANCE65543 = Pattern.compile(".*\"virtualMachine\":\"65540\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_INSTANCE65543 = "{\"remoteId\":null,\"cloudProviderId\":null,\"remoteState\":\"OK\",\"applicationComponent\":6,\"applicationInstance\":1,\"virtualMachine\":65540,\"link\":[{\"href\":\"http://localhost:9000/api/instance/65543\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_INSTANCE65544 = Pattern.compile(".*\"virtualMachine\":\"65541\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_INSTANCE65544 = "{\"remoteId\":null,\"cloudProviderId\":null,\"remoteState\":\"OK\",\"applicationComponent\":5,\"applicationInstance\":1,\"virtualMachine\":65541,\"link\":[{\"href\":\"http://localhost:9000/api/instance/65544\",\"rel\":\"self\"}]}";

	
	
	
	public static final String RESP_GET_CLOUD = "[{\"name\":\"Flexiant\",\"endpoint\":\"https://cp.sd1.flexiant.net/soap/user/current/\",\"api\":1,\"link\":[{\"href\":\"http://localhost:9000/api/cloud/1\",\"rel\":\"self\"}]},{\"name\":\"Omistack\",\"endpoint\":\"http://omistack-beta.e-technik.uni-ulm.de:5000/v2.0\",\"api\":32768,\"link\":[{\"href\":\"http://localhost:9000/api/cloud/32768\",\"rel\":\"self\"}]}]";
	public static final String RESP_GET_LOCATION = "[{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98\",\"remoteState\":\"INPROGRESS\",\"cloud\":1,\"name\":\"KVM (CEPH Cluster)\",\"parent\":null,\"locationScope\":null,\"isAssignable\":false,\"geoLocation\":null,\"cloudCredentials\":[1],\"link\":[{\"href\":\"http://localhost:9000/api/location/1\",\"rel\":\"self\"}]},{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/1ff16f43-4a82-34bf-8f07-ea6d210548ab\",\"cloudProviderId\":\"1ff16f43-4a82-34bf-8f07-ea6d210548ab\",\"remoteState\":\"INPROGRESS\",\"cloud\":1,\"name\":\"KVM\",\"parent\":null,\"locationScope\":null,\"isAssignable\":false,\"geoLocation\":null,\"cloudCredentials\":[1],\"link\":[{\"href\":\"http://localhost:9000/api/location/2\",\"rel\":\"self\"}]},{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/b15e1545-7ca3-361c-b6a7-b5cf2828cf28\",\"cloudProviderId\":\"b15e1545-7ca3-361c-b6a7-b5cf2828cf28\",\"remoteState\":\"INPROGRESS\",\"cloud\":1,\"name\":\"KVM (CEPH Cluster) VDC\",\"parent\":1,\"locationScope\":null,\"isAssignable\":true,\"geoLocation\":null,\"cloudCredentials\":[1],\"link\":[{\"href\":\"http://localhost:9000/api/location/3\",\"rel\":\"self\"}]},{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/b68f15a8-0f7b-3d4c-a1d6-9eb48a486a6a\",\"cloudProviderId\":\"b68f15a8-0f7b-3d4c-a1d6-9eb48a486a6a\",\"remoteState\":\"INPROGRESS\",\"cloud\":1,\"name\":\"KVM VDC\",\"parent\":2,\"locationScope\":null,\"isAssignable\":true,\"geoLocation\":null,\"cloudCredentials\":[1],\"link\":[{\"href\":\"http://localhost:9000/api/location/159\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne\",\"cloudProviderId\":\"RegionOne\",\"remoteState\":\"INPROGRESS\",\"cloud\":32768,\"name\":\"RegionOne\",\"parent\":null,\"locationScope\":null,\"isAssignable\":true,\"geoLocation\":null,\"cloudCredentials\":[32768],\"link\":[{\"href\":\"http://localhost:9000/api/location/32768\",\"rel\":\"self\"}]}]";
	public static final String RESP_GET_HARDWARE = "[{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98/c59a9066-d2f8-32e0-a227-6d90cbe3c9e2:2aedbbc7-41de-3628-918f-2c909fa81054\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98/c59a9066-d2f8-32e0-a227-6d90cbe3c9e2:2aedbbc7-41de-3628-918f-2c909fa81054\",\"remoteState\":\"INPROGRESS\",\"name\":\"e92bb306-72cd-33a2-a952-908db2f47e98/c59a9066-d2f8-32e0-a227-6d90cbe3c9e2:2aedbbc7-41de-3628-918f-2c909fa81054\",\"cloud\":1,\"hardwareOffer\":1,\"location\":1,\"cloudCredentials\":[1],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/4\",\"rel\":\"self\"}]},{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/1ff16f43-4a82-34bf-8f07-ea6d210548ab/26ed5b62-5404-3ff3-bb44-db2cfa88c321:1ef1f045-b399-3f32-91b5-9521ff1b8188\",\"cloudProviderId\":\"1ff16f43-4a82-34bf-8f07-ea6d210548ab/26ed5b62-5404-3ff3-bb44-db2cfa88c321:1ef1f045-b399-3f32-91b5-9521ff1b8188\",\"remoteState\":\"INPROGRESS\",\"name\":\"1ff16f43-4a82-34bf-8f07-ea6d210548ab/26ed5b62-5404-3ff3-bb44-db2cfa88c321:1ef1f045-b399-3f32-91b5-9521ff1b8188\",\"cloud\":1,\"hardwareOffer\":2,\"location\":2,\"cloudCredentials\":[1],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/5\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/4\",\"cloudProviderId\":\"RegionOne/4\",\"remoteState\":\"INPROGRESS\",\"name\":\"m1.large\",\"cloud\":32768,\"hardwareOffer\":32769,\"location\":32768,\"cloudCredentials\":[32768],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/32776\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/1\",\"cloudProviderId\":\"RegionOne/1\",\"remoteState\":\"INPROGRESS\",\"name\":\"m1.tiny\",\"cloud\":32768,\"hardwareOffer\":32770,\"location\":32768,\"cloudCredentials\":[32768],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/32777\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/2\",\"cloudProviderId\":\"RegionOne/2\",\"remoteState\":\"INPROGRESS\",\"name\":\"m1.small\",\"cloud\":32768,\"hardwareOffer\":16,\"location\":32768,\"cloudCredentials\":[32768],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/32778\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/5\",\"cloudProviderId\":\"RegionOne/5\",\"remoteState\":\"INPROGRESS\",\"name\":\"m1.xlarge\",\"cloud\":32768,\"hardwareOffer\":32771,\"location\":32768,\"cloudCredentials\":[32768],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/32779\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/3\",\"cloudProviderId\":\"RegionOne/3\",\"remoteState\":\"INPROGRESS\",\"name\":\"m1.medium\",\"cloud\":32768,\"hardwareOffer\":32772,\"location\":32768,\"cloudCredentials\":[32768],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/32780\",\"rel\":\"self\"}]}]";
	public static final String RESP_GET_IMAGE = "[{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98/d8cee060-e487-34fa-aa8b-9e3fef10eb8c\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98/d8cee060-e487-34fa-aa8b-9e3fef10eb8c\",\"remoteState\":null,\"name\":\"Ubuntu 14.04 (Cluster Two)\",\"cloud\":1,\"location\":1,\"operatingSystem\":2,\"cloudCredentials\":[1],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/158\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/47eaf851-d05e-4213-a1ee-fef19c5e848f\",\"cloudProviderId\":\"RegionOne/47eaf851-d05e-4213-a1ee-fef19c5e848f\",\"remoteState\":\"INPROGRESS\",\"name\":\"Windows2012R2_cloudiator_v2\",\"cloud\":32768,\"location\":32768,\"operatingSystem\":null,\"cloudCredentials\":[32768],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/32769\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/673b8967-dc56-4e4f-a759-a5b1b10f7b70\",\"cloudProviderId\":\"RegionOne/673b8967-dc56-4e4f-a759-a5b1b10f7b70\",\"remoteState\":\"INPROGRESS\",\"name\":\"Centos 7\",\"cloud\":32768,\"location\":32768,\"operatingSystem\":null,\"cloudCredentials\":[32768],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/32770\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/87945f45-c4fc-45bb-b5a2-9aa7be31a036\",\"cloudProviderId\":\"RegionOne/87945f45-c4fc-45bb-b5a2-9aa7be31a036\",\"remoteState\":\"INPROGRESS\",\"name\":\"cirros\",\"cloud\":32768,\"location\":32768,\"operatingSystem\":null,\"cloudCredentials\":[32768],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/32771\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/9c154d9a-fab9-4507-a3d7-21b72d31de97\",\"cloudProviderId\":\"RegionOne/9c154d9a-fab9-4507-a3d7-21b72d31de97\",\"remoteState\":\"INPROGRESS\",\"name\":\"Ubuntu Server 14.04.2 AMD64 LTS\",\"cloud\":32768,\"location\":32768,\"operatingSystem\":null,\"cloudCredentials\":[32768],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/32772\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/5bc61c5d-0fbb-4f86-965b-ad662264be88\",\"cloudProviderId\":\"RegionOne/5bc61c5d-0fbb-4f86-965b-ad662264be88\",\"remoteState\":\"INPROGRESS\",\"name\":\"Windows2012R2_cloudiator\",\"cloud\":32768,\"location\":32768,\"operatingSystem\":null,\"cloudCredentials\":[32768],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/32773\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/4d079447-fba5-48c9-b0ed-a36a33a042d2\",\"cloudProviderId\":\"RegionOne/4d079447-fba5-48c9-b0ed-a36a33a042d2\",\"remoteState\":\"INPROGRESS\",\"name\":\"molpro-image-v1.5-OUTDATED\",\"cloud\":32768,\"location\":32768,\"operatingSystem\":null,\"cloudCredentials\":[32768],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/32774\",\"rel\":\"self\"}]}]";
	public static final String RESP_GET_IMAGE_158 = "{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98/d8cee060-e487-34fa-aa8b-9e3fef10eb8c\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98/d8cee060-e487-34fa-aa8b-9e3fef10eb8c\",\"remoteState\":null,\"name\":\"Ubuntu 14.04 (Cluster Two)\",\"cloud\":1,\"location\":1,\"operatingSystem\":2,\"cloudCredentials\":[1],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/158\",\"rel\":\"self\"}]}";

	public RedeploymentScenario3(ClientDriverRule driver){
		
		//SimpleDeploymentDeleteResponses delSimpleDeploy = new SimpleDeploymentDeleteResponses(driver);
		
		/*driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LOGIN)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse("{\"createdOn\":1450816014555,\"expiresAt\":1450816314555,\"token\":\""+ token + "\",\"userId\":1}", "application/json")
				.withStatus(200)).anyTimes();*/
		
		/*driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATION)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_APP, "application/json")
				.withStatus(200)).times(0);*/
		
		/*driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONINSTANCE)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_APPInst, "application/json")
				.withStatus(200)).times(0);*/
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_CLOUD)
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_GET_CLOUD, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LOCATION)
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_GET_LOCATION, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_HARDWARE)
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_GET_HARDWARE, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_IMAGE)
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_GET_IMAGE, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_IMAGE+"/158")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_GET_IMAGE_158, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINETEMPLATE)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_VMT3, "application/json")
				.withStatus(200)).times(1);

		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINETEMPLATE)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_VMT4, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LIFECYCLECOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_LCCOMP4, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_LCCOMP4, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LIFECYCLECOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_LCCOMP5, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_LCCOMP5, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LIFECYCLECOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_LCCOMP6, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_LCCOMP6, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONCOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_AC4, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_AC4, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONCOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_AC5, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_AC5, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONCOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_AC6, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_AC6, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTREQ)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_PORTREQ6, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_PORTREQ6, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTREQ)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_PORTREQ9, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_PORTREQ9, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTPROV)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_PORTPROV7, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_PORTPROV7, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTPROV)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_PORTPROV8, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_PORTPROV8, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTPROV)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_PORTPROV10, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_PORTPROV10, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_COMMUNICATION)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_COMM3, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_COMM3, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_COMMUNICATION)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_COMM4, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_COMM4, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_VIRTUALMACHINE65539, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_VIRTUALMACHINE65539, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_VIRTUALMACHINE65540, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_VIRTUALMACHINE65540, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_VIRTUALMACHINE65541, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_VIRTUALMACHINE65541, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65539")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_VIRTUALMACHINE65539, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65540")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_VIRTUALMACHINE65540, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65541")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_VIRTUALMACHINE65541, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_INSTANCE65542, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_INSTANCE65542, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_INSTANCE65543, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_INSTANCE65543, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE)
				.withHeader("Content-Type", "application/json")
				.withBody(RedeploymentScenario3.PATTERN_INSTANCE65544, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_INSTANCE65544, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65542")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_INSTANCE65542, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65543")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_INSTANCE65543, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65544")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(RedeploymentScenario3.RESP_POST_INSTANCE65544, "application/json")
				.withStatus(200)).anyTimes();
	}
	
	public String getDeploymentXMI(){
		return this.TEST_INPUTFILE_ReDeploymentScenario3;
	}
}
