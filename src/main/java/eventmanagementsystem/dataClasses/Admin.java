package eventmanagementsystem.dataClasses;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private List<Event> events; // List to store events
    private String workingHours;
    private List<Room> rooms;
    private List<Organizer> organizers;
    private List<Attendee> attendees;
    // Constructor
    public Admin(String username, String password, String dateOfBirth, String workingHours) {
        super(username, password, dateOfBirth);
        this.workingHours = workingHours;
        this.events = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.organizers = new ArrayList<>();
        this.attendees = new ArrayList<>();
    }

    // Add an event

    public Event addEvent(String eventName, String description, String date,
                          Category category, Organizer organizer, Room room,
                          double ticketPrice, int availableSeats) {
        Event newEvent = new Event(eventName, description, date, category,
                organizer, room, ticketPrice, availableSeats);
        events.add(newEvent);

        System.out.println("Event added successfully: " + eventName);
        return newEvent;
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
    public List<Event> getEvents() {
        return new ArrayList<>(events);
    }   public void addRoom(Room room) {
        rooms.add(room);
        System.out.println("Room added successfully: " + room.getRoomName());
    }
    public void addOrganizer(Organizer organizer) {
        organizers.add(organizer);
        System.out.println("Organizer added: " + organizer.getUsername());
    }

    public List<Organizer> getOrganizers() {
        return new ArrayList<>(organizers);
    }

    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
        System.out.println("Attendee added: " + attendee.getUsername());
    }

    public List<Attendee> getAttendees() {
        return new ArrayList<>(attendees);
    }

    public List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }


    @Override
    public String displayProfile() {
        System.out.println("===== Admin Profile =====");
        System.out.println("Username: " + getUsername());
        System.out.println("Date of Birth: " + getDateOfBirth());
        return null;
    }

    public String getWorkingHours() {
        return workingHours;
    }
}
