/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.Component;
import eu.paasage.camel.deployment.ComponentInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.location.CloudLocation;
import eu.paasage.camel.location.Location;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.requirement.HorizontalScaleRequirement;
import eu.paasage.camel.requirement.Requirement;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ComponentMetricRelationship;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.ProviderDimension;
import eu.paasage.upperware.metamodel.application.RequiredFeature;
import eu.paasage.upperware.metamodel.application.VirtualMachine;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;
import eu.paasage.upperware.metamodel.types.typesPaasage.CityUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.ContinentUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.CountryUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType;
import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.OS;
import eu.paasage.upperware.metamodel.types.typesPaasage.OSArchitectureEnum;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasageFactory;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.lib.PaaSageConfigurationWrapper;

/**
 * This class provides auxiliares methods to deal with paasage models
 * @author danielromero
 *
 */
public class PaasageModelTool 
{
	/*
	 * ATTRIBUTES
	 */

	public static String NAME_SEPARATOR="_"; 
	
	public static String SUFFIX="VM_PROFILE"; 
	
	/*
	 * THE LOGGER 
	 */
	public static Logger logger= Logger.getLogger("paasage-profiler-log");
		
	/*
	 * METHODS
	 */
	/**
	 * Searches for an operating system with a given name
	 * @param osName The operating system name
	 * @param pcw The paasage configuration wrapper containing the operating systems
	 * @return The operating system or null if it does not exist
	 */
	public static OS getOSFromName(String osName, PaaSageConfigurationWrapper pcw)
	{
		for(OS os: pcw.getOperatingSystems().getOss())
		{
			if(os.getName().equals(osName)) //TODO toLowerCase? Usage of version?
				return os; 
		}
		
		return null; 
	}
	
	/**
	 * Searches for an operating system with a given name and architecture type
	 * @param osName The operating system name
	 * @param is64Bits Indicates if the OS is 64 bits
	 * @param pcw The paasage configuration wrapper containing the operating systems
	 * @return The operating system or null if it does not exist
	 */
	public static OS getOSFromNameAndArchitecture(String osName, boolean is64Bits, PaaSageConfigurationWrapper pcw)
	{
		logger.debug("*************** new req --- =========================");
		for(OS os: pcw.getOperatingSystems().getOss())
		{

			boolean is64= os.getArchitecture().getValue()==OSArchitectureEnum.SIXTY_FOUR_BITS_VALUE; 
			
			logger.debug("*************** ==> "+os.getName()+" ==? "+osName+ " = "+os.getName().equals(osName));
			logger.debug("*************** ==> "+os.getArchitecture().getValue() + " ==? " + OSArchitectureEnum.SIXTY_FOUR_BITS_VALUE);
			logger.debug("*************** ==> "+is64+" ==? "+is64Bits);

			if(os.getName().equals(osName) && is64==is64Bits) {
				logger.debug("*************** SUCCESS");
				return os; 
			}
		}
		
		return null; 
	}
	
	public static OS cloneOS(OS os)
	{
		OS copy= TypesPaasageFactory.eINSTANCE.createOS();
		
		copy.setArchitecture(os.getArchitecture());
		copy.setName(os.getName());
		copy.setTypeId(os.getTypeId());
		copy.setVers(os.getVers());
		
		return copy; 
	}
	
	/**
	 * Searches for a location with a given name
	 * @param locationName The name of the location
	 * @param pcw The paasage configuration wrapper that contains the locations
	 * @return The location or null if it does not exist
	 */
	public static LocationUpperware getLocationFromName(String locationName, PaaSageConfigurationWrapper pcw)
	{
		for(LocationUpperware location:pcw.getLocations().getLocations())
		{
			if(location.getName().equals(locationName) || isInList(location.getAlternativeNames(), locationName))
			{
				return location; 
			}
			
		}
		
		return null; 
	}
	
