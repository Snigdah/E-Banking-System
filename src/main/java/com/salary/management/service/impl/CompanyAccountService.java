package com.salary.management.service.impl;

import com.salary.management.dto.CompanyAccount.*;
import com.salary.management.dto.Salary.TransferRequestDTO;
import com.salary.management.entity.CompanyAccount;
import com.salary.management.entity.Employee;
import com.salary.management.exception.CustomException;
import com.salary.management.logic.SalaryLogic;
import com.salary.management.mapper.CompanyAccountMapper;
import com.salary.management.repository.CompanyAccountRepository;
import com.salary.management.repository.EmployeeRepository;
import com.salary.management.service.ICompanyAccountService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing bank Company account operations.
 */
@Service
public class CompanyAccountService implements ICompanyAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyAccountService.class);
    private final CompanyAccountRepository companyAccountRepository;
    private final EmployeeRepository employeeRepository;
    private final SalaryLogic salaryLogic;

    public CompanyAccountService(CompanyAccountRepository companyAccountRepository,
                                 EmployeeRepository employeeRepository,
                                 SalaryLogic salaryLogic) {
        this.companyAccountRepository = companyAccountRepository;
        this.employeeRepository = employeeRepository;
        this.salaryLogic = salaryLogic;
    }

    /**
     * Creates a new company account.
     *
     * @param dto The company account request data.
     * @return CompanyAccountResponseDTO containing the details of the created company account.
     */
    @Override
    public CompanyAccountResponseDTO createCompanyAccount(CompanyAccountRequestDTO dto) {
        LOGGER.info("Creating new company account: {}", dto.getAccountName());

        // Convert request DTO to entity
        CompanyAccount account = CompanyAccountMapper.toEntity(dto);

        // Save entity
        CompanyAccount savedAccount = companyAccountRepository.save(account);
        LOGGER.info("Company account created successfully with account number: {}", savedAccount.getAccountNumber());

        // Convert saved entity to response DTO
        return CompanyAccountMapper.toResponseDto(savedAccount);
    }

    /**
     * Retrieves a company account by its account number and account name.
     *
     * @param dto The company account search data.
     * @return CompanyAccountResponseDTO containing the company account details.
     */
    @Override
    public CompanyAccountResponseDTO getCompanyAccountByDetails(CompanyAccountSearchRequestDTO dto) {
        LOGGER.info("Fetching company account with account number: {} and account name: {}", dto.getAccountNumber(), dto.getAccountName());

        CompanyAccount account = companyAccountRepository.findByAccountNumberAndAccountName(dto.getAccountNumber(), dto.getAccountName());
        if (account == null) {
            LOGGER.error("Company account not found with the provided details: Account number: {} and Account name: {}", dto.getAccountNumber(), dto.getAccountName());
            throw new CustomException(HttpStatus.NOT_FOUND, "Company account not found with the provided details");
        }
        return CompanyAccountMapper.toResponseDto(account);
    }

    /**
     * Updates the company account details.
     *
     * @param dto The company account update data.
     * @return CompanyAccountResponseDTO containing the updated company account details.
     */
    @Override
    public CompanyAccountResponseDTO updateCompanyAccount(CompanyAccountUpdateRequestDTO dto) {
        LOGGER.info("Updating company account with account number: {}", dto.getAccountNumber());

        CompanyAccount account = companyAccountRepository.findByAccountNumber(dto.getAccountNumber());
        if (account == null) {
            LOGGER.error("Company account not found with the provided account number: {}", dto.getAccountNumber());
            throw new CustomException(HttpStatus.NOT_FOUND, "Company account not found with the provided details");
        }

        // Update entity
        CompanyAccountMapper.updateEntity(account, dto);

        // Save updated entity
        CompanyAccount updatedAccount = companyAccountRepository.save(account);
        return CompanyAccountMapper.toResponseDto(updatedAccount);
    }

    /**
     * Deletes a company account based on account number and account name.
     *
     * @param dto The company account search data.
     */
    @Override
    public void deleteCompanyAccount(CompanyAccountSearchRequestDTO dto) {
        LOGGER.info("Deleting company account with account number: {} and account name: {}", dto.getAccountNumber(), dto.getAccountName());

        CompanyAccount account = companyAccountRepository.findByAccountNumberAndAccountName(dto.getAccountNumber(), dto.getAccountName());
        if (account == null) {
            LOGGER.error("Company account not found with the provided details: Account number: {} and Account name: {}", dto.getAccountNumber(), dto.getAccountName());
            throw new CustomException(HttpStatus.NOT_FOUND, "Company account not found with the provided details");
        }

        companyAccountRepository.delete(account);
        LOGGER.info("Company account deleted successfully with account number: {}", dto.getAccountNumber());
    }

    /**
     * Retrieves all company accounts.
     *
     * @return List of CompanyAccountResponseDTO containing the details of all company accounts.
     */
    @Override
    public List<CompanyAccountResponseDTO> getAllCompanyAccounts() {
        LOGGER.info("Fetching all company accounts.");

        List<CompanyAccount> accounts = companyAccountRepository.findAll();
        if (accounts.isEmpty()) {
            LOGGER.warn("No company accounts found.");
        }

        // Convert entities to response DTOs
        return accounts.stream()
                .map(CompanyAccountMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addFundsToCompanyAccount(CompanyAccountAddFundsRequestDTO addFundsRequestDto) {
        LOGGER.info("Attempting to add funds to account: {}", addFundsRequestDto.getAccountNumber());

        // Find the company account by account number
        CompanyAccount companyAccount = companyAccountRepository.findByAccountNumber(addFundsRequestDto.getAccountNumber());

        if (companyAccount == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Company account not found with the provided details");
        }

        // Update the balance
        BigDecimal newBalance = companyAccount.getCurrentBalance().add(addFundsRequestDto.getAmount());
        companyAccount.setCurrentBalance(newBalance);

        // Save the updated company account
        companyAccountRepository.save(companyAccount);
        LOGGER.info("Funds added successfully. New balance: {}", newBalance);
        return true;
    }

    /**
     * Transfers salary from the company account to an employee's bank account.
     *
     * @param request The transfer request DTO containing the company account number and employee ID
     * @throws CustomException if company account or employee does not exist, or if funds are insufficient
     */
    @Override
    @Transactional
    public void transferSalary(TransferRequestDTO request) {
        LOGGER.info("Initiating transfer from company account {} to employee ID {}",
                request.getCompanyAccountNumber(), request.getEmployeeId());

        CompanyAccount companyAccount = validateCompanyAccount(request.getCompanyAccountNumber());
        Employee employee = validateEmployee(request.getEmployeeId());

        double totalSalary = calculateTotalSalary(employee.getGrade());

        validateSufficientFunds(companyAccount, totalSalary);

        performTransfer(companyAccount, employee, totalSalary);

        LOGGER.info("Transfer completed. Company account balance: {}, Employee account balance: {}",
                companyAccount.getCurrentBalance(), employee.getBankAccount().getCurrentBalance());
    }

    /**
     * Validates the existence of a company account using the account number.
     *
     * @param accountNumber The company account number to validate
     * @return The CompanyAccount if found
     * @throws CustomException if the company account is not found
     */
    private CompanyAccount validateCompanyAccount(String accountNumber) {
        CompanyAccount companyAccount = companyAccountRepository.findByAccountNumber(accountNumber);
        if (companyAccount == null) {
            LOGGER.error("Company account not found");
            throw new CustomException(HttpStatus.NOT_FOUND, "Company account not found");
        }
        return companyAccount;
    }

    /**
     * Validates the existence of an employee using the employee ID.
     *
     * @param employeeId The unique employee ID to validate
     * @return The Employee if found
     * @throws CustomException if the employee is not found
     */
    private Employee validateEmployee(String employeeId) {
        return employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> {
                    LOGGER.error("Employee not found");
                    return new CustomException(HttpStatus.NOT_FOUND, "Employee not found");
                });
    }

    /**
     * Calculates the total salary for an employee based on their grade.
     *
     * @param grade The grade of the employee
     * @return The total computed salary
     */
    private double calculateTotalSalary(int grade) {
        double[] salaryComponents = salaryLogic.calculateSalaryComponents(grade);
        return salaryComponents[3]; // Total salary is at index 3
    }

    /**
     * Validates that the company account has sufficient funds to cover the salary.
     *
     * @param companyAccount The company account to check
     * @param totalSalary    The total salary to be transferred
     * @throws CustomException if the company account has insufficient funds
     */
    private void validateSufficientFunds(CompanyAccount companyAccount, double totalSalary) {
        if (companyAccount.getCurrentBalance().compareTo(BigDecimal.valueOf(totalSalary)) < 0) {
            LOGGER.error("Insufficient funds in company account");
            throw new CustomException(HttpStatus.BAD_REQUEST, "Insufficient funds in company account");
        }
    }

    /**
     * Performs the salary transfer, updating both company and employee account balances.
     *
     * @param companyAccount The source company account
     * @param employee       The destination employee
     * @param totalSalary    The amount to transfer
     */
    private void performTransfer(CompanyAccount companyAccount, Employee employee, double totalSalary) {
        // Deduct the salary from company balance and update paid balance
        companyAccount.setCurrentBalance(
                companyAccount.getCurrentBalance().subtract(BigDecimal.valueOf(totalSalary)));
        companyAccount.setPaidBalance(
                companyAccount.getPaidBalance().add(BigDecimal.valueOf(totalSalary)));
        companyAccountRepository.save(companyAccount);

        // Add salary to employee's bank account
        employee.getBankAccount().setCurrentBalance(
                employee.getBankAccount().getCurrentBalance().add(BigDecimal.valueOf(totalSalary)));
        employeeRepository.save(employee);
    }
}

