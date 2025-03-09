package com.salary.management.dto.CompanyAccount;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountRequestDTO {
    @NotBlank(message = "Account name is required")
    @Size(max = 50, message = "Account name must not exceed 50 characters")
    private String accountName;

    @NotNull(message = "Current balance is required")
    @PositiveOrZero(message = "Current balance must be positive or zero")
    private BigDecimal currentBalance;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotBlank(message = "Branch name is required")
    private String branchName;
}

