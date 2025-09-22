# 🏦 Banking Application (Java + Collections)

[![Java](https://img.shields.io/badge/Java-8+-orange.svg)](https://www.oracle.com/java/)
[![Collections](https://img.shields.io/badge/Collections-HashMap%20%7C%20ArrayList-blue.svg)]()
[![OOP](https://img.shields.io/badge/Programming-OOP-green.svg)]()
[![Console App](https://img.shields.io/badge/Interface-Console-yellow.svg)]()

A comprehensive **Banking Application** built using Java Collections Framework, demonstrating core banking operations with **HashMap**, **ArrayList**, **OOP concepts**, **exception handling**, and **user authentication**.

## 🎯 Project Overview

This Banking Application showcases real-world banking operations through a console-based interface, implementing:
- **Account Management**: Create, authenticate, and manage bank accounts
- **Transaction Processing**: Deposits, withdrawals, and transfers with validation
- **Collections Framework**: HashMap for accounts, ArrayList for transactions
- **Security Features**: Password authentication with lockout mechanism
- **Exception Handling**: Custom exceptions for banking scenarios
- **File Persistence**: Save/load data using file I/O operations

## 🔧 Tech Stack & Concepts

### **Core Technologies**
- **Language**: Java (Core)
- **Collections**: HashMap, ArrayList, List, Map
- **File I/O**: BufferedReader, BufferedWriter, FileReader, FileWriter
- **Date/Time**: LocalDateTime, LocalDate

### **OOP Concepts Demonstrated**
- **Encapsulation**: Private fields with getter/setter methods
- **Inheritance**: Not directly used (single-level class hierarchy)
- **Polymorphism**: Method overriding (toString, equals)
- **Abstraction**: Service layer abstraction

### **Design Patterns**
- **Service Layer Pattern**: BankingService, AuthenticationService
- **Repository Pattern**: Data access through FileHandler
- **Exception Handling Pattern**: Custom exception hierarchy

## 🚀 Main Features

### 🏦 **Account Management**
- ✅ Create new accounts with unique account numbers
- ✅ Store customer details (Name, Email, Phone, Address)
- ✅ Support multiple account types (SAVINGS, CURRENT)
- ✅ Account activation/deactivation
- ✅ Password management with validation

### 🔐 **User Authentication**
- ✅ Secure login with account number and password
- ✅ Failed login attempt tracking
- ✅ Account lockout after 3 failed attempts (5-minute lockout)
- ✅ Password change functionality
- ✅ Session management

### 💰 **Banking Operations**
- ✅ **Deposit**: Add money to account with validation
- ✅ **Withdraw**: Subtract money with balance verification
- ✅ **Transfer**: Move money between accounts
- ✅ **Balance Inquiry**: Real-time balance checking
- ✅ **Transaction History**: Last 10 transactions per account

### 🛡️ **Exception Handling**
- ✅ `InsufficientBalanceException`: Withdrawal exceeding balance
- ✅ `InvalidAmountException`: Negative or zero amounts
- ✅ `AccountNotFoundException`: Non-existent account access
- ✅ `InvalidCredentialsException`: Authentication failures
- ✅ `AccountInactiveException`: Operations on inactive accounts

### 💾 **Data Persistence**
- ✅ File-based storage (no database required)
- ✅ Automatic data loading on startup
- ✅ Data backup functionality with timestamps
- ✅ Account statement export
- ✅ CSV-compatible format

### 📊 **Collections Framework Usage**

#### **HashMap Implementation**
```java
// Account storage with O(1) lookup
private HashMap<String, Account> accounts;
private HashMap<String, Customer> customers;

// Security tracking
private Map<String, Integer> loginAttempts;
private Map<String, Long> lockedAccounts;
```

#### **ArrayList Implementation**
```java
// Transaction history per account
private List<Transaction> transactionHistory;

// Global transaction tracking
private ArrayList<Transaction> allTransactions;
```

## 📁 Project Structure

```
BankingApplication/
├── src/
│   ├── Account.java              # Account entity with business logic
│   ├── Customer.java             # Customer entity with personal details
│   ├── Transaction.java          # Transaction entity with timestamp
│   ├── BankingService.java       # Core banking operations using Collections
│   ├── AuthenticationService.java # Security and login management
│   ├── FileHandler.java          # Data persistence and file operations
│   ├── BankingApp.java           # Main application with console UI
│   ├── InsufficientBalanceException.java    # Custom exception
│   ├── InvalidAmountException.java          # Custom exception
│   ├── AccountNotFoundException.java        # Custom exception
│   ├── InvalidCredentialsException.java     # Custom exception
│   └── AccountInactiveException.java        # Custom exception
├── data/                         # Data storage directory (auto-created)
│   ├── customers.txt            # Customer data file
│   ├── accounts.txt             # Account data file
│   └── transactions.txt         # Transaction history file
├── build/                       # Compiled class files (auto-created)
├── compile_and_run.bat         # Windows compilation and run script
├── run.bat                     # Windows quick run script
└── README.md                   # This documentation
```

## 🚀 Quick Start

### Prerequisites
- **Java JDK 8 or higher** installed
- Command prompt or terminal access
- Windows OS (for batch files) or any OS with Java

### Method 1: Using Batch Files (Windows)
```bash
# Clone or download the project
cd BankingApplication

# Compile and run (first time)
.\compile_and_run.bat

# Quick run (subsequent times)
.\run.bat
```

### Method 2: Manual Compilation
```bash
# Navigate to project directory
cd BankingApplication

# Create build directory
mkdir build

# Compile all Java files
javac -d build src/*.java

# Run the application
cd build
java BankingApp
```

### Method 3: Using IDE
1. Import the project into your Java IDE (IntelliJ IDEA, Eclipse, etc.)
2. Set the `src` folder as the source directory
3. Run the `BankingApp.java` file

## 🎮 How to Use

### 1. **First Launch**
- Application starts with sample accounts for testing
- Data files are automatically created in `data/` directory
- Sample accounts: ACC001001, ACC001002, ACC001003 (password: password123, password456, password789)

### 2. **Main Menu Navigation**
```
┌─────────────────────────────────────────────────────────┐
│                      MAIN MENU                         │
├─────────────────────────────────────────────────────────┤
│ 1. Create New Account                                   │
│ 2. Login to Account                                     │
│ 3. View All Accounts (Demo)                            │
│ 4. Banking Statistics                                   │
│ 5. Security Status                                      │
│ 6. Exit                                                 │
└─────────────────────────────────────────────────────────┘
```

### 3. **Sample Operations**

#### Create New Account:
1. Select "Create New Account" from main menu
2. Enter personal details (name, email, phone, address)
3. Choose account type (SAVINGS/CURRENT)
4. Set initial deposit amount
5. Create a secure password
6. Note down the generated account number

#### Login and Banking:
1. Select "Login to Account"
2. Enter account number and password
3. Access banking operations menu
4. Perform deposits, withdrawals, transfers
5. View transaction history and account details

#### Transfer Money:
1. Login to your account
2. Select "Transfer Money"
3. Enter recipient account number
4. Enter transfer amount
5. Confirm the transaction

## 💡 Collections Framework Implementation

### **HashMap Usage Examples**

#### Account Storage and Retrieval
```java
// O(1) account lookup
public Account getAccount(String accountNumber) throws AccountNotFoundException {
    Account account = accounts.get(accountNumber);
    if (account == null) {
        throw new AccountNotFoundException("Account not found", accountNumber);
    }
    return account;
}

// Account type distribution using HashMap
public Map<String, Integer> getAccountTypeDistribution() {
    Map<String, Integer> distribution = new HashMap<>();
    for (Account account : accounts.values()) {
        String type = account.getAccountType();
        distribution.put(type, distribution.getOrDefault(type, 0) + 1);
    }
    return distribution;
}
```

#### Security Tracking
```java
// Track failed login attempts
private Map<String, Integer> loginAttempts = new HashMap<>();

// Increment failed attempts
int attempts = loginAttempts.getOrDefault(accountNumber, 0) + 1;
loginAttempts.put(accountNumber, attempts);
```

### **ArrayList Usage Examples**

#### Transaction Management
```java
// Maintain transaction history with size limit
private void addTransaction(String type, double amount, String description) {
    Transaction transaction = new Transaction(transactionId, accountNumber, 
                                            type, amount, balance, description);
    
    // Keep only last 10 transactions
    if (transactionHistory.size() >= MAX_TRANSACTION_HISTORY) {
        transactionHistory.remove(0);
    }
    transactionHistory.add(transaction);
}
```

#### Filtering and Searching
```java
// Filter accounts by type using Streams
public List<Account> getAccountsByType(String accountType) {
    return accounts.values().stream()
            .filter(account -> account.getAccountType().equalsIgnoreCase(accountType))
            .collect(Collectors.toList());
}

// Get transactions by type
public List<Transaction> getTransactionsByType(String transactionType) {
    return allTransactions.stream()
            .filter(transaction -> transaction.getTransactionType().equalsIgnoreCase(transactionType))
            .collect(Collectors.toList());
}
```

## 🔒 Security Features

### **Authentication System**
- Password-based authentication
- Failed login attempt tracking
- Account lockout mechanism (3 attempts = 5-minute lockout)
- Password strength validation
- Secure password change process

### **Exception Handling**
```java
// Custom exception with detailed information
public class InsufficientBalanceException extends Exception {
    private double availableBalance;
    private double requestedAmount;
    
    public InsufficientBalanceException(String message, double available, double requested) {
        super(message);
        this.availableBalance = available;
        this.requestedAmount = requested;
    }
}
```

## 📊 Sample Data

### **Default Accounts**
| Account Number | Customer Name | Type     | Balance  | Password    |
|---------------|---------------|----------|----------|-------------|
| ACC001001     | John Doe      | SAVINGS  | ₹5,000   | password123 |
| ACC001002     | Jane Smith    | CURRENT  | ₹10,000  | password456 |
| ACC001003     | Bob Johnson   | SAVINGS  | ₹2,500   | password789 |

### **Account Types**
- **SAVINGS**: Standard personal account
- **CURRENT**: Business account for frequent transactions

## 🎓 Learning Outcomes

### **Collections Framework Mastery**
- HashMap for O(1) key-value operations
- ArrayList for dynamic arrays and transaction history
- Stream API for filtering and data processing
- Collection iteration and manipulation

### **Exception Handling**
- Custom exception design and implementation
- Try-catch blocks for error management
- Exception propagation and handling strategies

### **File I/O Operations**
- Reading and writing text files
- Data serialization and deserialization
- Backup and restore functionality
- CSV format handling

### **Object-Oriented Design**
- Service layer architecture
- Encapsulation and data hiding
- Method design and parameter validation
- Class relationships and dependencies

## 🚀 Future Enhancements

### **Technical Improvements**
- [ ] **Database Integration**: MySQL/PostgreSQL support
- [ ] **GUI Interface**: Swing or JavaFX implementation
- [ ] **Web Interface**: Spring Boot REST API
- [ ] **Multi-threading**: Concurrent transaction processing
- [ ] **Encryption**: Password hashing and data encryption

### **Banking Features**
- [ ] **Interest Calculation**: Automatic interest on savings accounts
- [ ] **Loan Management**: Personal and business loans
- [ ] **Fixed Deposits**: Time-based investment products
- [ ] **ATM Simulation**: Card-based transactions
- [ ] **Mobile Banking**: SMS and email notifications

### **Advanced Collections**
- [ ] **TreeMap**: Sorted account listings
- [ ] **LinkedHashMap**: Insertion-order preservation
- [ ] **ConcurrentHashMap**: Thread-safe operations
- [ ] **Priority Queue**: Transaction processing queue

## 🎯 Key Collections Concepts Demonstrated

### **HashMap Benefits**
1. **O(1) Average Time Complexity**: Fast account lookups
2. **Key-Value Storage**: Perfect for account number → account mapping
3. **Null Handling**: Supports null values for optional fields
4. **Memory Efficient**: Optimal space utilization

### **ArrayList Benefits**
1. **Dynamic Sizing**: Grows as transactions are added
2. **Index-based Access**: Fast access to specific transactions
3. **Iteration Support**: Easy traversal for display
4. **Collection Operations**: Sorting, filtering, searching

### **Real-world Applications**
- **Banking Systems**: Account management and transaction processing
- **E-commerce**: Shopping carts and order history
- **Social Media**: User profiles and activity feeds
- **Gaming**: Player statistics and leaderboards

## 🤝 Contributing

### How to Contribute
1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Implement your changes with proper Collections usage
4. Add comprehensive error handling
5. Test thoroughly with various scenarios
6. Commit changes: `git commit -m 'Add amazing feature'`
7. Push to branch: `git push origin feature/amazing-feature`
8. Open a Pull Request

### Development Guidelines
- Follow Java naming conventions
- Use appropriate Collections for data structures
- Implement comprehensive exception handling
- Add JavaDoc comments for public methods
- Include unit tests for new features
- Update README for significant changes



## 🙏 Acknowledgments

- **Java Collections Framework** for powerful data structures
- **Object-Oriented Programming Principles** for clean design
- **Exception Handling Best Practices** for robust error management
- **File I/O Patterns** for data persistence implementation

---

<div align="center">

### 🏦 **Perfect for Learning Collections Framework & Banking Systems!**

**Built with ❤️ using Java Collections, OOP, and Exception Handling**

[📖 View Code](src/) • [🐛 Report Bug](issues) • [✨ Request Feature](issues)

</div>

---

**Project Type**: Educational/Professional  
**Difficulty Level**: Intermediate to Advanced  
**Estimated Time**: 3-4 days  
**Learning Focus**: Collections Framework, Exception Handling, Banking Domain
