package com.anvar.em.employee_management.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Getter
@Service
@ToString
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @CreationTimestamp
    @Column(name = "create_ts")
    @JsonIgnore
    private Instant createdTimeStamp;

    @UpdateTimestamp
    @Column(name = "update_ts")
    @JsonIgnore
    private Instant updatedTimeStamp;


}
