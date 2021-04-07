package web.service;

import web.model.User;
import java.util.List;

public interface UserService {
    User getUserById(long id);
    void addUser(User user);
    void updateUser(User user, boolean byCoding);
    void remove(long id);
    List<User> getListUser();
    User getUserByLogin(String username);
}
