package e.melodic.upperware.dlms.client;

import e.melodic.upperware.dlms.DataSource;
import e.melodic.upperware.dlms.DataSourceType;
import e.melodic.upperware.dlms.MigrationData;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Client component for the DLMS webservice. The client can be used from inside an application or called directly from the command line.
 */
public class DLMSWebServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DLMSWebServiceClient.class);

    private static final String DATASOURCE_SERVER_URL = "http://localhost:14000/ds";
    private static final String MIGRATION_SERVER_URL = "http://localhost:14000/migrate";

    /**
     * Main method for use from the command line.
     * Arguments must be the mode for the method to be called plus the parameters required by that method in the correct order (see method run() below for details).
     */
    public static void main(String args[]) {
        new DLMSWebServiceClient().run(args);
    }

    private void run(String... args) {
        String mode = args[0];

        switch(mode) {
            // get all DSs
            case "GETALL": {
                List<DataSource> result = getAllDatasources();
                LOGGER.info(result.toString());
                break;
            }
            // get one DS
            case "GET": {
                long id = Long.valueOf(args[1]);
                DataSource result = getDatasourceForId(id);
                LOGGER.info(result.toString());
                break;
            }
            // add DS
            case "POST": {
                String name = args[1];
                String type = args[2];
                String mountPoint = args[3];
                String ufsURI = args[4];
                DataSource dataSource = new DataSource(name, DataSourceType.valueOf(type), ufsURI, mountPoint);
                addDatasource(dataSource);
                break;
            }
            // update DS
            case "PUT": {
                long id = Long.valueOf(args[1]);
                String name = args[2];
                String type = args[3];
                String mountPoint = args[4];
                String ufsURI = args[5];
                DataSource dataSource = new DataSource(name, DataSourceType.valueOf(type), ufsURI, mountPoint);
                updateDatasource(id, dataSource);
                break;
            }
            // delete DS
            case "DELETE": {
                long id = Long.valueOf(args[1]);
                deleteDatasource(id);
                break;
            }
            case "FILE": {
                String pathFrom = args[1];
                String pathTo = args[2];
                migrateFile(pathFrom, pathTo);
                break;
            }
            case "DIR": {
                String pathFrom = args[1];
                String pathTo = args[2];
                migrateDirectory(pathFrom, pathTo);
                break;
            }
            case "DS": {
                String pathTo = args[1];
                long id = Long.valueOf(args[2]);
                migrateDatasource(pathTo, id);
                break;
            }
            default: {
                throw new IllegalStateException("Unsupported operation " + mode);
            }
        }
    }

    /**
     * Calls the service and returns a list containing all datasources in the database.
     */
    public List<DataSource> getAllDatasources() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(DATASOURCE_SERVER_URL);
            HttpHeaders headers = createHeaders();
            HttpEntity<DataSource> entity = new HttpEntity<>(null, headers);
            ResponseEntity<List> response = restTemplate.exchange(uri, HttpMethod.GET, entity, List.class);

            return extractDataSourcesFromResponse(response);
        }
        catch(URISyntaxException | RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    private List<DataSource> extractDataSourcesFromResponse(ResponseEntity<List> response) {
        List<DataSource> dataSources = new ArrayList<>(response.getBody().size());
        for(Object row : response.getBody()) {
            Map<String, Object> map = (Map<String, Object>) row;
            DataSource ds = new DataSource();
            ds.setId(((Number) map.get("id")).longValue());
            ds.setName((String) map.get("name"));
            String type = (String) map.get("type");
            ds.setDataSourceType(type == null ? null : DataSourceType.valueOf(type));
            ds.setUfsURI((String) map.get("ufsURI"));
            ds.setMountPoint((String) map.get("mountPoint"));
            dataSources.add(ds);
        }

        return dataSources;
    }

    /**
     * Calls the service and returns a single datasource corresponding to the given id.
     */
    public DataSource getDatasourceForId(long id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(DATASOURCE_SERVER_URL + '/' + id);
            HttpHeaders headers = createHeaders();
            HttpEntity<DataSource> entity = new HttpEntity<>(null, headers);
            ResponseEntity<DataSource> response = restTemplate.exchange(uri, HttpMethod.GET, entity, DataSource.class);
            return response.getBody();
        }
        catch(URISyntaxException | RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Calls the service and adds this datasource.
     */
    public void addDatasource(DataSource dataSource) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(DATASOURCE_SERVER_URL);
            HttpHeaders headers = createHeaders();
            HttpEntity<DataSource> entity = new HttpEntity<>(dataSource, headers);
            restTemplate.exchange(uri, HttpMethod.POST, entity, void.class);
        }
        catch(URISyntaxException | RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Calls the service and updates the datasource with the given id with the data from the given datasource object.
     */
    public void updateDatasource(long id, DataSource dataSource) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(DATASOURCE_SERVER_URL + '/' + id);
            HttpHeaders headers = createHeaders();
            HttpEntity<DataSource> entity = new HttpEntity<>(dataSource, headers);
            restTemplate.exchange(uri, HttpMethod.PUT, entity, void.class);
        }
        catch(URISyntaxException | RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Deletes the datasource with the given id.
     */
    public void deleteDatasource(long id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(DATASOURCE_SERVER_URL + '/' + id);
            HttpHeaders headers = createHeaders();
            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            restTemplate.exchange(uri, HttpMethod.DELETE, entity, void.class);
        }
        catch(URISyntaxException | RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Migrates (moves) a file from pathFrom to pathTo.
     */
    public void migrateFile(String pathFrom, String pathTo) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(MIGRATION_SERVER_URL + "/file");
            HttpHeaders headers = createHeaders();
            MigrationData migrationData = new MigrationData(pathFrom, pathTo);
            HttpEntity<MigrationData> entity = new HttpEntity<>(migrationData, headers);
            restTemplate.exchange(uri, HttpMethod.POST, entity, void.class);
        }
        catch(URISyntaxException | RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Migrates (moves) a directory from pathFrom to pathTo.
     */
    public void migrateDirectory(String pathFrom, String pathTo) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(MIGRATION_SERVER_URL + "/dir");
            HttpHeaders headers = createHeaders();
            MigrationData migrationData = new MigrationData(pathFrom, pathTo);
            HttpEntity<MigrationData> entity = new HttpEntity<>(migrationData, headers);
            restTemplate.exchange(uri, HttpMethod.POST, entity, void.class);
        }
        catch(URISyntaxException | RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Migrates (moves) the complete datasource with the given id to the location in pathTo.
     */
    public void migrateDatasource(String pathTo, long id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = new URI(MIGRATION_SERVER_URL + "/ds");
            HttpHeaders headers = createHeaders();
            MigrationData migrationData = new MigrationData(id, null, pathTo);
            HttpEntity<MigrationData> entity = new HttpEntity<>(migrationData, headers);
            restTemplate.exchange(uri, HttpMethod.POST, entity, void.class);
        }
        catch(URISyntaxException | RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Creates the HTTPHeaders with the security information.
     */
    private HttpHeaders createHeaders() {
        return new HttpHeaders() {{
            String auth = "user" + ":" + "9687831d-a0bd-4d7a-993d-5d31e740749b";
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }
}
