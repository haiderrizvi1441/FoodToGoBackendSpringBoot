package com.foodtogo.spring.security.login.controllers;

import java.util.Optional;

public class UserIdProvider {
    /*
    replace with your logic to provide user id
     */
    public static Optional<String> getCurrentUserId() {
        return Optional.of("mohammad.shahnawaz2@dxc.com");
    }
}
