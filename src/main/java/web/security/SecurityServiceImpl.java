package web.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.Dao.RoleDao;
import web.Dao.UserDao;
import web.model.Role;
import web.security.SecurityService;

import java.util.List;
import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private UserDao userDao;
    private RoleDao roleDao;

    public SecurityServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public Role getRoleByName(String roleName) {
        Optional<Role> roleOptional = roleDao.getRoleByName(roleName);
        if(roleOptional.isPresent()) {return roleOptional.get();}
        else {
            addRole(roleName);
            return getRoleByName(roleName);
        }
    }

    @Override
    public void addRole(String roleName) {
        Role role = new Role(roleName);
        roleDao.save(role);
    }

    @Override
    public List<Role> getListRoles() {
        return (roleDao.getAllRoles());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  (userDao.getUserByLogin(username)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
