package com.salary.management.controller;

import com.salary.management.dto.BankAccount.BankAccountRequestDTO;
import com.salary.management.dto.BankAccount.BankAccountResponseDTO;
import com.salary.management.dto.BankAccount.BankAccountSearchRequestDTO;
import com.salary.management.dto.BankAccount.BankAccountUpdateRequestDTO;
import com.salary.management.response.ResponseHandler;
import com.salary.management.service.IBankAccountService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing bank accounts.
 */
@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountController.class);
    private final IBankAccountService bankAccountService;

    public BankAccountController(IBankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    /**
     * Creates a new bank account.
     *
     * @param bankAccountRequestDto The request DTO containing bank account details.
     * @return ResponseEntity containing the created bank account.
     */
    @PostMapping
    public ResponseEntity<Object> createBankAccount(
            @Valid @RequestBody BankAccountRequestDTO bankAccountRequestDto) {
        LOGGER.info("Received request to create a bank account for: {}", bankAccountRequestDto.getAccountName());
        BankAccountResponseDTO response = bankAccountService.createBankAccount(bankAccountRequestDto);
        return ResponseHandler.generateResponse("Create Account Successfully", HttpStatus.CREATED, response);
    }

    /**
     * Retrieves a bank account based on account number and account name.
     *
     * @param searchRequestDto The DTO containing the search criteria.
     * @return ResponseEntity containing the matching bank account details.
     */
    @PostMapping("/search")
    public ResponseEntity<Object> getBankAccountByDetails(
            @Valid @RequestBody BankAccountSearchRequestDTO searchRequestDto) {
        LOGGER.info("Received request to search for bank account with account number: {}", searchRequestDto.getAccountNumber());
        BankAccountResponseDTO response = bankAccountService.getBankAccountByAccountDetails(searchRequestDto);
        return ResponseHandler.generateResponse("Fetch bank details Successfully", HttpStatus.OK, response);
    }

    /**
     * Retrieves all bank accounts from the database.
     *
     * @return ResponseEntity containing a list of all bank accounts.
     */
    @GetMapping
    public ResponseEntity<Object> getAllBankAccounts() {
        LOGGER.info("Received request to fetch all bank accounts.");
        List<BankAccountResponseDTO> response = bankAccountService.getAllBankAccounts();
        return ResponseHandler.generateResponse("Fetch all bank info Successfully", HttpStatus.OK, response);
    }

    /**
     * Updates an existing bank account.
     *
     * @param updateRequestDto The DTO containing updated account details.
     * @return ResponseEntity containing the updated bank account.
     */
    @PutMapping
    public ResponseEntity<Object> updateBankAccount(
            @Valid @RequestBody BankAccountUpdateRequestDTO updateRequestDto) {
        LOGGER.info("Received request to update bank account with account number: {}", updateRequestDto.getAccountNumber());
        BankAccountResponseDTO response = bankAccountService.updateBankAccount(updateRequestDto);
        return ResponseHandler.generateResponse("Bank account updated successfully", HttpStatus.OK, response);
    }
}
