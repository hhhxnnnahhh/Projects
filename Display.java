package eventmanagementsystem.services;
import eventmanagementsystem.dataClasses.Event;
import eventmanagementsystem.dataClasses.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Display {
    //private static List<Attendee> attendees = new ArrayList<>();
    //private static List<Event> Organizedevents;

    public static void showUsers(List<User> users) {
        for (User user : users) {
            System.out.println("Username: " + user.getUsername() + ", Role: " + user.getClass().getSimpleName());
        }
    }

    // Method to show all events
    public static void showEvents(List<Event> events) {
        for (Event event : events) {
            System.out.println("\n=== Event Details ===");
            System.out.println("Name: " + event.getName());
            System.out.println("Description: " + event.getDescription());
            System.out.println("Date: " + event.getDate());
            System.out.println("Category: " + (event.getCategory() != null ? event.getCategory().getName() : "N/A"));
           // System.out.println("Organizer: " + (event.getOrganizer() != null ? event.getOrganizer().getUsername() : "N/A"));
            System.out.println("Room: " + (event.getRoom() != null ? event.getRoom().getRoomId() : "N/A"));
            System.out.println("Ticket Price: $" + event.getTicketPrice());
            System.out.println("Available Seats: " + event.getAvailableSeats());
            System.out.println("=======================");
        }
    }

    public static void showRegisteredEvents(Attendee attendee) {
        List<Event> registeredEvents = attendee.getRegisteredEvents();

        if (registeredEvents == null || registeredEvents.isEmpty()) {
            System.out.println("\nYou have not registered for any events yet.");
        } else {
            System.out.println("\n=== Your Registered Events ===");
            for (Event event : registeredEvents) {
                System.out.println("Event Name: " + event.getName());
                System.out.println("Date: " + event.getDate());
                System.out.println("Organizer: " + event.getOrganizer().getUsername());
                System.out.println("-----------------------------");
            }
        }
    }

    // Method to show all rooms
    public static void showRooms(List<Room> rooms) {
        for (Room room : rooms) {
            System.out.println("\n=== All Rooms ===");
            System.out.println("Room ID: " + room.getRoomId() + ", Capacity: " + room.getCapacity());
            System.out.println("Available Hours: " + room.getAvailableHours());
            System.out.println("--------------------------");
        }
    }

    public static void showOrganizers(List<User> users) {
        System.out.println("\n=== All Organizers ===");

        boolean found = false;
        for (User user : users) {
            if (user instanceof Organizer) {
                Organizer organizer = (Organizer) user;
                System.out.println("Username: " + organizer.getUsername());
                System.out.println("Date of Birth: " + organizer.getDateOfBirth());
                System.out.println("--------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No organizers found.");
        }
    }

    public static void registerForEvent(Scanner scanner, List<Event> allEvents, Attendee attendee) {
        if (allEvents == null || allEvents.isEmpty()) {
            System.out.println("No events available.");
            return;
        }

        System.out.println("\n=== Available Events ===");
        for (int i = 0; i < allEvents.size(); i++) {
            Event event = allEvents.get(i);
            System.out.printf("%d. %s on %s | $%.2f | Seats left: %d%n",
                    i + 1, event.getName(), event.getDate(), event.getTicketPrice(), event.getAvailableSeats());
        }

        System.out.print("Select an event by number: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index < 1 || index > allEvents.size()) {
            System.out.println("Invalid event number.");
            return;
        }

        Event selectedEvent = allEvents.get(index - 1);

        if (selectedEvent.getAvailableSeats() <= 0) {
            System.out.println("Sorry, this event's already full.");
            return;
        }

        double ticketPrice = selectedEvent.getTicketPrice();

        // Attempt to withdraw from attendee's wallet
        if (!attendee.getWallet().buyTicketATTENDEE(ticketPrice)) {
            System.out.println("Invalid. No sufficient balance. Add funds first.");
            return;
        }

        // Pay the organizer
        Organizer organizer = selectedEvent.getOrganizer();
        if (organizer != null) {
            organizer.getWallet().buyTicketORGANIZER(ticketPrice);
        }

        // Register and update seat count
        attendee.registerForEvent(selectedEvent);
        selectedEvent.reduceAvailableSeats();  // Ensure this method exists
        System.out.println("You’ve successfully registered for: " + selectedEvent.getName());

        // Show final balances
        System.out.printf("Your wallet balance: $%.2f%n", attendee.getWallet().getBalance());
        if (organizer != null) {
            System.out.printf("Organizer (%s) balance: $%.2f%n",
                    organizer.getUsername(), organizer.getWallet().getBalance());
        }
    }

    public static List<Attendee> getAttendees() {
        return Database.attendees;
    }

    public static void adminDashboard(Admin admin, Scanner scanner, List<User> users, List<Event> events, List<Room> rooms) {
        while (true) {
            System.out.println("\n=== Admin Dashboard ===");
            System.out.println("Welcome, " + admin.getUsername());
            System.out.println("1. View All Organizers");
            System.out.println("2. View All Attendees");
            System.out.println("3. View All Events");
            System.out.println("4. View All Rooms");
            System.out.println("5. Add Room");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine();
            clearConsole();

            switch (input) {
                case "1":
                    Display.showOrganizers(Database.users);
                    break;
                case "2":
                    Display.showAttendees(Database.users);
                    break;
                case "3":
                    Display.showEvents(Database.events);
                    break;
                case "4":
                    Display.showRooms(Database.rooms);
                    break;
                case "5":
                    Add.addRoom(scanner, Database.rooms);
                    break;
                case "6":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void attendeeDashboard(Scanner scanner, Attendee attendee, List<Event> allEvents) {
        while (true) {
            System.out.println("\n=== Attendee Dashboard ===");
            System.out.println("1- View Profile ");
            System.out.println("2- Browse Events ");
            System.out.println("3- Register for an Event ");
            System.out.println("4- View Registered Events");
            System.out.println("5- Manage Wallet ");
            System.out.println("6- Log Out ");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    attendee.displayProfile();
                    break;
                case 2:
                    Display.showEvents(Database.events);
                    break;
                case 3:
                    Display.registerForEvent(scanner, Database.events, attendee);
                    break;
                case 4:
                    Display.showRegisteredEvents(attendee);
                    break;
                case 5:
                    Wallet.updateBalance(scanner);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid Option. Please try again!");
            }

            System.out.println("\nPress Enter to return to the Attendee Menu...");
            scanner.nextLine();
        }
    }

    public static void organizerDashboard(Organizer organizer, List<Room> allRooms) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Organizer Menu ===");
            System.out.println("Welcome, " + organizer.getUsername());
            System.out.println("1. View Available Rooms");
            System.out.println("2. Create an Event");
            System.out.println("3. View My Events");
            System.out.println("4. View Attendees for My Events");
            System.out.println("5. Manage Wallet");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Display.viewAvailableRooms(Database.getRooms());
                    break;

                case 2:
                    System.out.print("Enter event name: ");
                    String eventName = scanner.nextLine();

                    System.out.print("Enter event description: ");
                    String eventDescription = scanner.nextLine();

                    System.out.print("Enter category name: ");
                    String categoryName = scanner.nextLine();

                    System.out.print("Enter event date (e.g., 2025-06-15): ");
                    String eventDate = scanner.nextLine();

                    System.out.print("Enter number of available seats: ");
                    int availableSeats = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter category ID: ");
                    String categoryID = scanner.nextLine();

                    Category selectedCategory = new Category(categoryID, categoryName);
                    Database.getCategories().add(selectedCategory);

                    List<Room> availableRooms = new ArrayList<>();
                    for (Room room : Database.getRooms()) {
                        if (room.isAvailable()) {
                            availableRooms.add(room);
                        }
                    }

                    if (availableRooms.isEmpty()) {
                        System.out.println("No available rooms to assign.");
                        break;
                    }

                    System.out.println("Select a room:");
                    for (int i = 0; i < availableRooms.size(); i++) {
                        Room r = availableRooms.get(i);
                        System.out.println((i + 1) + ". Room ID: " + r.getRoomId() + " | Available Hours: " + r.getAvailableHours());
                    }

                    System.out.print("Enter the room number: ");
                    int roomIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (roomIndex < 0 || roomIndex >= availableRooms.size()) {
                        System.out.println("Invalid room selection.");
                        break;
                    }

                    Room selectedRoom = availableRooms.get(roomIndex);

                    System.out.print("Enter ticket price: ");
                    double ticketPrice = scanner.nextDouble();
                    scanner.nextLine();

                    Event newEvent = new Event(
                            eventName,
                            eventDescription,
                            eventDate,
                            selectedCategory,
                            organizer,
                            selectedRoom,
                            ticketPrice,
                            availableSeats
                    );

                    Database.getEvents().add(newEvent);
                    organizer.addOrganizedEvent(newEvent);
                    System.out.println("Event created successfully!");
                    break;

                case 3:
                    Display.viewOrganizedEvents(organizer);
                    break;

                case 4:
                    Display.viewAttendeesForMyEvents();
                    break;

                case 5:
                    Wallet.updateBalance(scanner);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid Option. Please try again!");
            }

            System.out.println("\nPress Enter to return to the Organizer Menu...");
            scanner.nextLine();  // Wait for enter to continue
        }
    }

    private static void viewAvailableRooms(List<Room> allRooms) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : allRooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }

        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms to display.");
        } else {
            System.out.println("\nAvailable Rooms:");
            for (Room room : availableRooms) {
                System.out.println("Room ID: " + room.getRoomId() + " | Available Hours: " + room.getAvailableHours());
            }
        }
    }

    // Clear Console
    public static void clearConsole() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    public static void viewAttendeesForMyEvents() {
        for (Event event : Database.events) {
            System.out.println("Event: " + event.getName());
            List<Attendee> attendees = event.getAttendees();

            if (attendees == null) {
                System.out.println("  No attendees yet.");
            } else {
                for (Attendee attendee : attendees) {
                    System.out.println("  - " + attendee.getUsername());
                }
            }
        }
    }

    public static void viewOrganizedEvents(Organizer organizer) {
        List<Event> events = organizer.getOrganizedEvents();
        if (events.isEmpty()) {
            System.out.println("You haven't organized any events yet.");
        } else {
            System.out.println("Events organized by " + organizer.getUsername() + ":");
            for (Event event : Database.events) {
                System.out.println(event);
            }
        }
    }

    public static void showAttendees(List<User> users) {
        System.out.println("\n=== All Attendees ===");

        boolean found = false;
        for (User user : users) {
            if (user instanceof Attendee) {
                Attendee attendee = (Attendee) user;
                System.out.println("Username: " + attendee.getUsername());
                System.out.println("Date of Birth: " + attendee.getDateOfBirth());
                System.out.println("--------------------------");
                found = true;
            }
        }
    }
}