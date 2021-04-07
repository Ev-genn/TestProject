package web.Dao;

import web.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User,Long> {

    @Query("select u from User u join fetch u.roles r where u.username = :username")
    Optional<User> getUserByLogin(@Param("username") String login);

    @Query("select distinct u from User u join fetch u.roles r")
    List<User> getListUser();
}
