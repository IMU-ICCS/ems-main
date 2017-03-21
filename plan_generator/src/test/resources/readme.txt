These examples:

ScalarmModel.xmi - 
created by Kyriakos on 23/6/2015.  This one can be 'opened' by the tree editor.  Have been edited to include cloudcredentials (username/password)

Scarlarm_full.xmi - 
created by Alessandro at the Ulm meeing. I have changed the schemaLocation to make it works with my local installation of emf.

Scarlarm_V2.xmi - 
predates the ULM meeting.  It will not work with the Camel lib updated after the ULM meeting.

test.xmi - 
deployment model created by S2D, but manually updated to correct incorrect mappings in communication intances and duplicate name in an vm instance.  Exported using the CDO client exportWithRefRec method.
Manually edited to include cloudcredentials (username/password)

upperware-models_fms_1436444254010_GWDG-DE-1436444254477.xmi - 
provider model associated with test.xmi.  Exported using the CDO client exportWithRefRec method.  Manually edited to include endpoint and driver.

test_1_reconfig.xmi -
based on test.xmi and has been manually updated to add more objects to test the reconfiguration code.  The provider model references/URIs have been updated to point to 
upperware-models_fms_1.xmi.  Manually edited to include cloudcredentials (username/password)

upperware-models_fms_1.xmi - provider model associated with 
test_1_reconfig.xmi.  The xmi is based on upperware-models_fms_1436444254010_GWDG-DE-1436444254477.xmi  

----
Shirley Crompton, STFC
26/08/2015
----
1.  Please note that these models are getting out-of-date due to regular changes in CAMEL.   To enable the tests to run with minimal modifications, I have removed
conflicting elements which do not impact on the plan generation scenarios.  So used these models at your own risks.

2.  Also note that in cases when both a providerModel and the main camelModel are used, the tests won't be able to locate the 
VMType values as the camelModel cross-references the providerModel.  The cross-reference only works if full absolute paths are provided when loading the models. 
This is not possible when running on the Jenkins platform as I do not know the absolute paths in Jenkins.

Shirley Crompton, STFC  
20/10/2015
----
