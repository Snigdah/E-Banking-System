package com.salary.management.mapper;

import com.salary.management.dto.BankAccount.BankAccountRequestDTO;
import com.salary.management.dto.BankAccount.BankAccountResponseDTO;
import com.salary.management.dto.BankAccount.BankAccountUpdateRequestDTO;
import com.salary.management.entity.BankAccount;

import java.math.BigDecimal;
import java.util.UUID;

import static com.salary.management.logic.BankAccountLogic.convertStringToAccountType;

public class BankAccountMapper {

    /**
     * Converts a BankAccount entity to a BankAccountResponseDTO.
     *
     * @param bankAccount The entity to convert.
     * @return The corresponding response DTO.
     */
    public static BankAccountResponseDTO toResponseDto(BankAccount bankAccount) {
        return new BankAccountResponseDTO(
                bankAccount.getAccountName(),
                bankAccount.getAccountNumber(),
                bankAccount.getCurrentBalance(),
                bankAccount.getBankName(),
                bankAccount.getBranchName(),
                bankAccount.getAccountType()
        );
    }

    /**
     * Converts a BankAccountRequestDTO to a BankAccount entity.
     *
     * @param requestDto The request DTO with bank account details.
     * @return The corresponding BankAccount entity.
     */
    public static BankAccount toEntity(BankAccountRequestDTO requestDto) {
        BankAccount account = new BankAccount();
        account.setAccountName(requestDto.getAccountName());
        account.setBankName(requestDto.getBankName());
        account.setBranchName(requestDto.getBranchName());
        account.setAccountType(convertStringToAccountType(requestDto.getAccountType()));
        account.setCurrentBalance(BigDecimal.ZERO);
        return account;
    }

    /**
     * Updates an existing BankAccount entity with details from BankAccountUpdateRequestDTO.
     *
     * @param existingAccount The existing account to update.
     * @param dto The DTO containing updated bank account details.
     */
    public static void updateEntity(BankAccount existingAccount, BankAccountUpdateRequestDTO dto) {
        existingAccount.setAccountName(dto.getAccountName());
        existingAccount.setBankName(dto.getBankName());
        existingAccount.setBranchName(dto.getBranchName());
        existingAccount.setAccountType(convertStringToAccountType(dto.getAccountType()));
    }
}
