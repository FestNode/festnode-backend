package com.festnode.festnode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deptId;
    private String departmentName;
    private String departmentDescription;

    @OneToOne
    @JoinColumn(name = "convener")
    private AppUser convener;

    @OneToMany
    private List<Event> events;
}
