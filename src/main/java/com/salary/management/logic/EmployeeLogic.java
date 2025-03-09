package com.salary.management.logic;

import com.salary.management.dto.Employee.EmployeeRequestDTO;
import com.salary.management.entity.BankAccount;
import com.salary.management.entity.Employee;
import com.salary.management.exception.CustomException;
import com.salary.management.repository.BankAccountRepository;
import com.salary.management.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.salary.management.utils.Constants.Employee.GRADE_LIMITS;

/**
 * Utility class for handling employee business logic.
 */
@Component
public class EmployeeLogic {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeLogic.class);

    private final EmployeeRepository employeeRepository;
    private final BankAccountRepository bankAccountRepository;

    /**
     * Constructor for injecting dependencies into EmployeeLogic.
     *
     * @param employeeRepository    the employee repository to access employee data
     * @param bankAccountRepository the bankAccount repository to access bank info
     */
    public EmployeeLogic(EmployeeRepository employeeRepository,
                         BankAccountRepository bankAccountRepository) {
        this.employeeRepository = employeeRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Validates the number of employees within a specific grade.
     * Throws an exception if the maximum count is exceeded.
     *
     * @param grade the grade to validate
     */
    public void validateGradeLimits(int grade) {
        LOGGER.debug("Validating employee count for grade: {}", grade);
        long count = employeeRepository.countByGrade(grade);
        if (count >= GRADE_LIMITS[grade - 1]) {
            LOGGER.warn("Maximum number of employees reached for grade: {}", grade);
            throw new CustomException(HttpStatus.BAD_REQUEST, "Maximum number of employees reached for grade " + grade);
        }
    }

    /**
     * Generates a new unique 4-digit employee ID.
     *
     * @return the newly generated employee ID
     */
    public String generateNewEmployeeId() {
        LOGGER.debug("Generating new employee ID");
        Optional<Employee> employeeWithMaxId = employeeRepository.findTopByOrderByEmployeeIdDesc();
        String lastEmployeeId = employeeWithMaxId.map(Employee::getEmployeeId).orElse("0000");
        int newId = Integer.parseInt(lastEmployeeId) + 1;
        String newEmployeeId = String.format("%04d", newId);
        LOGGER.debug("New employee ID generated: {}", newEmployeeId);
        return newEmployeeId;
    }

    /**
     * Retrieves a valid bank account and checks if it's already associated with another employee.
     *
     * @param request The EmployeeRequestDTO containing bank account details.
     * @return The valid BankAccount entity.
     * @throws CustomException If the bank account does not exist or is already associated with another employee.
     */
    public BankAccount getValidBankAccount(EmployeeRequestDTO request) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumberAndAccountName(
                request.getAccountNumber(), request.getAccountName());

        if (bankAccount == null) {
            LOGGER.error("Bank account does not exist for account number: {} and account name: {}",
                    request.getAccountNumber(), request.getAccountName());
            throw new CustomException(HttpStatus.NOT_FOUND, "Bank account does not exist.");
        }

        // Check if the bank account is already associated with another employee
        Optional<Employee> existingEmployee = employeeRepository.findByBankAccount(bankAccount);
        if (existingEmployee.isPresent()) {
            LOGGER.error("Bank account {} is already associated with another employee with ID: {}",
                    request.getAccountNumber(), existingEmployee.get().getEmployeeId());
            throw new CustomException(HttpStatus.CONFLICT, "This account owner is someone else.");
        }

        return bankAccount;
    }
}
