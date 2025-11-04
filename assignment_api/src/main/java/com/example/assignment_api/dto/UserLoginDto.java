package com.example.assignment_api.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserLoginDto {
    
    @NotBlank(message = "Enrollment number is required")
    @Pattern(regexp = "\\d{10}", message = "Enrollment number must be 10 digits")
    private String enrollment_no;

    @NotBlank(message = "Password is required")
    private String password;

}
