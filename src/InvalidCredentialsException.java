/**
 * Custom exception for authentication failures
 * Thrown when login credentials are invalid
 */
public class InvalidCredentialsException extends Exception {
    private String accountNumber;
    private int attemptCount;
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
    
    public InvalidCredentialsException(String message, String accountNumber) {
        super(message);
        this.accountNumber = accountNumber;
    }
    
    public InvalidCredentialsException(String message, String accountNumber, int attemptCount) {
        super(message);
        this.accountNumber = accountNumber;
        this.attemptCount = attemptCount;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public int getAttemptCount() {
        return attemptCount;
    }
    
    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder(super.getMessage());
        
        if (accountNumber != null && !accountNumber.isEmpty()) {
            message.append(" (Account: ").append(accountNumber).append(")");
        }
        
        if (attemptCount > 0) {
            message.append(" [Attempt: ").append(attemptCount).append("]");
        }
        
        return message.toString();
    }
}
