package com.salary.management.service;

import com.salary.management.dto.Salary.BaseSalaryRequestDTO;
import com.salary.management.dto.Salary.BaseSalaryResponseDTO;
import com.salary.management.dto.Salary.SalaryCalculationRequestDTO;
import com.salary.management.dto.Salary.SalaryResponseDTO;

public interface IBaseSalaryService {
    /**
     * Sets the base salary for the lowest grade.
     *
     * @param requestDTO The request DTO containing the base salary amount.
     * @return The response DTO containing the saved base salary details.
     */
    BaseSalaryResponseDTO setBaseSalary(BaseSalaryRequestDTO requestDTO);

    /**
     * Retrieves the base salary for the lowest grade.
     *
     * @return The response DTO containing the base salary details.
     */
    BaseSalaryResponseDTO getBaseSalary();

    /**
     * Calculates the salary for a given employee grade.
     *
     * @param requestDTO Contains the grade for which salary is to be calculated.
     * @return SalaryResponseDTO containing the salary breakdown.
     */
    SalaryResponseDTO calculateSalaryForGrade(SalaryCalculationRequestDTO requestDTO);
}
