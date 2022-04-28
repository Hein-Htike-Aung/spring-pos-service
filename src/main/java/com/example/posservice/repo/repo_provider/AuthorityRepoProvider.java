package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model._Authority;
import com.example.posservice.repo.AuthorityRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AuthorityRepoProvider {
    private final AuthorityRepo authorityRepo;

    public List<_Authority> findByUsername(String username) {
        return this.authorityRepo.findByUsername(username)
                .orElseThrow(() -> new SpringPosException("No Authority found with username - " + username));
    }
}
