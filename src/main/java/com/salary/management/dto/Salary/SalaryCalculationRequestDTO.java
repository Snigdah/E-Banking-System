package com.salary.management.dto.Salary;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryCalculationRequestDTO {
    @NotNull(message = "Grade is required")
    @Min(value = 1, message = "Grade must be between 1 and 6")
    @Max(value = 6, message = "Grade must be between 1 and 6")
    private Integer grade;
}
