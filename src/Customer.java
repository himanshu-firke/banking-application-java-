/**
 * Customer class representing a bank customer
 * Demonstrates encapsulation with private fields and public methods
 */
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    
    // Constructor
    public Customer(String customerId, String name, String email, String phone, String address) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    
    // Getters and Setters (Encapsulation)
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
    
    // Method to display customer information
    public void displayCustomerInfo() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│           CUSTOMER INFORMATION          │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│ Customer ID: " + String.format("%-23s", customerId) + " │");
        System.out.println("│ Name:        " + String.format("%-23s", name) + " │");
        System.out.println("│ Email:       " + String.format("%-23s", email) + " │");
        System.out.println("│ Phone:       " + String.format("%-23s", phone) + " │");
        System.out.println("│ Address:     " + String.format("%-23s", address) + " │");
        System.out.println("└─────────────────────────────────────────┘");
    }
    
    // Method to convert customer data to file string format
    public String toFileString() {
        return customerId + "," + name + "," + email + "," + phone + "," + address;
    }
    
    // Static method to create customer from file string
    public static Customer fromFileString(String fileString) {
        String[] parts = fileString.split(",");
        if (parts.length == 5) {
            return new Customer(parts[0], parts[1], parts[2], parts[3], parts[4]);
        }
        return null;
    }
}
