package eventmanagementsystem.dataClasses;

import java.util.Scanner;

public class Wallet {
    private double balance;  // The current balance of the wallet

    // Constructor
    public Wallet(double initialBalance) {
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            this.balance = 0;  // Set to 0 if the balance is negative
        }
    }

    // Getter and Setter
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("Error! Invalid."); // negative value
        }
    }

    // add funds
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Deposit amount must be greater than zero.");
        }
    }

    // withdraw funds
    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Invalid.");
        }
    }

    // attendee buys a ticket and his balance decreases
    public boolean buyTicketATTENDEE(double ticketPrice) {
        if (balance >= ticketPrice) {
            balance -= ticketPrice;
            System.out.println("Ticket purchased for " + ticketPrice + ". Remaining balance: " + balance);
            return true;  // Transaction successful
        } else {
            System.out.println("Insufficient funds to buy the ticket.");
            return false;  // Transaction failed
        }
    }

    // attendee buys a ticket and the organizer's balance increases
    public void buyTicketORGANIZER(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Funds added: " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Amount must be greater than zero.");
        }
    }

    public static void updateBalance(Scanner scanner) {
        // Prompt the user for their username to find the user
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        // Search for the user in the Database (both Attendees and Organizers)
        User user = null;

        // Search through both attendees and organizers in the database
        for (Attendee a : Database.attendees) {
            if (a.getUsername().equals(username)) {
                user = a;  // Found an attendee
                break;
            }
        }

        // If not found among attendees, search among organizers
        if (user == null) {
            for (Organizer o : Database.organizers) {
                if (o.getUsername().equals(username)) {
                    user = o;  // Found an organizer
                    break;
                }
            }
        }

        // If the user is found, proceed to update the balance
        if (user != null) {
            if (user instanceof Attendee) {
                Attendee attendee = (Attendee) user;
                // Show the current wallet balance before adding funds
                System.out.println("Your Current Wallet Balance: $" + attendee.getWallet().getBalance());

                // Prompt the user for the amount to add to the wallet
                System.out.print("Enter amount to add: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();  // Consume the newline character left after nextDouble()

                // Check if the amount is valid (greater than 0)
                if (amount > 0) {
                    // Add the funds using the deposit method
                    attendee.getWallet().deposit(amount);

                    // Show the updated balance after depositing the funds
                    System.out.println("Balance Updated! New Balance: $" +
                            String.format("%.2f", attendee.getWallet().getBalance()));
                } else {
                    System.out.println("Insufficient amount. Please enter a positive amount.");
                }
            } else if (user instanceof Organizer) {
                Organizer organizer = (Organizer) user;
                // Show the current wallet balance before adding funds
                System.out.println("Your Current Wallet Balance: $" + organizer.getWallet().getBalance());

                // Prompt the user for the amount to add to the wallet
                System.out.print("Enter amount to add: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();  // Consume the newline character left after nextDouble()

                // Check if the amount is valid (greater than 0)
                if (amount > 0) {
                    // Add the funds using the deposit method
                    organizer.getWallet().deposit(amount);

                    // Show the updated balance after depositing the funds
                    System.out.println("Balance Updated! New Balance: $" +
                            String.format("%.2f", organizer.getWallet().getBalance()));
                } else {
                    System.out.println("Insufficient amount. Please enter a positive amount.");
                }
            }
        } else {
            // If user is not found, display an error message
            System.out.println("User not found with username: " + username);
        }
    }

}
