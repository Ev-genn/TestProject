package web.Dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.model.Role;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.role = :roleName")
    Optional<Role> getRoleByName(@Param("roleName") String roleName);
}