	/**
	 * Searches for a location using a camel location
	 * @param loc The camel location 
	 * @param pcw The paasage configuration wrapper that contains the locations
	 * @return The location or null if it does not exist
	 */
	public static LocationUpperware getLocation(Location loc, PaaSageConfigurationWrapper pcw)
	{
		String locationName= loc.getId(); 
		logger.debug("***************looking for location "+locationName);
		
		if(loc instanceof CloudLocation)
		{
			//TODO Check CloudLocation ??
			CloudLocation cloudLocation= (CloudLocation) loc; 
			List<CityUpperware> cities= searchCityByName(locationName, pcw.getLocations().getLocations());
			
			if(cities.size()>0)
			{
				if(cities.size()==1)
				{
					return cities.get(0); 
				}
				else
				{
					
					if(cloudLocation.getGeographicalRegion()!=null)
					{
						if(cloudLocation.getGeographicalRegion() instanceof eu.paasage.camel.location.Country)
						{
							eu.paasage.camel.location.Country theCountry= (eu.paasage.camel.location.Country) cloudLocation.getGeographicalRegion(); 
							
							String countryName= theCountry.getName(); //TODO TO USE ALTERNATIVE NAMES OF theCountry
									
							//if(countryName!=null)
							for(CityUpperware city:cities)
							{
								if(city.getCountry().getName().equals(countryName) || isInList(city.getCountry().getAlternativeNames(), countryName))
								{
									return city; 
								}
							}		
									
						}
						else //It is a continent //TODO TO CHECK THIS
						{
							eu.paasage.camel.location.GeographicalRegion theContinent= (eu.paasage.camel.location.GeographicalRegion) cloudLocation.getGeographicalRegion(); 
							String continentName= theContinent.getName(); 
							
							for(CityUpperware city:cities)
							{
								if(city.getCountry().getContinent().getName().equals(continentName) || isInList(city.getCountry().getContinent().getAlternativeNames(), continentName))
								{
									return city; 
								}
							}
						}
							
					}
				}
				
				
				
			}
			else if(cloudLocation.getGeographicalRegion()!=null) //Try with regions 
			{
				String regionName= cloudLocation.getGeographicalRegion().getName(); 
				
				{
					if(cloudLocation.getGeographicalRegion() instanceof eu.paasage.camel.location.Country)
					{
						//TODO TO USE ALTERNATIVE NAMES OF theCountry
						CountryUpperware country= searchCountryByName(regionName, pcw.getLocations().getLocations()); 
								
						return country; 
								
					}
					else //It is a continent
					{						
						ContinentUpperware continent= searchContinentByName(regionName, pcw.getLocations().getLocations()); 
						
						return continent; 
					}
						
				}
			}
		}
		else if(loc instanceof eu.paasage.camel.location.Country)
		{
			CountryUpperware country= searchCountryByName(locationName, pcw.getLocations().getLocations());
			
			return country; 
		}
		else //It is a continent
		{
			ContinentUpperware continent= searchContinentByName(locationName, pcw.getLocations().getLocations()); 
			
			return continent; 
		}
		
		if(locationName!=null && !locationName.equals(""))
		{
			
			

		}
		
				
		return null; 
	}
	
	/**
	 * Searches an attribute by using a given suffix
	 * @param suffix The suffix
	 * @param attributes The list of attributes
	 * @return The attribute or null if it does not exist
	 */
	public static Attribute searchAttributeBySuffix(String suffix, EList<Attribute> attributes)
	{
		
		for(Attribute att: attributes)
		{
			if(att.getName().endsWith(suffix))
				return att; 
		}
		
		return null; 
	}
	
	/**
	 * Searches for a citing with a given name
	 * @param cityName The city name
	 * @param locations The list of locations
	 * @return the list of cities with the given name
	 */
	public static List<CityUpperware> searchCityByName(String cityName, EList<LocationUpperware> locations)
	{
		List<CityUpperware> cities= new ArrayList<CityUpperware>(); 
		
		for(LocationUpperware loc: locations)
		{
			logger.debug("***************Comparing location name "+loc.getName()+" with city "+cityName);
			logger.debug("***************Alternative names size "+loc.getAlternativeNames().size());
			if((loc instanceof CityUpperware) && (loc.getName().equals(cityName) || isInList(loc.getAlternativeNames(), cityName)))
			{
				cities.add((CityUpperware) loc); 
			}
		}
		
		return cities; 
		
	}
	
	/**
	 * Searches for a country with a given name
	 * @param countryName The country name
	 * @param locations The list of locations
	 * @return The country or null if it does not exist
	 */
	public static CountryUpperware searchCountryByName(String countryName, EList<LocationUpperware> locations)
	{
		
		
		for(LocationUpperware loc: locations)
		{
			logger.debug("***************Comparing country name "+loc.getName()+" with "+countryName);
			if((loc instanceof CountryUpperware) && (loc.getName().equals(countryName) || isInList(loc.getAlternativeNames(), countryName)))
			{
				return (CountryUpperware) loc; 
			}
		}
		
		return null; 
		
	}
	
	/**
	 * Searches for a continent with a given name
	 * @param continentName The continent name
	 * @param locations The list of locations
	 * @return The continent or null if it does not exist
	 */
	public static ContinentUpperware searchContinentByName(String continentName, EList<LocationUpperware> locations)
	{
		for(LocationUpperware loc: locations)
		{
			logger.debug("***************Comparing continent name "+loc.getName()+" with "+continentName);
			if((loc instanceof ContinentUpperware) && (loc.getName().equals(continentName) || isInList(loc.getAlternativeNames(), continentName)))
			{
				return (ContinentUpperware) loc; 
			}
		}
		
		return null; 
		
	}
	
	/**
	 * Verifies if a string is a given list
	 * @param names The list of strings
	 * @param name The string to search for
	 * @return True if the string is in the list
	 */
	protected static boolean isInList(EList<String> names, String name)
	{
		for(String n:names)
		{	logger.debug("***************Comparing name "+n+" with "+name);
			if(n.equals(name))
				return true; 
		}
		return false; 
	}
	
