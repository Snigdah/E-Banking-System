package com.salary.management.dto.Salary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryResponseDTO {
    private Integer grade;
    private Double basicSalary;
    private Double houseRent;
    private Double medicalAllowance;
    private Double totalSalary;
}
