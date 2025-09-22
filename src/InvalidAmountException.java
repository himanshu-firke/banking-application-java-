/**
 * Custom exception for invalid amount scenarios
 * Thrown when negative or zero amounts are provided for transactions
 */
public class InvalidAmountException extends Exception {
    private double invalidAmount;
    
    public InvalidAmountException(String message) {
        super(message);
    }
    
    public InvalidAmountException(String message, double invalidAmount) {
        super(message);
        this.invalidAmount = invalidAmount;
    }
    
    public double getInvalidAmount() {
        return invalidAmount;
    }
    
    @Override
    public String getMessage() {
        if (invalidAmount != 0) {
            return super.getMessage() + String.format(" (Invalid amount: ₹%.2f)", invalidAmount);
        }
        return super.getMessage();
    }
}
