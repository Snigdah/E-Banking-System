package com.salary.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", unique = true, nullable = false)
    @Size(min = 4, max = 4, message = "Employee ID must be 4 digits")
    private String employeeId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Grade is required")
    @Min(value = 1, message = "Grade must be between 1 and 6")
    @Max(value = 6, message = "Grade must be between 1 and 6")
    private Integer grade;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{11}$", message = "Mobile number must be 11 digits")
    private String mobileNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
    private BankAccount bankAccount;
}
