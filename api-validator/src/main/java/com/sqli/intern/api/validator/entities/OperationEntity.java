package com.sqli.intern.api.validator.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operation")
public class OperationEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String type;
    @Column(columnDefinition = "TEXT")
    private String body;
    @Column(columnDefinition = "TEXT")
    private String expectedResponse;
    @Column(columnDefinition = "TEXT")
    private String actualResponse;
    private String expectedType;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;
    private String httpStatus;

}
