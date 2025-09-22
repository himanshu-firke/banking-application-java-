/**
 * Custom exception for inactive account scenarios
 * Thrown when trying to perform operations on inactive accounts
 */
public class AccountInactiveException extends RuntimeException {
    private String accountNumber;
    
    public AccountInactiveException(String message) {
        super(message);
    }
    
    public AccountInactiveException(String message, String accountNumber) {
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
