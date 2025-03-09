package com.salary.management.mapper;

import com.salary.management.dto.Salary.BaseSalaryRequestDTO;
import com.salary.management.dto.Salary.BaseSalaryResponseDTO;
import com.salary.management.dto.Salary.SalaryResponseDTO;
import com.salary.management.entity.BaseSalary;

public class SalaryMapper {

    /**
     * Converts a BaseSalaryRequestDTO to a BaseSalary entity.
     *
     * @param requestDTO The request DTO to convert.
     * @return The converted BaseSalary entity.
     */
    public static BaseSalary toEntity(BaseSalaryRequestDTO requestDTO) {
        BaseSalary baseSalary = new BaseSalary();
        baseSalary.setDescription("lowest_grade_salary");
        baseSalary.setAmount(requestDTO.getAmount());
        return baseSalary;
    }

    /**
     * Converts a BaseSalary entity to a BaseSalaryResponseDTO.
     *
     * @param baseSalary The entity to convert.
     * @return The converted BaseSalaryResponseDTO.
     */
    public static BaseSalaryResponseDTO toResponseDTO(BaseSalary baseSalary) {
        BaseSalaryResponseDTO responseDTO = new BaseSalaryResponseDTO();
        responseDTO.setDescription(baseSalary.getDescription());
        responseDTO.setAmount(baseSalary.getAmount());
        return responseDTO;
    }

    public static SalaryResponseDTO toResponseDTO(int grade, double basicSalary, double houseRent, double medicalAllowance, double totalSalary) {
        SalaryResponseDTO responseDTO = new SalaryResponseDTO();
        responseDTO.setGrade(grade);
        responseDTO.setBasicSalary(basicSalary);
        responseDTO.setHouseRent(houseRent);
        responseDTO.setMedicalAllowance(medicalAllowance);
        responseDTO.setTotalSalary(totalSalary);
        return responseDTO;
    }
}
