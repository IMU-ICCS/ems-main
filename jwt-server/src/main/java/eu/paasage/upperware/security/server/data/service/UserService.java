package eu.paasage.upperware.security.server.data.service;

import eu.paasage.upperware.security.authapi.SecurityConstants;
import eu.paasage.upperware.security.authapi.token.JWTService;
import eu.paasage.upperware.security.server.controller.request.ChangePasswordRequest;
import eu.paasage.upperware.security.server.controller.request.NewUserRequest;
import eu.paasage.upperware.security.server.controller.response.UserDataResponse;
import eu.paasage.upperware.security.server.controller.response.UserResponse;
import eu.paasage.upperware.security.server.data.repository.User;
import eu.paasage.upperware.security.server.data.repository.UserLdapRepository;
import eu.paasage.upperware.security.server.data.repository.UserRole;
import eu.paasage.upperware.security.server.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ldap.NoSuchAttributeException;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private UserLdapRepository userLdapRepository;
    private LdapTemplate ldapTemplate;
    private LdapContextSource ldapContextSource;
    private JWTService jwtService;

    private static final String PASSWORD_CHAR = "*";
    private static final String PASSWORD_LOCKED_KEY = "pwdAccountLockedTime";
    private static final String GROUP_PREFIX = "ou=";

    public boolean authenticate(final String username, final String password) {
        log.info("Login request: l: {}, password: {}", username, createPasswordCode(password));
        boolean authenticationResult = ldapTemplate.authenticate("", String.format("(cn=%s)", username), password);
        log.info("Authentication result for user {} = {}", username, authenticationResult);
        return authenticationResult;
    }

    private String createPasswordCode(String password) {
        return StringUtils.repeat(PASSWORD_CHAR, password.length());
    }

    public boolean exists(String username) {
        return userLdapRepository.findByUsername(username).isPresent();
    }

    public UserResponse create(NewUserRequest userRequest) {

        User newUser = new User(userRequest.getUsername(), userRequest.getFullName(), userRequest.getMail(), digestSHA(userRequest.getPassword()), userRequest.getUserRole(), false);

        String userDN = createUserDN(newUser.getUsername(), newUser.getUserRole());

        DirContextAdapter context = new DirContextAdapter(userDN);

        context.setAttributeValues("objectclass", new String[] {"person"});
        context.setAttributeValues("objectclass", new String[] {"InetOrgPerson"});
        context.setAttributeValue("cn", newUser.getUsername());
        context.setAttributeValue("sn", newUser.getUsername());
        context.setAttributeValue("fullName", newUser.getFullName());
        context.setAttributeValue("mail", newUser.getMail());
        context.setAttributeValue("userPassword", newUser.getPassword());
        context.setAttributeValue("pwdPolicySubentry", "cn=ppolicy,dc=example,dc=org");

        ldapTemplate.bind(context);

        return createUserResponse(newUser.getUsername());
    }

    private String createUserDN(String userName, UserRole userRole) {
        return String.format("cn=%s,ou=%s", userName, userRole.name());
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

    public void delete(String username) {
        User user = userLdapRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        userLdapRepository.delete(user);
    }

    public String createToken(String username) {
        return SecurityConstants.TOKEN_PREFIX + jwtService.create(username);
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) throws AuthenticationException {
        if (!authenticate(changePasswordRequest.getUsername(), changePasswordRequest.getOldPassword())) {
            throw new AuthenticationException();
        }

        User user = userLdapRepository.findByUsernameAndPassword(changePasswordRequest.getUsername(), digestSHA(changePasswordRequest.getOldPassword()))
                .orElseThrow(UserNotFoundException::new);
        user.setPassword(digestSHA(changePasswordRequest.getNewPassword()));
        userLdapRepository.save(user);
    }

    public void unlockAccount(String username) {

        User user = userLdapRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        String userDN = createUserDN(username, findUserRole(user.getId()));

        ModificationItem[] modItems = new ModificationItem[1];
        modItems[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute(PASSWORD_LOCKED_KEY));
        try {
            ldapTemplate.modifyAttributes(userDN, modItems);
        } catch (NoSuchAttributeException ex) {
            log.error("User {} account was not locked", username, ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User %s account was not locked", username));
        }
    }

    public List<User> getUsersList() {
        return mapUserLdapResponseToUser(userLdapRepository.findAll());
    }

    private List<User> mapUserLdapResponseToUser(Iterable<User> ldapUsers) {
        List<User> result = new ArrayList<>();
        ldapUsers.forEach(user -> {
            user.setLockedAccount(isLockedAccount(user.getUsername()));
            user.setUserRole(findUserRole(user.getId()));
            result.add(user);
        });
        return result;
    }

    public UserRole findUserRole(Name id) {
        Enumeration<String> allIdElements = id.getAll();
        while (allIdElements.hasMoreElements()) {
            String idElement = allIdElements.nextElement();
            if (idElement.startsWith(GROUP_PREFIX)) {
                String userRole = idElement.replace(GROUP_PREFIX, StringUtils.EMPTY);
                return UserRole.valueOf(userRole);
            }
        }
        return UserRole.USER; // user without defined role -> common user
    }

    private boolean isLockedAccount(String username) {
        Attribute pwdAccountLockedTime = null;
        SearchControls controls = new SearchControls();
        controls.setReturningAttributes(new String[]{"*", "+"});
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        try {
            NamingEnumeration<SearchResult> results = ldapContextSource.getReadOnlyContext().search("", String.format("(cn=%s)", username), controls);
            if (results.hasMore()) {
                SearchResult searchResult = results.next();
                Attributes attributes = searchResult.getAttributes();
                pwdAccountLockedTime = attributes.get(PASSWORD_LOCKED_KEY);
            }
        } catch (NamingException e) {
            log.error("Error by checking locked account state for user {}:", username, e);
        }
        return pwdAccountLockedTime != null;
    }

    public UserResponse createUserResponse(String username) {
        User user = userLdapRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return UserResponse.builder()
                .username(username)
                .userRole(findUserRole(user.getId()))
                .build();
    }

    public UserDataResponse getUserDataResponse(String username) {
        User user = userLdapRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return UserDataResponse.builder()
                .username(username)
                .fullName(user.getFullName())
                .mail(user.getMail())
                .userRole(findUserRole(user.getId()))
                .build();
    }
}
