import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Transaction class to represent banking transactions
 * Demonstrates encapsulation and data management
 */
public class Transaction {
    private String transactionId;
    private String accountNumber;
    private String transactionType; // DEPOSIT, WITHDRAWAL, TRANSFER
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;
    private String description;
    
    // Constructor
    public Transaction(String transactionId, String accountNumber, String transactionType, 
                      double amount, double balanceAfter, String description) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }
    
    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public double getBalanceAfter() {
        return balanceAfter;
    }
    
    public void setBalanceAfter(double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    // Method to format timestamp for display
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return timestamp.format(formatter);
    }
    
    // Method to display transaction details
    public void displayTransaction() {
        System.out.printf("│ %-12s │ %-10s │ %10.2f │ %10.2f │ %-19s │%n",
                transactionId, transactionType, amount, balanceAfter, getFormattedTimestamp());
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", balanceAfter=" + balanceAfter +
                ", timestamp=" + timestamp +
                ", description='" + description + '\'' +
                '}';
    }
    
    // Method to convert transaction to file string format
    public String toFileString() {
        return transactionId + "," + accountNumber + "," + transactionType + "," + 
               amount + "," + balanceAfter + "," + timestamp + "," + description;
    }
    
    // Static method to create transaction from file string
    public static Transaction fromFileString(String fileString) {
        String[] parts = fileString.split(",");
        if (parts.length == 7) {
            Transaction transaction = new Transaction(parts[0], parts[1], parts[2], 
                                                    Double.parseDouble(parts[3]), 
                                                    Double.parseDouble(parts[4]), parts[6]);
            transaction.setTimestamp(LocalDateTime.parse(parts[5]));
            return transaction;
        }
        return null;
    }
}
