import java.util.HashMap;
import java.util.Map;

/**
 * AuthenticationService class for handling user authentication
 * Demonstrates security concepts and session management
 */
public class AuthenticationService {
    private BankingService bankingService;
    private Map<String, Integer> loginAttempts; // Track failed login attempts
    private Map<String, Long> lockedAccounts; // Track locked accounts with unlock time
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final long LOCKOUT_DURATION = 300000; // 5 minutes in milliseconds
    
    public AuthenticationService(BankingService bankingService) {
        this.bankingService = bankingService;
        this.loginAttempts = new HashMap<>();
        this.lockedAccounts = new HashMap<>();
    }
    
    /**
     * Authenticate user with account number and password
     */
    public boolean login(String accountNumber, String password) throws InvalidCredentialsException, 
                                                                      AccountNotFoundException {
        // Check if account is locked
        if (isAccountLocked(accountNumber)) {
            long remainingTime = getRemainingLockTime(accountNumber);
            if (remainingTime > 0) {
                throw new InvalidCredentialsException(
                    "Account is locked due to multiple failed attempts. Try again in " + 
                    (remainingTime / 1000) + " seconds", accountNumber);
            } else {
                // Unlock account if lockout period has expired
                unlockAccount(accountNumber);
            }
        }
        
        try {
            // Attempt authentication
            boolean isAuthenticated = bankingService.authenticateAccount(accountNumber, password);
            
            if (isAuthenticated) {
                // Reset login attempts on successful login
                loginAttempts.remove(accountNumber);
                return true;
            }
            
        } catch (InvalidCredentialsException e) {
            // Increment failed login attempts
            int attempts = loginAttempts.getOrDefault(accountNumber, 0) + 1;
            loginAttempts.put(accountNumber, attempts);
            
            if (attempts >= MAX_LOGIN_ATTEMPTS) {
                // Lock account
                lockAccount(accountNumber);
                throw new InvalidCredentialsException(
                    "Account locked due to " + MAX_LOGIN_ATTEMPTS + " failed login attempts", 
                    accountNumber, attempts);
            }
            
            throw new InvalidCredentialsException(
                "Invalid credentials. Attempt " + attempts + " of " + MAX_LOGIN_ATTEMPTS, 
                accountNumber, attempts);
        }
        
        return false;
    }
    
    /**
     * Check if account is currently locked
     */
    public boolean isAccountLocked(String accountNumber) {
        if (!lockedAccounts.containsKey(accountNumber)) {
            return false;
        }
        
        long lockTime = lockedAccounts.get(accountNumber);
        long currentTime = System.currentTimeMillis();
        
        // Check if lockout period has expired
        if (currentTime - lockTime >= LOCKOUT_DURATION) {
            lockedAccounts.remove(accountNumber);
            return false;
        }
        
        return true;
    }
    
    /**
     * Get remaining lock time in milliseconds
     */
    public long getRemainingLockTime(String accountNumber) {
        if (!lockedAccounts.containsKey(accountNumber)) {
            return 0;
        }
        
        long lockTime = lockedAccounts.get(accountNumber);
        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - lockTime;
        
        return Math.max(0, LOCKOUT_DURATION - elapsed);
    }
    
    /**
     * Lock account due to failed login attempts
     */
    private void lockAccount(String accountNumber) {
        lockedAccounts.put(accountNumber, System.currentTimeMillis());
        System.out.println("⚠️  Account " + accountNumber + " has been locked for " + 
                          (LOCKOUT_DURATION / 1000) + " seconds due to multiple failed login attempts.");
    }
    
    /**
     * Unlock account (called when lockout period expires)
     */
    private void unlockAccount(String accountNumber) {
        lockedAccounts.remove(accountNumber);
        loginAttempts.remove(accountNumber);
        System.out.println("✅ Account " + accountNumber + " has been unlocked.");
    }
    
    /**
     * Admin method to manually unlock account
     */
    public void adminUnlockAccount(String accountNumber) {
        unlockAccount(accountNumber);
    }
    
    /**
     * Get current failed login attempts for an account
     */
    public int getLoginAttempts(String accountNumber) {
        return loginAttempts.getOrDefault(accountNumber, 0);
    }
    
    /**
     * Change password with current password verification
     */
    public boolean changePassword(String accountNumber, String currentPassword, String newPassword) 
                                 throws AccountNotFoundException, InvalidCredentialsException {
        
        // Verify current password
        if (!bankingService.authenticateAccount(accountNumber, currentPassword)) {
            int attempts = loginAttempts.getOrDefault(accountNumber, 0) + 1;
            loginAttempts.put(accountNumber, attempts);
            
            throw new InvalidCredentialsException("Current password is incorrect", accountNumber, attempts);
        }
        
        // Validate new password
        if (!isValidPassword(newPassword)) {
            throw new InvalidCredentialsException(
                "New password does not meet security requirements. " +
                "Password must be at least 6 characters long and contain letters and numbers.");
        }
        
        // Change password
        return bankingService.changePassword(accountNumber, currentPassword, newPassword);
    }
    
    /**
     * Validate password strength
     */
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        
        boolean hasLetter = false;
        boolean hasDigit = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        
        return hasLetter && hasDigit;
    }
    
    /**
     * Display security status for all accounts (admin function)
     */
    public void displaySecurityStatus() {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                   SECURITY STATUS                      │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        
        if (loginAttempts.isEmpty() && lockedAccounts.isEmpty()) {
            System.out.println("│ No security issues detected.                           │");
        } else {
            // Display accounts with failed login attempts
            if (!loginAttempts.isEmpty()) {
                System.out.println("│ Accounts with Failed Login Attempts:                   │");
                for (Map.Entry<String, Integer> entry : loginAttempts.entrySet()) {
                    System.out.printf("│   %-15s: %d attempts                        │%n", 
                                    entry.getKey(), entry.getValue());
                }
            }
            
            // Display locked accounts
            if (!lockedAccounts.isEmpty()) {
                System.out.println("│ Locked Accounts:                                        │");
                for (Map.Entry<String, Long> entry : lockedAccounts.entrySet()) {
                    long remainingTime = getRemainingLockTime(entry.getKey());
                    if (remainingTime > 0) {
                        System.out.printf("│   %-15s: %d seconds remaining           │%n", 
                                        entry.getKey(), remainingTime / 1000);
                    }
                }
            }
        }
        
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }
    
    /**
     * Reset security data (admin function)
     */
    public void resetSecurityData() {
        loginAttempts.clear();
        lockedAccounts.clear();
        System.out.println("✅ Security data has been reset.");
    }
}
