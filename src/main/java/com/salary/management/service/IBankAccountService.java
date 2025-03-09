package com.salary.management.service;

import com.salary.management.dto.BankAccount.BankAccountRequestDTO;
import com.salary.management.dto.BankAccount.BankAccountResponseDTO;
import com.salary.management.dto.BankAccount.BankAccountSearchRequestDTO;
import com.salary.management.dto.BankAccount.BankAccountUpdateRequestDTO;

import java.util.List;

public interface IBankAccountService {
    /**
     * Creates a new bank account.
     *
     * @param bankAccountRequestDto The request DTO containing bank account details.
     * @return BankAccountResponseDTO containing the saved bank account details.
     */
    BankAccountResponseDTO createBankAccount(BankAccountRequestDTO bankAccountRequestDto);

    /**
     * Retrieves a bank account based on account number and account name.
     *
     * @param searchRequestDto The DTO containing the search criteria.
     * @return BankAccountResponseDTO containing the matching bank account details.
     */
    BankAccountResponseDTO getBankAccountByAccountDetails(BankAccountSearchRequestDTO searchRequestDto);

    /**
     * Retrieves all bank accounts from the database.
     *
     * @return List of BankAccountResponseDTO containing all bank accounts.
     */

    List<BankAccountResponseDTO> getAllBankAccounts();

    /**
     * Updates an existing bank account.
     *
     * @param updateRequestDto The details to update the account.
     * @return BankAccountResponseDTO with the updated account details.
     * @throws CustomException if the account is not found.
     */
    BankAccountResponseDTO updateBankAccount(BankAccountUpdateRequestDTO updateRequestDto);
}
