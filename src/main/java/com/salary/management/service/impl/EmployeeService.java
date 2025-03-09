package com.salary.management.service.impl;

import com.salary.management.dto.Employee.EmployeeRequestDTO;
import com.salary.management.dto.Employee.EmployeeResponseDTO;
import com.salary.management.dto.Employee.EmployeeUpdateRequestDTO;
import com.salary.management.entity.BankAccount;
import com.salary.management.entity.Employee;
import com.salary.management.exception.CustomException;
import com.salary.management.logic.EmployeeLogic;
import com.salary.management.logic.SalaryLogic;
import com.salary.management.mapper.EmployeeMapper;
import com.salary.management.repository.EmployeeRepository;
import com.salary.management.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing employees.
 */
@Service
public class EmployeeService implements IEmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final SalaryLogic salaryLogic;
    private final EmployeeLogic employeeLogic;

    /**
     * Constructs the EmployeeService with the required dependencies.
     *
     * @param employeeRepository    the employee repository
     * @param salaryLogic           the salary logic service
     * @param employeeLogic         the employee logic service
     */
    public EmployeeService(EmployeeRepository employeeRepository,
                           SalaryLogic salaryLogic,
                           EmployeeLogic employeeLogic) {
        this.employeeRepository = employeeRepository;
        this.salaryLogic = salaryLogic;
        this.employeeLogic = employeeLogic;
    }

    /**
     * Creates a new employee with details from the given request.
     * Validates the grade limit and bank account, generates a new employee ID, and persists the employee.
     *
     * @param request the DTO containing employee creation details
     * @return EmployeeResponseDTO containing the created employee's details
     */
    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO request) {
        LOGGER.info("Creating a new employee with request data: {}", request);

        // Validate grade limit (keeping this in createEmployee)
        employeeLogic.validateGradeLimits(request.getGrade());

        // Retrieve and validate bank account
        BankAccount bankAccount = employeeLogic.getValidBankAccount(request);

        // Generate a new employee ID
        String newEmployeeId = employeeLogic.generateNewEmployeeId();

        // Create and save the employee (keeping this logic in createEmployee)
        Employee employee = EmployeeMapper.toEntity(request, bankAccount);
        employee.setEmployeeId(newEmployeeId);
        employeeRepository.save(employee);

        LOGGER.info("Employee created successfully with employee ID: {}", newEmployeeId);

        // Calculate salary components
        double[] salaryComponents = salaryLogic.calculateSalaryComponents(employee.getGrade());

        // Convert to response DTO
        return EmployeeMapper.toResponseDto(employee, salaryComponents);
    }

    /**
     * Retrieves an employee's details using their unique employee ID.
     *
     * @param employeeId the employee ID
     * @return EmployeeResponseDTO containing the employee's details
     */
    @Override
    public EmployeeResponseDTO getEmployeeById(String employeeId) {
        LOGGER.info("Retrieving employee with employee ID: {}", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> {
                    LOGGER.error("Employee not found with ID: {}", employeeId);
                    return new CustomException(HttpStatus.NOT_FOUND, "Employee not found with ID: " + employeeId);
                });

        double[] salaryComponents = salaryLogic.calculateSalaryComponents(employee.getGrade());
        return EmployeeMapper.toResponseDto(employee, salaryComponents);
    }

    /**
     * Retrieves all employees and their details.
     *
     * @return a list of EmployeeResponseDTO containing details of all employees
     */
    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        LOGGER.info("Retrieving all employees");

        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> {
                    double[] salaryComponents = salaryLogic.calculateSalaryComponents(employee.getGrade());
                    return EmployeeMapper.toResponseDto(employee, salaryComponents);
                })
                .collect(Collectors.toList());
    }


    /**
     * Deletes an employee using their unique employee ID.
     *
     * @param employeeId the employee ID of the employee to be deleted
     * @throws CustomException if no employee is found with the given ID
     */
    @Override
    public void deleteEmployeeById(String employeeId) {
        LOGGER.info("Deleting employee with ID: {}", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> {
                    LOGGER.error("Employee not found with ID: {}", employeeId);
                    return new CustomException(HttpStatus.NOT_FOUND, "Employee not found with ID: " + employeeId);
                });

        employeeRepository.delete(employee);
        LOGGER.info("Employee deleted successfully with ID: {}", employeeId);
    }


    /**
     * Updates an employee's basic information by employee ID.
     *
     * @param employeeId the ID of the employee to update
     * @param request    the DTO containing name, grade, address, and mobile number
     * @return an EmployeeResponseDTO with updated employee details
     * @throws CustomException if the employee ID is not found
     */
    @Override
    public EmployeeResponseDTO updateEmployeeById(String employeeId, EmployeeUpdateRequestDTO request) {
        LOGGER.info("Updating employee with ID: {}", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> {
                    LOGGER.error("Employee not found with ID: {}", employeeId);
                    return new CustomException(HttpStatus.NOT_FOUND, "Employee not found with ID: " + employeeId);
                });

        // Update only the allowed fields
        employee.setName(request.getName());
        employee.setGrade(request.getGrade());
        employee.setAddress(request.getAddress());
        employee.setMobileNumber(request.getMobileNumber());

        employeeRepository.save(employee);

        double[] salaryComponents = salaryLogic.calculateSalaryComponents(employee.getGrade());
        return EmployeeMapper.toResponseDto(employee, salaryComponents);
    }
}
