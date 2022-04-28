package com.example.posservice.repo;

import com.example.posservice.model._Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepo extends JpaRepository<_Authority, Long> {

    Optional<List<_Authority>> findByUsername(String username);
}
