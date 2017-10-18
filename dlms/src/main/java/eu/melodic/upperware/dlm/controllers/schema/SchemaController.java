package eu.melodic.upperware.dlm.controllers.schema;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path="/schema") 
public class SchemaController {
	@Autowired 
	private SchemaRepository schemaRepository;
	
	@GetMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewSchema (@RequestParam String userName
			, @RequestParam String schema) {
		DbSchema n = new DbSchema();
		n.setUserName(userName);
		n.setSchema(schema);
		schemaRepository.save(n);
		return "Saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<DbSchema> getAllSchema() {
		return schemaRepository.findAll();
	}
	
}