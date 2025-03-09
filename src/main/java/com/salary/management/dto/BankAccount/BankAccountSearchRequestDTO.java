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
public class BankAccountSearchRequestDTO {

    @NotBlank(message = "Account number is required")
    @Size(max = 20, message = "Account number must not exceed 20 characters")
    private String accountNumber;

    @NotBlank(message = "Account name is required")
    @Size(max = 50, message = "Account name must not exceed 50 characters")
    private String accountName;
}