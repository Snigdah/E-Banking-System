package com.salary.management.dto.BankAccount;

import com.salary.management.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountResponseDTO {
    private String accountName;
    private String accountNumber;
    private BigDecimal currentBalance;
    private String bankName;
    private String branchName;
    private AccountType accountType;
}
