package com.example.posservice.security;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model._User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.Optional;

@ApplicationScope
@Component
public class CurrentUserHolder {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    public User getCurrentUser() {
        return getUser().orElseThrow(() -> new SpringPosException("There is no logged in user"));
    }

}
