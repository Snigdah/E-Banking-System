package com.salary.management.logic;

import com.salary.management.entity.AccountType;
import com.salary.management.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Utility class for handling bank account-related business logic.
 */
@Component
public class BankAccountLogic {

    /**
     * Converts a string representation of an account type into an {@link AccountType} enum.
     *
     * @param accountTypeStr The string representation of the account type (e.g., "SAVINGS", "CURRENT").
     * @return The corresponding {@link AccountType} enum value.
     * @throws CustomException If the provided account type is invalid.
     */
    public static AccountType convertStringToAccountType(String accountTypeStr) {
        if ("SAVINGS".equalsIgnoreCase(accountTypeStr)) {
            return AccountType.SAVINGS;
        } else if ("CURRENT".equalsIgnoreCase(accountTypeStr)) {
            return AccountType.CURRENT;
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Invalid account type. Supported types are SAVINGS and CURRENT.");
        }
    }

    /**
     * Generates a unique 10-character bank account number using a UUID.
     *
     * @return A unique 10-digit alphanumeric account number.
     */
    public static String generateUniqueAccountNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
