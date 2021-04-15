package web.service;

import web.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUserById(long id);
    Optional<User> addUser(User user);
    void updateUser(User user);
    void remove(long id);
    List<User> getListUser();
    User getUserByLogin(String username);
}
