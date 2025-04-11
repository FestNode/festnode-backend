package com.festnode.festnode.model;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum UserRole {
    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    CHIEF_CONVENER("CHIEF_CONVENER"),
    CONVENER("CONVENER"),
    COORDINATOR("COORDINATOR"),
    USER("USER");


    private final String role;
    UserRole(String role) {
        this.role = role;
    }

    public static Set<String> getUserRoleType(){
        Set<String> userRoles=new HashSet<>(0);

        for(UserRole role: UserRole.values()) {
            userRoles.add(role.role);
        }
        return userRoles;
    }
}


