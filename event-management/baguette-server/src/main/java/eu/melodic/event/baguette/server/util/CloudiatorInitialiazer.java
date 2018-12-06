/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.util;

import de.uniulm.omi.cloudiator.colosseum.client.Client;
import de.uniulm.omi.cloudiator.colosseum.client.ClientBuilder;
import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import de.uniulm.omi.cloudiator.colosseum.client.entities.internal.NamedRemoteEntityInLocation;
import de.uniulm.omi.cloudiator.colosseum.client.entities.internal.RemoteEntityInCloud;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

/**
 *  Colosseum Initialization Utility
 *  Usage:
 *  	java eu.melodic.event.baguette.server.util.CloudiatorInitialiazer <scenario-properties-file>
 */
@Slf4j
public class CloudiatorInitialiazer
{
	//public static final String DEFAULT_CLOUDIATOR_PROPERTIES = "/cloudiator.properties";
	
	public static void main(String[] args) throws IOException {
		log.info("Starting");
		CloudiatorInitialiazer ci = new CloudiatorInitialiazer(args[0]);
		ci.deployApplication( Integer.parseInt(args[1]) );
		ci.printIds();
		log.info("Done");
	}
	
	// ------------------------------------------------------------------------
	
	private Properties config;
	private Client client;
	private long apiId = -1;
	private long cloudId = -1;
	private long credentialsId = -1;
	private long appId = -1;
	private long appInstanceId = -1;
	
	private Map<String,Long> imageMap = new HashMap<>();
	private Map<String,Long> hardwareMap = new HashMap<>();
	private Map<String,Long> locationMap = new HashMap<>();
	
	private List<Long> componentId = new LinkedList<>();
	private List<Long> vmTemplateId = new LinkedList<>();
	private List<Long> appComponentId = new LinkedList<>();
	private List<Long> virtualMachineId = new LinkedList<>();
	private List<Long> portProvidedId = new LinkedList<>();
	private List<Long> portRequiredId = new LinkedList<>();
	private List<Long> communicationId = new LinkedList<>();
	private List<Long> instanceId = new LinkedList<>();
	
	public CloudiatorInitialiazer() {}
	
	public CloudiatorInitialiazer(String resource) throws IOException {
		loadConfig(resource);
	}
	
	public void deployApplication(int target) {
		if (target<0) { log.warn("Nothing to do: target={}", target); return; }
		int step = 0;
		
		initClient();
		testCloudiator();
		
		if (step++<target) createApi();
		if (step++<target) createCloud();
		if (step++<target) createCloudCredentials();
		
		if (step<target) checkProviderOffers(true);
		
		log.info("Retrieving Id's of Images, Hardwares and Locations");
		retrieveEntityIds("image.names", imageMap, Image.class);
		retrieveEntityIds("hardware.names", hardwareMap, Hardware.class);
		retrieveEntityIds("location.names", locationMap, Location.class);
		
		if (step++<target) createApplication();
		if (step++<target) createMultipleEntities(LifecycleComponent.class, "Lifecycle Components", "comp", this::createLcComponent);
		if (step++<target) createApplicationInstance();
		if (step++<target) createMultipleEntities(VirtualMachineTemplate.class, "Virtual Machine Template", "vmt", this::createVirtualMachineTemplate);
		if (step++<target) createMultipleEntities(ApplicationComponent.class, "Application Component", "ac", this::createApplicationComponent);
		if (step++<target) createMultipleEntities(VirtualMachine.class, "Virtual Machine", "vm", this::createVirtualMachine);
		if (step++<target) createMultipleEntities(PortProvided.class, "Provided Port", "provided", this::createPortProvided);
		if (step++<target) createMultipleEntities(PortRequired.class, "Required Port", "required", this::createPortRequired);
		if (step++<target) createMultipleEntities(Communication.class, "Communication", "comm", this::createCommunication);
		if (step++<target) createMultipleEntities(Instance.class, "Instance", "instance", this::createInstance);
	}
	
	protected void loadConfig(String resource) throws IOException {
		//try (InputStream in = getClass().getResourceAsStream(resource)) {
		try (InputStream in = new FileInputStream(resource)) {
			this.config = new Properties();
			config.load(in);
		}
		log.debug("Loaded configuration:\n{}", config);
	}
	
	protected void initClient() {
		String colosseumEndpoint = config.getProperty("colosseum.endpoint");
		String email = config.getProperty("colosseum.auth.email");
		String tenant = config.getProperty("colosseum.auth.tenant");
		String password = config.getProperty("colosseum.auth.password");
		
		this.client = ClientBuilder.getNew().url(colosseumEndpoint).credentials(email, tenant, password).build();
	}
	
