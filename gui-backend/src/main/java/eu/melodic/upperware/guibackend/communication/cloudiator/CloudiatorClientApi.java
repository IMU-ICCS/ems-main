package eu.melodic.upperware.guibackend.communication.cloudiator;

import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.CloudApi;
import io.github.cloudiator.rest.api.SecurityApi;
import io.github.cloudiator.rest.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CloudiatorClientApi implements CloudiatorApi {

    private CloudApi cloudApi;
    private SecurityApi securityApi;
    private final String CLOUDIATOR_ERROR_MESSAGE = "Problem in communication with Cloudiator. Cloudiator not working. Please try again.";

    @Override
    public Integer getDiscoveryStatusTotal() {
        try {
            return Integer.valueOf(cloudApi.discoveryStatus().getOrDefault("total", "0"));
        } catch (ApiException e) {
            log.error("Error by getting total number of offers.", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Hardware> getHardwareList() {
        try {
            List<Hardware> hardware = cloudApi.findHardware(null);
            log.info("Number of hardware in response = {}", hardware.size());
            return hardware;
        } catch (ApiException e) {
            log.error("Error by getting hardware list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Location> getLocationList() {
        try {
            List<Location> locations = cloudApi.findLocations(null);
            log.info("Number of locations in response: {}", locations.size());
            return locations;
        } catch (ApiException e) {
            log.error("Error by getting location list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Image> getImageList() {
        try {
            List<Image> images = cloudApi.findImages(null);
            log.info("Number of images in response: {}", images.size());
            return images;
        } catch (ApiException e) {
            log.error("Error by getting images list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Cloud> getCloudList() {
        try {
            List<Cloud> clouds = cloudApi.findClouds();
            log.info("Number of clouds in response: {}", clouds.size());
            return clouds;
        } catch (ApiException e) {
            log.error("Error by getting clouds list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Function> getFunctionList() {
        try {
            List<Function> functions = cloudApi.findFunctions();
            log.info("Number of functions in response: {}", functions.size());
            return functions;
        } catch (ApiException e) {
            log.error("Error by getting functions list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<VirtualMachine> getVMList() {
        try {
            List<VirtualMachine> vMs = cloudApi.findVMs(null);
            log.info("Number of VMs in response: {}", vMs.size());
            return vMs;
        } catch (ApiException e) {
            log.error("Error by getting VMs list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public void storeSecureVariable(String key, String value) {
        Text cloudiatorText = new Text();
        try {
            securityApi.storeSecure(key, cloudiatorText.content(value));
        } catch (ApiException ex) {
            log.error("Error by secure storing of variable with name: {}", key, ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Error by storing secure variable with name: %s in Cloudiator's secure store", key));
        }
    }
}
