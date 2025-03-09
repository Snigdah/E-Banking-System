# eBanking System

This project is an eBanking system designed to manage multiple vendors and employees. It includes features like bank account creation for companies and employees, salary payments, and various other financial operations.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Features

- Create and manage bank accounts for employees and companies.
- Search for bank or company accounts using specific criteria.
- Update and delete company accounts.
- Employee management, including creating, updating, deleting, and retrieving employees.
- Manage salary operations including setting base salaries and calculating salaries based on employee grades.
- Transfer salaries from company accounts to employee accounts.
- Add funds to company accounts.

## Tech Stack

- Java
- Spring Boot
- RESTful APIs
- SLF4J for logging
- Jakarta Bean Validation
- DTO pattern for data transfer
- Custom Response Handler

## Prerequisites

- Java 11 or higher
- Maven
- Any IDE like IntelliJ IDEA or Eclipse

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   ```

2. **Navigate to the project directory:**
   ```bash
   cd ebanking-system
   ```

3. **Build the project using Maven:**
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the swagger documentation** for interacting with the APIs (if configured with swagger).

## API Endpoints

### Bank Account Management

- `POST /api/bank-accounts` - Create a new bank account.
- `POST /api/bank-accounts/search` - Retrieve account details using specific criteria.
- `GET /api/bank-accounts` - Fetch all bank accounts.
- `PUT /api/bank-accounts` - Update an existing bank account.

### Company Account Management

- `POST /api/company-accounts` - Create a new company account.
- `POST /api/company-accounts/search` - Search for company accounts.
- `GET /api/company-accounts` - Retrieve all company accounts.
- `PUT /api/company-accounts` - Update an existing company account.
- `DELETE /api/company-accounts/delete` - Delete a company account.
- `POST /api/company-accounts/add-funds` - Add funds to a company account.
- `POST /api/company-accounts/transfer-salary` - Transfer salary from company to employee.

### Employee Management

- `POST /api/employees/create` - Create a new employee.
- `GET /api/employees/{employeeId}` - Retrieve an employee by ID.
- `GET /api/employees/all` - Fetch all employees.
- `DELETE /api/employees/{employeeId}` - Delete an employee by ID.
- `PUT /api/employees/{employeeId}` - Update an employee by ID.

### Salary Management

- `POST /api/salary/setBaseSalary` - Set the base salary.
- `GET /api/salary/getBaseSalary` - Retrieve the base salary.
- `POST /api/salary/calculateSalary` - Calculate salary based on employee grade.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure that your code follows the coding standards and includes necessary tests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

For any questions or issues regarding this project, please open an issue or contact the project maintainer.
