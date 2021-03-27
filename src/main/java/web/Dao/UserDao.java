package web.Dao;

import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> getUserById(long id);
    void addUser(User user);
    void updateUser(User user);
    void remove(long id);
    List<User> getListUser();
    Optional<User> getUserByLogin(String login);
    Optional<Role> getRoleByName(String roleName);
    void addRole(String roleName);
}
