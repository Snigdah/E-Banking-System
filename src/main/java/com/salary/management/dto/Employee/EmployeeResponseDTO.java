package com.salary.management.dto.Employee;

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
public class EmployeeResponseDTO {
    private String employeeId;
    private String name;
    private Integer grade;
    private String address;
    private String mobileNumber;

    // Nested Bank Account JSON
    private BankAccountDetails bankAccountDetails;

    // Nested Salary Components JSON
    private SalaryComponents salaryComponents;

    @Getter
    @Setter
    public static class BankAccountDetails {
        private String accountName;
        private String accountNumber;
        private String bankName;
        private String branchName;
        private AccountType accountType;
        private BigDecimal currentBalance;
    }

    @Getter
    @Setter
    public static class SalaryComponents {
        private double basicSalary;
        private double houseRent;
        private double medicalAllowance;
        private double totalSalary;
    }
}
