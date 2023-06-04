package com.study.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.*;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AutoCloseable.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modifiedAt;
}