	/**
	 * Searches in a list a virtual machine profile by using a given id
	 * @param vmachines The list of virtual machine profiles
	 * @param id The id of the profile
	 * @return The virtual machine profile or null if it does not exist
	 */
	public static VirtualMachineProfile searchVMProfileById(EList<VirtualMachineProfile> vmachines, String id)
	{
		
		for(VirtualMachineProfile vmp: vmachines)
		{	
			if(vmp.getCloudMLId().equals(id))
				return vmp; 
		}
		
		return null; 
	}
	
	
	public static VirtualMachine searchVMById(EList<VirtualMachine> vms, String id)
	{
		for(VirtualMachine vm: vms)
		{
			if(vm.getId().equals(id))
				return vm; 
		}
		
		return null; 
	}
	
	/**
	 * Searches in a list a required feature  
	 * @param features The list of features
	 * @param searched The VO to execute the research 
	 * @return The required feature or null if it does not exist
	 */
	public static RequiredFeature searchFeatureByName(List<RequiredFeature> features, RequiredFeature searched)
	{
		
		for(RequiredFeature f: features)
		{
			if(f.getFeature().equals(searched.getFeature()))
				return f; 
		}
		
		return null; 
		
	}


	/**
	 * Searches a provider type by id
	 * @param providerName The provider name 
	 * @param pcw The paasage configuration wrapper that has the provider types
	 * @return The provider type or null if it does not exist
	 */
	public static ProviderType searchProviderTypeById(String providerName, PaaSageConfigurationWrapper pcw) 
	{
		
		for(ProviderType pt: pcw.getProviderTypes().getTypes())
		{
			if(pt.getId().equals(providerName))
				return pt; 
		}
		
		return null; 
		
	}
	
	/**
	 * Searches for a provided with a given id
	 * @param providers The list of providers
	 * @param id The provider id
	 * @return The provider or null if it does not exist
	 */
	public static Provider searchProviderById(EList<Provider> providers, String id)
	{
		for(Provider p: providers)
		{
			if(p.getId().equals(id))
				return p; 
		}
		
		
		return null;
		
	}
	
	/**
	 * Searches an application component by id
	 * @param components The list of components
	 * @param id The component id
	 * @return The component or null if it does not exist
	 */
	public static ApplicationComponent searchApplicationComponentById(EList<ApplicationComponent> components, String id)
	{
		for(ApplicationComponent ac:components)
		{
			if(ac.getCloudMLId().equals(id))
				return ac;
		}
		
		return null; 
	}
	
	/**
	 * Searches an application component by id
	 * @param components The list of components
	 * @param id The component id
	 * @return The component or null if it does not exist
	 */
	public static ApplicationComponent searchApplicationComponentByIdInRel(EList<ComponentMetricRelationship> components, String id)
	{
		for(ComponentMetricRelationship cmr:components)
		{
			logger.debug("PaasageModelTool - searchApplicationComponentByIdInRel "+cmr.getComponent().getCloudMLId());
			if(cmr.getComponent().getCloudMLId().equals(id))
				return cmr.getComponent();
		}
		
		return null; 
	}
	
	
	/**
	 * Searches a required feature by name
	 * @param features The list of features
	 * @param id The required feature id
	 * @return The required feature or null if it does not exist
	 */
	public static RequiredFeature searchFeatureByName(EList<RequiredFeature> features, String id)
	{
		for(RequiredFeature rf:features)
		{
			if(rf.getFeature().equals(id))
				return rf; 
		}
		
		return null; 
	}
	
	/**
	 * Creates a provider id by using a location and a type 
	 * @param type The provider type
	 * @param location The location
	 * @return The provider id
	 */
	public static String buildProviderId(String type, String location)
	{
		
		String suffix= location+Constants.PROVIDER_ID_SEPARATOR+System.currentTimeMillis(); 
		
		if(location==null || location.equals(""))
			suffix= ""+System.currentTimeMillis(); 
		
		return type+Constants.PROVIDER_ID_SEPARATOR+suffix; 
	}
	
	/**
	 * Searches the different types related to a list of providers
	 * @param providers The list of providers
	 * @return The list of provider types
	 */
	public static List<ProviderType> getProvidersTypeFromProviderList(EList<Provider> providers)
	{
		List<ProviderType> types= new ArrayList<ProviderType>(); 
		
		for(Provider p: providers)
		{
			if(!isProviderTypeInList(types, p.getType()))
				types.add(p.getType()); 
		}
		
		
		return types; 
	}
	
	
	/**
	 * Checks if a given profile is in a list of profiles
	 * @param profiles The list of profiles
	 * @param profile The profile
	 * @return True if the profile is in the list
	 */
	public static boolean isVMProfileInList(EList<VirtualMachineProfile> profiles, VirtualMachineProfile profile)
	{
		for(VirtualMachineProfile p: profiles)
		{
			if(p.getCloudMLId().equals(profile.getCloudMLId()))
				return true; 
		}
		
		return false; 
	}
	
