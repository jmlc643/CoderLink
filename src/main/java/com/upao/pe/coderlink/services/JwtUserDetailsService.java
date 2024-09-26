package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.models.User;
import com.upao.pe.coderlink.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            Set<SimpleGrantedAuthority> authorities = new HashSet<>(1);
            authorities.add(new SimpleGrantedAuthority("ROLE_".concat(user.get().getTypeUser().name())));
            return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                    user.get().getPassword(),
                    user.get().isEnabled(),
                    user.get().isAccountNoExpired(),
                    user.get().isCredentialNoExpired(),
                    user.get().isAccountNoLocked(),
                    authorities);
        }else{
            throw new UsernameNotFoundException("User "+username+" has not been founded");
        }
    }
}
