package eu.melodic.upperware.guibackend.communication.commons;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class RestCommunicationService {

    private RestTemplate restTemplate;

    public <T, G> ResponseEntity<T> getResponse(String requestUrl, ParameterizedTypeReference<T> responseType,
                                                HttpEntity<G> requestBody, String serviceName, HttpMethod httpMethod) {
        ResponseEntity<T> response;

        try {
            response = restTemplate.exchange(requestUrl, httpMethod, requestBody, responseType);
        } catch (ResourceAccessException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem in communication with %s. Service not working. Please try again.", serviceName));
        } catch (HttpServerErrorException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requested resource doesn't exist");
        } catch (HttpClientErrorException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization failed. Invalid credentials");
        }

        if (!HttpStatus.OK.equals(response.getStatusCode()) || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem in communication with %s. Service not working. Please try again.", serviceName));
        }

        return response;
    }
}
