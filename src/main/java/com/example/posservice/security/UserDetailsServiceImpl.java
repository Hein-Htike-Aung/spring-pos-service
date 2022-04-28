package com.example.posservice.security;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model._User;
import com.example.posservice.repo.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepo appUserRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<_User> userOptional = appUserRepo.findFirstByUsername(username);
        _User user = userOptional.orElseThrow(() -> new SpringPosException("No User found with this username - " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                user.getSimpleGrantedAuthorities()
        );
    }

}
