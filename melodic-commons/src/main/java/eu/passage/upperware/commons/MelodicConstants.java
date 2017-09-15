package eu.passage.upperware.commons;

/**
 * Created by pszkup on 13.09.17.
 */
public class MelodicConstants {

    public static final String APP_COMPONENT_VAR_PREFIX= "U_app_component_";
    public static final String APP_COMPONENT_VAR_MID= "_vm_";
    public static final String APP_COMPONENT_VAR_SUFFIX= "_provider_";

    public static final String CDO_SERVER_PATH= "upperware-models/";
    public static final String FMS_APP_CDO_SERVER_PATH = CDO_SERVER_PATH + "fms/";

    public static final String FUNCTION_TYPES_ID="cpGenerator-functionTypes";
    public static final String OPERATING_SYSTEMS_ID="cpGenerator-operatingSystems";
    public static final String LOCATIONS_ID="cpGenerator-locations";
    public static final String PROVIDER_TYPES_ID="cpGenerator-providerTypes";

    public static final String FUNCTION_TYPES_FILE = "FunctionTypes.xmi";
    public static final String OPERATING_SYSTEMS_FILE = "OperatingSystems.xmi";
    public static final String LOCATIONS_FILE = "Locations.xmi";
    public static final String PROVIDER_TYPES_FILE = "ProviderTypes.xmi";

    public static final String ATTRIB_LOCATION = "Location";
    public static final String ATTRIB_LOCATION_ID = "LocationId";

    public static final String ATTRIB_VM = "VM";
    public static final String ATTRIB_VM_IMAGE_ID = "VMImageId";

    private MelodicConstants() {}
}
