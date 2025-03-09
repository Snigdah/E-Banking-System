package com.salary.management.service.impl;

import com.salary.management.dto.Salary.BaseSalaryRequestDTO;
import com.salary.management.dto.Salary.BaseSalaryResponseDTO;
import com.salary.management.dto.Salary.SalaryCalculationRequestDTO;
import com.salary.management.dto.Salary.SalaryResponseDTO;
import com.salary.management.entity.BaseSalary;
import com.salary.management.exception.CustomException;
import com.salary.management.logic.SalaryLogic;
import com.salary.management.mapper.SalaryMapper;
import com.salary.management.repository.BaseSalaryRepository;
import com.salary.management.service.IBaseSalaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service implementation for managing salary operations.
 */
@Service
public class BaseSalaryService implements IBaseSalaryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseSalaryService.class);
    private final BaseSalaryRepository baseSalaryRepository;
    private final SalaryLogic salaryLogic;

    public BaseSalaryService(BaseSalaryRepository baseSalaryRepository,
                             SalaryLogic salaryLogic) {
        this.baseSalaryRepository = baseSalaryRepository;
        this.salaryLogic = salaryLogic;
    }


    /**
     * Sets the base salary for the lowest grade.
     *
     * @param requestDTO The request DTO containing the base salary amount.
     * @return The response DTO containing the saved base salary details.
     */
    @Override
    public BaseSalaryResponseDTO setBaseSalary(BaseSalaryRequestDTO requestDTO) {
        LOGGER.info("Setting base salary with amount: {}", requestDTO.getAmount());

        // Fetch existing base salary record for "lowest_grade_salary"
        BaseSalary baseSalary = baseSalaryRepository.findByDescription("lowest_grade_salary");

        if (baseSalary == null) {
            baseSalary = new BaseSalary();
            baseSalary.setDescription("lowest_grade_salary");
        }

        // Update amount
        baseSalary.setAmount(requestDTO.getAmount());

        // Save or update entity
        BaseSalary savedBaseSalary = baseSalaryRepository.save(baseSalary);

        LOGGER.info("Base salary saved/updated successfully with ID: {}", savedBaseSalary.getId());

        // Convert entity to response DTO
        return SalaryMapper.toResponseDTO(savedBaseSalary);
    }

    /**
     * Retrieves the base salary for the lowest grade.
     *
     * @return The response DTO containing the base salary details.
     * @throws CustomException If the base salary is not found.
     */
    @Override
    public BaseSalaryResponseDTO getBaseSalary() {
        LOGGER.info("Fetching base salary for description: lowest_grade_salary");

        // Find the base salary by description
        BaseSalary baseSalary = baseSalaryRepository.findByDescription("lowest_grade_salary");

        // Throw custom exception if base salary is not found
        if (Objects.isNull(baseSalary)) {
            LOGGER.error("Base salary not found for description: lowest_grade_salary");
            throw new CustomException(HttpStatus.NOT_FOUND, "Base salary not found for description: lowest_grade_salary");
        }

        LOGGER.info("Base salary found with ID: {}", baseSalary.getId());

        // Convert entity to response DTO
        return SalaryMapper.toResponseDTO(baseSalary);
    }

    /**
     * Calculates the salary for a given employee grade.
     *
     * @param requestDTO Contains the grade for which salary is to be calculated.
     * @return SalaryResponseDTO containing the salary breakdown.
     */
    @Override
    public SalaryResponseDTO calculateSalaryForGrade(SalaryCalculationRequestDTO requestDTO) {
        LOGGER.info("Starting salary calculation for grade: {}", requestDTO.getGrade());

        double[] salaryComponents = salaryLogic.calculateSalaryComponents(requestDTO.getGrade());
        double basicSalary = salaryComponents[0];
        double houseRent = salaryComponents[1];
        double medicalAllowance = salaryComponents[2];
        double totalSalary = salaryComponents[3];

        SalaryResponseDTO responseDTO = SalaryMapper.toResponseDTO(
                requestDTO.getGrade(), basicSalary, houseRent, medicalAllowance, totalSalary
        );

        LOGGER.info("Salary successfully calculated for grade {}: {}", requestDTO.getGrade(), responseDTO);
        return responseDTO;
    }

}
