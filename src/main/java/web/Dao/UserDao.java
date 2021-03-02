package web.Dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    User getUserById(long id);
    void addUser(User user);
    void updateUser(User user);
    void remove(long id);
    List<User> getListUser();
}
