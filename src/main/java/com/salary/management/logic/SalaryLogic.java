package com.salary.management.logic;

import com.salary.management.entity.BaseSalary;
import com.salary.management.exception.CustomException;
import com.salary.management.repository.BaseSalaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * SalaryLogic class handles the core salary calculation logic
 * and retrieves base salary data from the database.
 */
@Component
public class SalaryLogic {
    private static final Logger LOGGER = LoggerFactory.getLogger(SalaryLogic.class);
    private final BaseSalaryRepository baseSalaryRepository;

    public SalaryLogic(BaseSalaryRepository baseSalaryRepository) {
        this.baseSalaryRepository = baseSalaryRepository;
    }

    /**
     * Retrieves the base salary entity for the lowest grade.
     *
     * @return BaseSalary entity containing the base salary information.
     * @throws CustomException if the base salary is not found.
     */
    public BaseSalary getBaseSalaryEntity() {
        LOGGER.info("Fetching base salary entity for description: 'lowest_grade_salary'");

        BaseSalary baseSalary = baseSalaryRepository.findByDescription("lowest_grade_salary");
        if (Objects.isNull(baseSalary)) {
            LOGGER.error("Base salary not found for description: 'lowest_grade_salary'");
            throw new CustomException(HttpStatus.NOT_FOUND, "Base salary not found for description: lowest_grade_salary");
        }

        LOGGER.info("Base salary retrieved successfully: {}", baseSalary);
        return baseSalary;
    }

    /**
     * Calculates salary components (basic salary, house rent, medical allowance, total salary)
     * based on the provided grade.
     *
     * @param grade Employee grade.
     * @return An array containing salary components in the order:
     *         [basicSalary, houseRent, medicalAllowance, totalSalary].
     */
    public double[] calculateSalaryComponents(int grade) {
        LOGGER.info("Calculating salary components for grade: {}", grade);

        BaseSalary baseSalary = getBaseSalaryEntity();
        double basicSalary = baseSalary.getAmount() + (6 - grade) * 5000;
        double houseRent = 0.20 * basicSalary;
        double medicalAllowance = 0.15 * basicSalary;
        double totalSalary = basicSalary + houseRent + medicalAllowance;

        LOGGER.info("Salary calculation completed for grade {}: Basic Salary: {}, House Rent: {}, Medical Allowance: {}, Total Salary: {}",
                grade, basicSalary, houseRent, medicalAllowance, totalSalary);

        return new double[]{basicSalary, houseRent, medicalAllowance, totalSalary};
    }
}
