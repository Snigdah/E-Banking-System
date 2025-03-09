package com.salary.management.repository;

import com.salary.management.entity.BaseSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseSalaryRepository extends JpaRepository<BaseSalary, Long> {
    BaseSalary findByDescription(String description);
}
