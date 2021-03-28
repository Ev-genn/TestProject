package web.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.Dao.UserDao;
import web.model.User;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private PasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserDao userDao, PasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getUserById(long id) {
       return (userDao.findById(id)).orElseGet(null);
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userDao.save(user);
    }

    @Transactional
    @Override
    public void remove(long id) {
        userDao.deleteById(id);
    }

    @Override
    public List<User> getListUser() {
        return userDao.getListUser();
    }

    @Override
    public User getUserByLogin(String username) {
        return (userDao.getUserByLogin(username)).orElse(null);
    }

}
