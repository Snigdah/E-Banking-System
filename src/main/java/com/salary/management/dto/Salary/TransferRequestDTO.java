package com.salary.management.dto.Salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDTO {
    @NotBlank(message = "Company account number is required")
    private String companyAccountNumber;

    @NotBlank(message = "Employee ID is required")
    private String employeeId;
}