	/**
	 * Checks if a given provider type is in a list
	 * @param types The list of provider types
	 * @param type The provider type
	 * @return true is the provider is in the list
	 */
	public static boolean isProviderTypeInList(List<ProviderType> types, ProviderType type)
	{
		for(ProviderType pt:types)
		{
			if(pt.getId().equals(type))
				return true; 
		}
		
		return false; 
	}
	
	/**
	 * Checks is a provider is an list considering its location
	 * @param providers the list of providers
	 * @param provider The provider
	 * @return True is the provider is in the list
	 */
	public static boolean isProviderWithLocationInList(EList<Provider> providers, Provider provider)
	{
		String providerId= provider.getType().getId();
		
		String locationId= provider.getLocation().getName(); 
		
		for(Provider p: providers)
		{
			//if(p.getType().getId().equals(providerId) && (p.getLocation().getName().equals(locationId) || isInList(p.getLocation().getAlternativeNames(), locationId)))
			if(p.getType().getId().equals(providerId) && (p.getLocation().getName().equals(locationId) || isInList(p.getLocation().getAlternativeNames(), locationId) || (p.getLocation() instanceof CountryUpperware && isProviderInCountry((CountryUpperware) p.getLocation(), provider) || (p.getLocation() instanceof ContinentUpperware && isProviderInContinent((ContinentUpperware) p.getLocation(), provider)))))
					return true; 
		}
				
		return false; 
	}
	
	/**
	 * Searches a provider in a list considering its location
	 * @param providers The list of providers
	 * @param provider The provider
	 * @return The provider if it is in the list or null if it is not. 
	 */
	public static Provider searchProviderWithLocationInList(EList<Provider> providers, Provider provider)
	{
		String providerId= provider.getType().getId();
		
		String locationId= provider.getLocation().getName(); 
		
		for(Provider p: providers)
		{
			//if(p.getType().getId().equals(providerId) && (p.getLocation().getName().equals(locationId) || isInList(p.getLocation().getAlternativeNames(), locationId)))
			if(p.getType().getId().equals(providerId) && (p.getLocation().getName().equals(locationId) || isInList(p.getLocation().getAlternativeNames(), locationId) || (p.getLocation() instanceof CountryUpperware && isProviderInCountry((CountryUpperware) p.getLocation(), provider) || (p.getLocation() instanceof ContinentUpperware && isProviderInContinent((ContinentUpperware) p.getLocation(), provider)))))
					return p; 
		}
				
		return null; 
	}
	
	/**
	 * Searches a provider in a list considering its location
	 * @param providers The list of providers
	 * @param provider The provider
	 * @return The provider if it is in the list or null if it is not. 
	 */
	public static Provider searchProviderWithLocationInList(List<Provider> providers, Provider provider)
	{
		String providerId= provider.getType().getId();
		
		String locationId= provider.getLocation().getName(); 
		
		for(Provider p: providers)
		{
			if(p.getType().getId().equals(providerId) && (p.getLocation().getName().equals(locationId) || isInList(p.getLocation().getAlternativeNames(), locationId) || (p.getLocation() instanceof CountryUpperware && isProviderInCountry((CountryUpperware) p.getLocation(), provider) || (p.getLocation() instanceof ContinentUpperware && isProviderInContinent((ContinentUpperware) p.getLocation(), provider)))))
					return p; 
		}
				
		return null; 
	}
	
	
	public static boolean isProviderInContinent(ContinentUpperware c, Provider provider)
	{
		
		LocationUpperware location= provider.getLocation(); 
		
		if(location instanceof ContinentUpperware)
		{
			return location.getName().equals(c.getName()) || isInList(c.getAlternativeNames(), location.getName()); 
		}
		
		if(location instanceof CityUpperware)
		{
			CityUpperware city= (CityUpperware) location; 
			
			if(city.getCountry()!=null && city.getCountry().getContinent()!=null)
			{
				ContinentUpperware pContinent= city.getCountry().getContinent(); 
				
				return pContinent.getName().equals(c.getName()) || isInList(c.getAlternativeNames(), pContinent.getName()); 
			}
		}
		
		if(location instanceof CountryUpperware)
		{
			CountryUpperware country= (CountryUpperware) location; 
			
			if(country.getContinent()!=null)
			{
				ContinentUpperware pContinent= country.getContinent(); 
				
				return pContinent.getName().equals(c.getName()) || isInList(c.getAlternativeNames(), pContinent.getName()); 
			}
		}
		
		return false; 
	}
	
	public static boolean isProviderInCountry(CountryUpperware c, Provider provider)
	{
		
		LocationUpperware location= provider.getLocation(); 
		
		
		if(location instanceof CityUpperware)
		{
			CityUpperware city= (CityUpperware) location; 
			
			if(city.getCountry()!=null)
			{
				CountryUpperware pCountry= city.getCountry(); 
				
				return pCountry.getName().equals(c.getName()) || isInList(c.getAlternativeNames(), pCountry.getName()); 
			}
		}
		
		if(location instanceof CountryUpperware)
		{
				
			return location.getName().equals(c.getName()) || isInList(c.getAlternativeNames(), location.getName()); 
			
		}
		
		return false; 
	}
	
