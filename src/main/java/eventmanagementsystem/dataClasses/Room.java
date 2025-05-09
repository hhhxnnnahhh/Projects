package eventmanagementsystem.dataClasses;

import java.util.List;

public class Room {
    private String roomID;
    private int capacity;
    private List<String> availableHours;
    public boolean isAvailable;  // Instance variable, not static

    // Constructor
    public Room(String roomID, int capacity, List<String> availableHours) {
        this.roomID = roomID;
        this.capacity = capacity;
        this.availableHours = availableHours;
        this.isAvailable = true; // Default availability
    }

    // Getters and Setters
    public String getRoomId() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<String> getAvailableHours() {
        return availableHours;
    }

    public void setAvailableHours(List<String> availableHours) {
        this.availableHours = availableHours;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    // Check if the room is available at a given hour
    public boolean isAvailableAt(String hour) {
        return availableHours.contains(hour);
    }

    // Get room name with capacity
    public String getRoomName() {
        return "Room " + roomID + " (Capacity: " + capacity + ")";
    }
    public String toString() {
        return "Room ID: " + roomID + ", Capacity: " + capacity + ", Available Hours: " + availableHours;
    }
}

