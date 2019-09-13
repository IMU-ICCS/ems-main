package eu.melodic.upperware.dlms.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
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
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.dlmsws.access")
public class DLMSDataSourceAccess {

	@Valid
	@NotNull
	private DataSource dataSource;

	@Getter
	@Setter
	public static class DataSource {
		@NotBlank
		private String access;

		private Map<String, List<String>> accountMap = new HashMap<>();

		public void computeAccount() {
			if (StringUtils.isNotBlank(access)) {
				for (String account : access.split(";")) {
					String[] accoundText = account.trim().split(",");
					List<String> user = new ArrayList<>();
					user.add(accoundText[1]);
					user.add(accoundText[2]);

					accountMap.put(accoundText[0], user);
				}
			}
		}
	}

}
