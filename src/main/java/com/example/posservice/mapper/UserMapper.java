package com.example.posservice.mapper;

import com.example.posservice.dto.UserResponse;
import com.example.posservice.model._User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
@AllArgsConstructor
public class UserMapper {

    public UserResponse mapToUserResponse(_User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .created(user.getCreated())
                .email(user.getEmail())
                .build();
    }
}
