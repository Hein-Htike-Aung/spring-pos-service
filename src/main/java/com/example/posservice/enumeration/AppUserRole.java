package com.example.posservice.enumeration;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.posservice.enumeration.AppUserPermission.*;

public enum AppUserRole {

    ADMIN(Sets.newHashSet(ADMIN_READ, ADMIN_WRITE)),
    EMPLOYEE(Sets.newHashSet(EMPLOYEE_READ, EMPLOYEE_WRITE)),
    CUSTOMER(Sets.newHashSet(CUSTOMER_READ_WRITE));

    private final Set<AppUserPermission> permissions;

    AppUserRole(Set<AppUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {

        Set<SimpleGrantedAuthority> authorities = this.permissions.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getPermission()))
                .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
