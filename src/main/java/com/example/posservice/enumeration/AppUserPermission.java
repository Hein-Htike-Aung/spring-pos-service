package com.example.posservice.enumeration;

public enum AppUserPermission {

    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_WRITE("employee:write"),
    CUSTOMER_READ_WRITE("customer:read_write");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
