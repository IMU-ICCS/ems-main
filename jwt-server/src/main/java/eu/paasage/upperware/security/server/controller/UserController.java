package eu.paasage.upperware.security.server.controller;

import eu.melodic.models.interfaces.security.UserRequest;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.server.controller.response.UserLoginResponse;
import eu.paasage.upperware.security.server.data.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

	private UserService userService;
	private JWTService jwtService;

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserRequest userRequest, HttpServletResponse response

    ) throws AuthenticationException {
        log.info("Login request for user with username: {}", userRequest.getUsername());
		Boolean authenticate = userService.authenticate(userRequest.getUsername(), userRequest.getPassword());
		log.info("User authentication = {}", authenticate);
		if(authenticate){
            String token = TOKEN_PREFIX + jwtService.create(userRequest.getUsername());
            response.setHeader(HEADER_STRING, token);
            return new UserLoginResponse(userRequest.getUsername());
        }
        throw new AuthenticationException();
    }

    @PostMapping("/users/sign-up")
	public ResponseEntity<Object> signUp(@RequestBody UserRequest userRequest) {
		log.info("Sign-up request for username: {}", userRequest.getUsername());
		if(!userService.search(userRequest.getUsername()).isEmpty()){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is used, please try with another username");
		}
		userService.create(userRequest.getUsername(), userRequest.getPassword());
		return ResponseEntity.status(HttpStatus.CREATED).body("User created");
	}
}