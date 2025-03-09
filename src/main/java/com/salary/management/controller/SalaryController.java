package com.salary.management.controller;

import com.salary.management.dto.Salary.BaseSalaryRequestDTO;
import com.salary.management.dto.Salary.BaseSalaryResponseDTO;
import com.salary.management.dto.Salary.SalaryCalculationRequestDTO;
import com.salary.management.dto.Salary.SalaryResponseDTO;
import com.salary.management.response.ResponseHandler;
import com.salary.management.service.IBaseSalaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Controller for managing salary.
 */
@RestController
@RequestMapping("/api/salary")
public class SalaryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SalaryController.class);
    private final IBaseSalaryService baseSalaryService;

    public SalaryController(IBaseSalaryService baseSalaryService) {
        this.baseSalaryService = baseSalaryService;
    }

    /**
     * Endpoint to set the base salary.
     *
     * @param requestDTO The request DTO containing the base salary amount.
     * @return ResponseEntity containing the saved base salary details.
     */
    @PostMapping("/setBaseSalary")
    public ResponseEntity<Object> setBaseSalary(
            @Valid @RequestBody BaseSalaryRequestDTO requestDTO) {

        BaseSalaryResponseDTO responseDTO = baseSalaryService.setBaseSalary(requestDTO);
        LOGGER.info("Base salary set successfully: {}", responseDTO);

        return ResponseHandler.generateResponse("Set base salary Successfully", HttpStatus.OK, requestDTO);
    }

    /**
     * Endpoint to retrieve the base salary for the lowest grade.
     *
     * @return ResponseEntity containing the base salary details.
     */
    @GetMapping("/getBaseSalary")
    public ResponseEntity<Object> getBaseSalary() {
        LOGGER.info("Received request to fetch base salary for the lowest grade.");

        BaseSalaryResponseDTO responseDTO = baseSalaryService.getBaseSalary();
        LOGGER.info("Base salary retrieved successfully: {}", responseDTO);

        return ResponseHandler.generateResponse("Fetch base salary Successfully", HttpStatus.CREATED, responseDTO);
    }

    /**
     * Endpoint to calculate the salary based on the employee grade.
     *
     * @param requestDTO Contains the grade for which salary is to be calculated.
     * @return ResponseEntity containing the calculated salary breakdown.
     */
    @PostMapping("/calculateSalary")
    public ResponseEntity<Object> calculateSalaryForGrade(
            @Valid @RequestBody SalaryCalculationRequestDTO requestDTO) {
        LOGGER.info("Received request to calculate salary for grade: {}", requestDTO.getGrade());

        SalaryResponseDTO responseDTO = baseSalaryService.calculateSalaryForGrade(requestDTO);
        LOGGER.info("Salary calculation completed for grade {}: {}", requestDTO.getGrade(), responseDTO);

        return ResponseHandler.generateResponse("Received request to calculate salary for grade",HttpStatus.OK, responseDTO);
    }
}
