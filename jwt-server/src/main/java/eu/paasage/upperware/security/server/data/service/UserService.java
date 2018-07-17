package eu.paasage.upperware.security.server.data.service;

import eu.paasage.upperware.security.server.data.repository.User;
import eu.paasage.upperware.security.server.data.repository.UserLdapRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserService {//} implements UserDetailsService{

    private UserLdapRepository userLdapRepository;
    private PasswordEncoder passwordEncoder;
    private LdapTemplate ldapTemplate;


    public Boolean authenticate(final String username, final String password) {
        User user = userLdapRepository.findByUsernameAndPassword(username, password);
        return user != null;
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
        //User newUser = new User(username, passwordEncoder.encode(password));
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
        //newUser.setId(LdapUtils.emptyLdapName());
        log.info("Saving new user with credential: login={}, id={}", username, newUser.getId());
        //userRepository.save(newUser);
    }

    public void modify(final String username, final String password) {
        User user = userLdapRepository.findByUsername(username);
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
        return "{SHA}" + base64;
    }
}