/**
 * Custom exception for account not found scenarios
 * Thrown when trying to access non-existent accounts
 */
public class AccountNotFoundException extends Exception {
    private String accountNumber;
    
    public AccountNotFoundException(String message) {
        super(message);
    }
    
    public AccountNotFoundException(String message, String accountNumber) {
        super(message);
        this.accountNumber = accountNumber;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    @Override
    public String getMessage() {
        if (accountNumber != null && !accountNumber.isEmpty()) {
            return super.getMessage() + " (Account Number: " + accountNumber + ")";
        }
        return super.getMessage();
    }
}