	protected void testCloudiator() {
		log.info("Testing connection to Cloudiator: {}", config.getProperty("colosseum.endpoint"));
		client.controller(Api.class).getList();
	}
	
	protected boolean checkProviderOffers(boolean throughException) {
		int countImages = client.controller(Image.class).getList().size();
		int countHardware = client.controller(Hardware.class).getList().size();
		int countLocations = client.controller(Location.class).getList().size();
		
		if (countImages==0 || countHardware==0 || countLocations==0) {
			if (throughException) throw new RuntimeException("Cloudiator has not been initialized with Provider Offers");
			else return false;
		}
		
		log.info("Provider Offer counts:");
		log.info("    Image:     {}", countImages);
		log.info("    Hardware:  {}", countHardware);
		log.info("    Locations: {}", countLocations);
		
		return true;
	}
	
	protected void retrieveEntityIds(String propertyName, Map<String,Long> idMap, Class<? extends RemoteEntityInCloud> clazz) {
		String[] names = config.getProperty(propertyName,"").split(",");
		if (names.length==1 && names[0].trim().isEmpty()) return;
		retrieveEntityIds(names, idMap, clazz);
	}
	
	protected void retrieveEntityIds(String[] names, Map<String,Long> idMap, Class<? extends RemoteEntityInCloud> clazz) {
		for (String name : names) {
			name=name.trim();
			if (!name.isEmpty()) {
				idMap.put( name, getIdByName(client.controller( clazz ).getList(), name) );
			}
		}
	}
	
	protected Long getIdByName(List<? extends RemoteEntityInCloud> list, String name) {
		for (RemoteEntityInCloud re : list) {
			if (re instanceof NamedRemoteEntityInLocation) {
				NamedRemoteEntityInLocation ne = (NamedRemoteEntityInLocation)re;
				if (ne.getName().equals(name)) {
					return ne.getId();
				}
			} else 
			if (re instanceof Location) {
				if (((Location)re).getName().equals(name)) {
					return re.getId();
				}
			} else {
				throw new IllegalArgumentException("List contains an illegal element: " + re.getClass().getName());
			}
		}
		return -1L;
	}
	
	// ------------------------------------------------------------------------
	
	protected long getIdFromProperty(String pName) {
		String val = config.getProperty(pName,"-1").trim();
		if (val.isEmpty()) return -1;
		return Long.parseLong(val);
	}
	
	protected int countEntitiesOf(Class clazz) {
		return client.controller(clazz).getList().size();
	}
	
	protected void createMultipleEntities(Class clazz, String label, String configDomain, java.util.function.Function<Integer,Object> callback) {
		int n;
		if ((n=countEntitiesOf(clazz))>0) { log.info("{}s have already been configured: count={}", label, n); return; }
		
		String startStr = configDomain+"[";
		int startPos = startStr.length();
		List<Integer> indexes = ((java.util.Collection<String>)java.util.Collections.list(config.propertyNames()))
			.stream()
			.filter(name -> name.startsWith(startStr))
			.map(name -> name.substring(startPos, name.indexOf(']', startPos)))
			.filter(index -> !index.isEmpty())
			.map(Integer::parseInt)
			.sorted()
			.distinct()
			//.forEach(System.out::println);
			.collect(java.util.stream.Collectors.toList());
		
		log.debug("{} indexes: {}", label, indexes);
		indexes.forEach(callback::apply);
	}
	
	protected void printIds() {
		log.info("-----------------------------------------------");
		log.info("api-id:          {}", apiId);
		log.info("cloud-id:        {}", cloudId);
		log.info("credentials-id:  {}", credentialsId);
		log.info("app-id:          {}", appId);
		log.info("app-instance-id: {}", appInstanceId);
		log.info("-----------------------------------------------");
		log.info("image-ids:    {}", imageMap);
		log.info("hardware-ids: {}", hardwareMap);
		log.info("location-ids: {}", locationMap);
		log.info("-----------------------------------------------");
		log.info("component-ids:       {}", componentId);
		log.info("vm-template-ids:     {}", vmTemplateId);
		log.info("app-component-ids:   {}", appComponentId);
		log.info("virtual-machine-ids: {}", virtualMachineId);
		log.info("port-provided-ids:   {}", portProvidedId);
		log.info("port-required-ids:   {}", portRequiredId);
		log.info("communication-ids:   {}", communicationId);
		log.info("instance-ids:        {}", instanceId);
		log.info("-----------------------------------------------");
	}
	
