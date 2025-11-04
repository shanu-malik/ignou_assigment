package com.example.assignment_api.core.constants;

import org.springframework.stereotype.Component;

@Component
public class DbConstants {

    // Prefix for all DB names
    public static final String PREFIX = "ign_";

    // Database table names
    public static final String USER_TABLE = PREFIX + "user";
    public static final String ASSIGNMENT_TABLE = PREFIX + "assignment";
    public static final String COURSE_TABLE = PREFIX + "course";

    private DbConstants() {} // prevent instantiation
}
