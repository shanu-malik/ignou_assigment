package com.example.assignment_api.core.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AppConstants {

    // Status values
    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_INACTIVE = 0;

    // Default roles
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    // JWT Config - dynamic from env/properties
    public static String JWT_SECRET;
    public static long JWT_EXPIRATION_MS;

    @Value("${JWT_SECRET:DefaultSecretKey}")
    public void setJwtSecret(String jwtSecret) {
        AppConstants.JWT_SECRET = jwtSecret;
    }

    @Value("${JWT_EXPIRATION_MS:86400000}")
    public void setJwtExpirationMs(long jwtExpirationMs) {
        AppConstants.JWT_EXPIRATION_MS = jwtExpirationMs;
    }

    // Other global constants
    public static final Boolean NOT_DELETED = false;
    public static final Boolean DELETED = true;
    public static final List<String> PUBLIC_ROUTES = List.of(
            "/api/login",
            "/api/register"
    );

    private AppConstants() {} // prevent instantiation
}
