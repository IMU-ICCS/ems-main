package eu.melodic.dlms.algorithms.metric_sender;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

/**
 * For selecting random data center at specific region selection
 * This class is only for TEST and should be commented in production
 */
public class DC_Region {

	protected static final Map<String,String> MYHASH;
    static{
        Hashtable<String,String> VALUES = 
            new Hashtable<String,String>();
        VALUES.put("us-east-1", "Virginia");
        VALUES.put("us-east-2", "Ohio");
        VALUES.put("us-west-1", "California");
        VALUES.put("us-west-2", "Oregon");
        VALUES.put("ca-central-1", "Central");
        VALUES.put("eu-central-1", "Frankfurt");
        VALUES.put("eu-west-1", "Ireland");
        VALUES.put("eu-west-2", "London");
        VALUES.put("eu-west-3", "Paris");
        VALUES.put("ap-northeast-1", "Tokyo");
        VALUES.put("ap-northeast-2", "Seoul");
        VALUES.put("ap-northeast-3", "Osaka");
        VALUES.put("ap-southeast-1", "Singapore");
        VALUES.put("ap-southeast-2", "Sydney");
        VALUES.put("ap-south-1", "Mumbai");
        VALUES.put("sa-east-1", "São Paulo");
        VALUES.put("East-US", "Virginia");
        VALUES.put("East-US-2", "Virginia");
        VALUES.put("Central-US", "Iowa");
        VALUES.put("North-Central-US", "Illinois");
        VALUES.put("South-Central-US", "Texas");
        VALUES.put("West Central-US", "Wyoming");
        VALUES.put("West-US", "California");
        VALUES.put("West-US-2", "Washington");
        VALUES.put("Canada-East", "Quebec City");
        VALUES.put("Brazil-South", "Sao Paulo State");
        VALUES.put("North-Europe", "Ireland");
        VALUES.put("West-Europe", "Netherlands");
        VALUES.put("France-Central", "Paris");
        VALUES.put("France-South", "Marseille");
        VALUES.put("UK-West", "Cardiff");
        VALUES.put("UK-South", "London");
        VALUES.put("Germany Central", "Frankfurt");
        VALUES.put("Germany-Northeast", "Magdeburg");
        VALUES.put("Switzerland-North", "Zurich");
        VALUES.put("Switzerland-West", "Geneva");
        VALUES.put("Norway-East", "Norway");
        VALUES.put("Southeast-Asia", "Singapore");
        VALUES.put("East-Asia", "Hong Kong");
        VALUES.put("Australia-East", "New South Wales");
        VALUES.put("Australia-Southeast", "Victoria");
        VALUES.put("Australia-Central", "Canberra");
        VALUES.put("China-East", "Shanghai");
        VALUES.put("China-North", "Beijing");
        VALUES.put("Central India", "Pune");
        VALUES.put("West-India", "Mumbai");
        VALUES.put("South-India", "Chennai");
        VALUES.put("Japan-East", "Tokyo");
        VALUES.put("Japan-West", "Osaka");
        VALUES.put("Korea-Central", "Seoul");
        VALUES.put("Korea-South", "Busan");
        VALUES.put("South-Africa-West", "Cape Town");
        VALUES.put("South-Africa-North", "Johannesburg");
        VALUES.put("UAE-Central", "Abu Dhabi");
        VALUES.put("UAE-North", "Dubai");

        MYHASH = Collections.unmodifiableMap(VALUES);
    }
	
}