	/**
	 * Searches vm profiles related to the given cloud vm id
	 * @param profiles The list of profiles
	 * @param cloudVMId Cloud Id of the VM
	 * @return The list of vm profiles related to the virtual machine
	 */
	public static List<VirtualMachineProfile> searchRelatedVMProfiles(EList<VirtualMachineProfile> profiles, String cloudVMId)
	{
		List<VirtualMachineProfile> relatedProfiles= new ArrayList<VirtualMachineProfile>(); 
		
		for(VirtualMachineProfile p: profiles)
		{
			if(p.getRelatedCloudVMId().equals(cloudVMId))
				relatedProfiles.add(p); 
		}
				
		return relatedProfiles; 
	}
	
	/**
	 * Searches a provider in a list considering its location
	 * @param providers The list of providers
	 * @param provider The provider
	 * @return The provider if it is in the list or null if it is not. 
	 */
	public static Provider searchProviderByIdWithoutLocation(EList<Provider> providers, Provider provider)
	{
		String providerId= provider.getType().getId();
		
		
		for(Provider p: providers)
		{
			if(p.getType().getId().equals(providerId) && (p.getLocation()==null))
					return p; 
		}
				
		return null; 
	}
	
	/**
	 * Checks if the provider is in a list
	 * @param providers The list of providers
	 * @param provider The provider to search in the list
	 * @return True if the provider is in the list or false if it is not
	 */
	public static boolean isProviderInList(EList<Provider> providers, Provider provider)
	{
		String providerId= provider.getType().getId();
		
		for(Provider p: providers)
		{
			if(p.getType().getId().equals(providerId))
					return true; 
		}
				
		return false; 
	}
	
	/**
	 * Checks if the provider is in a list
	 * @param providers The list of providers
	 * @param provider The provider to search in the list
	 * @return True if the provider is in the list or false if it is not
	 */
	public static boolean isProviderInList(List<Provider> providers, Provider provider)
	{
		String providerId= provider.getType().getId();
		
		for(Provider p: providers)
		{
			if(p.getType().getId().equals(providerId))
					return true; 
		}
				
		return false; 
	}
	
	/**
	 * Removes all the providers with a given type in a list
	 * @param providers The list of providers
	 * @param type The provider type
	 * @return The removed provider
	 */
	public static Provider removeProvidersFromListByType(EList<Provider> providers, ProviderType type)
	{
		
		int i=0; 
		
		while(i<providers.size())
		{
			if(providers.get(i).getType().getId().equals(type.getId()))
			{
				return providers.remove(i); 
				 
			}
			else
				i++; 
		}
		
		return null; 
	}
	
	/**
	 * Removes all the virtual machine profile with a given provide type from a list
	 * @param profiles The virtual machine profiles 
	 * @param type The provider type
	 * @param components The list of application components
	 */
	public static void removeVirtualMahineProfilesByProviderType(EList<VirtualMachineProfile> profiles, ProviderType type, EList<ApplicationComponent> components)
	{
		int i=0; 
		
		while(i<profiles.size())
		{
			int j=0; 
			while(j< profiles.get(i).getProviderDimension().size())
			{	
				ProviderDimension pc= profiles.get(i).getProviderDimension().get(j); 
				logger.debug("Provider type "+pc.getProvider().getType().getId());
				if(pc.getProvider().getType().getId().equals(type.getId()))
				{
					profiles.get(i).getProviderDimension().remove(j); 
				}
				else
					j++; 		
			}
			
			if(profiles.get(i).getProviderDimension().size()==0)
			{
				profiles.remove(i); 
				 
			}
			
			else
				i++; 
		}
		
		for(ApplicationComponent apc:components)
		{
			int j=0; 
			
			while(j<apc.getRequiredProfile().size())
			{
				if(apc.getRequiredProfile().get(j).getProviderDimension().size()==0)
				{
					apc.getRequiredProfile().remove(j); 
				}
				else
					j++; 
			}
		}
		
	}
	
	
	/**
	 * Removes all the virtual machine profile with a given provide from a list
	 * @param profiles The virtual machine profiles 
	 * @param provider The provider 
	 * @param components The list of application components
	 */
	public static void removeVirtualMahineProfilesByProvider(EList<VirtualMachineProfile> profiles, Provider provider, EList<ApplicationComponent> components)
	{
		int i=0; 
		
		while(i<profiles.size())
		{
			int j=0; 
			while(j< profiles.get(i).getProviderDimension().size())
			{	
				ProviderDimension pc= profiles.get(i).getProviderDimension().get(j); 
				logger.debug("Provider type "+pc.getProvider().getType().getId());
				if(pc.getProvider().getId().equals(provider.getId()))
				{
					profiles.get(i).getProviderDimension().remove(j); 
				}
				else
					j++; 		
			}
			
			if(profiles.get(i).getProviderDimension().size()==0)
			{
				profiles.remove(i); 
				 
			}
			
			else
				i++; 
		}
		
		for(ApplicationComponent apc:components)
		{
			int j=0; 
			
			while(j<apc.getRequiredProfile().size())
			{
				if(apc.getRequiredProfile().get(j).getProviderDimension().size()==0)
				{
					apc.getRequiredProfile().remove(j); 
				}
				else
					j++; 
			}
		}
		
	}
	
