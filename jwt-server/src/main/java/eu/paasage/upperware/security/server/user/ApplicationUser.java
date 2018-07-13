//package eu.paasage.upperware.security.server.user;
//
//import eu.melodic.models.interfaces.security.UserRequest;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import java.io.Serializable;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//public class ApplicationUser implements Serializable {
//
//	private ApplicationUser(String username, String password) {
//		this.username = username;
//		this.password = password;
//	}
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private long id;
//	private String username;
//	private String password;
//
//	public static ApplicationUser of(UserRequest userRequest){
//		return new ApplicationUser(userRequest.getUsername(), userRequest.getPassword());
//	}
//
//}
