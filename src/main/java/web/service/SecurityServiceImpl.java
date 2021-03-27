package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.Dao.UserDao;
import web.model.Role;


import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService{

    private UserDao userDao;

    public SecurityServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Role getRoleByName(String roleName) {
        Optional<Role> roleOptional = userDao.getRoleByName(roleName);
        if(roleOptional.isPresent()) {return roleOptional.get();}
        else {
            addRole(roleName);
            return getRoleByName(roleName);
        }
    }

    @Override
    public void addRole(String roleName) {
        userDao.addRole(roleName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  (userDao.getUserByLogin(username)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
