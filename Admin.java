package eventmanagementsystem.dataClasses;

import eventmanagementsystem.services.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {
    private List<Event> events; // List to store events

    // Constructor
    public Admin(String username, String password, String dateOfBirth) {
        super(username, password, dateOfBirth);  // Calling the parent constructor
        this.events = new ArrayList<>();
    }

    // Add an event
    public void addEvent(String eventName, String description, String date,
                         Category category, Organizer organizer, Room room, double ticketPrice, int availableSeats) {

        // Create a new Event
        Event newEvent = new Event(eventName, description, date, category, organizer, room, ticketPrice, availableSeats);

        // Add the event to the list
        events.add(newEvent);
        System.out.println("Event added successfully: " + eventName);
    }

    public void deleteEvent(String eventName) {
        Event eventToRemove = null;
        for (Event event : events) {
            if (event.getName().equalsIgnoreCase(eventName)) {
                eventToRemove = event;
                break;
            }
        }

        if (eventToRemove != null) {
            events.remove(eventToRemove);
            System.out.println("Event \"" + eventName + "\" deleted successfully.");
        } else {
            System.out.println("Event \"" + eventName + "\" not found.");
        }
    }

    public void displayAllEvents() {
        if (events.isEmpty()) {
            System.out.println("No events available.");
        } else {
            System.out.println("===== All Events =====");
            for (Event event : events) {
                event.displayEventDetails();
            }
        }
    }

    @Override
    public void displayProfile() {
        System.out.println("===== Admin Profile =====");
        System.out.println("Username: " + getUsername());
        System.out.println("Date of Birth: " + getDateOfBirth());
        System.out.println("Total Events: " + events.size());
    }

}