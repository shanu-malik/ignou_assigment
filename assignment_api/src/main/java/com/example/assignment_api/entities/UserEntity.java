package com.example.assignment_api.entities;

import com.example.assignment_api.core.common.BaseEntity;
import com.example.assignment_api.core.constants.DbConstants;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DbConstants.USER_TABLE)
public class UserEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String mobile;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false,name = "enrollment_no")
    private String enrollmentNumber;

    @Column
    private String address;

    @Column(name = "study_centre_code")
    private String studyCentreCode;

    @Column
    private String token;
}
