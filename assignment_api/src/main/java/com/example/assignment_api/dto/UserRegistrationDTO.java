package com.example.assignment_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserRegistrationDTO {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Enrollment number is required")
    @Pattern(regexp = "\\d{10}", message = "Enrollment number must be 10 digits")
    private String enrollment_no;

    @NotBlank(message = "Mobile is required")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobile;

    private String address; // optional

    @NotBlank(message = "Study centre code is required")
    private String study_centre_code;
}
