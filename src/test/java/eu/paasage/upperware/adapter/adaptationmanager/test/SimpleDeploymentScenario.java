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

public class SimpleDeploymentScenario {
	
	private static final String token = "bo6t3h9fl1i6ie7fu2l1k1bafnsr0jv1qo5ampb2qlt1u1q2o12674qoad8qa1sh306d1riffu471nr055tv3ftqm0u07camoo9j2luut1pqm3oosb62sqgheh0aak0bm2266n730sa62eifj78qipj3elipdnt74mc1tas8h1qv60cld5nrn2f8gttfqf5r35ol4b0obcdos";
	
	private final String TEST_INPUTFILE_SimpleDeployment = System
			.getProperty("user.dir") + "/src/test/resources/CAMEL_test_SimpleDeployment.xmi";
	
	/**
	 * Request Strings
	 */
	public static final String API_LOGIN1 = "/api/login";
	
	/**
	 * Response Strings
	 */
	
	public static final String RESP_POST_APP = "{\"name\":\"BewanApplication\",\"link\":[{\"href\":\"http://localhost:9000/api/application/1\",\"rel\":\"self\"}]}";
	public static final String RESP_POST_APPInst = "{\"application\":1,\"link\":[{\"href\":\"http://localhost:9000/api/applicationInstance/1\",\"rel\":\"self\"}]}";
	
