package com.example.assignment_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.assignment_api.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEnrollmentNumber(String enrollmentNumber);
    Optional<UserEntity> findByMobile(String mobile);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEnrollmentNumberAndPassword(String enrollmentNumber, String password);
}
