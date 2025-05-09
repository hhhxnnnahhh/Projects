package eventmanagementsystem.services;

import eventmanagementsystem.Main;
import eventmanagementsystem.dataClasses.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;
import java.util.*;

public class Display extends Application {
    @Override
    public void start(Stage primaryStage) {
        Database.initializeData();

        User currentUser = Database.getUsers().get(0);

        // Loading a dashboard based on the user logged in
        if (currentUser instanceof Admin) {
            Admin admin = (Admin) currentUser;
            adminDashboard(primaryStage, admin);
        } else if (currentUser instanceof Organizer) {
            Organizer organizer = (Organizer) currentUser;
            List<Room> rooms = Database.getRooms();
            organizerDashboard(primaryStage, organizer, rooms);
        } else if (currentUser instanceof Attendee) {
            Attendee attendee = (Attendee) currentUser;
            List<Event> events = Database.getEvents();
            attendeeDashboard(primaryStage, attendee, events);
        }
    }

    public static void adminDashboard(Stage stage, Admin admin) {
        stage.setTitle("Admin Dashboard");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-padding: 20; -fx-background-color: #deb887;");

        TextArea displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefSize(600, 600);

        VBox rightPanel = new VBox(displayArea);
        rightPanel.setAlignment(Pos.TOP_LEFT);
        root.setCenter(rightPanel);

        VBox leftPanel = new VBox(15);
        leftPanel.setAlignment(Pos.TOP_LEFT);
        leftPanel.setStyle("-fx-padding: 10;");

        Button profileBtn = new Button("1. View Profile");
        Button viewEventsBtn = new Button("2. View All Events");
        Button addEventBtn = new Button("3. Add Event");
        Button deleteEventBtn = new Button("4. Delete Selected Event");
        Button viewRoomsBtn = new Button("5. View All Rooms");
        Button addRoomBtn = new Button("6. Add Room");
        Button viewOrganizersBtn = new Button("7. View All Organizers");
        Button viewAttendeesBtn = new Button("8. View All Attendees");
        Button logoutBtn = new Button("9. Logout");

        TextField nameField = new TextField(); nameField.setPromptText("Event Name");
        TextField descField = new TextField(); descField.setPromptText("Description");
        TextField dateField = new TextField(); dateField.setPromptText("Date (YYYY-MM-DD)");
        TextField priceField = new TextField(); priceField.setPromptText("Ticket Price");
        TextField seatsField = new TextField(); seatsField.setPromptText("Available Seats");

        ComboBox<Category> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll(Database.getCategories());

        ComboBox<Organizer> organizerBox = new ComboBox<>();
        organizerBox.getItems().addAll(Database.getOrganizers());

        ComboBox<Room> roomBox = new ComboBox<>();
        roomBox.getItems().addAll(Database.getRooms());

        ComboBox<Event> deleteEventBox = new ComboBox<>();
        deleteEventBox.getItems().addAll(Database.getEvents());
        deleteEventBox.setPromptText("Select Event to Delete");

        categoryBox.setCellFactory(cb -> new ListCell<>() {
            @Override protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });
        categoryBox.setButtonCell(categoryBox.getCellFactory().call(null));

        organizerBox.setCellFactory(cb -> new ListCell<>() {
            @Override protected void updateItem(Organizer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getUsername());
            }
        });
        organizerBox.setButtonCell(organizerBox.getCellFactory().call(null));

