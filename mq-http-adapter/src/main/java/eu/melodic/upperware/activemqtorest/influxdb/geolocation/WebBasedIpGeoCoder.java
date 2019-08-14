package eu.melodic.upperware.activemqtorest.influxdb.geolocation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebBasedIpGeoCoder implements IIpGeoCoder {

	//TODO change service or use PRO ACCOUNT in case of real commercial usage outside of research!
	// --> see legal terms/policy under http://ip-api.com/docs/legal
	private static final String HTTP_URL_SCHEME = "http://ip-api.com/json/%s";
	private static final String COUNTRY_CODE = "countryCode";
	private static final String ERROR_MESSAGE = "message";

	private RestTemplate restTemplate;

	public WebBasedIpGeoCoder() {
		restTemplate = new RestTemplate();
	}

	@Override
	public String getCountryCode(String ipAddress) {
		Optional<JsonObject> completeJsonResult = getCompleteJsonResult(ipAddress);
		if (completeJsonResult.isPresent()) {
			if (completeJsonResult.get().has(ERROR_MESSAGE)) {
				return StringUtils.EMPTY;
			} else if (completeJsonResult.get().has(COUNTRY_CODE)) {
				return completeJsonResult.get().get(COUNTRY_CODE).getAsString();
			}
		}
		return StringUtils.EMPTY;
	}

	private Optional<JsonObject> getCompleteJsonResult(String ipAddress) {
		if (ipAddress == null || ipAddress.isEmpty()) {
			return Optional.empty();
		}

		String httpGetUrl = String.format(HTTP_URL_SCHEME, ipAddress);
		HttpEntity<String> requestEntity = new HttpEntity<>(StringUtils.EMPTY);
		ResponseEntity<String> responseEntity = restTemplate.exchange(httpGetUrl, HttpMethod.GET, requestEntity, String.class);

		if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			log.debug("Successfuly retrieved IP from GeoCoder API.");
			try {
				JsonObject jsonObject = new JsonParser().parse(responseEntity.getBody()).getAsJsonObject();
				return Optional.of(jsonObject);
			} catch (JsonSyntaxException | NullPointerException x) {
				log.warn("Error during JSON result parsing.");
				return Optional.empty();
			}
		}
		log.warn("Could not retrieve IP from GeoCoder API. Returning empty value.");
		return Optional.empty();
	}
}
