package eu.paasage.upperware.security.server.data.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Setter
@Getter
@Entry(objectClasses = {"person"})
@NoArgsConstructor
public class User {

    @Id
    private Name id;

    @Attribute(name = "sn")
    private String username;

    @Attribute(name = "userPassword")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}