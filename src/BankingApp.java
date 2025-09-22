import java.util.Scanner;
import java.util.List;

/**
 * Main Banking Application with Console-based User Interface
 * Demonstrates complete banking system with menu-driven interface
 */
public class BankingApp {
    private static BankingService bankingService;
    private static AuthenticationService authService;
    private static Scanner scanner;
    private static String currentLoggedInAccount = null;
    
    public static void main(String[] args) {
        // Initialize services
        bankingService = new BankingService();
        authService = new AuthenticationService(bankingService);
        scanner = new Scanner(System.in);
        
        // Display welcome message
        displayWelcomeMessage();
        
        // Main application loop
        boolean running = true;
        while (running) {
            try {
                if (currentLoggedInAccount == null) {
                    // Show main menu for non-authenticated users
                    displayMainMenu();
                    int choice = getIntInput("Enter your choice: ");
                    running = handleMainMenuChoice(choice);
                } else {
                    // Show banking operations menu for authenticated users
                    displayBankingMenu();
                    int choice = getIntInput("Enter your choice: ");
                    handleBankingMenuChoice(choice);
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
        System.out.println("Thank you for using our Banking System!");
    }
    
    private static void displayWelcomeMessage() {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                                                         │");
        System.out.println("│            🏦 WELCOME TO SECURE BANKING 🏦             │");
        System.out.println("│                                                         │");
        System.out.println("│         Your Trusted Financial Partner                  │");
        System.out.println("│                                                         │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        System.out.println();
    }
    
    private static void displayMainMenu() {
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                      MAIN MENU                         │");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        System.out.println("│ 1. Create New Account                                   │");
        System.out.println("│ 2. Login to Account                                     │");
        System.out.println("│ 3. View All Accounts (Demo)                            │");
        System.out.println("│ 4. Banking Statistics                                   │");
        System.out.println("│ 5. Security Status                                      │");
        System.out.println("│ 6. Exit                                                 │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
    }
    
    private static void displayBankingMenu() {
        try {
            Account account = bankingService.getAccount(currentLoggedInAccount);
            System.out.println("┌─────────────────────────────────────────────────────────┐");
            System.out.println("│                   BANKING OPERATIONS                    │");
            System.out.println("├─────────────────────────────────────────────────────────┤");
            System.out.printf("│ Welcome, %-43s │%n", account.getCustomer().getName());
            System.out.printf("│ Account: %-43s │%n", currentLoggedInAccount);
            System.out.printf("│ Balance: ₹%-42.2f │%n", account.getBalance());
            System.out.println("├─────────────────────────────────────────────────────────┤");
            System.out.println("│ 1. View Account Details                                 │");
            System.out.println("│ 2. Deposit Money                                        │");
            System.out.println("│ 3. Withdraw Money                                       │");
            System.out.println("│ 4. Transfer Money                                       │");
            System.out.println("│ 5. View Transaction History                             │");
            System.out.println("│ 6. Change Password                                      │");
            System.out.println("│ 7. Logout                                               │");
            System.out.println("└─────────────────────────────────────────────────────────┘");
        } catch (AccountNotFoundException e) {
            System.out.println("❌ Error loading account information: " + e.getMessage());
            currentLoggedInAccount = null;
        }
    }
    
    private static boolean handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                createNewAccount();
                break;
            case 2:
                loginToAccount();
                break;
            case 3:
                viewAllAccounts();
                break;
            case 4:
                viewBankingStatistics();
                break;
            case 5:
                viewSecurityStatus();
                break;
            case 6:
                return false; // Exit application
            default:
                System.out.println("❌ Invalid choice! Please try again.");
        }
        
        if (currentLoggedInAccount == null) {
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
        return true;
    }
    
    private static void handleBankingMenuChoice(int choice) {
        switch (choice) {
            case 1:
                viewAccountDetails();
                break;
            case 2:
                depositMoney();
                break;
            case 3:
                withdrawMoney();
                break;
            case 4:
                transferMoney();
                break;
            case 5:
                viewTransactionHistory();
                break;
            case 6:
                changePassword();
                break;
            case 7:
                logout();
                break;
            default:
                System.out.println("❌ Invalid choice! Please try again.");
        }
        
        if (currentLoggedInAccount != null) {
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    // Account Creation
    private static void createNewAccount() {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                  CREATE NEW ACCOUNT                    │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
        try {
            String name = getStringInput("Enter your full name: ");
            String email = getStringInput("Enter your email: ");
            String phone = getStringInput("Enter your phone number: ");
            String address = getStringInput("Enter your address: ");
            
            System.out.println("\nAccount Types:");
            System.out.println("1. SAVINGS (Standard account)");
            System.out.println("2. CURRENT (Business account)");
            int typeChoice = getIntInput("Choose account type (1-2): ");
            
            String accountType = (typeChoice == 2) ? "CURRENT" : "SAVINGS";
            
            double initialDeposit = getDoubleInput("Enter initial deposit amount: ₹");
            String password = getStringInput("Create a password (min 6 characters): ");
            
            // Validate password
            if (password.length() < 6) {
                System.out.println("❌ Password must be at least 6 characters long!");
                return;
            }
            
            String accountNumber = bankingService.createAccount(name, email, phone, address, 
                                                              accountType, initialDeposit, password);
            
            System.out.println("✅ Account created successfully!");
            System.out.println("📋 Your Account Number: " + accountNumber);
            System.out.println("💡 Please save this account number for future login.");
            
        } catch (InvalidAmountException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    // Login
    private static void loginToAccount() {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                    LOGIN TO ACCOUNT                    │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
        try {
            String accountNumber = getStringInput("Enter account number: ");
            String password = getStringInput("Enter password: ");
            
            if (authService.login(accountNumber, password)) {
                currentLoggedInAccount = accountNumber;
                System.out.println("✅ Login successful! Welcome back!");
            }
            
        } catch (InvalidCredentialsException | AccountNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    // View Account Details
    private static void viewAccountDetails() {
        try {
            Account account = bankingService.getAccount(currentLoggedInAccount);
            account.displayAccountInfo();
        } catch (AccountNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    // Deposit Money
    private static void depositMoney() {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                     DEPOSIT MONEY                      │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
        try {
            double amount = getDoubleInput("Enter deposit amount: ₹");
            double balanceBefore = bankingService.getBalance(currentLoggedInAccount);
            
            bankingService.deposit(currentLoggedInAccount, amount);
            double balanceAfter = bankingService.getBalance(currentLoggedInAccount);
            
            System.out.println("✅ Deposit successful!");
            System.out.printf("💰 Previous Balance: ₹%.2f%n", balanceBefore);
            System.out.printf("💰 New Balance: ₹%.2f%n", balanceAfter);
            
        } catch (AccountNotFoundException | InvalidAmountException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    // Withdraw Money
    private static void withdrawMoney() {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                    WITHDRAW MONEY                      │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
        try {
            double currentBalance = bankingService.getBalance(currentLoggedInAccount);
            System.out.printf("💰 Available Balance: ₹%.2f%n", currentBalance);
            
            double amount = getDoubleInput("Enter withdrawal amount: ₹");
            
            bankingService.withdraw(currentLoggedInAccount, amount);
            double newBalance = bankingService.getBalance(currentLoggedInAccount);
            
            System.out.println("✅ Withdrawal successful!");
            System.out.printf("💰 New Balance: ₹%.2f%n", newBalance);
            
        } catch (AccountNotFoundException | InsufficientBalanceException | InvalidAmountException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    // Transfer Money
    private static void transferMoney() {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                    TRANSFER MONEY                      │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
        try {
            double currentBalance = bankingService.getBalance(currentLoggedInAccount);
            System.out.printf("💰 Available Balance: ₹%.2f%n", currentBalance);
            
            String toAccount = getStringInput("Enter recipient account number: ");
            double amount = getDoubleInput("Enter transfer amount: ₹");
            
            // Verify recipient account exists
            Account recipientAccount = bankingService.getAccount(toAccount);
            System.out.println("📋 Recipient: " + recipientAccount.getCustomer().getName());
            
            String confirm = getStringInput("Confirm transfer? (yes/no): ");
            if (!confirm.toLowerCase().startsWith("y")) {
                System.out.println("❌ Transfer cancelled.");
                return;
            }
            
            bankingService.transfer(currentLoggedInAccount, toAccount, amount);
            double newBalance = bankingService.getBalance(currentLoggedInAccount);
            
            System.out.println("✅ Transfer successful!");
            System.out.printf("💰 New Balance: ₹%.2f%n", newBalance);
            
        } catch (AccountNotFoundException | InsufficientBalanceException | InvalidAmountException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    // View Transaction History
    private static void viewTransactionHistory() {
        try {
            Account account = bankingService.getAccount(currentLoggedInAccount);
            System.out.println("\n📊 TRANSACTION HISTORY for " + currentLoggedInAccount);
            account.displayTransactionHistory();
            
        } catch (AccountNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    // Change Password
    private static void changePassword() {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│                    CHANGE PASSWORD                     │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
        try {
            String currentPassword = getStringInput("Enter current password: ");
            String newPassword = getStringInput("Enter new password: ");
            String confirmPassword = getStringInput("Confirm new password: ");
            
            if (!newPassword.equals(confirmPassword)) {
                System.out.println("❌ New passwords do not match!");
                return;
            }
            
            if (authService.changePassword(currentLoggedInAccount, currentPassword, newPassword)) {
                System.out.println("✅ Password changed successfully!");
            }
            
        } catch (AccountNotFoundException | InvalidCredentialsException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
    // Logout
    private static void logout() {
        System.out.println("👋 Logging out... Thank you for banking with us!");
        currentLoggedInAccount = null;
    }
    
    // View All Accounts (Demo feature)
    private static void viewAllAccounts() {
        System.out.println("\n📋 ALL ACCOUNTS (Demo View)");
        bankingService.displayAllAccounts();
    }
    
    // View Banking Statistics
    private static void viewBankingStatistics() {
        System.out.println("\n📊 BANKING STATISTICS");
        bankingService.displayBankingStatistics();
    }
    
    // View Security Status
    private static void viewSecurityStatus() {
        System.out.println("\n🔒 SECURITY STATUS");
        authService.displaySecurityStatus();
    }
    
    // Input Helper Methods
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value < 0) {
                    System.out.println("❌ Amount cannot be negative!");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid amount!");
            }
        }
    }
}
