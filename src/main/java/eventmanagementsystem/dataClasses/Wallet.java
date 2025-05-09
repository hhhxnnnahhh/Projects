package eventmanagementsystem.dataClasses;

import java.util.Scanner;

public class Wallet {
    private double balance;


    public Wallet(double initialBalance) {
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            this.balance = 0;
        }
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("Error! Invalid.");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Deposit amount must be greater than zero.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Invalid.");
        }
    }

    public boolean buyTicketATTENDEE(double ticketPrice) {
        if (balance >= ticketPrice) {
            balance -= ticketPrice;
            System.out.println("Ticket purchased for " + ticketPrice + ". Remaining balance: " + balance);
            return true;
        } else {
            System.out.println("Insufficient funds to buy the ticket.");
            return false;
        }
    }


    public void buyTicketORGANIZER(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Funds added: " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Amount must be greater than zero.");
        }
    }

    public void updateBalance(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

}
