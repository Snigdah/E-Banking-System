package com.salary.management.mapper;

import com.salary.management.dto.Employee.EmployeeRequestDTO;
import com.salary.management.dto.Employee.EmployeeResponseDTO;
import com.salary.management.entity.BankAccount;
import com.salary.management.entity.Employee;

/**
 * Mapper class for converting Employee-related DTOs and entities.
 * Provides methods for mapping between DTOs and entity objects.
 */
public class EmployeeMapper {

    /**
     * Converts an EmployeeRequestDTO to an Employee entity.
     *
     * @param dto         The EmployeeRequestDTO containing employee details.
     * @param bankAccount The associated BankAccount entity.
     * @return The Employee entity.
     */
    public static Employee toEntity(EmployeeRequestDTO dto, BankAccount bankAccount) {
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setGrade(dto.getGrade());
        employee.setAddress(dto.getAddress());
        employee.setMobileNumber(dto.getMobileNumber());
        employee.setBankAccount(bankAccount);
        return employee;
    }

    /**
     * Converts an Employee entity to an EmployeeResponseDTO.
     *
     * @param employee         The Employee entity containing employee details.
     * @param salaryComponents An array representing salary breakdown (basic, rent, medical, total).
     * @return The EmployeeResponseDTO containing employee and salary details.
     */
    public static EmployeeResponseDTO toResponseDto(Employee employee, double[] salaryComponents) {
        EmployeeResponseDTO responseDto = new EmployeeResponseDTO();
        responseDto.setEmployeeId(employee.getEmployeeId());
        responseDto.setName(employee.getName());
        responseDto.setGrade(employee.getGrade());
        responseDto.setAddress(employee.getAddress());
        responseDto.setMobileNumber(employee.getMobileNumber());

        // Map bank account details
        responseDto.setBankAccountDetails(getBankAccountDetails(employee));

        // Map salary details
        responseDto.setSalaryComponents(mapSalaryComponents(salaryComponents));

        return responseDto;
    }

    /**
     * Extracts bank account details from an Employee entity.
     *
     * @param employee The Employee entity containing bank account details.
     * @return A BankAccountDetails DTO containing bank-related information.
     */
    private static EmployeeResponseDTO.BankAccountDetails getBankAccountDetails(Employee employee) {
        BankAccount bankAccount = employee.getBankAccount();
        EmployeeResponseDTO.BankAccountDetails bankAccountDetails = new EmployeeResponseDTO.BankAccountDetails();
        bankAccountDetails.setAccountName(bankAccount.getAccountName());
        bankAccountDetails.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountDetails.setBankName(bankAccount.getBankName());
        bankAccountDetails.setBranchName(bankAccount.getBranchName());
        bankAccountDetails.setAccountType(bankAccount.getAccountType());
        bankAccountDetails.setCurrentBalance(bankAccount.getCurrentBalance());

        return bankAccountDetails;
    }

    /**
     * Maps an array of salary components to a SalaryComponents DTO.
     *
     * @param salaryComponents An array representing salary breakdown.
     *                          Index 0 → Basic Salary
     *                          Index 1 → House Rent
     *                          Index 2 → Medical Allowance
     *                          Index 3 → Total Salary
     * @return A SalaryComponents DTO containing salary breakdown.
     */
    private static EmployeeResponseDTO.SalaryComponents mapSalaryComponents(double[] salaryComponents) {
        EmployeeResponseDTO.SalaryComponents salary = new EmployeeResponseDTO.SalaryComponents();
        salary.setBasicSalary(salaryComponents[0]);
        salary.setHouseRent(salaryComponents[1]);
        salary.setMedicalAllowance(salaryComponents[2]);
        salary.setTotalSalary(salaryComponents[3]);

        return salary;
    }
}
