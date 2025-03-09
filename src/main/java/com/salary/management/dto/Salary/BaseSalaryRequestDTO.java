package com.salary.management.dto.Salary;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseSalaryRequestDTO {
    @NotNull(message = "Base salary amount is required")
    @Positive(message = "Base salary must be a positive value")
    private Double amount;
}
