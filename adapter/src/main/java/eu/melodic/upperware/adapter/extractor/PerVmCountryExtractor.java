package eu.melodic.upperware.adapter.extractor;

import io.github.cloudiator.rest.model.GeoLocation;
import io.github.cloudiator.rest.model.Location;
import io.github.cloudiator.rest.model.NodeCandidate;
import org.springframework.stereotype.Service;

@Service
public class PerVmCountryExtractor extends PerVmAbstractExtractor<String> {
    @Override
    public String getKey() {
        return "per-vm-countries";
    }

    @Override
	protected String extractInfo(NodeCandidate nodeCandidate) {
		String vmCountry = null;
		Location vmLoc = nodeCandidate.getHardware().getLocation();
		if (vmLoc==null) vmLoc = nodeCandidate.getLocation();
		if (vmLoc!=null) {
			GeoLocation geoLoc = vmLoc.getGeoLocation();
			if (geoLoc!=null) {
				vmCountry = geoLoc.getCountry();
			}
		}
		return com.google.common.base.Strings.nullToEmpty(vmCountry);
    }
}
