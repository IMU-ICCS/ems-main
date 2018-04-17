package eu.paasage.upperware.security.server;

import eu.melodic.models.interfaces.security.UserRequest;
import eu.paasage.upperware.security.server.user.ApplicationUser;
import eu.paasage.upperware.security.server.user.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
	public void signUp(@RequestBody UserRequest user) {
		ApplicationUser applicationUser = ApplicationUser.of(user);
		applicationUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		applicationUserRepository.save(applicationUser);
	}

}
