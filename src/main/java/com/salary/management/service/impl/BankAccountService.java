package com.salary.management.service.impl;

import com.salary.management.dto.BankAccount.BankAccountRequestDTO;
import com.salary.management.dto.BankAccount.BankAccountResponseDTO;
import com.salary.management.dto.BankAccount.BankAccountSearchRequestDTO;
import com.salary.management.dto.BankAccount.BankAccountUpdateRequestDTO;
import com.salary.management.entity.BankAccount;
import com.salary.management.exception.CustomException;
import com.salary.management.mapper.BankAccountMapper;
import com.salary.management.repository.BankAccountRepository;
import com.salary.management.service.IBankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.salary.management.logic.BankAccountLogic.generateUniqueAccountNumber;

/**
 * Service implementation for managing bank account operations.
 */
@Service
public class BankAccountService implements IBankAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountService.class);
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Creates a new bank account with a unique account number.
     *
     * @param bankAccountRequestDto The request DTO containing bank account details.
     * @return BankAccountResponseDTO containing the saved bank account details.
     */
    @Override
    public BankAccountResponseDTO createBankAccount(BankAccountRequestDTO bankAccountRequestDto) {
        LOGGER.info("Creating a new bank account for: {}", bankAccountRequestDto.getAccountName());

        // Convert request DTO to entity
        BankAccount bankAccount = BankAccountMapper.toEntity(bankAccountRequestDto);

        // Set a unique account number
        bankAccount.setAccountNumber(generateUniqueAccountNumber());

        // Save entity
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        LOGGER.info("Bank account created successfully with account number: {}", savedAccount.getAccountNumber());

        // Convert saved entity to response DTO
        return BankAccountMapper.toResponseDto(savedAccount);
    }


    /**
     * Retrieves a bank account based on account number and account name.
     *
     * @param searchRequestDto The DTO containing the search criteria.
     * @return BankAccountResponseDTO containing the matching bank account details.
     * @throws CustomException if no matching account is found.
     */
    @Override
    public BankAccountResponseDTO getBankAccountByAccountDetails(BankAccountSearchRequestDTO searchRequestDto) {
        LOGGER.info("Searching for bank account with account number: {} and account name: {}",
                searchRequestDto.getAccountNumber(), searchRequestDto.getAccountName());

        BankAccount bankAccount = bankAccountRepository.findByAccountNumberAndAccountName(
                searchRequestDto.getAccountNumber(), searchRequestDto.getAccountName());

        if (bankAccount == null) {
            LOGGER.warn("No bank account found with the provided details.");
            throw new CustomException(HttpStatus.NOT_FOUND,"Bank account not found with the provided details");
        }

        LOGGER.info("Bank account found: {}", bankAccount.getAccountNumber());
        return BankAccountMapper.toResponseDto(bankAccount);
    }

    /**
     * Retrieves all bank accounts from the database.
     *
     * @return List of BankAccountResponseDTO containing all bank accounts.
     */
    @Override
    public List<BankAccountResponseDTO> getAllBankAccounts() {
        LOGGER.info("Fetching all bank accounts from the database.");
        List<BankAccount> accounts = bankAccountRepository.findAll();

        LOGGER.info("Total bank accounts retrieved: {}", accounts.size());
        return accounts.stream()
                .map(BankAccountMapper::toResponseDto)
                .collect(Collectors.toList());
    }


    /**
     * Updates an existing bank account with the provided details.
     *
     * Finds the account by account number, updates it using the request DTO,
     * saves the changes, and returns the updated account in the response DTO.
     *
     * @param updateRequestDto The DTO with the updated bank account details.
     * @return BankAccountResponseDTO containing the updated account details.
     * @throws CustomException if the account is not found.
     */
    @Override
    public BankAccountResponseDTO updateBankAccount(BankAccountUpdateRequestDTO updateRequestDto) {
        LOGGER.info("Updating bank account with account number: {}", updateRequestDto.getAccountNumber());

        // Find the existing account
        BankAccount existingAccount = bankAccountRepository.findByAccountNumber(updateRequestDto.getAccountNumber());
        if (existingAccount == null) {
            LOGGER.warn("No bank account found with account number: {}", updateRequestDto.getAccountNumber());
            throw new CustomException(HttpStatus.NOT_FOUND, "Bank account not found with the provided account number");
        }

        // Update entity using mapper
        BankAccountMapper.updateEntity(existingAccount, updateRequestDto);

        // Save updated entity
        BankAccount updatedAccount = bankAccountRepository.save(existingAccount);
        LOGGER.info("Bank account updated successfully: {}", updatedAccount.getAccountNumber());

        // Convert updated entity to response DTO
        return BankAccountMapper.toResponseDto(updatedAccount);
    }
}
