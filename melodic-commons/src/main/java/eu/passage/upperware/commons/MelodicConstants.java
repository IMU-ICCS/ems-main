package eu.passage.upperware.commons;

import java.io.File;

/**
 * Created by pszkup on 13.09.17.
 */
public class MelodicConstants {

    public static final String APP_COMPONENT_VAR_PREFIX= "U_app_component_";
    public static final String APP_COMPONENT_VAR_MID= "_vm_";
    public static final String APP_COMPONENT_VAR_SUFFIX= "_provider_";

    public static final String CDO_SERVER_PATH= "upperware-models/";
    public static final String FMS_APP_CDO_SERVER_PATH = CDO_SERVER_PATH + "fms/";

    //TODO - to remove - begin
    public static final String FUNCTION_TYPES_ID="cpGenerator-functionTypes";
    public static final String OPERATING_SYSTEMS_ID="cpGenerator-operatingSystems";
    public static final String LOCATIONS_ID="cpGenerator-locations";
    public static final String PROVIDER_TYPES_ID="cpGenerator-providerTypes";

    public static final String FUNCTION_TYPES_FILE = "FunctionTypes.xmi";
    public static final String OPERATING_SYSTEMS_FILE = "OperatingSystems.xmi";
    public static final String LOCATIONS_FILE = "Locations.xmi";
    public static final String PROVIDER_TYPES_FILE = "ProviderTypes.xmi";
//TODO - to remove - end

    public static final String GENERATION_DIR= "paasage" + File.separator + "configurations" + File.separator;
    public static final String TOMCAT_ALT_TEM_DIR= File.separator+"tmp"+File.separator;
    public static final String TOMCAT_TEM_DIR= ".."+File.separator+"temp"+File.separator;
    public static final String TOMCAT_GENERATION_DIR= TOMCAT_TEM_DIR+"paasage"+File.separator+"configurations"+File.separator;
    public static final String TOMCAT_ALT_GENERATION_TEMP_DIR= TOMCAT_ALT_TEM_DIR+"paasage"+File.separator+"configurations"+File.separator;

    public static final String PAASAGE_CONFIGURATION_MODEL_FILE_NAME= "paasageConfigurationModel.xmi";
    public static final String CP_MODEL_FILE_NAME= "cpModel.xmi";
    public static final String APP_MODEL_FILE_NAME= "appModel.xmi";

    private MelodicConstants() {}
}
