package eu.paasage.upperware.security.server.controller;

import eu.melodic.models.interfaces.security.UserRequest;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.server.controller.response.UserLoginInResponse;
import eu.paasage.upperware.security.server.data.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

	private UserService userService;
	private JWTService jwtService;


	@PostMapping("/login-in")
	public UserLoginInResponse loginIn(@RequestBody UserRequest userRequest) throws AuthenticationException {
		log.info("Login-in request for user with username: {}", userRequest.getUsername());
		Boolean authenticate = userService.authenticate(userRequest.getUsername(), userRequest.getPassword());
		log.info("User authentication = {}", authenticate);
		if(authenticate){
			return new UserLoginInResponse(userRequest.getUsername(), jwtService.create(userRequest.getUsername()));
		}
        throw new SecurityException("Invalid credentials");
	}

	@PostMapping("/sign-up")
	public ResponseEntity<Object> signUp(@RequestBody UserRequest userRequest) {
		log.info("Sign-up request for username: {}", userRequest.getUsername());
		if(!userService.search(userRequest.getUsername()).isEmpty()){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is used, please try with another username");
		}
		//User newUser = User.of(user);
		//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.create(userRequest.getUsername(), userRequest.getPassword());
		return ResponseEntity.status(HttpStatus.CREATED).body("User created");
	}

    @GetMapping("")
    public ResponseEntity<Object> getUsersList() {
        log.info("Request for users list");
        return ResponseEntity.status(HttpStatus.OK).body("Users list");
    }

}