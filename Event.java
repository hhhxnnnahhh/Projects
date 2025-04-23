package eventmanagementsystem.dataClasses;
import eventmanagementsystem.dataClasses.Organizer;
import eventmanagementsystem.services.CRUD;

import java.util.ArrayList;
import java.util.List;

public class Event implements CRUD {
    private String name;
    private String description;
    private String date;
    private Category category;
    private Organizer eventOrganizer;
    private Room room;
    private double ticketPrice;
    private int availableSeats;
    private List<Attendee> attendees;


    // Constructor
    public Event( String name, String description, String date, Category category,
                  Organizer eventOrganizer, Room room, double ticketPrice, int availableSeats) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.category = category;
        this.eventOrganizer = eventOrganizer;
        this.room = room;
        this.ticketPrice = ticketPrice;
        this.availableSeats = availableSeats;
        this.attendees = new ArrayList<>();
    }

    private static List<Event> eventList = Database.events;

    public static List<Attendee> getAttendees() {
        return Database.attendees;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getOrganizerName() {
        return eventOrganizer.getName();  // Get the name from the organizer object
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void displayEventDetails() {
        System.out.println("----- Event Details -----");
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Date: " + date);
        System.out.println("Category: " + (category != null ? category.getName() : "N/A"));
        System.out.println("Organizer Name: " + eventOrganizer);
        System.out.println("Room: " + (room != null ? room.getRoomName() : "N/A"));
        System.out.println("Ticket Price: $" + ticketPrice);
        System.out.println("Available Seats: " + availableSeats);
    }
    public void addAttendee(Attendee attendee) {
        this.attendees.add(attendee);
    }
    private Organizer organizer;

    public Event(String name, String date, Organizer organizer /* other params */) {
        this.name = name;
        this.date = date;
        this.organizer = organizer;
        // other assignments
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public String toString() {
        return "\n=== Event Details ===" +
                "\nName: " + name +
                "\nDescription: " + description +
                "\nDate: " + date +
                "\nCategory: " + (category != null ? category.getName() : "N/A") +
                "\nRoom: " + (room != null ? room.getRoomId() : "N/A") +
                "\nTicket Price: $" + String.format("%.2f", ticketPrice) +
                "\nAvailable Seats: " + availableSeats +
                "\n=======================";
    }
    public void reduceAvailableSeats() {
        if (availableSeats > 0) {
            availableSeats--;
        } else {
            System.out.println("No available seats left to reduce.");
        }
    }

    @Override
    public void create(Object entity) {
        if (entity instanceof Event) {
            eventList.add((Event) entity);
            System.out.println("Event created: " + ((Event) entity).getName());
        } else {
            System.out.println("Invalid entity for creation.");
        }
    }

    @Override
    public Object read(int index) {
        if (index >= 0 && index < eventList.size()) {
            return eventList.get(index);
        }
        System.out.println("Event not found.");
        return null;
    }

    @Override
    public void update(int index, Object updatedEntity) {
        if (updatedEntity instanceof Event && index >= 0 && index < eventList.size()) {
            eventList.set(index, (Event) updatedEntity);
            System.out.println("Event updated.");
        } else {
            System.out.println("Failed to update event.");
        }
    }

    @Override
    public void delete(int index) {
        if (index >= 0 && index < eventList.size()) {
            Event removed = eventList.remove(index);
            System.out.println("Deleted event: " + removed.getName());
        } else {
            System.out.println("Invalid event index.");
        }
    }

}


