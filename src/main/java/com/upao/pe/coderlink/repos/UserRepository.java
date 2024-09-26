package com.upao.pe.coderlink.repos;

import com.upao.pe.coderlink.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);

    boolean existsUserByNames(String names);

    Optional<User> findByUsername(String username);

    boolean existsUserByUsername(String names);
}
