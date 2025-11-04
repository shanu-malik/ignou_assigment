package com.example.assignment_api.core.common;

// import com.example.assignment_api.core.constants.AppConstants;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.example.assignment_api.core.constants.AppConstants;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    private Integer status = AppConstants.STATUS_ACTIVE; // default = 1 (active)

    @Column(name = "is_deleted")
    private Boolean isDeleted = false; // default = false

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
