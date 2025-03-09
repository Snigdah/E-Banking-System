package com.salary.management.repository;

import com.salary.management.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByAccountNumberAndAccountName(String accountNumber, String accountName);
    BankAccount findByAccountNumber(String accountNumber);
}
