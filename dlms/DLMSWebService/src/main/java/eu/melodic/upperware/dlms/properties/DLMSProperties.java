package eu.melodic.upperware.dlms.properties;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.dlmsws.properties")
public class DLMSProperties {

	@Getter
	@Setter
	private Alluxio alluxio;

	@Getter
	@Setter
	public static class Alluxio {
		@NotNull
		private Master master;

		public static class Master {
			@NotBlank
			private String hostname;

		}
	}

	@Valid
	@NotNull
	private Esb esb;

	@Getter
	@Setter
	public static class Esb {
		@NotBlank
		private String url;

	}

}
