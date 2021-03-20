package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.Dao.UserDao;
import web.model.Role;
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

    @Transactional
    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Transactional
    @Override
    public void remove(long id) {
        userDao.remove(id);
    }

    @Transactional
    @Override
    public List<User> getListUser() {
        return userDao.getListUser();
    }

    @Transactional
    public Role getRoleById(long id){
        return userDao.getRoleById(id);
    }

    @Transactional
    @Override
    public User getUserByLogin(String username) {
        return userDao.getUserByLogin(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByLogin(username);
        if(user == null) {throw new UsernameNotFoundException("User not found");}
        return  user;
    }
}