	/**
	 * Removes all the virtual machine profile with a given provide from a list
	 * @param profiles The virtual machine profiles 
	 * @param provider The provider 
	 * @param pcw The paasage configuration
	 */
	public static void removeVirtualMahineProfilesByProvider(List<VirtualMachineProfile> profiles, Provider provider, PaaSageConfigurationWrapper pcw)
	{
		int i=0; 
		
		while(i<profiles.size())
		{
			int j=0; 
			while(j< profiles.get(i).getProviderDimension().size())
			{	
				ProviderDimension pc= profiles.get(i).getProviderDimension().get(j); 
				logger.debug("Provider type "+pc.getProvider().getType().getId());
				if(pc.getProvider().getId().equals(provider.getId()))
				{
					profiles.get(i).getProviderDimension().remove(j); 
				}
				else
					j++; 		
			}
			
			if(profiles.get(i).getProviderDimension().size()==0)
			{
				pcw.getPaasageConfiguration().getVmProfiles().remove(profiles.get(i)); 
				 
			}
			
			i++; 
		}
		
		for(ApplicationComponent apc:pcw.getPaasageConfiguration().getComponents())
		{
			int j=0; 
			
			while(j<apc.getRequiredProfile().size())
			{
				if(apc.getRequiredProfile().get(j).getProviderDimension().size()==0)
				{
					apc.getRequiredProfile().remove(j); 
				}
				else
					j++; 
			}
		}
		
	}
	
	/**
	 * Removes all the virtual machine profile with a given provide from a list
	 * @param profiles The virtual machine profiles 
	 * @param type The provider type
	 * @param pc The paasage configuration
	 */
	public static void removeVirtualMahineProfilesByProviderType(List<VirtualMachineProfile> profiles, ProviderType type, PaasageConfiguration pc)
	{
		int i=0; 
		
		while(i<profiles.size())
		{
			int j=0; 
			while(j< profiles.get(i).getProviderDimension().size())
			{	
				ProviderDimension pd= profiles.get(i).getProviderDimension().get(j); 
				if(pd.getProvider().getType().getId().equals(type.getId()))
				{
					profiles.get(i).getProviderDimension().remove(j); 
				}
				else
					j++; 		
			}
			
			if(profiles.get(i).getProviderDimension().size()==0)
			{
				pc.getVmProfiles().remove(profiles.get(i)); 
				 
			}
			i++; 
		}
		
		for(ApplicationComponent apc:pc.getComponents())
		{
			int j=0; 
			
			while(j<apc.getRequiredProfile().size())
			{
				if(apc.getRequiredProfile().get(j).getProviderDimension().size()==0)
				{
					apc.getRequiredProfile().remove(j); 
				}
				else
					j++; 
			}
		}
		
	}
	
	
	/**
	 * Searches a provider in a given list
	 * @param providers The list of providers
	 * @param provider The provider to search
	 * @return The provider if it is in the list or null if it is not
	 */
	public static Provider searchProviderInList(EList<Provider> providers, Provider provider)
	{
		String providerId= provider.getType().getId();
		
		for(Provider p: providers)
		{
			if(p.getType().getId().equals(providerId))
					return p; 
		}
				
		return null; 
	}
	
	/**
	 * Searches a provider in a given list
	 * @param providers The list of providers
	 * @param provider The provider to search
	 * @return The provider if it is in the list or null if it is not
	 */
	public static Provider searchProviderInList(List<Provider> providers, Provider provider)
	{
		String providerId= provider.getType().getId();
		
		for(Provider p: providers)
		{
			if(p.getType().getId().equals(providerId))
					return p; 
		}
				
		return null; 
	}
	
	/**
	 * Chechs if there is a goal with a given type in a list
	 * @param goals The goal list
	 * @param type The goal type
	 * @return True if there is a goal with the provide type
	 */
	public static boolean existGoalWithTypeInList(EList<PaaSageGoal> goals, GoalOperatorEnum type)
	{
		
		
		for(PaaSageGoal goal: goals)
		{
			if(goal.getGoal().getName().toLowerCase().equals(type.getName().toLowerCase()))
					return true; 
		}
				
		return false; 
	}
	
	/**
	 * Generates an ID for a paasage Application Configuration
	 * @param appId The application Id
	 * @return  appId+System.currentTimeMillis()
	 */
	public static String generatePaasageAppConfigurationId(String appId)
	{
		return appId+System.currentTimeMillis(); 
	}

