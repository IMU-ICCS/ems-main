package eu.melodic.upperware.adapter.proactive.client;

import eu.melodic.upperware.adapter.exception.ProactiveClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.builder.fluent.PropertiesBuilderParameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.convert.ListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Objects;

@Slf4j
public class ProactiveClientConfiguration {

    /**
     * The default configuration file
     */
    public static final String PROPERTIES_FILE = "proactive.properties";

    public static final String REST_URL = "pa.rest.url";

    public static final String REST_LOGIN = "pa.rest.login";

    public static final String REST_PASSWORD = "pa.rest.password";

    public static final String SCHEDULER_REST_URL = "pa.scheduler.rest.url";

    public static final String RM_REST_URL = "pa.rm.rest.url";

    private static final ListDelimiterHandler DELIMITER = new DefaultListDelimiterHandler(';');

    private static final String SEPARATOR = File.separator;

    private ProactiveClientConfiguration() {
    }

    /**
     * Load ProActive configuration
     * @return A ProActive configuration
     * @throws ConfigurationException If a problem occurs when loading ProActive configuration
     */
    public static Configuration loadPAConfiguration() throws ConfigurationException, ProactiveClientException {
        String path = Objects.requireNonNull(ProactiveClientConfiguration.class.getClassLoader().getResource(ProactiveClientConfiguration.PROPERTIES_FILE)).toExternalForm();

        log.info("Loading ProActive configuration from file: " + path);

        if(StringUtils.isNotEmpty(path)) {
            return loadConfig(path);
        } else {
            throw new ProactiveClientException(String.format("ProActive configuration file path is incorrect (got path: %s)", path));
        }
    }

    /**
     * Loads the configuration of ProActive.
     * @param path configuration file to load
     * @return A ProActive configuration
     * @throws ConfigurationException If a problem occurs when loading ProActive configuration
     */
    private static Configuration loadConfig(String path) throws ConfigurationException {

        Configuration config;

        PropertiesBuilderParameters propertyParameters = new Parameters().properties();
        propertyParameters.setPath(path);
        propertyParameters.setThrowExceptionOnMissing(true);
        propertyParameters.setListDelimiterHandler(DELIMITER);

        FileBasedConfigurationBuilder<PropertiesConfiguration> builder = new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class);

        builder.configure(propertyParameters);

        config = builder.getConfiguration();

        log.debug("ProActive configuration loaded");

        return config;
    }
}
