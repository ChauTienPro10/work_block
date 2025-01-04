package com.work_block.main.repository;

import com.work_block.main.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Xoa user
     * @param id
     * @param email
     * @param name
     * @param username
     */
    @Transactional
    @Modifying
    @Query("""
    DELETE FROM User u
    WHERE u.id = :id
       OR u.email = :email
       OR u.name = :name
       OR EXISTS (
           SELECT 1 FROM Account acc WHERE acc.username = :username AND acc.user = u
       )
    """)
    void deleteUser(
            @Param("id") Long id,
            @Param("email") String email,
            @Param("name") String name,
            @Param("username") String username
    );

    @Query("""
    SELECT u FROM User u
    WHERE u.id = :id
       OR u.email = :email
       OR u.name = :name
       OR EXISTS (
           SELECT 1 FROM Account acc WHERE acc.username = :username AND acc.user = u
       )
""")
    User findUser(
            @Param("id") Long id,
            @Param("email") String email,
            @Param("name") String name,
            @Param("username") String username
    );
}
