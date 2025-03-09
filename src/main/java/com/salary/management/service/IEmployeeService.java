package com.salary.management.service;

import com.salary.management.dto.Employee.EmployeeRequestDTO;
import com.salary.management.dto.Employee.EmployeeResponseDTO;
import com.salary.management.dto.Employee.EmployeeUpdateRequestDTO;

import java.util.List;

/**
 * Interface for managing employee operations.
 */
public interface IEmployeeService {
    /**
     * Create a new employee record.
     *
     * @param request The EmployeeRequestDTO containing details of the employee to be created.
     * @return EmployeeResponseDTO The response data transfer object including employee details with salary info.
     */
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO request);

    /**
     * Retrieve an employee by their employee ID.
     *
     * @param employeeId The unique ID of the employee.
     * @return EmployeeResponseDTO The response DTO containing the employee's details.
     */
    EmployeeResponseDTO getEmployeeById(String employeeId);

    /**
     * Retrieve all employees.
     *
     * @return List<EmployeeResponseDTO> A list containing the response DTOs of all employees.
     */
    List<EmployeeResponseDTO> getAllEmployees();

    /**
     * Deletes an employee using their employee ID.
     *
     * @param employeeId the unique ID of the employee to be deleted
     */
    void deleteEmployeeById(String employeeId);

    /**
     * Updates specified fields of an employee using their employee ID.
     *
     * @param employeeId the ID of the employee to update
     * @param request the DTO containing the fields to be updated
     * @return EmployeeResponseDTO containing the updated employee's details
     * @throws CustomException if no employee is found with the given ID
     */
    EmployeeResponseDTO updateEmployeeById(String employeeId, EmployeeUpdateRequestDTO request);
}
