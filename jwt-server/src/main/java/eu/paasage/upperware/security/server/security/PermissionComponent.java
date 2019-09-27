package eu.paasage.upperware.security.server.security;

import eu.paasage.upperware.security.server.data.repository.UserLdapRepository;
import eu.paasage.upperware.security.server.data.repository.UserRole;
import eu.paasage.upperware.security.server.data.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("PermissionComponent")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionComponent {

    private UserService userService;
    private UserLdapRepository userLdapRepository;

    public boolean isUserInAdminGroup(String username) {
        return userLdapRepository.findByUsername(username)
                .map(user -> UserRole.ADMIN.equals(userService.findUserRole(user.getId())))
                .orElse(false);
    }
}