	public static final String RESP_POST_VMT1 = "{\"cloud\":1,\"image\":158,\"location\":3,\"hardware\":4,\"templateOptions\":null,\"link\":[{\"href\":\"http://localhost:9000/api/vmt/1\",\"rel\":\"self\"}]}";
	public static final String RESP_POST_VMT2 = "{\"cloud\":1,\"image\":158,\"location\":3,\"hardware\":4,\"templateOptions\":null,\"link\":[{\"href\":\"http://localhost:9000/api/vmt/2\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_LCCOMP1 = Pattern.compile(".*\"name\":\"WebApplication\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_LCCOMP1 = "{\"name\":\"WebApplication\",\"init\":null,\"preInstall\":\"apt-get install -y git && cd /usr && git clone http://paasage_demo:PaaSageReview@git.cetic.be/paasage/enterprise_service_application.git\",\"install\":null,\"postInstall\":\"cd /usr/enterprise_service_application && bash deploy/scripts/webApplication.sh -e prod\",\"preStart\":null,\"start\":\"apachectl -DFOREGROUND\",\"startDetection\":null,\"stopDetection\":null,\"postStart\":null,\"preStop\":null,\"stop\":null,\"postStop\":null,\"shutdown\":null,\"link\":[{\"href\":\"http://localhost:9000/api/lifecycleComponent/1\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_LCCOMP2 = Pattern.compile(".*\"name\":\"LoadBalancer\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_LCCOMP2 = "{\"name\":\"LoadBalancer\",\"init\":null,\"preInstall\":\"apt-get install -y git && cd /usr && git clone http://paasage_demo:PaaSageReview@git.cetic.be/paasage/enterprise_service_application.git\",\"install\":null,\"postInstall\":\"cd /usr/enterprise_service_application && bash deploy/scripts/loadBalancer.sh -e prod\",\"preStart\":null,\"start\":\"apachectl -DFOREGROUND\",\"startDetection\":null,\"stopDetection\":null,\"postStart\":null,\"preStop\":null,\"stop\":null,\"postStop\":null,\"shutdown\":null,\"link\":[{\"href\":\"http://localhost:9000/api/lifecycleComponent/2\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_LCCOMP3 = Pattern.compile(".*\"name\":\"DatabaseComp\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_LCCOMP3 = "{\"name\":\"DatabaseComp\",\"init\":null,\"preInstall\":\"apt-get install -y git && cd /usr && git clone http://paasage_demo:PaaSageReview@git.cetic.be/paasage/enterprise_service_application.git\",\"install\":null,\"postInstall\":\"cd /usr/enterprise_service_application && bash deploy/scripts/databaseComp.sh -e prod\",\"preStart\":null,\"start\":\"mysqld_safe --log-error=/var/log/mysql.err\",\"startDetection\":null,\"stopDetection\":null,\"postStart\":null,\"preStop\":null,\"stop\":null,\"postStop\":null,\"shutdown\":null,\"link\":[{\"href\":\"http://localhost:9000/api/lifecycleComponent/3\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_AC1 = Pattern.compile(".*\"component\":1.*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_AC1 = "{\"application\":1,\"component\":1,\"virtualMachineTemplate\":1,\"link\":[{\"href\":\"http://localhost:9000/api/ac/1\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_AC2 = Pattern.compile(".*\"component\":2.*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_AC2 = "{\"application\":1,\"component\":2,\"virtualMachineTemplate\":1,\"link\":[{\"href\":\"http://localhost:9000/api/ac/2\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_AC3 = Pattern.compile(".*\"component\":3.*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_AC3 = "{\"application\":1,\"component\":3,\"virtualMachineTemplate\":2,\"link\":[{\"href\":\"http://localhost:9000/api/ac/3\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_PORTREQ1 = Pattern.compile(".*(\"applicationComponent\":1|\"name\":\"DATABASECOMPREQ\").*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_PORTREQ1 = "{\"name\":\"DATABASECOMPREQ\",\"applicationComponent\":1,\"updateAction\":\"null\",\"isMandatory\":true,\"link\":[{\"href\":\"http://localhost:9000/api/portReq/1\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_PORTREQ4 = Pattern.compile(".*(\"applicationComponent\":2|\"name\":\"WEBAPPLICATIONREQ\").*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_PORTREQ4 = "{\"name\":\"WEBAPPLICATIONREQ\",\"applicationComponent\":2,\"updateAction\":\"cd /usr/enterprise_service_application && bash deploy/scripts/startCommunicationCommand.sh -l /tmp/start.log -e prod\",\"isMandatory\":true,\"link\":[{\"href\":\"http://localhost:9000/api/portReq/4\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_PORTPROV2 = Pattern.compile(".*(\"applicationComponent\":1|\"name\":\"WEBAPPLICATIONPROV\").*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_PORTPROV2 = "{\"name\":\"WEBAPPLICATIONPROV\",\"applicationComponent\":1,\"port\":80,\"link\":[{\"href\":\"http://localhost:9000/api/portProv/2\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_PORTPROV3 = Pattern.compile(".*(\"applicationComponent\":2|\"name\":\"LOADBALANCERPROV\").*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_PORTPROV3 = "{\"name\":\"LOADBALANCERPROV\",\"applicationComponent\":2,\"port\":80,\"link\":[{\"href\":\"http://localhost:9000/api/portProv/3\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_PORTPROV5 = Pattern.compile(".*(\"applicationComponent\":3|\"name\":\"DATABASECOMPPROV\").*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_PORTPROV5 = "{\"name\":\"DATABASECOMPPROV\",\"applicationComponent\":3,\"port\":3306,\"link\":[{\"href\":\"http://localhost:9000/api/portProv/5\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_COMM1 = Pattern.compile(".*(\"requiredPort\":4|\"providedPort\":2).*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_COMM1 = "{\"requiredPort\":4,\"providedPort\":2,\"link\":[{\"href\":\"http://localhost:9000/api/communication/1\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_COMM2 = Pattern.compile(".*(\"requiredPort\":1|\"providedPort\":5).*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_COMM2 = "{\"requiredPort\":1,\"providedPort\":5,\"link\":[{\"href\":\"http://localhost:9000/api/communication/2\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_VIRTUALMACHINE65536 = Pattern.compile(".*\"name\":\"MediumComputeLowStorageUbuntuVMInstance_4\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_VIRTUALMACHINE65536 = "{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98/d9cef10b-bd4d-3d8e-83c9-225fba5f9406\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98/d9cef10b-bd4d-3d8e-83c9-225fba5f9406\",\"remoteState\":\"OK\",\"name\":\"MediumComputeLowStorageUbuntuVMInstance_4\",\"cloud\":1,\"image\":158,\"hardware\":4,\"location\":3,\"templateOptions\":null,\"link\":[{\"href\":\"http://localhost:9000/api/virtualMachine/65536\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_VIRTUALMACHINE65537 = Pattern.compile(".*\"name\":\"MediumComputeMediumStorageUbuntuVMInstance_9\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_VIRTUALMACHINE65537 = "{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98/eadf0541-444d-3b59-aeb4-187cf27ead92\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98/eadf0541-444d-3b59-aeb4-187cf27ead92\",\"remoteState\":\"OK\",\"name\":\"MediumComputeMediumStorageUbuntuVMInstance_9\",\"cloud\":1,\"image\":158,\"hardware\":4,\"location\":3,\"templateOptions\":null,\"link\":[{\"href\":\"http://localhost:9000/api/virtualMachine/65537\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_VIRTUALMACHINE65538 = Pattern.compile(".*\"name\":\"MediumComputeLowStorageUbuntuVMInstance_15\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_VIRTUALMACHINE65538 = "{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98/b22e250b-ba28-3352-ae12-41b95b3ec6e0\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98/b22e250b-ba28-3352-ae12-41b95b3ec6e0\",\"remoteState\":\"OK\",\"name\":\"MediumComputeLowStorageUbuntuVMInstance_15\",\"cloud\":1,\"image\":158,\"hardware\":4,\"location\":3,\"templateOptions\":null,\"link\":[{\"href\":\"http://localhost:9000/api/virtualMachine/65538\",\"rel\":\"self\"}]}";
	
	public static final Pattern PATTERN_INSTANCE65539 = Pattern.compile(".*\"virtualMachine\":\"65536\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_INSTANCE65539 = "{\"remoteId\":null,\"cloudProviderId\":null,\"remoteState\":\"OK\",\"applicationComponent\":1,\"applicationInstance\":1,\"virtualMachine\":65536,\"link\":[{\"href\":\"http://localhost:9000/api/instance/65539\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_INSTANCE65540 = Pattern.compile(".*\"virtualMachine\":\"65537\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_INSTANCE65540 = "{\"remoteId\":null,\"cloudProviderId\":null,\"remoteState\":\"OK\",\"applicationComponent\":3,\"applicationInstance\":1,\"virtualMachine\":65537,\"link\":[{\"href\":\"http://localhost:9000/api/instance/65540\",\"rel\":\"self\"}]}";

	public static final Pattern PATTERN_INSTANCE65541 = Pattern.compile(".*\"virtualMachine\":\"65538\".*", Pattern.CASE_INSENSITIVE);
	public static final String RESP_POST_INSTANCE65541 = "{\"remoteId\":null,\"cloudProviderId\":null,\"remoteState\":\"OK\",\"applicationComponent\":2,\"applicationInstance\":1,\"virtualMachine\":65538,\"link\":[{\"href\":\"http://localhost:9000/api/instance/65541\",\"rel\":\"self\"}]}";

	
	
	
	public static final String RESP_GET_CLOUD = "[{\"name\":\"Flexiant\",\"endpoint\":\"https://cp.sd1.flexiant.net/soap/user/current/\",\"api\":1,\"link\":[{\"href\":\"http://localhost:9000/api/cloud/1\",\"rel\":\"self\"}]},{\"name\":\"Omistack\",\"endpoint\":\"http://omistack-beta.e-technik.uni-ulm.de:5000/v2.0\",\"api\":32768,\"link\":[{\"href\":\"http://localhost:9000/api/cloud/32768\",\"rel\":\"self\"}]}]";
	public static final String RESP_GET_LOCATION = "[{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98\",\"remoteState\":\"INPROGRESS\",\"cloud\":1,\"name\":\"KVM (CEPH Cluster)\",\"parent\":null,\"locationScope\":null,\"isAssignable\":false,\"geoLocation\":null,\"cloudCredentials\":[1],\"link\":[{\"href\":\"http://localhost:9000/api/location/1\",\"rel\":\"self\"}]},{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/1ff16f43-4a82-34bf-8f07-ea6d210548ab\",\"cloudProviderId\":\"1ff16f43-4a82-34bf-8f07-ea6d210548ab\",\"remoteState\":\"INPROGRESS\",\"cloud\":1,\"name\":\"KVM\",\"parent\":null,\"locationScope\":null,\"isAssignable\":false,\"geoLocation\":null,\"cloudCredentials\":[1],\"link\":[{\"href\":\"http://localhost:9000/api/location/2\",\"rel\":\"self\"}]},{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/b15e1545-7ca3-361c-b6a7-b5cf2828cf28\",\"cloudProviderId\":\"b15e1545-7ca3-361c-b6a7-b5cf2828cf28\",\"remoteState\":\"INPROGRESS\",\"cloud\":1,\"name\":\"KVM (CEPH Cluster) VDC\",\"parent\":1,\"locationScope\":null,\"isAssignable\":true,\"geoLocation\":null,\"cloudCredentials\":[1],\"link\":[{\"href\":\"http://localhost:9000/api/location/3\",\"rel\":\"self\"}]},{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/b68f15a8-0f7b-3d4c-a1d6-9eb48a486a6a\",\"cloudProviderId\":\"b68f15a8-0f7b-3d4c-a1d6-9eb48a486a6a\",\"remoteState\":\"INPROGRESS\",\"cloud\":1,\"name\":\"KVM VDC\",\"parent\":2,\"locationScope\":null,\"isAssignable\":true,\"geoLocation\":null,\"cloudCredentials\":[1],\"link\":[{\"href\":\"http://localhost:9000/api/location/159\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne\",\"cloudProviderId\":\"RegionOne\",\"remoteState\":\"INPROGRESS\",\"cloud\":32768,\"name\":\"RegionOne\",\"parent\":null,\"locationScope\":null,\"isAssignable\":true,\"geoLocation\":null,\"cloudCredentials\":[32768],\"link\":[{\"href\":\"http://localhost:9000/api/location/32768\",\"rel\":\"self\"}]}]";
	public static final String RESP_GET_HARDWARE = "[{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98/c59a9066-d2f8-32e0-a227-6d90cbe3c9e2:2aedbbc7-41de-3628-918f-2c909fa81054\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98/c59a9066-d2f8-32e0-a227-6d90cbe3c9e2:2aedbbc7-41de-3628-918f-2c909fa81054\",\"remoteState\":\"INPROGRESS\",\"name\":\"e92bb306-72cd-33a2-a952-908db2f47e98/c59a9066-d2f8-32e0-a227-6d90cbe3c9e2:2aedbbc7-41de-3628-918f-2c909fa81054\",\"cloud\":1,\"hardwareOffer\":1,\"location\":1,\"cloudCredentials\":[1],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/4\",\"rel\":\"self\"}]},{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/1ff16f43-4a82-34bf-8f07-ea6d210548ab/26ed5b62-5404-3ff3-bb44-db2cfa88c321:1ef1f045-b399-3f32-91b5-9521ff1b8188\",\"cloudProviderId\":\"1ff16f43-4a82-34bf-8f07-ea6d210548ab/26ed5b62-5404-3ff3-bb44-db2cfa88c321:1ef1f045-b399-3f32-91b5-9521ff1b8188\",\"remoteState\":\"INPROGRESS\",\"name\":\"1ff16f43-4a82-34bf-8f07-ea6d210548ab/26ed5b62-5404-3ff3-bb44-db2cfa88c321:1ef1f045-b399-3f32-91b5-9521ff1b8188\",\"cloud\":1,\"hardwareOffer\":2,\"location\":2,\"cloudCredentials\":[1],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/5\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/4\",\"cloudProviderId\":\"RegionOne/4\",\"remoteState\":\"INPROGRESS\",\"name\":\"m1.large\",\"cloud\":32768,\"hardwareOffer\":32769,\"location\":32768,\"cloudCredentials\":[32768],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/32776\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/1\",\"cloudProviderId\":\"RegionOne/1\",\"remoteState\":\"INPROGRESS\",\"name\":\"m1.tiny\",\"cloud\":32768,\"hardwareOffer\":32770,\"location\":32768,\"cloudCredentials\":[32768],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/32777\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/2\",\"cloudProviderId\":\"RegionOne/2\",\"remoteState\":\"INPROGRESS\",\"name\":\"m1.small\",\"cloud\":32768,\"hardwareOffer\":16,\"location\":32768,\"cloudCredentials\":[32768],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/32778\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/5\",\"cloudProviderId\":\"RegionOne/5\",\"remoteState\":\"INPROGRESS\",\"name\":\"m1.xlarge\",\"cloud\":32768,\"hardwareOffer\":32771,\"location\":32768,\"cloudCredentials\":[32768],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/32779\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/3\",\"cloudProviderId\":\"RegionOne/3\",\"remoteState\":\"INPROGRESS\",\"name\":\"m1.medium\",\"cloud\":32768,\"hardwareOffer\":32772,\"location\":32768,\"cloudCredentials\":[32768],\"link\":[{\"href\":\"http://localhost:9000/api/hardware/32780\",\"rel\":\"self\"}]}]";
	public static final String RESP_GET_IMAGE = "[{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98/d8cee060-e487-34fa-aa8b-9e3fef10eb8c\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98/d8cee060-e487-34fa-aa8b-9e3fef10eb8c\",\"remoteState\":null,\"name\":\"Ubuntu 14.04 (Cluster Two)\",\"cloud\":1,\"location\":1,\"operatingSystem\":2,\"cloudCredentials\":[1],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/158\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/47eaf851-d05e-4213-a1ee-fef19c5e848f\",\"cloudProviderId\":\"RegionOne/47eaf851-d05e-4213-a1ee-fef19c5e848f\",\"remoteState\":\"INPROGRESS\",\"name\":\"Windows2012R2_cloudiator_v2\",\"cloud\":32768,\"location\":32768,\"operatingSystem\":null,\"cloudCredentials\":[32768],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/32769\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/673b8967-dc56-4e4f-a759-a5b1b10f7b70\",\"cloudProviderId\":\"RegionOne/673b8967-dc56-4e4f-a759-a5b1b10f7b70\",\"remoteState\":\"INPROGRESS\",\"name\":\"Centos 7\",\"cloud\":32768,\"location\":32768,\"operatingSystem\":null,\"cloudCredentials\":[32768],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/32770\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/87945f45-c4fc-45bb-b5a2-9aa7be31a036\",\"cloudProviderId\":\"RegionOne/87945f45-c4fc-45bb-b5a2-9aa7be31a036\",\"remoteState\":\"INPROGRESS\",\"name\":\"cirros\",\"cloud\":32768,\"location\":32768,\"operatingSystem\":null,\"cloudCredentials\":[32768],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/32771\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/9c154d9a-fab9-4507-a3d7-21b72d31de97\",\"cloudProviderId\":\"RegionOne/9c154d9a-fab9-4507-a3d7-21b72d31de97\",\"remoteState\":\"INPROGRESS\",\"name\":\"Ubuntu Server 14.04.2 AMD64 LTS\",\"cloud\":32768,\"location\":32768,\"operatingSystem\":null,\"cloudCredentials\":[32768],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/32772\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/5bc61c5d-0fbb-4f86-965b-ad662264be88\",\"cloudProviderId\":\"RegionOne/5bc61c5d-0fbb-4f86-965b-ad662264be88\",\"remoteState\":\"INPROGRESS\",\"name\":\"Windows2012R2_cloudiator\",\"cloud\":32768,\"location\":32768,\"operatingSystem\":null,\"cloudCredentials\":[32768],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/32773\",\"rel\":\"self\"}]},{\"remoteId\":\"252aa06a-6a02-4e84-940a-5f66f1d681ad/RegionOne/4d079447-fba5-48c9-b0ed-a36a33a042d2\",\"cloudProviderId\":\"RegionOne/4d079447-fba5-48c9-b0ed-a36a33a042d2\",\"remoteState\":\"INPROGRESS\",\"name\":\"molpro-image-v1.5-OUTDATED\",\"cloud\":32768,\"location\":32768,\"operatingSystem\":null,\"cloudCredentials\":[32768],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/32774\",\"rel\":\"self\"}]}]";
	public static final String RESP_GET_IMAGE_158 = "{\"remoteId\":\"30260ebc-d106-4ade-8441-559bf212428a/e92bb306-72cd-33a2-a952-908db2f47e98/d8cee060-e487-34fa-aa8b-9e3fef10eb8c\",\"cloudProviderId\":\"e92bb306-72cd-33a2-a952-908db2f47e98/d8cee060-e487-34fa-aa8b-9e3fef10eb8c\",\"remoteState\":null,\"name\":\"Ubuntu 14.04 (Cluster Two)\",\"cloud\":1,\"location\":1,\"operatingSystem\":2,\"cloudCredentials\":[1],\"defaultLoginUsername\":null,\"defaultLoginPassword\":null,\"link\":[{\"href\":\"http://localhost:9000/api/image/158\",\"rel\":\"self\"}]}";

	public SimpleDeploymentScenario(ClientDriverRule driver, boolean enableDeleteAPIs){
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LOGIN)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse("{\"createdOn\":1450816014555,\"expiresAt\":1450816314555,\"token\":\""+ token + "\",\"userId\":1}", "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATION)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_APP, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONINSTANCE)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_APPInst, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_CLOUD)
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_GET_CLOUD, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LOCATION)
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_GET_LOCATION, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_HARDWARE)
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_GET_HARDWARE, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_IMAGE)
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_GET_IMAGE, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_IMAGE+"/158")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_GET_IMAGE_158, "application/json")
				.withStatus(200)).anyTimes();
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINETEMPLATE)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_VMT1, "application/json")
				.withStatus(200)).times(1);

		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINETEMPLATE)
				.withHeader("Content-Type", "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_VMT2, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LIFECYCLECOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_LCCOMP1, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_LCCOMP1, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LIFECYCLECOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_LCCOMP2, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_LCCOMP2, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LIFECYCLECOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_LCCOMP3, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_LCCOMP3, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONCOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_AC1, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_AC1, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONCOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_AC2, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_AC2, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONCOMPONENT)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_AC3, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_AC3, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTREQ)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_PORTREQ1, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_PORTREQ1, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTREQ)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_PORTREQ4, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_PORTREQ4, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTPROV)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_PORTPROV2, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_PORTPROV2, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTPROV)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_PORTPROV3, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_PORTPROV3, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTPROV)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_PORTPROV5, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_PORTPROV5, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_COMMUNICATION)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_COMM1, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_COMM1, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_COMMUNICATION)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_COMM2, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_COMM2, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_VIRTUALMACHINE65536, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_VIRTUALMACHINE65536, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_VIRTUALMACHINE65537, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_VIRTUALMACHINE65537, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_VIRTUALMACHINE65538, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_VIRTUALMACHINE65538, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65536")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_VIRTUALMACHINE65536, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65537")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_VIRTUALMACHINE65537, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65538")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_VIRTUALMACHINE65538, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_INSTANCE65539, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_INSTANCE65539, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_INSTANCE65540, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_INSTANCE65540, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE)
				.withHeader("Content-Type", "application/json")
				.withBody(SimpleDeploymentScenario.PATTERN_INSTANCE65541, "application/json")
				.withMethod(Method.POST),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_INSTANCE65541, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65539")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_INSTANCE65539, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65540")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_INSTANCE65540, "application/json")
				.withStatus(200)).times(1);
		
		driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65541")
				.withMethod(Method.GET),
				RestClientDriver
				.giveResponse(SimpleDeploymentScenario.RESP_POST_INSTANCE65541, "application/json")
				.withStatus(200)).times(1);

		
		if(enableDeleteAPIs){
			
			//Delete requests
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINETEMPLATE+"/1")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);

			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINETEMPLATE+"/2")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LIFECYCLECOMPONENT+"/1")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LIFECYCLECOMPONENT+"/2")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_LIFECYCLECOMPONENT+"/3")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONCOMPONENT+"/1")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONCOMPONENT+"/2")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_APPLICATIONCOMPONENT+"/3")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTREQ+"/1")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTREQ+"/4")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTPROV+"/2")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTPROV+"/3")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_PORTPROV+"/5")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_COMMUNICATION+"/1")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_COMMUNICATION+"/2")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65536")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65537")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65538")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65539")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65540")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65541")
					.withMethod(Method.DELETE),
					RestClientDriver
					.giveEmptyResponse()
					.withStatus(200)).times(1);
			
			//404 GET Empty Responses
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65536")
					.withMethod(Method.GET),
					RestClientDriver
					.giveResponse("Could not find entity of type VirtualMachine with id 65536", "application/json")
					.withStatus(404)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65537")
					.withMethod(Method.GET),
					RestClientDriver
					.giveResponse("Could not find entity of type VirtualMachine with id 65537", "application/json")
					.withStatus(404)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_VIRTUALMACHINE+"/65538")
					.withMethod(Method.GET),
					RestClientDriver
					.giveResponse("Could not find entity of type VirtualMachine with id 65538", "application/json")
					.withStatus(404)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65539")
					.withMethod(Method.GET),
					RestClientDriver
					.giveResponse("Could not find entity of type Instance with id 65539", "application/json")
					.withStatus(404)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65540")
					.withMethod(Method.GET),
					RestClientDriver
					.giveResponse("Could not find entity of type Instance with id 65540", "application/json")
					.withStatus(404)).times(1);
			
			driver.addExpectation(RestClientDriver.onRequestTo(ExecInterfacer.API_INSTANCE+"/65541")
					.withMethod(Method.GET),
					RestClientDriver
					.giveResponse("Could not find entity of type Instance with id 65541", "application/json")
					.withStatus(404)).times(1);
			
		}
		
	}
	
	public String getDeploymentXMI(){
		return this.TEST_INPUTFILE_SimpleDeployment;
	}
}