	/**
	 * Retrieves a directory to generate models related to a given paasage configuration
	 * @param pc The paasage configuration
	 * @return The directory to generate models of the configuration
	 */
	public static File getGenerationDirForPaasageAppConfiguration(PaasageConfiguration pc)
	{
		return getGenerationDirForPaasageAppConfiguration(pc.getId()); 
		
		
	}
	
	/**
	 * Creates a directory with a given id
	 * @param pcId The id
	 * @return The created directory
	 */
	public static File getGenerationDirForPaasageAppConfiguration(String pcId)
	{
		File tempDir= new File(Constants.GENERATION_DIR);
		
		if(!tempDir.isDirectory())
		{	
			//tempDir.mkdirs(); 
			
			File tomcatTempDir= new File(Constants.TOMCAT_TEM_DIR); 
			
			if(tomcatTempDir.exists())
			{	
				tempDir= new File(Constants.TOMCAT_GENERATION_DIR); 
				tempDir.mkdirs(); 
			}
			else
			{
				tempDir= new File(Constants.TOMCAT_ALT_GENERATION_TEMP_DIR);
				
				tempDir.mkdirs(); 
			}
		}
		
		File configDir= new File(tempDir, pcId);
		
		if(!configDir.isDirectory())
			configDir.mkdirs(); 
		
		return configDir; 
		
		
	}
	
	/**
	 * Generates a provider model resource id
	 * @param pc The paasage configuration 
	 * @param provider The provider related to the model
	 * @return pc.getId()+"/"+provider.getId()
	 */
	public static String getFMResourceId(PaasageConfiguration pc, Provider provider)
	{
		return pc.getId()+"/"+provider.getId(); 
	}
	
	/**
	 * Generates a provider model resource id
	 * @param appId The application id
	 * @param providerId The provider related to the model
	 * @return pc.getId()+"/"+provider.getId()
	 */
	public static String getFMResourceId(String appId, String providerId)
	{
		return appId+"/"+providerId; 
	}
	
	public static boolean existComponentInstance(Component component, List<ComponentInstance> instances)
	{
		String typeId= component.getName(); 
		
		for(ComponentInstance instance:instances)
		{
			if(instance.getType().getName().equals(typeId))
				return true; 
		}
		
		return false; 
	}
	
	public static List<InternalComponentInstance> getInternalComponentInstancesByTypeId(EList<InternalComponentInstance> instances, String typeId)
	{
		List<InternalComponentInstance> filtred= new ArrayList<InternalComponentInstance>(); 
		
		for(InternalComponentInstance instance:instances)
		{
			if(instance.getType().getName().equals(typeId))
			{
				filtred.add(instance); 
			}
		}
		
		
		return filtred; 
	}
	
	
	public static List<ComponentInstance> getComponentInstancesByTypeId(List<ComponentInstance> instances, String typeId)
	{
		List<ComponentInstance> filtred= new ArrayList<>(); 
		
		for(ComponentInstance instance:instances)
		{
			if(instance.getType().getName().equals(typeId))
			{
				filtred.add(instance); 
			}
		}
		
		
		return filtred; 
	}
	
	public static List<HostingInstance> getHostingInstanceByTypeId(EList<HostingInstance> hostingInstances, String typeId)
	{
		List<HostingInstance> instances= new ArrayList<HostingInstance>(); 
		
		
		for(HostingInstance hi:hostingInstances)
		{
			if(hi.getType().getName().equals(typeId))
			{
				instances.add(hi); 
			}
		}
		
		
		return instances; 
	}
	
	public static List<CommunicationInstance> getCommunicationInstanceByTypeId(EList<CommunicationInstance> communicationInstances, String typeId)
	{
		List<CommunicationInstance> instances= new ArrayList<>(); 
		
		for(CommunicationInstance instance:communicationInstances)
		{
			
			if(instance.getType().getName().equals(typeId))
			{
				instances.add(instance); 
			}
		}
		
		
		return instances; 
	}
	
	public static boolean existHostingInstanceForComponentInstance(List<HostingInstance> hostingInstances, ComponentInstance instance)
	{
		for(HostingInstance hi: hostingInstances)
		{
			ComponentInstance ici= (ComponentInstance) hi.getRequiredHostInstance().eContainer();
			
			if(ici.getName().equals(instance.getName()))
				return true; 
		}
		
		return false;
	}
	
	public static boolean existCommunicationInstanceForClientComponent(List<CommunicationInstance> communicationInstances, ComponentInstance instance)
	{
		for(CommunicationInstance ci:communicationInstances)
		{
			ComponentInstance owner= (ComponentInstance) ci.getRequiredCommunicationInstance().eContainer();
			

			if(owner.getName().equals(instance.getName()))
			{
				return true; 
			}
		}
		
		return false; 
	}
	