        roomBox.setCellFactory(cb -> new ListCell<>() {
            @Override protected void updateItem(Room item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : "Room " + item.getRoomId());
            }
        });
        roomBox.setButtonCell(roomBox.getCellFactory().call(null));

        deleteEventBox.setCellFactory(cb -> new ListCell<>() {
            @Override protected void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });
        deleteEventBox.setButtonCell(deleteEventBox.getCellFactory().call(null));

        VBox formBox = new VBox(5,
                new Label("Add New Event:"),
                nameField, descField, dateField, priceField, seatsField,
                new Label("Category:"), categoryBox,
                new Label("Organizer:"), organizerBox,
                new Label("Room:"), roomBox
        );

        VBox deleteBox = new VBox(5,
                new Label("Delete Event:"),
                deleteEventBox,
                deleteEventBtn
        );

        TextField roomCapacityField = new TextField(); roomCapacityField.setPromptText("Capacity");
        TextField roomLocationField = new TextField(); roomLocationField.setPromptText("Available Hours (e.g., 9AM-11AM,1PM-3PM)");
        VBox addRoomBox = new VBox(5,
                new Label("Add Room:"),
                roomCapacityField, roomLocationField,
                addRoomBtn
        );

        leftPanel.getChildren().addAll(
                new Label("Welcome, " + admin.getUsername()),
                profileBtn, viewEventsBtn,
                new Separator(),
                formBox, addEventBtn,
                new Separator(),
                deleteBox,
                new Separator(),
                viewRoomsBtn, addRoomBox,
                new Separator(),
                viewOrganizersBtn, viewAttendeesBtn,
                new Separator(),
                logoutBtn
        );

        ScrollPane scrollPane = new ScrollPane(leftPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(400);
        root.setLeft(scrollPane);

        displayArea.setStyle("-fx-font-size: 14px; -fx-text-alignment: right;");
        displayArea.setFont(Font.font("Arial", 14)); // Set font family and size

        profileBtn.setOnAction(e -> {
            String profileText = "===== Admin Profile =====\n\n" +
                    "Username: " + admin.getUsername() + "\n\n" +
                    "Date of Birth: " + admin.getDateOfBirth() + "\n\n" +
                    "Working Hours: " + admin.getWorkingHours() + "\n";
            displayArea.setText(profileText);
            displayArea.setStyle("-fx-font-size: 16px; -fx-text-alignment: right;");
        });

        viewEventsBtn.setOnAction(e -> {
            List<Event> events = Database.getEvents();
            if (events.isEmpty()) {
                displayArea.setText("No events found.");
                displayArea.setStyle("-fx-font-size: 16px; -fx-text-alignment: right;");
            } else {
                StringBuilder sb = new StringBuilder("=== All Events ===\n\n");
                for (Event event : events) {
                    String name = event.getName();
                    String date = event.getDate();
                    String organizer = (event.getOrganizer() != null) ? event.getOrganizer().getUsername() : "N/A";
                    String category = (event.getCategory() != null) ? event.getCategory().getName() : "N/A";
                    String room = (event.getRoom() != null) ? String.valueOf(event.getRoom().getRoomId()) : "N/A";

                    sb.append("Event: ").append(name).append("\n")
                            .append("Date: ").append(date).append("\n")
                            .append("Organizer: ").append(organizer).append("\n")
                            .append("Category: ").append(category).append("\n")
                            .append("Room: ").append(room).append("\n\n");
                }
                displayArea.setText(sb.toString());
                displayArea.setStyle("-fx-font-size: 16px; -fx-text-alignment: right;");
            }
        });

        addEventBtn.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String desc = descField.getText();
                String dateInput = dateField.getText();
                double price = Double.parseDouble(priceField.getText());
                int seats = Integer.parseInt(seatsField.getText());
                Category category = categoryBox.getValue();
                Organizer organizer = organizerBox.getValue();
                Room room = roomBox.getValue();

                if (name.isEmpty() || desc.isEmpty() || dateInput.isEmpty() ||
                        category == null || organizer == null || room == null) {
                    displayArea.setText("Please fill all fields.");
                    return;
                }

                LocalDate parsedDate;
                try {
                    parsedDate = LocalDate.parse(dateInput);
                } catch (DateTimeParseException ex) {
                    displayArea.setText("Invalid date format. Please use YYYY-MM-DD.");
                    return;
                }

                String formattedDate = parsedDate.toString();

                Event newEvent = admin.addEvent(name, desc, formattedDate, category, organizer, room, price, seats);
                Database.getEvents().add(newEvent);
                deleteEventBox.getItems().add(newEvent);

                displayArea.setText("Event '" + name + "' added successfully to the system.");
            } catch (NumberFormatException ex) {
                displayArea.setText("Price and seats must be numeric values.");
            } catch (Exception ex) {
                displayArea.setText("Error adding event: " + ex.getMessage());
            }
        });

        deleteEventBtn.setOnAction(e -> {
            Event selected = deleteEventBox.getValue();
            if (selected != null) {
                admin.deleteEvent(selected.getName());
                Database.getEvents().remove(selected);
                deleteEventBox.getItems().remove(selected);
                displayArea.setText("Deleted event: " + selected.getName());
            } else {
                displayArea.setText("Please select an event to delete.");
            }
        });

        viewRoomsBtn.setOnAction(e -> {
            List<Room> rooms = Database.getRooms();
            if (rooms.isEmpty()) {
                displayArea.setText("No rooms available.");
            } else {
                StringBuilder sb = new StringBuilder("=== All Rooms ===\n");
                for (Room r : rooms) {
                    sb.append("Room ").append(r.getRoomId())
                            .append(" | Capacity: ").append(r.getCapacity())
                            .append("\n");
                }
                displayArea.setText(sb.toString());
            }
        });

        addRoomBtn.setOnAction(e -> {
            try {
                int capacity = Integer.parseInt(roomCapacityField.getText().trim());
                String hoursText = roomLocationField.getText().trim();

                if (hoursText.isEmpty()) {
                    displayArea.setText("Please enter available hours (e.g., 9AM-11AM,1PM-3PM).");
                    displayArea.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");
                    return;
                }

                List<String> availableHours = Arrays.stream(hoursText.split(","))
                        .map(String::trim)
                        .toList();

                String newRoomId = String.valueOf(Database.getRooms().size() + 1);
                Room room = new Room(newRoomId, capacity, availableHours);

                Database.getRooms().add(room);
                roomBox.getItems().add(room);

                displayArea.setText("Room added successfully: Room " + room.getRoomId());
                displayArea.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");
            } catch (NumberFormatException ex) {
                displayArea.setText("Capacity must be a valid number.");
                displayArea.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");
            }
        });

        viewOrganizersBtn.setOnAction(e -> {
            List<Organizer> organizers = Database.getOrganizers();
            if (organizers.isEmpty()) {
                displayArea.setText("No organizers available.");
                displayArea.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");
            } else {
                StringBuilder sb = new StringBuilder("=== All Organizers ===\n");
                for (Organizer o : organizers) {
                    sb.append("Username: ").append(o.getUsername())
                            .append(" | DOB: ").append(o.getDateOfBirth())
                            .append("\n");
                }
                displayArea.setText(sb.toString());
                displayArea.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");
            }
        });

        viewAttendeesBtn.setOnAction(e -> {
            List<Attendee> attendees = Database.getAttendees();
            if (attendees.isEmpty()) {
                displayArea.setText("No attendees available.");
                displayArea.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");
            } else {
                StringBuilder sb = new StringBuilder("=== All Attendees ===\n");
                for (Attendee a : attendees) {
                    sb.append("Username: ").append(a.getUsername())
                            .append(" | DOB: ").append(a.getDateOfBirth())
                            .append("\n");
                }
                displayArea.setText(sb.toString());
                displayArea.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");
            }
        });

        logoutBtn.setOnAction(e -> {
            showMainMenu(stage);
        });

        stage.setScene(new Scene(root, 1000, 700));
        stage.show();
    }
    public static void organizerDashboard(Stage primaryStage, Organizer organizer, List<Room> allRooms) {
        primaryStage.setTitle("Organizer Dashboard");

        // Book layout
        HBox bookLayout = new HBox(30);
        bookLayout.setStyle("-fx-padding: 30; -fx-background-color: #deb887;");
        bookLayout.setAlignment(Pos.TOP_CENTER);
        VBox leftPage = new VBox(10);
        leftPage.setAlignment(Pos.TOP_LEFT);

        Label welcomeLabel = new Label("Welcome, " + organizer.getUsername());
        welcomeLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        // TextFields
        TextField eventNameField = new TextField();
        eventNameField.setPromptText("Event Name");
        eventNameField.setMaxWidth(300);

        TextField ticketPriceField = new TextField();
        ticketPriceField.setPromptText("Ticket Price");
        ticketPriceField.setMaxWidth(300);

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");
        descriptionField.setMaxWidth(300);

        TextField dateField = new TextField();
        dateField.setPromptText("Date");
        dateField.setMaxWidth(300);

        TextField availableSeatsField = new TextField();
        availableSeatsField.setPromptText("Available Seats");
        availableSeatsField.setMaxWidth(300);

        ComboBox<Room> roomComboBox = new ComboBox<>();
        roomComboBox.getItems().addAll(allRooms);

        ComboBox<Category> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(Database.getCategories());

        // Buttons
        Button viewRoomsButton = new Button("1. View Available Rooms");
        Button createEventButton = new Button("2. Create an Event");
        Button deleteEventButton = new Button("3. Delete My Event");
        Button viewMyEventsButton = new Button("4. View My Events");
        Button viewAttendeesButton = new Button("5. View Attendees for My Events");
        Button manageWalletButton = new Button("6. Manage Wallet");
        Button logoutButton = new Button("7. Logout");

        // Dashboard layout
        VBox eventForm = new VBox(5);
        eventForm.getChildren().addAll(
                new Label("Event Name:"), eventNameField,
                new Label("Ticket Price:"), ticketPriceField,
                new Label("Description:"), descriptionField,
                new Label("Date:"), dateField,
                new Label("Available Seats:"), availableSeatsField,
                new Label("Room:"), roomComboBox,
                new Label("Category:"), categoryComboBox
        );

        leftPage.getChildren().addAll(
                welcomeLabel,
                viewRoomsButton,
                createEventButton,
                deleteEventButton,
                viewMyEventsButton,
                viewAttendeesButton,
                manageWalletButton,
                logoutButton,
                new Separator(),
                eventForm
        );

        // Right Page: Text area
        TextArea displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefSize(400, 500);

        VBox rightPage = new VBox(displayArea);
        rightPage.setAlignment(Pos.TOP_RIGHT);

        bookLayout.getChildren().addAll(leftPage, rightPage);

        // Button Actions
        viewRoomsButton.setOnAction(e -> {
            displayArea.clear();
            showRooms(Database.getRooms(), displayArea);
        });

        createEventButton.setOnAction(e -> {
            try {
                String eventName = eventNameField.getText();
                Room selectedRoom = roomComboBox.getSelectionModel().getSelectedItem();
                Category selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();
                double ticketPrice = Double.parseDouble(ticketPriceField.getText());
                String description = descriptionField.getText();
                String date = dateField.getText();
                int availableSeats = Integer.parseInt(availableSeatsField.getText());

                if (eventName.isEmpty() || selectedRoom == null || selectedCategory == null ||
                        ticketPrice <= 0 || description.isEmpty() || date.isEmpty() || availableSeats <= 0) {
                    displayArea.setText("All fields must be filled correctly!");
                    return;
                }

                organizer.createEvent(eventName, selectedRoom, selectedCategory, ticketPrice,
                        description, date, organizer.getUsername(), availableSeats);

                displayArea.setText("Event '" + eventName + "' created successfully!");

            } catch (NumberFormatException ex) {
                displayArea.setText("Please enter valid numeric values for ticket price and available seats.");
            }
        });

        deleteEventButton.setOnAction(e -> {
            List<Event> myEvents = organizer.getOrganizedEvents();

            if (myEvents.isEmpty()) {
                displayArea.setText("You have no events to delete.");
                return;
            }

            ChoiceDialog<Event> dialog = new ChoiceDialog<>(myEvents.get(0), myEvents);
            dialog.setTitle("Delete Event");
            dialog.setHeaderText("Select the event you want to delete:");
            dialog.setContentText("Your Events:");

            Optional<Event> result = dialog.showAndWait();
            result.ifPresent(eventToDelete -> {
                Database.getEvents().remove(eventToDelete);
                organizer.getOrganizedEvents().remove(eventToDelete);

                displayArea.setText("Event '" + eventToDelete.getName() + "' deleted successfully.\n\n");
                viewOrganizedEvents(organizer, displayArea); // refresh
            });
        });





        viewMyEventsButton.setOnAction(e -> {
            displayArea.clear();
            viewOrganizedEvents(organizer, displayArea);
        });

        viewAttendeesButton.setOnAction(e -> {
            displayArea.clear();
            viewAttendeesForMyEvents(organizer, displayArea);
        });

        manageWalletButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Manage Wallet");
            dialog.setHeaderText("Update Wallet Balance");
            dialog.setContentText("Enter amount to add:");

            dialog.showAndWait().ifPresent(input -> {
                try {
                    double amount = Double.parseDouble(input);
                    organizer.getWallet().updateBalance(amount);
                    displayArea.setText("Balance updated successfully.\nNew balance: $" + organizer.getWallet().getBalance());
                } catch (NumberFormatException ex) {
                    displayArea.setText("Invalid amount entered. Please enter a valid number.");
                }
            });
        });

        logoutButton.setOnAction(e -> {
            showMainMenu(primaryStage);
        });

        Scene scene = new Scene(bookLayout, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void showRooms(List<Room> rooms, TextArea displayArea) {
        StringBuilder roomList = new StringBuilder("Available Rooms:\n");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                roomList.append("Room ID: ").append(room.getRoomId())
                        .append(" | Capacity: ").append(room.getCapacity())
                        .append(" | Available Hours: ").append(room.getAvailableHours())
                        .append("\n");
            }
        }
        displayArea.setText(roomList.toString());
    }

    public static void viewOrganizedEvents(Organizer organizer, TextArea displayArea) {
        // Get the list of events organized by the current organizer
        List<Event> events = organizer.getOrganizedEvents();
        StringBuilder eventList = new StringBuilder("Events organized by " + organizer.getUsername() + ":\n");

        // If the organizer has no events, show that
        if (events.isEmpty()) {
            eventList.append("You haven't organized any events yet.");
        } else {
            // Otherwise, display each event's details
            for (Event event : events) {
                eventList.append(event).append("\n");  // Appending each event's details
            }
        }

        // Update the display area with the new event list
        displayArea.setText(eventList.toString());
    }



    public static void attendeeDashboard(Stage primaryStage, Attendee attendee, List<Event> allEvents) {
        primaryStage.setTitle("Attendee Dashboard");

        // Book Layout
        HBox mainLayout = new HBox(30);
        mainLayout.setStyle("-fx-padding: 30; -fx-background-color: #deb887;");
        mainLayout.setAlignment(Pos.TOP_CENTER);

        VBox leftPanel = new VBox(10);
        leftPanel.setAlignment(Pos.TOP_LEFT);

        Label welcomeLabel = new Label("Welcome, " + attendee.getUsername());
        welcomeLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        ComboBox<Event> eventComboBox = new ComboBox<>();
        eventComboBox.getItems().addAll(allEvents);
        eventComboBox.setPromptText("Select Event");

        // Buttons
        Button viewProfileButton = new Button("1. View Profile");
        Button browseEventsButton = new Button("2. Browse Events");
        Button registerEventButton = new Button("3. Register for Event");
        Button viewRegisteredButton = new Button("4. View Registered Events");
        Button manageWalletButton = new Button("5. Manage Wallet");
        Button searchMenuButton = new Button("6. Search");
        Button logoutButton = new Button("7. Logout");

        // Search Option
        TextField searchField = new TextField();
        searchField.setPromptText("Enter keyword...");
        searchField.setMaxWidth(200);

        ComboBox<String> searchTypeBox = new ComboBox<>();
        searchTypeBox.getItems().addAll("Events", "Organizers", "Attendees");
        searchTypeBox.setPromptText("Search Type");

        Button searchButton = new Button("Search");

        VBox searchControls = new VBox(5,
                new Label("Search:"), searchField, searchTypeBox, searchButton
        );
        searchControls.setVisible(false);

        VBox eventForm = new VBox(5);
        eventForm.getChildren().addAll(
                new Label("Select an Event:"), eventComboBox
        );

        leftPanel.getChildren().addAll(
                welcomeLabel,
                viewProfileButton,
                browseEventsButton,
                registerEventButton,
                viewRegisteredButton,
                manageWalletButton,
                searchMenuButton,
                logoutButton,
                new Separator(),
                eventForm,
                searchControls
        );

        // Right Layout: Display Area
        TextArea displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefSize(400, 500);

        VBox rightPanel = new VBox(displayArea);
        rightPanel.setAlignment(Pos.TOP_RIGHT);

        mainLayout.getChildren().addAll(leftPanel, rightPanel);

        // Button Actions
        viewProfileButton.setOnAction(e -> {
            displayArea.clear();
            displayArea.setText(attendee.displayProfile());
        });

        browseEventsButton.setOnAction(e -> {
            displayArea.clear();
            displayArea.setText(showEvents(allEvents));
        });

        registerEventButton.setOnAction(e -> {
            Event selectedEvent = eventComboBox.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                try {
                    attendee.registerForEvent(selectedEvent);
                    selectedEvent.reduceAvailableSeats();
                    displayArea.setText("You’ve successfully registered for: " + selectedEvent.getName());
                } catch (Exception ex) {
                    displayArea.setText("Failed to register. Error: " + ex.getMessage());
                }
            } else {
                displayArea.setText("Please select an event to register.");
            }
        });

        viewRegisteredButton.setOnAction(e -> {
            displayArea.clear();
            List<Event> registered = attendee.getRegisteredEvents();
            if (registered == null || registered.isEmpty()) {
                displayArea.setText("You have not registered for any events yet.");
            } else {
                StringBuilder sb = new StringBuilder("=== Your Registered Events ===\n");
                for (Event event : registered) {
                    sb.append("Event Name: ").append(event.getName()).append("\n")
                            .append("Date: ").append(event.getDate()).append("\n")
                            .append("Organizer: ").append(event.getOrganizer().getUsername()).append("\n")
                            .append("-----------------------------\n");
                }
                displayArea.setText(sb.toString());
            }
        });

        manageWalletButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Manage Wallet");
            dialog.setHeaderText("Update Wallet Balance");
            dialog.setContentText("Enter amount to add:");

            dialog.showAndWait().ifPresent(input -> {
                displayArea.clear();
                try {
                    double amount = Double.parseDouble(input);
                    attendee.getWallet().updateBalance(amount);
                    displayArea.setText("Balance updated.\nNew Balance: $" + attendee.getWallet().getBalance());
                } catch (NumberFormatException ex) {
                    displayArea.setText("Invalid amount. Please enter a valid number.");
                }
            });
        });

        searchMenuButton.setOnAction(e -> {
            searchControls.setVisible(!searchControls.isVisible());
            displayArea.clear();
            if (searchControls.isVisible()) {
                displayArea.setText("Enter a keyword and select a type to search.");
            }
        });

        searchButton.setOnAction(e -> {
            String keyword = searchField.getText().toLowerCase().trim();
            String type = searchTypeBox.getValue();
            StringBuilder results = new StringBuilder();

            if (type == null || keyword.isEmpty()) {
                displayArea.setText("Please enter a keyword and select a type.");
                return;
            }

            switch (type) {
                case "Events":
                    for (Event event : Database.getEvents()) {
                        if (event.getName().toLowerCase().contains(keyword)) {
                            results.append("Event: ").append(event.getName())
                                    .append(" | Date: ").append(event.getDate()).append("\n");
                        }
                    }
                    break;
                case "Attendees":
                    for (Attendee att : Database.getAttendees()) {
                        if (att.getUsername().toLowerCase().contains(keyword)) {
                            results.append("Attendee: ").append(att.getUsername()).append("\n");
                        }
                    }
                    break;
                case "Organizers":
                    for (Organizer org : Database.getOrganizers()) {
                        if (org.getUsername().toLowerCase().contains(keyword)) {
                            results.append("Organizer: ").append(org.getUsername()).append("\n");
                        }
                    }
                    break;
            }

            displayArea.setText(results.length() > 0 ? results.toString() : "No matches found.");
        });

        logoutButton.setOnAction(e -> {
            showMainMenu(primaryStage);
        });


        Scene scene = new Scene(mainLayout, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void viewAttendeesForMyEvents(Organizer organizer, TextArea displayArea) {
        List<Event> events = organizer.getOrganizedEvents();
        StringBuilder attendeeList = new StringBuilder("Attendees for Your Events:\n");

        if (events.isEmpty()) {
            attendeeList.append("You haven't organized any events yet.");
        } else {
            for (Event event : events) {
                attendeeList.append("Event: ").append(event.getName()).append("\n");
                List<Attendee> attendees = event.getAttendees();
                if (attendees == null || attendees.isEmpty()) {
                    attendeeList.append("  (no attendees yet)\n");
                } else {
                    for (Attendee a : attendees) {
                        attendeeList.append("  - ").append(a.getUsername()).append("\n");
                    }
                }
                attendeeList.append("\n");
            }
        }
        displayArea.setText(attendeeList.toString());
    }

    public static String showEvents(List<Event> events) {
        StringBuilder eventList = new StringBuilder("=== All Events ===\n");
        for (Event event : events) {
            eventList.append("Event Name: ").append(event.getName()).append("\n")
                    .append("Date: ").append(event.getDate()).append("\n")
                    .append("Category: ").append(event.getCategory().getName()).append("\n")
                    .append("Available Seats: ").append(event.getAvailableSeats()).append("\n")
                    .append("=====================\n");
        }
        return eventList.toString();
    }

    public static void deleteEventByName(String eventName, Organizer organizer) {
        Event eventToDelete = null;

        for (Event event : Database.getEvents()) {
            if (event.getName().equalsIgnoreCase(eventName.trim()) &&
                    event.getOrganizer().equals(organizer)) {
                eventToDelete = event;
                break;
            }
        }

        if (eventToDelete != null) {
            Database.getEvents().remove(eventToDelete);
            organizer.getOrganizedEvents().removeIf(ev ->
                    ev.getName().equalsIgnoreCase(eventName.trim())
            ); // <-- THIS IS CRUCIAL
        }
    }







    public static void viewRegisteredEvents(Attendee attendee, TextArea displayArea) {
        List<Event> registeredEvents = attendee.getRegisteredEvents();

        if (registeredEvents == null || registeredEvents.isEmpty()) {
            displayArea.setText("You have not registered for any events yet.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Your Registered Events ===\n");
            for (Event event : registeredEvents) {
                sb.append("Event Name: ").append(event.getName()).append("\n")
                        .append("Date: ").append(event.getDate()).append("\n")
                        .append("Organizer: ").append(event.getOrganizer().getUsername()).append("\n")
                        .append("-----------------------------\n");
            }
            displayArea.setText(sb.toString());
        }
    }

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

        // Withdraw from attendee's wallet
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

    public static void showLoginForm(Stage stage) {
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register");
        Label messageLabel = new Label();

        VBox layout = new VBox(10, userLabel, userField, passLabel, passField, loginBtn, registerBtn, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 40; -fx-background-color: #deb887;");

        loginBtn.setOnAction(e -> {
            String username = userField.getText().trim();
            String password = passField.getText().trim();

            // Check if user exists in database
            User user = null;
            for (User u : Database.getUsers()) {
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    user = u;
                    break;
                }
            }

            if (user != null) {
                if (user instanceof Admin admin) {
                    adminDashboard(stage, admin);
                } else if (user instanceof Organizer organizer) {
                    organizerDashboard(stage, organizer, Database.getRooms());
                } else if (user instanceof Attendee attendee) {
                    attendeeDashboard(stage, attendee, Database.getEvents());
                }
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        });

        registerBtn.setOnAction(e -> showRegisterForm(stage));

        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Login");
        stage.show();
    }

    public static void showRegisterForm(Stage stage) {
        Label userLabel = new Label("New Username:");
        TextField userField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("Admin", "Organizer", "Attendee");
        Button submitBtn = new Button("Register");
        Label messageLabel = new Label();

        VBox layout = new VBox(10, userLabel, userField, passLabel, passField, new Label("Role:"), roleBox, submitBtn, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 40; -fx-background-color: #deb887;");

        // User registration
        submitBtn.setOnAction(e -> {
            String username = userField.getText().trim();
            String password = passField.getText().trim();
            String role = roleBox.getValue();

            // Validation
            if (username.isEmpty() || username.contains(" ")) {
                messageLabel.setText("Invalid username. Cannot be empty or contain spaces.");
                return;
            }
            if (password.isEmpty() || password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Z].*")) {
                messageLabel.setText("Invalid password. Must be at least 8 characters long and contain at least one number and one uppercase letter.");
                return;
            }
            if (role == null) {
                messageLabel.setText("Please select a role.");
                return;
            }

            // Check if the username already exists
            boolean userExists = Database.getUsers().stream()
                    .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));

            if (userExists) {
                messageLabel.setText("Username already exists.");
                return;
            }

            // Create the new user based on role
            User newUser = null;
            switch (role) {
                case "Admin":
                    newUser = new Admin(username, password, "1990-01-01", "9AM-5PM");
                    break;
                case "Organizer":
                    newUser = new Organizer(username, password, "1990-01-01", 0.0, new ArrayList<>());
                    break;
                case "Attendee":
                    newUser = new Attendee(username, password, "1990-01-01", 0.0, Gender.MALE, "N/A", new ArrayList<>());
                    break;
            }

            if (newUser != null) {
                Database.getUsers().add(newUser);
                messageLabel.setText("User registered successfully.");
                // Redirect to dashboard
                switch (role) {
                    case "Admin":
                        adminDashboard(stage, (Admin) newUser);
                        break;
                    case "Organizer":
                        organizerDashboard(stage, (Organizer) newUser, Database.getRooms());
                        break;
                    case "Attendee":
                        attendeeDashboard(stage, (Attendee) newUser, Database.getEvents());
                        break;
                }
            }
        });

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Register");
        stage.show();
    }

    public static void showMainMenu(Stage stage) {
        Label welcomeLabel = new Label("Welcome to our\n Event Management System");
        welcomeLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register");

        VBox layout = new VBox(20, welcomeLabel, loginBtn, registerBtn); // Add welcomeLabel here
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 40; -fx-background-color: #deb887;");

        loginBtn.setOnAction(e -> showLoginForm(stage));
        registerBtn.setOnAction(e -> showRegisterForm(stage));

        stage.setScene(new Scene(layout, 500, 300)); // Slightly larger for better appearance
        stage.setTitle("Welcome");
        stage.show();
    }

}