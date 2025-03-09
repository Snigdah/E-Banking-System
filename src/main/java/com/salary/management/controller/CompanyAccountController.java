package com.salary.management.controller;

import com.salary.management.dto.CompanyAccount.*;
import com.salary.management.dto.Salary.TransferRequestDTO;
import com.salary.management.response.ResponseHandler;
import com.salary.management.service.ICompanyAccountService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing company bank accounts.
 */
@RestController
@RequestMapping("/api/company-accounts")
public class CompanyAccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyAccountController.class);
    private final ICompanyAccountService companyAccountService;

    public CompanyAccountController(ICompanyAccountService companyAccountService) {
        this.companyAccountService = companyAccountService;
    }

    /**
     * Creates a new company account.
     *
     * @param companyAccountRequestDto The request DTO containing company account details.
     * @return ResponseEntity containing the created company account.
     */
    @PostMapping
    public ResponseEntity<Object> createCompanyAccount(
            @Valid @RequestBody CompanyAccountRequestDTO companyAccountRequestDto) {
        LOGGER.info("Received request to create a company account for: {}", companyAccountRequestDto.getAccountName());
        CompanyAccountResponseDTO response = companyAccountService.createCompanyAccount(companyAccountRequestDto);
        return ResponseHandler.generateResponse("Company Account Created Successfully", HttpStatus.CREATED, response);
    }

    /**
     * Retrieves a company account based on account number and account name.
     *
     * @param searchRequestDto The DTO containing the search criteria.
     * @return ResponseEntity containing the matching company account details.
     */
    @PostMapping("/search")
    public ResponseEntity<Object> getCompanyAccountByDetails(
            @Valid @RequestBody CompanyAccountSearchRequestDTO searchRequestDto) {
        LOGGER.info("Received request to search for company account with account number: {}", searchRequestDto.getAccountNumber());
        CompanyAccountResponseDTO response = companyAccountService.getCompanyAccountByDetails(searchRequestDto);
        return ResponseHandler.generateResponse("Fetch company account details successfully", HttpStatus.OK, response);
    }

    /**
     * Retrieves all company accounts from the database.
     *
     * @return ResponseEntity containing a list of all company accounts.
     */
    @GetMapping
    public ResponseEntity<Object> getAllCompanyAccounts() {
        LOGGER.info("Received request to fetch all company accounts.");
        List<CompanyAccountResponseDTO> response = companyAccountService.getAllCompanyAccounts();
        return ResponseHandler.generateResponse("Fetch all company accounts successfully", HttpStatus.OK, response);
    }

    /**
     * Updates an existing company account.
     *
     * @param updateRequestDto The DTO containing updated company account details.
     * @return ResponseEntity containing the updated company account.
     */
    @PutMapping
    public ResponseEntity<Object> updateCompanyAccount(
            @Valid @RequestBody CompanyAccountUpdateRequestDTO updateRequestDto) {
        LOGGER.info("Received request to update company account with account number: {}", updateRequestDto.getAccountNumber());
        CompanyAccountResponseDTO response = companyAccountService.updateCompanyAccount(updateRequestDto);
        return ResponseHandler.generateResponse("Company account updated successfully", HttpStatus.OK, response);
    }

    /**
     * Deletes a company account.
     *
     * @param searchRequestDto The DTO containing account number and name for deletion.
     * @return ResponseEntity indicating the result of the deletion.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteCompanyAccount(
            @Valid @RequestBody CompanyAccountSearchRequestDTO searchRequestDto) {
        LOGGER.info("Received request to delete company account with account number: {}", searchRequestDto.getAccountNumber());
        companyAccountService.deleteCompanyAccount(searchRequestDto);
        return ResponseHandler.generateResponse("Company account deleted successfully", HttpStatus.OK);
    }

    /**
     * Adds funds to a company account.
     *
     * @param addFundsRequestDto The DTO containing the account number and amount to add.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/add-funds")
    public ResponseEntity<Object> addFundsToCompanyAccount(
            @Valid @RequestBody CompanyAccountAddFundsRequestDTO addFundsRequestDto) {
        LOGGER.info("Received request to add funds to company account with account number: {}", addFundsRequestDto.getAccountNumber());
        boolean success = companyAccountService.addFundsToCompanyAccount(addFundsRequestDto);
        if (success) {
            return ResponseHandler.generateResponse("Funds added successfully", HttpStatus.OK);
        } else {
            return ResponseHandler.generateResponse("Failed to add funds", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to transfer salary from company to employee.
     *
     * @param transferRequest DTO containing company account number and employee ID
     * @return Response entity indicating the success of the operation
     */
    @PostMapping("/transfer-salary")
    public ResponseEntity<Object> transferSalary(@Valid @RequestBody TransferRequestDTO transferRequest) {
        LOGGER.info("Received request to transfer salary from company account {} to employee ID {}",
                transferRequest.getCompanyAccountNumber(), transferRequest.getEmployeeId());
        companyAccountService.transferSalary(transferRequest);
        return ResponseHandler.generateResponse("Transfer completed successfully.", HttpStatus.OK);
    }
}
