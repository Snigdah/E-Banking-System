package com.salary.management.repository;

import com.salary.management.entity.CompanyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAccountRepository extends JpaRepository<CompanyAccount, Long> {
    CompanyAccount findByAccountNumberAndAccountName(String accountNumber, String accountName);
    CompanyAccount findByAccountNumber(String accountNumber);
}

