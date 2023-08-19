package com.sqli.intern.api.validator.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class ProjectEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private boolean deleted;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<OperationEntity> operations;

    private Boolean withAuth;
}
