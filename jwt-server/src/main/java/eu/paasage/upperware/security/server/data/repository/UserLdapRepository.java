package eu.paasage.upperware.security.server.data.repository;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserLdapRepository extends LdapRepository<User> {
    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    List<User> findByUsernameLikeIgnoreCase(String username);

    User save(User user);
}
