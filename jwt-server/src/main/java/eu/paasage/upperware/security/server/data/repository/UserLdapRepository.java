package eu.paasage.upperware.security.server.data.repository;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserLdapRepository extends LdapRepository<User> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
