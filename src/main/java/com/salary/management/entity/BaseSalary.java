package com.salary.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "base_salaries")
public class BaseSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String description = "lowest_grade_salary";

    @NotNull(message = "Base salary amount is required")
    @Positive(message = "Base salary must be a positive value")
    private Double amount;
}
