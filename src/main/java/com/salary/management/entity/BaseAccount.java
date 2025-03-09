package com.salary.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseAccount {
    @NotBlank(message = "Account name is required")
    private String accountName;

    @NotBlank(message = "Account number is required")
    @Size(min = 10, max = 18, message = "Account number must be between 10 and 18 digits")
    private String accountNumber;

    @NotNull(message = "Current balance is required")
    @PositiveOrZero(message = "Current balance must be positive or zero")
    @Column(precision = 15, scale = 2)
    private BigDecimal currentBalance;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotBlank(message = "Branch name is required")
    private String branchName;
}
