package eventmanagementsystem.dataClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import eventmanagementsystem.dataClasses.Event;
public class Attendee extends User {
    private double balance;
    private String address;
    private List<String> interests;
    private Gender gender;
    private Wallet wallet;

    private List<Event> registeredEvents = new ArrayList<>();

    public Attendee(String username, String password, String dateOfBirth, double balance, Gender gender, String address, List<String> interests) {
        super(username, password, dateOfBirth);
        this.balance = balance;
        this.gender = gender;
        this.address = address;
        this.interests = interests != null ? interests : new ArrayList<>();
        this.wallet = new Wallet(balance);
    }

    public List<Event> getRegisteredEvents() {
        return registeredEvents;
    }

    public void setRegisteredEvents(List<Event> registeredEvents) {
        this.registeredEvents = registeredEvents;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public void displayProfile() {
        System.out.println("=== Attendee Profile ===");
        System.out.println("Username: " + getUsername());
        System.out.println("Date of Birth: " + getDateOfBirth());
        System.out.println("Address: " + address);
        System.out.println("Balance: $" + balance);
        System.out.println("Interests: " + String.join(", ", interests));
        System.out.println("Gender: " + gender);
    }

    // Method to browse Events
    public void browseEvents(List<Event> allEvents, Scanner scanner) {
        System.out.println("\n=== Available Events ===");

        if (allEvents.isEmpty()) {
            System.out.println("No events available.");
        } else {
            for (int i = 0; i < allEvents.size(); i++) {
                Event event = allEvents.get(i);
                System.out.println((i + 1) + "-" + event.getName() + " | Date: " + event.getDate() + " | Price : $ " + event.getTicketPrice());
            }
        }
    }

    // Method to register for an event
    public void registerForEvent(Event event) {
        // Check if already registered
        if (registeredEvents.contains(event)) {
            System.out.println("You are already registered for this event.");
        } else {
            registeredEvents.add(event);
            System.out.println("Successfully registered for " + event.getName());
        }
    }

    public Wallet getWallet() {
        return wallet;
    }
}
