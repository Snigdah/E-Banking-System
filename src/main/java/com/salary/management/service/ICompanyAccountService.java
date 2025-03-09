package com.salary.management.service;

import com.salary.management.dto.CompanyAccount.*;
import com.salary.management.dto.Salary.TransferRequestDTO;

import java.util.List;

/**
 * Interface for managing bank company account operations.
 */
public interface ICompanyAccountService{

    /**
     * Creates a new company account.
     *
     * @param dto The company account request data.
     * @return CompanyAccountResponseDTO containing the details of the created company account.
     */
    CompanyAccountResponseDTO createCompanyAccount(CompanyAccountRequestDTO dto);

    /**
     * Retrieves a company account by its account number and account name.
     *
     * @param dto The company account search data.
     * @return CompanyAccountResponseDTO containing the company account details.
     */
    CompanyAccountResponseDTO getCompanyAccountByDetails(CompanyAccountSearchRequestDTO dto);

    /**
     * Updates the company account details.
     *
     * @param dto The company account update data.
     * @return CompanyAccountResponseDTO containing the updated company account details.
     */
    CompanyAccountResponseDTO updateCompanyAccount(CompanyAccountUpdateRequestDTO dto);

    /**
     * Deletes a company account based on account number and account name.
     *
     * @param dto The company account search data.
     */
    void deleteCompanyAccount(CompanyAccountSearchRequestDTO dto);

    /**
     * Retrieves all company accounts.
     *
     * @return List of CompanyAccountResponseDTO containing the details of all company accounts.
     */
    List<CompanyAccountResponseDTO> getAllCompanyAccounts();

    /**
     * Adds funds to a company account.
     *
     * @param addFundsRequestDto The request DTO containing the account number and the amount to add.
     * @return true if funds were successfully added, false otherwise.
     */
    public boolean addFundsToCompanyAccount(CompanyAccountAddFundsRequestDTO addFundsRequestDto);

    /**
     * Transfers salary from the company account to an employee's bank account.
     *
     * @param request The transfer request DTO containing the company account number and employee ID
     * @throws CustomException if company account or employee does not exist, or if funds are insufficient
     */
    void transferSalary(TransferRequestDTO request);
}