	// ------------------------------------------------------------------------
	
	protected void createApi() {
		this.apiId = getIdFromProperty("id.api");
		if (apiId>0) { log.info("API pre-configured: id={}", apiId); return; }
		
		String apiName = config.getProperty("api.name");
		String apiInternalProviderName = config.getProperty("api.internalProviderName");
		Api newApi = new Api(apiName, apiInternalProviderName);
		newApi = client.controller(Api.class).create(newApi);
		this.apiId = newApi.getId();
		log.info("API created: id={}", apiId);
	}
	
	protected void createCloud() {
		this.cloudId = getIdFromProperty("id.cloud");
		if (cloudId>0) { log.info("Cloud pre-configured: id={}", cloudId); return; }
		
		String cloudName = config.getProperty("cloud.name");
		String cloudEndpoint = config.getProperty("cloud.endpoint");
		Cloud newCloud = new Cloud(cloudName, cloudEndpoint, this.apiId);
		newCloud = client.controller(Cloud.class).create(newCloud);
		this.cloudId = newCloud.getId();
		log.info("Cloud created: id={}", cloudId);
	}
	
	protected void createCloudCredentials() {
		this.credentialsId = getIdFromProperty("id.credentials");
		if (credentialsId>0) { log.info("Cloud Credentials pre-configured: id={}", credentialsId); return; }
		
		String user = config.getProperty("credentials.user");
		String secret = config.getProperty("credentials.secret");
		long tenant = Long.parseLong(config.getProperty("credentials.tenant"));
		CloudCredential newCredentials = new CloudCredential(user, secret, this.cloudId, tenant);
		newCredentials = client.controller(CloudCredential.class).create(newCredentials);
		this.credentialsId = newCredentials.getId();
		log.info("Cloud Credentials created: id={}", credentialsId);
	}
	
	protected void createApplication() {
		this.appId = getIdFromProperty("id.app");
		if (appId>0) { log.info("Application pre-configured: id={}", appId); return; }
		
		String appName = config.getProperty("app.name");
		Application newApp = new Application(appName);
		newApp = client.controller(Application.class).create(newApp);
		this.appId = newApp.getId();
		log.info("Application created: id={}", appId);
	}
	
	protected Object createLcComponent(int index) {
		String compInit = config.getProperty(String.format("comp[%d].init", index));
		String compInstall = config.getProperty(String.format("comp[%d].install", index));
		String compName = config.getProperty(String.format("comp[%d].name", index));
		String compPostInstall = config.getProperty(String.format("comp[%d].postInstall", index));
		String compPostStart = config.getProperty(String.format("comp[%d].postStart", index));
		String compPostStop = config.getProperty(String.format("comp[%d].postStop", index));
		String compPreInstall = config.getProperty(String.format("comp[%d].preInstall", index));
		String compPreStart = config.getProperty(String.format("comp[%d].preStart", index));
		String compPreStop = config.getProperty(String.format("comp[%d].preStop", index));
		String compShutdown = config.getProperty(String.format("comp[%d].shutdown", index));
		String compStart = config.getProperty(String.format("comp[%d].start", index));
		String compStartDetection = config.getProperty(String.format("comp[%d].startDetection", index));
		String compStop = config.getProperty(String.format("comp[%d].stop", index));
		String compStopDetection = config.getProperty(String.format("comp[%d].stopDetection", index));
		
		LifecycleComponent newComp = new LifecycleComponent(
			compName, compInit, compPreInstall, compInstall, compPostInstall, 
			compStart, compStartDetection, compStopDetection, compPreStart, 
			compPostStart, compPreStop, compStop, compPostStop, compShutdown
		);
		newComp = client.controller(LifecycleComponent.class).create(newComp);
		long compId = newComp.getId();
		this.componentId.add(compId);
		log.info("Lifecycle Component created: id={}", compId);
		return null;
	}
	
	protected void createApplicationInstance() {
		this.appInstanceId = getIdFromProperty("id.app-instance");
		if (appInstanceId>0) { log.info("Application Instance pre-configured: id={}", appInstanceId); return; }
		
		ApplicationInstance newAppInst = new ApplicationInstance(this.appId);
		newAppInst = client.controller(ApplicationInstance.class).create(newAppInst);
		this.appInstanceId = newAppInst.getId();
		log.info("Application Instance created: id={}", appInstanceId);
	}
	
