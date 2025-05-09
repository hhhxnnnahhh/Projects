package eventmanagementsystem.dataClasses;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public String displayProfile() {
        return "=== Attendee Profile ===\n" +
                "Username: " + getUsername() + "\n" +
                "Date of Birth: " + getDateOfBirth() + "\n" +
                "Address: " + address + "\n" +
                "Balance: $" + balance + "\n" +
                "Interests: " + String.join(", ", interests) + "\n" +
                "Gender: " + gender;
    }


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

    public void registerForEvent(Event event) {
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
