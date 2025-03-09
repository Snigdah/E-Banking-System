package com.salary.management.dto.CompanyAccount;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountAddFundsRequestDTO {
    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Amount balance is required")
    @PositiveOrZero(message = "Amount balance must be positive or zero")
    private BigDecimal amount;
}
