package com.salary.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company_accounts")
public class CompanyAccount extends BaseAccount{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero(message = "Current balance must be positive or zero")
    @Column(precision = 15, scale = 2)
    private BigDecimal paidBalance;
}
