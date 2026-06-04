package com.example.matchings.global.security;

import com.example.matchings.entity.Role;
import lombok.Getter;

@Getter
public class UserPrincipal {

    private final String userId;

    private final Role role;

    public UserPrincipal(String userId, Role role) {
        this.userId = userId;
        this.role = role;
    }
}