	public static CommunicationInstance getCommunicationInstanceForClientComponent(List<CommunicationInstance> communicationInstances, ComponentInstance instance)
	{
		for(CommunicationInstance ci:communicationInstances)
		{
			ComponentInstance owner= (ComponentInstance) ci.getRequiredCommunicationInstance().eContainer();
			
			if(owner.getName().equals(instance.getName()))
			{
				return ci; 
			}
		}
		
		return null; 
	}
	
	
	public static List<Component> getComponentsList(DeploymentModel pim) {
		
		List<Component> components= new ArrayList<>(); 
		
		
		components.addAll(pim.getInternalComponents()); 
				
		return components;
	}

	
	public static List<ComponentInstance> getComponentInstancesList(DeploymentModel pim) {
		
		List<ComponentInstance> components= new ArrayList<>(); 
				
		components.addAll(pim.getInternalComponentInstances()); 
		
		
		for(ComponentInstance c:pim.getInternalComponentInstances())
		{
			if(!(c instanceof VMInstance ))
			{
				components.add(c); 
			}
		}
		
		return components;
	}
	
	/**
	 * Returns the list of VM instances from a given deployment model
	 * @param dm The deployment model
	 * @return The list of VM instances
	 */
	public static List<VMInstance> getVMInstancesList(DeploymentModel dm)
	{
		List<VMInstance> vms= new ArrayList<VMInstance>(); 
		
		vms.addAll(dm.getVmInstances()); 
		
		for(ComponentInstance c: dm.getInternalComponentInstances())
		{
			if(c instanceof VMInstance)
				vms.add((VMInstance) c); 
		}
		
		vms.addAll(dm.getVmInstances()); 
				
		return vms; 
		
	}
	
	public static boolean existProviderOfVMInList(VMInstance vmInstance, EList<Provider> providers)
	{
		
		VM vmType= (VM) vmInstance.getType(); 
		
		if(vmType.getVmRequirementSet().getProviderRequirement()!=null)
		{
			
		}
		
		
		EList<CloudProvider> cloudProviders= ((VM)vmInstance.getType()).getVmRequirementSet().getProviderRequirement().getProviders();  //organisationModel.getProvider().getName(); 
		
		
		for(CloudProvider c: cloudProviders)
		{	
			for(Provider provider:providers)
			{
				if(provider.getType().getId().equals(c.getName()))
					return true; 
			}
		}
		
		return false; 
	}
	
	public static String getVMProfileId(String location, String provider, String hardwareId, String osImageId, String vmID)
	{
		String id= ""; 
		
		if(!location.equals(""))
		{
			id+=location+NAME_SEPARATOR; 
		}
		
		if(!provider.equals(""))
		{
			id+=provider+NAME_SEPARATOR; 
		}
		
		if(!hardwareId.equals(""))
		{
			id+=hardwareId+NAME_SEPARATOR; 
		}
		
		if(!osImageId.equals(""))
		{
			id+=osImageId+NAME_SEPARATOR; 
		}
		
		
		if(location.equals("") && provider.equals("") && hardwareId.equals("") && osImageId.equals(""))
		{
			id+=vmID+SUFFIX; 
		}
		else
			id+=NAME_SEPARATOR+vmID+SUFFIX; 
		
		
		return id; 
		
	}
	
	public static HorizontalScaleRequirement getScaleRequirementForComponent(EList<Requirement> reqs, Component component)
	{
		String name= component.getName(); 
		
		for(Requirement req: reqs)
		{
			if(req instanceof HorizontalScaleRequirement)
			{
				HorizontalScaleRequirement hsr= (HorizontalScaleRequirement) req; 
				if(hsr.getComponent().getName().equals(name))
					return hsr;
			}	
		}
		
		return null; 
	}
	
	/**
	 * Searches a function type with a given name
	 * @param name The name
	 * @param proxy The database proxy
	 * @return The function type with name name or null if it does not exist
	 */
	public static FunctionType getFunctionTypeByName(String name, IDatabaseProxy proxy)
	{
		for(FunctionType ft: proxy.getFunctionTypes().getTypes())
		{
			//if(ft.getId().equals(name))
			if(name.toLowerCase().contains(ft.getId().toLowerCase())) //TODO It should be not like this. How to identify the type of a raw metric ??
				return ft; 
		}
		
		return null; 
	}
	
	
	public static List<Provider> getProvidersFromVirtualMachineProfiles(List<VirtualMachineProfile> profiles)
	{
		List<Provider> providers= new ArrayList<Provider>(); 
		
		for(VirtualMachineProfile p: profiles)
		{
			for(ProviderDimension pd:p.getProviderDimension())
			{
				providers.add(pd.getProvider()); 
			}
		}
		
		return providers; 
	}
	

	public static boolean isProviderInListById(List<Provider> providers, Provider provider)
	{
		String providerId= provider.getId();
		
		for(Provider p: providers)
		{
			if(p.getId().equals(providerId))
					return true; 
		}
				
		return false; 
	}
}
