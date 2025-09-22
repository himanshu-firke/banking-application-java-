import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileHandler class for data persistence
 * Demonstrates file I/O operations for saving and loading banking data
 */
public class FileHandler {
    private static final String DATA_DIR = "data/";
    private static final String CUSTOMERS_FILE = DATA_DIR + "customers.txt";
    private static final String ACCOUNTS_FILE = DATA_DIR + "accounts.txt";
    private static final String TRANSACTIONS_FILE = DATA_DIR + "transactions.txt";
    
    // Initialize data directory
    static {
        createDataDirectory();
    }
    
    private static void createDataDirectory() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
            System.out.println("📁 Created data directory: " + DATA_DIR);
        }
    }
    
    // Save customers to file
    public static boolean saveCustomers(Map<String, Customer> customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer customer : customers.values()) {
                writer.write(customer.toFileString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error saving customers: " + e.getMessage());
            return false;
        }
    }
    
    // Load customers from file
    public static Map<String, Customer> loadCustomers() {
        Map<String, Customer> customers = new HashMap<>();
        File file = new File(CUSTOMERS_FILE);
        
        if (!file.exists()) {
            return customers;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = Customer.fromFileString(line);
                if (customer != null) {
                    customers.put(customer.getCustomerId(), customer);
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error loading customers: " + e.getMessage());
        }
        
        return customers;
    }
    
    // Save accounts to file
    public static boolean saveAccounts(Map<String, Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account account : accounts.values()) {
                writer.write(account.toFileString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error saving accounts: " + e.getMessage());
            return false;
        }
    }
    
    // Load accounts from file
    public static Map<String, Account> loadAccounts(Map<String, Customer> customers) {
        Map<String, Account> accounts = new HashMap<>();
        File file = new File(ACCOUNTS_FILE);
        
        if (!file.exists()) {
            return accounts;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String customerId = parts[2];
                    Customer customer = customers.get(customerId);
                    if (customer != null) {
                        Account account = Account.fromFileString(line, customer);
                        if (account != null) {
                            accounts.put(account.getAccountNumber(), account);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error loading accounts: " + e.getMessage());
        }
        
        return accounts;
    }
    
    // Save transactions to file
    public static boolean saveTransactions(List<Transaction> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.toFileString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error saving transactions: " + e.getMessage());
            return false;
        }
    }
    
    // Load transactions from file
    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        
        if (!file.exists()) {
            return transactions;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Transaction transaction = Transaction.fromFileString(line);
                if (transaction != null) {
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error loading transactions: " + e.getMessage());
        }
        
        return transactions;
    }
    
    // Backup all data
    public static boolean backupData(Map<String, Customer> customers, 
                                   Map<String, Account> accounts, 
                                   List<Transaction> transactions) {
        
        String timestamp = java.time.LocalDateTime.now().toString().replace(":", "-");
        String backupDir = DATA_DIR + "backup_" + timestamp + "/";
        
        File backupDirFile = new File(backupDir);
        if (!backupDirFile.exists()) {
            backupDirFile.mkdirs();
        }
        
        try {
            // Backup customers
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(backupDir + "customers.txt"))) {
                for (Customer customer : customers.values()) {
                    writer.write(customer.toFileString());
                    writer.newLine();
                }
            }
            
            // Backup accounts
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(backupDir + "accounts.txt"))) {
                for (Account account : accounts.values()) {
                    writer.write(account.toFileString());
                    writer.newLine();
                }
            }
            
            // Backup transactions
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(backupDir + "transactions.txt"))) {
                for (Transaction transaction : transactions) {
                    writer.write(transaction.toFileString());
                    writer.newLine();
                }
            }
            
            System.out.println("✅ Data backup created: " + backupDir);
            return true;
            
        } catch (IOException e) {
            System.err.println("❌ Error creating backup: " + e.getMessage());
            return false;
        }
    }
    
    // Export account statement to file
    public static boolean exportAccountStatement(Account account, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_DIR + filename))) {
            writer.write("ACCOUNT STATEMENT");
            writer.newLine();
            writer.write("================");
            writer.newLine();
            writer.newLine();
            
            // Account information
            writer.write("Account Number: " + account.getAccountNumber());
            writer.newLine();
            writer.write("Account Holder: " + account.getCustomer().getName());
            writer.newLine();
            writer.write("Account Type: " + account.getAccountType());
            writer.newLine();
            writer.write("Current Balance: ₹" + String.format("%.2f", account.getBalance()));
            writer.newLine();
            writer.write("Date Created: " + account.getDateCreated());
            writer.newLine();
            writer.newLine();
            
            // Transaction history
            writer.write("TRANSACTION HISTORY");
            writer.newLine();
            writer.write("===================");
            writer.newLine();
            
            List<Transaction> transactions = account.getTransactionHistory();
            if (transactions.isEmpty()) {
                writer.write("No transactions found.");
                writer.newLine();
            } else {
                writer.write(String.format("%-15s %-12s %-12s %-12s %-20s", 
                           "Transaction ID", "Type", "Amount", "Balance", "Timestamp"));
                writer.newLine();
                writer.write("-".repeat(75));
                writer.newLine();
                
                for (Transaction transaction : transactions) {
                    writer.write(String.format("%-15s %-12s %12.2f %12.2f %-20s",
                               transaction.getTransactionId(),
                               transaction.getTransactionType(),
                               transaction.getAmount(),
                               transaction.getBalanceAfter(),
                               transaction.getFormattedTimestamp()));
                    writer.newLine();
                }
            }
            
            writer.newLine();
            writer.write("End of Statement");
            writer.newLine();
            writer.write("Generated on: " + java.time.LocalDateTime.now());
            
            System.out.println("✅ Account statement exported: " + DATA_DIR + filename);
            return true;
            
        } catch (IOException e) {
            System.err.println("❌ Error exporting statement: " + e.getMessage());
            return false;
        }
    }
    
    // Check if data files exist
    public static boolean dataFilesExist() {
        return new File(CUSTOMERS_FILE).exists() || 
               new File(ACCOUNTS_FILE).exists() || 
               new File(TRANSACTIONS_FILE).exists();
    }
    
    // Get file sizes for statistics
    public static Map<String, Long> getDataFileInfo() {
        Map<String, Long> fileInfo = new HashMap<>();
        
        File customersFile = new File(CUSTOMERS_FILE);
        File accountsFile = new File(ACCOUNTS_FILE);
        File transactionsFile = new File(TRANSACTIONS_FILE);
        
        fileInfo.put("customers", customersFile.exists() ? customersFile.length() : 0);
        fileInfo.put("accounts", accountsFile.exists() ? accountsFile.length() : 0);
        fileInfo.put("transactions", transactionsFile.exists() ? transactionsFile.length() : 0);
        
        return fileInfo;
    }
    
    // Clean up old backup files (keep only last 5 backups)
    public static void cleanupOldBackups() {
        File dataDir = new File(DATA_DIR);
        File[] backupDirs = dataDir.listFiles((dir, name) -> name.startsWith("backup_"));
        
        if (backupDirs != null && backupDirs.length > 5) {
            // Sort by last modified time
            java.util.Arrays.sort(backupDirs, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));
            
            // Delete oldest backups, keep only last 5
            for (int i = 0; i < backupDirs.length - 5; i++) {
                deleteDirectory(backupDirs[i]);
            }
        }
    }
    
    // Helper method to delete directory recursively
    private static void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }
}
