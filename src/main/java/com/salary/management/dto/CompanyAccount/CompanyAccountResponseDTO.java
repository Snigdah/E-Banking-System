package com.salary.management.dto.CompanyAccount;

import com.salary.management.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountResponseDTO {
    private String accountName;
    private String accountNumber;
    private BigDecimal currentBalance;
    private String bankName;
    private String branchName;
    private BigDecimal paidBalance;
}
