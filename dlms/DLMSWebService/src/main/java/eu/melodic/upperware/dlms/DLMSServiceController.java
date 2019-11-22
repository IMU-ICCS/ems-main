/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Controller
 * @author: ferox
 */

package eu.melodic.upperware.dlms;

import eu.melodic.models.interfaces.dlms.ConfigurationResponse;
import eu.melodic.models.interfaces.dlms.ConfigurationResponseImpl;
import eu.melodic.models.interfaces.dlms.DataModelRequest;
import eu.melodic.upperware.dlms.component.ComponentId;
import eu.melodic.upperware.dlms.component.ConnectedComponentsFinder;
import eu.melodic.upperware.dlms.component.SendToDlmsAgent;
import io.github.cloudiator.rest.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Webservice controller for the DLMS service.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class DLMSServiceController {

    private final DLMSCoordinator dlmsCoordinator;

    private final DLMSService dlmsService;
    private final ComponentId comp;

    /**
     * Returns all datasources in the database.
     */
    @GetMapping(value = "/ds")
    public List<DataSource> getDataSources() {
        return dlmsService.getAllDataSources();
    }

    /**
     * Returns one datasource matching the given id.
     */
    @GetMapping(value = "/ds/id/{id}")
    public DataSource getDataSource(@PathVariable("id") Long id) {
        return dlmsService.getDataSourceById(id);
    }

    /**
     * Returns one datasource matching the given name.
     */
    @GetMapping(value = "/ds/name/{name}")
    public DataSource getDataSource(@PathVariable("name") String name) {
        return dlmsService.getDataSourceByName(name);
    }

    /**
     * Unmounts and deletes the datasource matching the given id.
     */
    @DeleteMapping("/ds/id/{id}")
    public void deleteDataSource(@PathVariable Long id) {
        dlmsService.deleteById(id);
    }

    /**
     * Unmounts and deletes the datasource matching the given name.
     */
    @DeleteMapping("/ds/name/{name}")
    public void deleteDataSource(@PathVariable String name) {
        dlmsService.deleteByName(name);
    }

    /**
     * Get a list of application component and linked data sources
     */
    @GetMapping("/ac")
    public List<AcDsMountPoint> getAppCompDataSource() {
        return dlmsService.getAllAcDsMp();
    }

    /**
     * Returns one data source and mount point linked with the component name.
     */
    @GetMapping(value = "/ac/{name}")
    public AcDsMountPoint getAppCompDataSource(@PathVariable("name") String name) {
        return dlmsService.getAcDsMpByName(name);
    }

    /**
     * Returns command and the component name.
     */
    @GetMapping(value = "/getDlmsAgentParams/{ip}")
    public SendToDlmsAgent getDlmsAgentParams(@PathVariable("ip") String ip) throws ApiException {
        log.info("Invoking getDlmsAgentParams with IP: {}", ip);

        final Optional<String> componentId = comp.findComponentId(ip);
        if (componentId.isPresent()) {
            final String cmpName = componentId.get();
            final SendToDlmsAgent toSend = dlmsService.getDlmsAgentParams(cmpName);
            log.info("Sending getAlluxioCmd response for IP: {}. Result: [cmpId:{}, metric:{}]", ip, cmpName,
                    toSend.getMetricName());
            return toSend;
        } else {
            log.info("There is no data source for componentId with IP: {}.", ip);
            return new SendToDlmsAgent("", "", null, null);
        }
    }

    /**
     * Adds/updates the datasource from the camel model to the database and mounts
     * the mount point
     */
    @PostMapping("/dataModel")
    public void addUpdateDataSources(@Valid @RequestBody DataModelRequest dataModelRequest) {
        log.info("The name of the camel model is {}", dataModelRequest.getApplicationId());
        dlmsCoordinator.doAddUpdateDataSourcesWork(
                dataModelRequest.getApplicationId(),
                dataModelRequest.getNotificationURI(),
                dataModelRequest.getWatermark().getUuid());
    }

    /**
     * Updates the datasource with the given id with the data provided in the
     * datasource object.
     */
    @PutMapping("/ds/id/{id}")
    public ResponseEntity<Object> updateDataSource(@RequestBody DataSource ds, @PathVariable Long id) {
        dlmsService.updateDataSource(ds, id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the datasource with the given name with the data provided in the
     * datasource object.
     */
    @PutMapping("/ds/name/{name}")
    public ResponseEntity<Object> updateDataSource(@RequestBody DataSource ds, @PathVariable String name) {
        dlmsService.updateDataSource(ds, name);
        return ResponseEntity.noContent().build();
    }

    /**
     * Migrates (moves) a file from pathFrom to pathTo in the MigrationData object.
     */
    @PostMapping("/migrate/file")
    public void migrateFile(@RequestBody MigrationData migrationData) {
        dlmsService.migrateFile(migrationData.getPathFrom(), migrationData.getPathTo());
    }

    /**
     * Migrates (moves) a directory from pathFrom to pathTo in the MigrationData
     * object.
     */
    @PostMapping("/migrate/dir")
    public void migrateDirectory(@RequestBody MigrationData migrationData) {
        dlmsService.migrateDirectory(migrationData.getPathFrom(), migrationData.getPathTo());
    }

    /**
     * Migrates (moves) the complete datasource (and all data) with the given id in
     * the MigrationData object to the location in pathTo (in the MigrationData
     * object).
     */
    @PostMapping("/migrate/ds")
    public void migrateDatasource(@RequestBody MigrationData migrationData) {
        dlmsService.migrateDatasource(migrationData.getId(), migrationData.getPathTo());
    }

    /**
     * Returns configuration list for the node of a given ip.
     * Element of a list is of a Configuration object of a proper type
     * (note: current version returns LatencyConfiguration as a default type)
     */
    @GetMapping(value = "/getConfiguration/{ip}")
    public ConfigurationResponse getConfiguration(@PathVariable("ip") String ip) throws ApiException {
        log.info("Invoking getConfiguration with IP: {}", ip);

        ConfigurationResponse configResp = new ConfigurationResponseImpl();
        final String agentNodeName = comp.findNodeName(ip)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Cloudiator could not find node name for a given agent IP: " + ip));
        configResp.setConfigurations(new ConnectedComponentsFinder(agentNodeName, comp).createConfigurationList());
        return configResp;
    }
}