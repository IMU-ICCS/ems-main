package eu.melodic.upperware.dlms.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IpAddress {

	private IpAddressType IpAddressType;
	private IpVersion IpVersion;
	private String value;
	
}
