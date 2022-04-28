package com.example.posservice.repo;

import com.example.posservice.model._User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<_User, Long> {

    @Query("select u from _User u JOIN FETCH u.authorities where u.username=?1 ")
    Optional<_User> findFirstByUsername(String username);
}
