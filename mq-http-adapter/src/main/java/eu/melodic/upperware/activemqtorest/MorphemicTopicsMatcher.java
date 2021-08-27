package eu.melodic.upperware.activemqtorest;

import java.util.regex.Pattern;

public class MorphemicTopicsMatcher {

    public static boolean isPredictionTopic(String topic) {
        Pattern pattern = Pattern.compile("prediction\\.(?!\\bslo_severity_value\\b$)\\D*");
        return pattern.matcher(topic).matches();
    }

    public static boolean isThresholdTopic(String topic) {
        return topic.contains("_ui_threshold_info");
    }

    public static boolean isInstanceInfoTopic(String topic) {
        return topic.contains("_ui_instance_info");
    }

    public static boolean isMetricTopic(String topic) {
        Pattern pattern = Pattern.compile("\\w+");
        return pattern.matcher(topic).matches();
    }
}
