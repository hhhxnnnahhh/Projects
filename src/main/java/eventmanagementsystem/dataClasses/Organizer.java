package eventmanagementsystem.dataClasses;

import eventmanagementsystem.services.Display;

import java.util.List;
import java.util.ArrayList;

import static eventmanagementsystem.dataClasses.Database.*;

public class Organizer extends User {
    private List<Event> organizedEvents;
    private String name;
    private String userID;
    private double balance;
    private Wallet wallet;

    public Organizer(String username, String password, String dateOfBirth, double balance, List<Event> organizedEvents) {
        super(username, password, dateOfBirth);
        this.wallet = new Wallet(balance);
        this.organizedEvents = organizedEvents != null ? organizedEvents : new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return this.name;
    }

    public String getUserID() {
        return userID;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getBalance() {
        return balance;
    }



    public void setBalance(double balance) {
        this.balance = balance;
    }
    public List<Event> getOrganizedEvents() {
        return organizedEvents;
    }

    public void setOrganizedEvents(List<Event> organizedEvents) {
        this.organizedEvents = organizedEvents;
    }

    public void addOrganizedEvent(Event event) {
        if (event != null) {
            organizedEvents.add(event);
            System.out.println("Event added: " + event.getName());
        } else {
            System.out.println("Error: The event is null and cannot be added.");
        }
    }

    public void createEvent(String eventname, Room room, Category category, double ticketprice,
                            String description, String date, String organizerId, int availableSeats) {
        if (eventname == null || room == null || category == null) {
            System.out.println("Error: Event name, room, and category cannot be empty.");
            return;
        }

        if (ticketprice <= 0) {
            System.out.println("Error: Ticket price must be greater than 0.");
            return;
        }

        Organizer eventOrganizer = null;
        for (Organizer organizer : getOrganizers()) {
            if (organizer.getUsername().equals(organizerId)) {
                eventOrganizer = organizer;
                break;
            }
        }

        if (eventOrganizer == null) {
            System.out.println("Error: Organizer not found.");
            return;
        }

        Event newEvent = new Event(eventname, description, date, category, eventOrganizer, room, ticketprice, availableSeats);
        Database.events.add(newEvent);
        eventOrganizer.addOrganizedEvent(newEvent);
        room.setAvailable(false);

        System.out.println("Event '" + eventname + "' created successfully.");
    }

    public void viewOrganizedEvents() {
        System.out.println("Events organized by " + getUsername() + ":");
        if (organizedEvents.isEmpty()) {
            System.out.println("  No events yet.");
        } else {
            for (Event event : organizedEvents) {
                System.out.println("  - " + event);
            }
        }
    }

    public void viewAttendeesForMyEvents() {
        for (Event event : organizedEvents) {
            System.out.println("Event: " + event.getName());
            List<Attendee> attendees = Display.getAttendees();

            if (attendees == null || attendees.isEmpty()) {
                System.out.println("  No attendees yet.");
            } else {
                for (Attendee attendee : attendees) {
                    System.out.println("  - " + attendee.getUsername());
                }
            }
        }
    }

    @Override
    public String displayProfile() {
        System.out.println("Username: " + getUsername());
        System.out.println("Name: " + (name != null ? name : "Not set"));
        return null;
    }
    @Override
    public String toString() {
        return getUsername();}
    public Wallet getWallet() {
        return wallet;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organizer organizer = (Organizer) o;
        return this.getUsername().equals(organizer.getUsername());
    }

    @Override
    public int hashCode() {
        return this.getUsername().hashCode();
    }


}
