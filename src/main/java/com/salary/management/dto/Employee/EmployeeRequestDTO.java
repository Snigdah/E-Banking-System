package com.salary.management.dto.Employee;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Grade is required")
    @Min(value = 1, message = "Grade must be between 1 and 6")
    @Max(value = 6, message = "Grade must be between 1 and 6")
    private Integer grade;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{11}$", message = "Mobile number must be 11 digits")
    private String mobileNumber;

    @NotBlank(message = "Account name is required")
    private String accountName;

    @NotBlank(message = "Account number is required")
    private String accountNumber;
}
