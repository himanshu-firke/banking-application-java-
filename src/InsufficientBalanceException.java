/**
 * Custom exception for insufficient balance scenarios
 * Demonstrates exception handling in banking operations
 */
public class InsufficientBalanceException extends Exception {
    private double availableBalance;
    private double requestedAmount;
    
    public InsufficientBalanceException(String message) {
        super(message);
    }
    
    public InsufficientBalanceException(String message, double availableBalance, double requestedAmount) {
        super(message);
        this.availableBalance = availableBalance;
        this.requestedAmount = requestedAmount;
    }
    
    public double getAvailableBalance() {
        return availableBalance;
    }
    
    public double getRequestedAmount() {
        return requestedAmount;
    }
    
    @Override
    public String getMessage() {
        if (availableBalance > 0 && requestedAmount > 0) {
            return super.getMessage() + 
                   String.format(" (Available: ₹%.2f, Requested: ₹%.2f)", availableBalance, requestedAmount);
        }
        return super.getMessage();
    }
}
