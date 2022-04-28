package com.example.posservice.repo.repo_provider;

import com.example.posservice.exception.SpringPosException;
import com.example.posservice.model.VerificationToken;
import com.example.posservice.repo.VerificationTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VerificationTokenRepoProvider {

    private final VerificationTokenRepo verificationTokenRepo;

    public VerificationToken findVerificationTokenByToken(String token) {
        return this.verificationTokenRepo.findByToken(token)
                .orElseThrow(() -> new SpringPosException("No VerificationToken found with token - " + token));
    }
}
