package com.salary.management.dto.BankAccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountUpdateRequestDTO {
    @NotBlank(message = "Account number is required")
    @Size(min = 10, max = 20, message = "Account number must not exceed 20 characters")
    private String accountNumber;

    @NotBlank(message = "Account name is required")
    @Size(max = 50, message = "Account name must not exceed 50 characters")
    private String accountName;

    @NotBlank(message = "Bank name is required")
    @Size(max = 50, message = "Bank name must not exceed 50 characters")
    private String bankName;

    @NotBlank(message = "Branch name is required")
    @Size(max = 50, message = "Branch name must not exceed 50 characters")
    private String branchName;

    @NotBlank(message = "Account type is required")
    @Size(max = 20, message = "Account type must not exceed 20 characters")
    private String accountType;
}
