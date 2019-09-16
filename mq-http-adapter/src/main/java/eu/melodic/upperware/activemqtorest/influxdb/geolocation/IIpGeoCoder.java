package eu.melodic.upperware.activemqtorest.influxdb.geolocation;

public interface IIpGeoCoder {
	String getCountryCode(String ipAddress);
}
