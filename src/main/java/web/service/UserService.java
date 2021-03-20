package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserById(long id);
    void addUser(User user);
    void updateUser(User user);
    void remove(long id);
    List<User> getListUser();
    Role getRoleById(long nameRole);
    User getUserByLogin(String username);
}
