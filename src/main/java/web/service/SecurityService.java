package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.Role;

public interface SecurityService extends UserDetailsService {
    Role getRoleByName(String role);
    void addRole(String roleName);
}
