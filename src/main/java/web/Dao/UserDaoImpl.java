package web.Dao;

import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void remove(long id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public List<User> getListUser() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUserByLogin(String username) {
        try {
            return entityManager.createQuery("select u from User u  " +
                            "where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

        } catch (NoResultException e){
            return null;
        }
    }

    public Role getRoleById(long id){
        List<Role> roles = entityManager.createQuery("select r from Role r" ,Role.class).getResultList();
        if(roles.isEmpty()) {
            Role role1 = new Role(1L,"ROLE_USER");
            Role role2 = new Role(2L,"ROLE_ADMIN");
            entityManager.merge(role1);
            entityManager.merge(role2);
        }
        return entityManager.find(Role.class, id);
    }
}
