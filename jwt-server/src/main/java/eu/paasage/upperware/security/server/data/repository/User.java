package eu.paasage.upperware.security.server.data.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Name id;

    @Attribute(name = "sn")
    private String username;

    @JsonIgnore
    @Attribute(name = "userPassword")
    private String password;

    private boolean lockedAccount;

    public User(String username, String password, boolean lockedAccount) {
        this.username = username;
        this.password = password;
        this.lockedAccount = lockedAccount;
    }
}
