package eu.paasage.upperware.security.server;

import eu.melodic.models.interfaces.security.UserRequest;
import eu.paasage.upperware.security.server.user.ApplicationUser;
import eu.paasage.upperware.security.server.user.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

	private ApplicationUserRepository applicationUserRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/sign-up")
	public ResponseEntity<Object> signUp(@RequestBody UserRequest user) {
		if (applicationUserRepository.existsByUsername(user.getUsername())){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is used, please try with another username");
		}
		ApplicationUser applicationUser = ApplicationUser.of(user);
		applicationUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		applicationUserRepository.save(applicationUser);
		return ResponseEntity.status(HttpStatus.CREATED).body("User created");
	}

}
