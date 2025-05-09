package eventmanagementsystem.dataClasses;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static eventmanagementsystem.dataClasses.Gender.FEMALE;
import static eventmanagementsystem.dataClasses.Gender.MALE;

public class Database {
    public static ArrayList<Attendee> attendees = new ArrayList<>();
    public static ArrayList<Admin> admins = new ArrayList<>();
    public static ArrayList<Organizer> organizers = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public static ArrayList<Event> events = new ArrayList<>();
    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();


    public static void initializeData() {
        attendees.clear();
        admins.clear();
        organizers.clear();
        users.clear();
        events.clear();
        rooms.clear();
        categories.clear();
        Admin admin1 = new Admin("hassan", "Hassanmoussa1", "2000-02-26","from 9AM to 3PM");

        admins.add(admin1);

        // Categories
        Category category1 = new Category("01", "Music");
        Category category2 = new Category("02", "Sports");
        Category category3 = new Category("03", "Tech");
        Category category4 = new Category("04", "Party");
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);

        // Rooms
        Room room1 = new Room("01",10, List.of("10:00- 12:00","01:00-03:00"));
        Room room2 = new Room("02",30, List.of("06:00-08:00"));
        Room room3 = new Room("03",20, List.of("02:00-04:00"));
        Room room4 = new Room("04",15, List.of("06:00-08:00"));

        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);


        // Organizers
        Organizer organizer1 = new Organizer("org1", "Orgpass1", "1995-02-19", 1000.0, new ArrayList<>());
        Organizer organizer2 = new Organizer("Mohammed", "Orgpass2", "2000-10-10", 2000.0, new ArrayList<>());
        Organizer organizer3 = new Organizer("Mariam", "Orgpass3", "2002-12-26", 15000.0, new ArrayList<>());
        organizers.addAll(List.of(organizer1, organizer2, organizer3));



        // Events
        Event event1 = new Event("Cairokee at Al Manara Arena", "Don’t miss the chance to experience the adrenaline rush as soon as the mic is turned on during a special performance by Cairokee.",
                "2025-11-12",category1,organizer2,room1,700.0,225);
        Event event2 = new Event("Tennis Tournament","Tennis Tournament for youth at Wadi Degla Club.","2025-1-12",category2,organizer1,room2,100.0,100);
        Event event3 = new Event("Halloween Party","Explore the mysteries lurking in every corner.","2025-1-12",category4,organizer3,room3,400.0,50);
        events.add(event1);
        events.add(event2);
        events.add(event3);

        organizer2.addOrganizedEvent(event1);
        organizer1.addOrganizedEvent(event2);
        organizer3.addOrganizedEvent(event3);




        // Attendees
        Attendee attendee1 = new Attendee("youssef", "Youssef123", "2004-01-01",200.0, MALE, "Cairo", List.of("Tennis"));
        Attendee attendee2 = new Attendee("john", "Johnashraf1", "2000-05-24", 500.0, MALE, "Giza", List.of("Football"));
        Attendee attendee3 = new Attendee("ahmed", "Ahmedbaher1", "2002-02-02", 800.0, MALE, "Alexandria", List.of("Gym"));
        Attendee attendee4 = new Attendee("sarah", "Sarahossama1", "2006-04-15", 1000.0, FEMALE, "Cairo", List.of("Music"));
        attendees.add(attendee1);
        attendees.add(attendee2);
        attendees.add(attendee3);
        attendees.add(attendee4);


        users.addAll(admins);
        users.addAll(organizers);
        users.addAll(attendees);

    }

    public static List<Attendee> getAttendees() {
        return attendees;
    }

    public static List<Organizer> getOrganizers() {
        return organizers;
    }

    public static List<Admin> getAdmins() {
        return admins;
    }

    public static void setAttendees(ArrayList<Attendee> attendees) {
        Database.attendees = attendees;
    }

    public static void setAdmins(ArrayList<Admin> admins) {
        Database.admins = admins;
    }

    public static void setOrganizers(ArrayList<Organizer> organizers) {
        Database.organizers = organizers;
    }

    public static void setUsers(List<User> users) {
        Database.users = users;
    }

    public static void setEvents(ArrayList<Event> events) {
        Database.events = events;
    }

    public static void setRooms(ArrayList<Room> rooms) {
        Database.rooms = rooms;
    }

    public static void setCategories(ArrayList<Category> categories) {
        Database.categories = categories;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Event> getEvents() {
        return new ArrayList<>(events); // Safe copy
    }

    public static List<Room> getRooms() {
        return rooms;
    }

    public static List<Category> getCategories() {
        return categories;
    }

}