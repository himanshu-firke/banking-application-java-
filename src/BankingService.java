import java.util.*;
import java.util.stream.Collectors;

/**
 * BankingService class - Core business logic using Collections Framework
 * Demonstrates HashMap, ArrayList, and various collection operations
 */
public class BankingService {
    // HashMap to store accounts with account number as key
    private HashMap<String, Account> accounts;
    
    // HashMap to store customers with customer ID as key
    private HashMap<String, Customer> customers;
    
    // ArrayList to store all transactions across all accounts
    private ArrayList<Transaction> allTransactions;
    
    // Counter for generating unique account numbers
    private static int accountCounter = 1000;
    private static int customerCounter = 1000;
    
    // Constructor
    public BankingService() {
        this.accounts = new HashMap<>();
        this.customers = new HashMap<>();
        this.allTransactions = new ArrayList<>();
        initializeSampleData();
    }
    
    // Account Management Methods
    public String createAccount(String customerName, String email, String phone, String address,
                              String accountType, double initialDeposit, String password) 
                              throws InvalidAmountException {
        
        if (initialDeposit < 0) {
            throw new InvalidAmountException("Initial deposit cannot be negative", initialDeposit);
        }
        
        // Create customer
        String customerId = generateCustomerId();
        Customer customer = new Customer(customerId, customerName, email, phone, address);
        customers.put(customerId, customer);
        
        // Create account
        String accountNumber = generateAccountNumber();
        Account account = new Account(accountNumber, password, customer, accountType, initialDeposit);
        accounts.put(accountNumber, account);
        
        // Add initial transaction to global list
        if (initialDeposit > 0) {
            List<Transaction> accountTransactions = account.getTransactionHistory();
            if (!accountTransactions.isEmpty()) {
                allTransactions.add(accountTransactions.get(accountTransactions.size() - 1));
            }
        }
        
        return accountNumber;
    }
    
    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found", accountNumber);
        }
        return account;
    }
    
    public boolean authenticateAccount(String accountNumber, String password) 
                                     throws AccountNotFoundException, InvalidCredentialsException {
        Account account = getAccount(accountNumber);
        if (!account.authenticate(password)) {
            throw new InvalidCredentialsException("Invalid password", accountNumber);
        }
        return true;
    }
    
    // Transaction Methods
    public void deposit(String accountNumber, double amount) 
                       throws AccountNotFoundException, InvalidAmountException {
        Account account = getAccount(accountNumber);
        account.deposit(amount);
        
        // Add transaction to global list
        List<Transaction> transactions = account.getTransactionHistory();
        if (!transactions.isEmpty()) {
            allTransactions.add(transactions.get(transactions.size() - 1));
        }
    }
    
    public void withdraw(String accountNumber, double amount) 
                        throws AccountNotFoundException, InsufficientBalanceException, InvalidAmountException {
        Account account = getAccount(accountNumber);
        account.withdraw(amount);
        
        // Add transaction to global list
        List<Transaction> transactions = account.getTransactionHistory();
        if (!transactions.isEmpty()) {
            allTransactions.add(transactions.get(transactions.size() - 1));
        }
    }
    
    public double getBalance(String accountNumber) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        return account.getBalance();
    }
    
    public List<Transaction> getTransactionHistory(String accountNumber) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        return account.getTransactionHistory();
    }
    
    // Transfer money between accounts
    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) 
                        throws AccountNotFoundException, InsufficientBalanceException, InvalidAmountException {
        
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new InvalidAmountException("Cannot transfer to the same account");
        }
        
        Account fromAccount = getAccount(fromAccountNumber);
        Account toAccount = getAccount(toAccountNumber);
        
        // Perform withdrawal from source account
        fromAccount.withdraw(amount);
        
        // Perform deposit to destination account
        toAccount.deposit(amount);
        
        // Update global transaction list
        List<Transaction> fromTransactions = fromAccount.getTransactionHistory();
        List<Transaction> toTransactions = toAccount.getTransactionHistory();
        
        if (!fromTransactions.isEmpty()) {
            allTransactions.add(fromTransactions.get(fromTransactions.size() - 1));
        }
        if (!toTransactions.isEmpty()) {
            allTransactions.add(toTransactions.get(toTransactions.size() - 1));
        }
    }
    
    // Search and Filter Methods using Collections
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
    
    public List<Account> getAccountsByType(String accountType) {
        return accounts.values().stream()
                .filter(account -> account.getAccountType().equalsIgnoreCase(accountType))
                .collect(Collectors.toList());
    }
    
    public List<Account> getActiveAccounts() {
        return accounts.values().stream()
                .filter(Account::isActive)
                .collect(Collectors.toList());
    }
    
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }
    
    public Customer findCustomerByName(String name) {
        return customers.values().stream()
                .filter(customer -> customer.getName().toLowerCase().contains(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
    
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(allTransactions);
    }
    
    public List<Transaction> getTransactionsByType(String transactionType) {
        return allTransactions.stream()
                .filter(transaction -> transaction.getTransactionType().equalsIgnoreCase(transactionType))
                .collect(Collectors.toList());
    }
    
    // Statistics Methods
    public int getTotalAccountsCount() {
        return accounts.size();
    }
    
    public int getActiveAccountsCount() {
        return (int) accounts.values().stream().filter(Account::isActive).count();
    }
    
    public double getTotalBalance() {
        return accounts.values().stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }
    
    public Map<String, Integer> getAccountTypeDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        for (Account account : accounts.values()) {
            String type = account.getAccountType();
            distribution.put(type, distribution.getOrDefault(type, 0) + 1);
        }
        return distribution;
    }
    
    public Map<String, Integer> getTransactionTypeDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        for (Transaction transaction : allTransactions) {
            String type = transaction.getTransactionType();
            distribution.put(type, distribution.getOrDefault(type, 0) + 1);
        }
        return distribution;
    }
    
    // Account Management
    public void deactivateAccount(String accountNumber) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        account.setActive(false);
    }
    
    public void activateAccount(String accountNumber) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);
        account.setActive(true);
    }
    
    public boolean changePassword(String accountNumber, String oldPassword, String newPassword) 
                                 throws AccountNotFoundException, InvalidCredentialsException {
        Account account = getAccount(accountNumber);
        if (!account.authenticate(oldPassword)) {
            throw new InvalidCredentialsException("Current password is incorrect", accountNumber);
        }
        account.setPassword(newPassword);
        return true;
    }
    
    // Utility Methods
    private String generateAccountNumber() {
        return "ACC" + String.format("%06d", ++accountCounter);
    }
    
    private String generateCustomerId() {
        return "CUST" + String.format("%06d", ++customerCounter);
    }
    
    // Initialize with sample data for testing
    private void initializeSampleData() {
        try {
            // Create sample accounts
            createAccount("John Doe", "john.doe@email.com", "9876543210", 
                         "123 Main St, City", "SAVINGS", 5000.0, "password123");
            
            createAccount("Jane Smith", "jane.smith@email.com", "9876543211", 
                         "456 Oak Ave, City", "CURRENT", 10000.0, "password456");
            
            createAccount("Bob Johnson", "bob.johnson@email.com", "9876543212", 
                         "789 Pine St, City", "SAVINGS", 2500.0, "password789");
            
        } catch (InvalidAmountException e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
        }
    }
    
    // Display all accounts in tabular format
    public void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        
        System.out.println("┌─────────────┬─────────────────────┬─────────────┬─────────────┬──────────┐");
        System.out.println("│ Account No  │    Customer Name    │    Type     │   Balance   │  Status  │");
        System.out.println("├─────────────┼─────────────────────┼─────────────┼─────────────┼──────────┤");
        
        for (Account account : accounts.values()) {
            System.out.printf("│ %-11s │ %-19s │ %-11s │ %11.2f │ %-8s │%n",
                    account.getAccountNumber(),
                    account.getCustomer().getName(),
                    account.getAccountType(),
                    account.getBalance(),
                    account.isActive() ? "Active" : "Inactive");
        }
        
        System.out.println("└─────────────┴─────────────────────┴─────────────┴─────────────┴──────────┘");
        System.out.println("Total Accounts: " + accounts.size());
    }
    
    // Display banking statistics
    public void displayBankingStatistics() {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                 BANKING STATISTICS                      │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.printf("│ Total Accounts:        %-28d │%n", getTotalAccountsCount());
        System.out.printf("│ Active Accounts:       %-28d │%n", getActiveAccountsCount());
        System.out.printf("│ Total Customers:       %-28d │%n", customers.size());
        System.out.printf("│ Total Balance:         ₹%-27.2f │%n", getTotalBalance());
        System.out.printf("│ Total Transactions:    %-28d │%n", allTransactions.size());
        System.out.println("├─────────────────────────────────────────────────────────┤");
        
        // Account type distribution
        Map<String, Integer> accountDist = getAccountTypeDistribution();
        System.out.println("│ Account Type Distribution:                              │");
        for (Map.Entry<String, Integer> entry : accountDist.entrySet()) {
            System.out.printf("│   %-12s:      %-28d │%n", entry.getKey(), entry.getValue());
        }
        
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }
}
