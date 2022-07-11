package eu.melodic.upperware.adapter.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class CamelInstanceNamingService {
    private static final String SEPARATOR_NAME_SIGN = "-";
    private static final String REQUIRED_HOST_LABEl = "RequiredHostInstance";
    private static final String REQUIRED_COMMUNICATION_LABEL = "ReqCommunicationInstance";
    private static final String PROVIDED_COMMUNICATION_LABEL = "ProvidedCommunicationInstance";


    public static int getInstanceNumberFromInstanceName(String softwareComponentInstanceName) {
        return Integer.parseInt(StringUtils.substringAfterLast(softwareComponentInstanceName, SEPARATOR_NAME_SIGN));
    }

    public static String getSoftwareComponentNameFromInstanceName(String softwareComponentInstanceName) {
        return removeSuffix(removeSuffix(softwareComponentInstanceName));
    }
    
    static String createSoftwareInstanceName(String softwareComponentName, int instanceNo) {
        String instanceName = createSoftCompInstNamePrefix(softwareComponentName) + SEPARATOR_NAME_SIGN +
                "instance" + SEPARATOR_NAME_SIGN + instanceNo;
        log.info("Created instance name = {} for software component : {}", instanceName, softwareComponentName);
        return instanceName;
    }

    static String createRequiredHostName(String prefix, String requiredHostName, int instanceNo) {
        return createLabeledName(prefix, REQUIRED_HOST_LABEl, requiredHostName, instanceNo);
    }

    static String createRequiredCommunicationName(String prefix, String requiredCommunicationName, int instanceNo) {
        return createLabeledName(prefix, REQUIRED_COMMUNICATION_LABEL, requiredCommunicationName, instanceNo);
    }

    static String createProvidedCommunicationName(String prefix, String providedCommunicationName, int instanceNo) {
        return createLabeledName(prefix, PROVIDED_COMMUNICATION_LABEL, providedCommunicationName, instanceNo);
    }



    private static String createLabeledName(String prefix, String label, String name, int instanceNo) {
        String labeledName = prefix + SEPARATOR_NAME_SIGN + label + SEPARATOR_NAME_SIGN + instanceNo;
        log.info("Created name = {} for object = {}", labeledName, name);
        return labeledName;
    }

    private static String createSoftCompInstNamePrefix(String name) {
        // 1. add separator after each capital letter except first
        String result = name.replaceAll("([A-Z])", SEPARATOR_NAME_SIGN + "$1");
        result = result.startsWith(SEPARATOR_NAME_SIGN) ? result.substring(1) : result;
        result = result.endsWith(SEPARATOR_NAME_SIGN) ? result.substring(0, result.length() - 2) : result;

        // 2. to lower case
        result = result.toLowerCase();

        // 3. remove all special signs except separator, lowercase and digits
        result = result.replaceAll("[^-a-z0-9]", "");

        return result;
    }

    private static String removeSuffix(String name) {
        return StringUtils.substringBeforeLast(name, SEPARATOR_NAME_SIGN);
    }

}
