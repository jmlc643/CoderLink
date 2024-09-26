package com.upao.pe.coderlink.repos;

import com.upao.pe.coderlink.models.Token;
import com.upao.pe.coderlink.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    boolean existsTokenByToken(String token);

    Token findByToken(String token);

    boolean existsTokenByUser(User user);

    Token findByUser(User user);
}