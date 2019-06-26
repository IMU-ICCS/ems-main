package eu.melodic.upperware.guibackend.communication.mule;

import eu.melodic.models.services.frontend.DeploymentProcessRequest;
import eu.melodic.upperware.guibackend.controller.deployment.response.DeploymentResponse;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class MuleClientApi implements MuleApi {

    private RestTemplate restTemplate;
    private GuiBackendProperties guiBackendProperties;

    @Override
    public DeploymentResponse createDeploymentProcess(DeploymentProcessRequest deploymentProcessRequest) {
        HttpEntity entity = createHttpEntity(deploymentProcessRequest);

        String muleUrl = guiBackendProperties.getEsb().getUrl() + "/api/frontend/deploymentProcess";
        ResponseEntity<DeploymentResponse> processResponse;

        try {
            if (!guiBackendProperties.getEsb().isSslVerificationEnabled()) {
                log.info("Disable SSL verification");
                disableSslVerification();
            }
            processResponse = restTemplate.exchange(muleUrl, HttpMethod.POST, entity, DeploymentResponse.class);

            if (processResponse.getStatusCode() != HttpStatus.OK) {
                throw new ResponseStatusException(processResponse.getStatusCode(), "Problem with starting your application");
            }

            // fail authorization as Melodic user
            if (processResponse.getBody() != null && processResponse.getBody().getProcessAuthorizationStatus() != 200) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials for Melodic");
            }

            // Cloudiator service not working
            if (processResponse.getBody() != null && processResponse.getBody().getCreateCloudsResponse() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem in connection with internal service. Please try again or restart Melodic machine.");
            }

            // catch exception if Mule not working
        } catch (ResourceAccessException ex) {
            log.error("Error by connection with Mule service.", ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem in connection with internal service. Please try again or restart Melodic machine");
        }

        return processResponse.getBody();
    }


    private HttpEntity<DeploymentProcessRequest> createHttpEntity(DeploymentProcessRequest deploymentProcessRequest) {
        HttpHeaders httpHeaders = createHttpHeaders();
        return new HttpEntity<>(deploymentProcessRequest, httpHeaders);
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

}
