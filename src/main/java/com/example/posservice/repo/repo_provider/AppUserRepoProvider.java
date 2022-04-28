package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model._User;
import com.example.posservice.repo.AppUserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppUserRepoProvider {

    private final AppUserRepo appUserRepo;

    public _User findFirstUserByUsername(String username) {
        return this.appUserRepo.findFirstByUsername(username)
                .orElseThrow(() -> new SpringPosException("No User found with username + " + username));
    }

    public _User findById(Long id) {
        return this.appUserRepo.findById(id)
                .orElseThrow(() -> new SpringPosException("No User found with userid - " + id));
    }
}
