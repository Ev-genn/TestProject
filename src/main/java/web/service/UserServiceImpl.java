package web.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.Dao.UserDao;
import web.model.Role;
import web.model.User;
import web.security.SecurityService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private SecurityService securityService;
    private PasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserDao userDao, SecurityService securityService, PasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.securityService = securityService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getUserById(long id) {
       return (userDao.findById(id)).orElseGet(null);
    }

    @Transactional
    @Override
    public Optional<User> addUser(User user) {
        Optional<User> userByDb = userDao.getUserByLogin(user.getUsername());
        if (userByDb.isPresent()){ // проверка на уникальность логина
            return userByDb;
        }
        Set<Role> roleUser = new HashSet<>();
        List<Role> listRole = securityService.getListRoles();
        for(Role roleByDb : listRole) {
            for (Role role : user.getRoles()) {
                if((String.valueOf(role)).equals(roleByDb.getRole())) {
                    roleUser.add(roleByDb);
                }
            }
        }
        user.setRoles(roleUser);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return Optional.empty();
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        if(!user.getPassword().equals("") || user.getPassword() != null){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        Set<Role> roleUser = new HashSet<>();
        for (Role role:user.getRoles()) {
            Role userRole = securityService.getRoleByName(String.valueOf(role));
            roleUser.add(userRole);
        }
        user.setRoles(roleUser);
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
