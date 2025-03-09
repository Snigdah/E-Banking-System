package com.salary.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank_accounts")
public class BankAccount extends BaseAccount{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Account type is required")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}
