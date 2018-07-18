package eu.paasage.upperware.security.server.data.service;

import eu.paasage.upperware.security.server.data.repository.User;
import eu.paasage.upperware.security.server.data.repository.UserLdapRepository;
import eu.paasage.upperware.security.server.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private UserLdapRepository userLdapRepository;
    private LdapTemplate ldapTemplate;

    public Boolean authenticate(final String username, final String password) {
        log.info("Login request: l: {}, password: {}", username, password);
        User userWithPassword = userLdapRepository.findByUsernameAndPassword(username, digestSHA(password)).orElseThrow(UserNotFoundException::new);
        return true;
    }

    public List<String> search(final String username) {
        List<User> userList = userLdapRepository.findByUsernameLikeIgnoreCase(username);
        if (userList == null) {
            return Collections.emptyList();
        }

        return userList.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

    public void create(final String username, final String password) {
        User newUser = new User(username, digestSHA(password));

        Name dn = LdapNameBuilder
                .newInstance()
                .add("cn", username)
                .build();
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues("objectclass", new String[] {"person"});
        context.setAttributeValue("cn", username);
        context.setAttributeValue("sn", username);
        context.setAttributeValue("userPassword", newUser.getPassword());

        ldapTemplate.bind(context);
        log.info("Saving new user with credential: login={}, id={}", username, newUser.getId());
    }

    public void modify(final String username, final String password) {
        User user = userLdapRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        user.setPassword(password);
        userLdapRepository.save(user);
    }

    private String digestSHA(final String password) {
        String base64;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            base64 = Base64.getEncoder()
                    .encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "{sha}" + base64;
    }
}