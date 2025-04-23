package eventmanagementsystem.services;

import eventmanagementsystem.dataClasses.Room;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Add {
    public static void addRoom(Scanner scanner, List<Room> rooms) {
        System.out.println("\n=== Add a New Room ===");

        System.out.print("Enter Room ID: ");
        String roomId = scanner.nextLine();

        int capacity;
        while (true) {
            System.out.print("Enter Room Capacity: ");
            String capInput = scanner.nextLine();
            try {
                capacity = Integer.parseInt(capInput);
                if (capacity <= 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid positive number.");
            }
        }

        System.out.println("Enter Available Hours (e.g., 09:00-10:00), separated by commas:");
        String hoursInput = scanner.nextLine();
        List<String> availableHours = Arrays.asList(hoursInput.split("\\s*,\\s*"));

        Room newRoom = new Room(roomId, capacity, availableHours);
        rooms.add(newRoom);

        System.out.println("Room added successfully with available hours: " + availableHours);
    }
}
