package web.Dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(entityManager.createQuery("select u from User u join fetch u.roles " +
                "where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult());
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
        entityManager.remove(getUserById(id).get());
    }

    @Override
    public List<User> getListUser() {
        return entityManager.createQuery("select distinct u from User u left join fetch u.roles r ", User.class).getResultList();
    }

    @Override
    public Optional<User> getUserByLogin(String username) {
            return Optional.ofNullable(entityManager.createQuery("select u from User u join fetch u.roles r " +
                            "where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getResultList()
                    .stream().findFirst().orElse(null));
    }

    @Override
    public Optional<Role> getRoleByName(String roleName){
        return  Optional.ofNullable(entityManager.createQuery("select r from Role r where r.role = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getResultList()
                .stream().findFirst().orElse(null));
    }

    @Transactional
    @Override
    public void addRole(String roleName) {
        Role role = new Role(roleName);
        System.out.println(role.toString());
        entityManager.persist(role);

    }

}