	protected Object createVirtualMachineTemplate(int index) {
		String vmtImage = config.getProperty(String.format("vmt[%d].image", index));
		String vmtLocation = config.getProperty(String.format("vmt[%d].location", index));
		String vmtHardware = config.getProperty(String.format("vmt[%d].hardware", index));
		String vmtOptions = config.getProperty(String.format("vmt[%d].templateOptions", index));
		
		VirtualMachineTemplate newVmt = new VirtualMachineTemplate(
			cloudId,  
			new Long(vmtImage),
			new Long(vmtLocation),
			new Long(vmtHardware),
			null	//new Long(vmtOptions)
		);
		newVmt = client.controller(VirtualMachineTemplate.class).create(newVmt);
		long vmtId = newVmt.getId();
		this.vmTemplateId.add(vmtId);
		log.info("Virtual Machine Template created: id={}", vmtId);
		return null;
	}
	
	protected Object createApplicationComponent(int index) {
		String acComponent = config.getProperty(String.format("ac[%d].component", index));
		String acVmt = config.getProperty(String.format("ac[%d].virtualMachineTemplate", index));
		String acContainerType = config.getProperty(String.format("ac[%d].containerType", index));
		
		ApplicationComponent newAc = new ApplicationComponent(
			appId,  
			new Long(acComponent),
			new Long(acVmt),
			null	//new Long(acContainerType)
		);
		newAc = client.controller(ApplicationComponent.class).create(newAc);
		long acId = newAc.getId();
		this.appComponentId.add(acId);
		log.info("Application Component created: id={}", acId);
		return null;
	}
	
	protected Object createVirtualMachine(int index) {
		String vmLocation = config.getProperty(String.format("vm[%d].location", index));
		String vmName = config.getProperty(String.format("vm[%d].name", index));
		String vmImage = config.getProperty(String.format("vm[%d].image", index));
		String vmHardware = config.getProperty(String.format("vm[%d].hardware", index));
		
		VirtualMachine newVm = new VirtualMachine(
			null, null, null, null, cloudId, null, null, new Long(vmLocation), vmName, new Long(vmImage), new Long(vmHardware), null
		);
		newVm = client.controller(VirtualMachine.class).create(newVm);
		long vmId = newVm.getId();
		this.virtualMachineId.add(vmId);
		log.info("Virtual Machine created: id={}", vmId);
		return null;
	}
	
	protected Object createPortProvided(int index) {
		String ppName = config.getProperty(String.format("provided[%d].name", index));
		String ppAc = config.getProperty(String.format("provided[%d].applicationComponent", index));
		String ppPort = config.getProperty(String.format("provided[%d].port", index));
		
		PortProvided newPp = new PortProvided( ppName, new Long(ppAc), new Integer(ppPort) );
		newPp = client.controller(PortProvided.class).create(newPp);
		long ppId = newPp.getId();
		this.portProvidedId.add(ppId);
		log.info("Provided Port created: id={}", ppId);
		return null;
	}
	
	protected Object createPortRequired(int index) {
		String prName = config.getProperty(String.format("required[%d].name", index));
		String prAc = config.getProperty(String.format("required[%d].applicationComponent", index));
		String prUpdAction = config.getProperty(String.format("required[%d].updateAction", index)).trim();
		String prMandatory = config.getProperty(String.format("required[%d].isMandatory", index));
		
		PortRequired newPr = new PortRequired( prName, new Long(prAc), prUpdAction, Boolean.parseBoolean(prMandatory) );
		newPr = client.controller(PortRequired.class).create(newPr);
		long prId = newPr.getId();
		this.portRequiredId.add(prId);
		log.info("Required Port created: id={}", prId);
		return null;
	}
	
	protected Object createCommunication(int index) {
		String commPortRequired = config.getProperty(String.format("comm[%d].requiredPort", index));
		String commPortProvided = config.getProperty(String.format("comm[%d].providedPort", index));
		
		Communication newComm = new Communication( new Long(commPortRequired), new Long(commPortProvided) );
		newComm = client.controller(Communication.class).create(newComm);
		long commId = newComm.getId();
		this.communicationId.add(commId);
		log.info("Communication created: id={}", commId);
		return null;
	}
	
	protected Object createInstance(int index) {
		String instAc = config.getProperty(String.format("instance[%d].applicationComponent", index));
		String instAppInst = config.getProperty(String.format("instance[%d].applicationInstance", index));
		String instVm = config.getProperty(String.format("instance[%d].virtualMachine", index));
		
		Instance newInst = new Instance(
			null, null, null, null, new Long(instAc), new Long(instAppInst), new Long(instVm)
		);
		newInst = client.controller(Instance.class).create(newInst);
		long instId = newInst.getId();
		this.instanceId.add(instId);
		log.info("Instance created: id={}", instId);
		return null;
	}
}
