package eu.paasage.upperware.security.server.data.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;

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

    @Attribute(name = "ou")
    private UserRole userRole;

    @Transient
    private boolean lockedAccount;

    public User(String username, String password, UserRole userRole, boolean lockedAccount) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.lockedAccount = lockedAccount;
    }
}
