package eu.melodic.upperware.activemqtorest;

import java.util.regex.Pattern;

public class MorphemicTopicsMatcher {

    public static boolean isPredictionTopic(String topic) {
        Pattern pattern = Pattern.compile("topic://(intermediate_)?prediction\\.(?!\\bslo_severity_value\\b$)\\D*");
        return pattern.matcher(topic).matches();
    }

    public static boolean isSloSeverityValueTopic(String topic) {
        return "topic://prediction.slo_severity_value".equals(topic);
    }

    public static boolean isTrainingModels(String topic) {
        return "topic://training_models".equals(topic);
    }

    public static boolean isStartEnsembler(String topic) {
        return "topic://start_ensembler".equals(topic);
    }

    public static boolean isMetricsToPredict(String topic) {
        return "topic://metrics_to_predict".equals(topic);
    }

    public static boolean isThresholdTopic(String topic) {
        return topic.contains("_ui_threshold_info");
    }

    public static boolean isInstanceInfoTopic(String topic) {
        return topic.contains("_ui_instance_info");
    }

    public static boolean isMetricTopic(String topic) {
        Pattern pattern = Pattern.compile("topic://\\w+");
        return (!isTrainingModels(topic)) && (!isMetricsToPredict(topic)) &&
                (!isThresholdTopic(topic)) && (!isInstanceInfoTopic(topic)) &&
                (!isStartEnsembler(topic)) &&
                pattern.matcher(topic).matches();
    }
}
