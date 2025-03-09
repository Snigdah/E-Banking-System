package com.salary.management.repository;

import com.salary.management.entity.BankAccount;
import com.salary.management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Count employees by grade
    long countByGrade(int grade);

    // Find the top employee with the highest ID for generating new IDs
    Optional<Employee> findTopByOrderByEmployeeIdDesc();

    // Find an employee by their unique employee ID
    Optional<Employee> findByEmployeeId(String employeeId);

    // Find bank account
    Optional<Employee> findByBankAccount(BankAccount bankAccount);
}
