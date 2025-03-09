package com.salary.management.mapper;

import com.salary.management.dto.CompanyAccount.CompanyAccountRequestDTO;
import com.salary.management.dto.CompanyAccount.CompanyAccountResponseDTO;
import com.salary.management.dto.CompanyAccount.CompanyAccountUpdateRequestDTO;
import com.salary.management.entity.CompanyAccount;

import java.math.BigDecimal;

import static com.salary.management.logic.BankAccountLogic.generateUniqueAccountNumber;

public class CompanyAccountMapper {

    /**
     * Converts a BankAccount entity to a BankAccountResponseDTO.
     *
     * @param companyAccount The entity to convert.
     * @return The corresponding response DTO.
     */
    public static CompanyAccountResponseDTO toResponseDto(CompanyAccount companyAccount) {
        return new CompanyAccountResponseDTO(
                companyAccount.getAccountName(),
                companyAccount.getAccountNumber(),
                companyAccount.getCurrentBalance(),
                companyAccount.getBankName(),
                companyAccount.getBranchName(),
                companyAccount.getPaidBalance()
        );
    }

    /**
     * Converts a BankAccountRequestDTO to a BankAccount entity.
     *
     * @param requestDto The request DTO with bank account details.
     * @return The corresponding BankAccount entity.
     */
    public static CompanyAccount toEntity(CompanyAccountRequestDTO requestDto) {
        CompanyAccount account = new CompanyAccount();
        account.setAccountName(requestDto.getAccountName());
        account.setAccountNumber(generateUniqueAccountNumber());
        account.setCurrentBalance(requestDto.getCurrentBalance());
        account.setBankName(requestDto.getBankName());
        account.setBranchName(requestDto.getBranchName());
        account.setPaidBalance(BigDecimal.ZERO);
        return account;
    }

    /**
     * Updates an existing BankAccount entity with details from BankAccountUpdateRequestDTO.
     *
     * @param existingAccount The existing account to update.
     * @param dto The DTO containing updated bank account details.
     */
    public static void updateEntity(CompanyAccount existingAccount, CompanyAccountUpdateRequestDTO dto) {
        existingAccount.setAccountName(dto.getAccountName());
        existingAccount.setBankName(dto.getBankName());
        existingAccount.setBranchName(dto.getBranchName());
    }
}
