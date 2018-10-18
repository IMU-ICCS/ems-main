package eu.melodic.dlms.algorithms.restController;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.melodic.dlms.algorithms.dbModel.DataCenter;
import eu.melodic.dlms.algorithms.exception.ResourceNotFoundException;
import eu.melodic.dlms.algorithms.repository.DataCenterRepository;

@RestController
@RequestMapping("/api")
public class DataCenterController {

	@Autowired
	DataCenterRepository dataCenterRespository;

	// Get All cloud providers
	@GetMapping("/datacenter")
	public List<DataCenter> getAllRegion() {
		return dataCenterRespository.findAll();
	}

	// Create a new cloud provider
	@PostMapping("/datacenter")
	public DataCenter createRegion(@Valid @RequestBody DataCenter dataCenter) {
		return dataCenterRespository.save(dataCenter);
	}

	// Get a single cloud provider
	@GetMapping("/datacenter/{id}")
	public DataCenter getRegionById(@PathVariable(value = "id") Long dataCenterId) {
		return dataCenterRespository.findById(dataCenterId)
				.orElseThrow(() -> new ResourceNotFoundException("DataCenter", "id", dataCenterId));
	}

}
