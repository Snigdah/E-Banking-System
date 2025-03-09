package com.salary.management.controller;

import com.salary.management.dto.Employee.EmployeeRequestDTO;
import com.salary.management.dto.Employee.EmployeeResponseDTO;
import com.salary.management.dto.Employee.EmployeeUpdateRequestDTO;
import com.salary.management.response.ResponseHandler;
import com.salary.management.service.IEmployeeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller for managing employee operations.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
    private final IEmployeeService employeeService;

    /**
     * Constructs the EmployeeController with the required service.
     *
     * @param employeeService the employee service
     */
    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Endpoint to create a new employee.
     *
     * @param requestDTO the request DTO containing employee creation details
     * @return ResponseEntity containing the created employee's details
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createEmployee(
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {

        LOGGER.info("Received request to create employee: {}", requestDTO);

        EmployeeResponseDTO responseDTO = employeeService.createEmployee(requestDTO);
        LOGGER.info("Employee created successfully with ID: {}", responseDTO.getEmployeeId());

        return ResponseHandler.generateResponse("Employee created successfully", HttpStatus.CREATED, responseDTO);
    }

    /**
     * Endpoint to retrieve an employee by their ID.
     *
     * @param employeeId the unique ID of the employee
     * @return ResponseEntity containing the employee's details
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable String employeeId) {
        LOGGER.info("Received request to get employee by ID: {}", employeeId);

        EmployeeResponseDTO responseDTO = employeeService.getEmployeeById(employeeId);
        LOGGER.info("Employee retrieved: {}", responseDTO);

        return ResponseHandler.generateResponse("Employee retrieved successfully", HttpStatus.OK, responseDTO);
    }

    /**
     * Endpoint to retrieve all employees.
     *
     * @return ResponseEntity containing all employees' details
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllEmployees() {
        LOGGER.info("Received request to retrieve all employees.");

        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        LOGGER.info("All employees retrieved successfully: {}", employees);

        return ResponseHandler.generateResponse("Employees retrieved successfully", HttpStatus.OK, employees);
    }

    /**
     * Endpoint to delete an employee by their ID.
     *
     * @param employeeId the unique ID of the employee to be deleted
     * @return ResponseEntity with a success message
     */
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Object> deleteEmployeeById(@PathVariable String employeeId) {
        LOGGER.info("Received request to delete employee with ID: {}", employeeId);

        employeeService.deleteEmployeeById(employeeId);
        LOGGER.info("Employee deleted successfully with ID: {}", employeeId);

        return ResponseHandler.generateResponse("Employee deleted successfully", HttpStatus.OK);
    }

    /**
     * Endpoint to update an employee's details by their ID.
     *
     * @param employeeId the unique ID of the employee to be updated
     * @param requestDTO the DTO containing fields to be updated
     * @return ResponseEntity with the updated employee's details
     */
    @PutMapping("/{employeeId}")
    public ResponseEntity<Object> updateEmployeeById(
            @PathVariable String employeeId,
            @Valid @RequestBody EmployeeUpdateRequestDTO requestDTO) {

        LOGGER.info("Received request to update employee with ID: {}", employeeId);

        EmployeeResponseDTO responseDTO = employeeService.updateEmployeeById(employeeId, requestDTO);
        LOGGER.info("Employee updated successfully with ID: {}", employeeId);

        return ResponseHandler.generateResponse("Employee updated successfully", HttpStatus.OK, responseDTO);
    }
}