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

    public static final String GENERATION_DIR= "paasage" + File.separator + "configurations" + File.separator;
    public static final String TOMCAT_ALT_TEM_DIR= File.separator+"tmp"+File.separator;
    public static final String TOMCAT_TEM_DIR= ".."+File.separator+"temp"+File.separator;
    public static final String TOMCAT_GENERATION_DIR= TOMCAT_TEM_DIR+"paasage"+File.separator+"configurations"+File.separator;
    public static final String TOMCAT_ALT_GENERATION_TEMP_DIR= TOMCAT_ALT_TEM_DIR+"paasage"+File.separator+"configurations"+File.separator;

    public static final String PAASAGE_CONFIGURATION_MODEL_FILE_NAME= "paasageConfigurationModel.xmi";
    public static final String CP_MODEL_FILE_NAME= "cpModel.xmi";

    private MelodicConstants() {}
}
