package com.team12.DASpring.repository;


import com.team12.DASpring.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IuserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username = ?1")
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_role (user_id, role_id) "+"VALUES(?1,?2)", nativeQuery = true)
    void addRoleToUser(Long userId, Long roleId);

    @Query(value = "select u.id from User u where u.username = ?1")
    Long getUserIdByUsername(String username);

    @Query(value = "SELECT r.name FROM  role  r INNER JOIN user_role ur "+ "ON r.id = ur.role_id " +
            "WHERE ur.user_id = ?1 ", nativeQuery = true)
    String[] getRoleOfUser(Long userId);

}
