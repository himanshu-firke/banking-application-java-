import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Account class representing a bank account
 * Demonstrates encapsulation, composition, and business logic
 */
public class Account {
    private String accountNumber;
    private String password;
    private Customer customer;
    private String accountType; // SAVINGS, CURRENT
    private double balance;
    private LocalDate dateCreated;
    private boolean isActive;
    private List<Transaction> transactionHistory;
    private static final int MAX_TRANSACTION_HISTORY = 10;
    
    // Constructor
    public Account(String accountNumber, String password, Customer customer, 
                   String accountType, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.customer = customer;
        this.accountType = accountType;
        this.balance = initialDeposit;
        this.dateCreated = LocalDate.now();
        this.isActive = true;
        this.transactionHistory = new ArrayList<>();
        
        // Add initial deposit transaction
        if (initialDeposit > 0) {
            addTransaction("DEPOSIT", initialDeposit, "Initial deposit");
        }
    }
    
    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String getAccountType() {
        return accountType;
    }
    
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public LocalDate getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory); // Return copy to maintain encapsulation
    }
    
    // Business methods
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
    
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive");
        }
        if (!isActive) {
            throw new AccountInactiveException("Account is inactive");
        }
        
        balance += amount;
        addTransaction("DEPOSIT", amount, "Cash deposit");
    }
    
    public void withdraw(double amount) throws InsufficientBalanceException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive");
        }
        if (!isActive) {
            throw new AccountInactiveException("Account is inactive");
        }
        if (balance < amount) {
            throw new InsufficientBalanceException("Insufficient balance. Available: " + balance);
        }
        
        balance -= amount;
        addTransaction("WITHDRAWAL", amount, "Cash withdrawal");
    }
    
    // Private method to add transaction to history
    private void addTransaction(String type, double amount, String description) {
        String transactionId = generateTransactionId();
        Transaction transaction = new Transaction(transactionId, accountNumber, type, 
                                                amount, balance, description);
        
        // Maintain only last MAX_TRANSACTION_HISTORY transactions
        if (transactionHistory.size() >= MAX_TRANSACTION_HISTORY) {
            transactionHistory.remove(0);
        }
        transactionHistory.add(transaction);
    }
    
    // Generate unique transaction ID
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
    
    // Display account information
    public void displayAccountInfo() {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                   ACCOUNT INFORMATION                   │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ Account Number: " + String.format("%-35s", accountNumber) + " │");
        System.out.println("│ Account Type:   " + String.format("%-35s", accountType) + " │");
        System.out.println("│ Balance:        " + String.format("₹%-34.2f", balance) + " │");
        System.out.println("│ Date Created:   " + String.format("%-35s", dateCreated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) + " │");
        System.out.println("│ Status:         " + String.format("%-35s", isActive ? "Active" : "Inactive") + " │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│                  CUSTOMER INFORMATION                   │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ Name:           " + String.format("%-35s", customer.getName()) + " │");
        System.out.println("│ Customer ID:    " + String.format("%-35s", customer.getCustomerId()) + " │");
        System.out.println("│ Email:          " + String.format("%-35s", customer.getEmail()) + " │");
        System.out.println("│ Phone:          " + String.format("%-35s", customer.getPhone()) + " │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }
    
    // Display transaction history
    public void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        
        System.out.println("┌──────────────┬────────────┬────────────┬────────────┬─────────────────────┐");
        System.out.println("│ Transaction  │    Type    │   Amount   │  Balance   │      Timestamp      │");
        System.out.println("│      ID      │            │            │   After    │                     │");
        System.out.println("├──────────────┼────────────┼────────────┼────────────┼─────────────────────┤");
        
        for (Transaction transaction : transactionHistory) {
            transaction.displayTransaction();
        }
        
        System.out.println("└──────────────┴────────────┴────────────┴────────────┴─────────────────────┘");
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", customer=" + customer.getName() +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", dateCreated=" + dateCreated +
                ", isActive=" + isActive +
                '}';
    }
    
    // Method to convert account to file string format
    public String toFileString() {
        return accountNumber + "," + password + "," + customer.getCustomerId() + "," + 
               accountType + "," + balance + "," + dateCreated + "," + isActive;
    }
    
    // Static method to create account from file string (requires customer lookup)
    public static Account fromFileString(String fileString, Customer customer) {
        String[] parts = fileString.split(",");
        if (parts.length == 7) {
            Account account = new Account(parts[0], parts[1], customer, parts[3], 
                                        Double.parseDouble(parts[4]));
            account.setDateCreated(LocalDate.parse(parts[5]));
            account.setActive(Boolean.parseBoolean(parts[6]));
            return account;
        }
        return null;
    }
}
