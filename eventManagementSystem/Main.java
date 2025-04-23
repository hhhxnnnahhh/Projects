import eventmanagementsystem.dataClasses.*;
import eventmanagementsystem.services.Add;
import eventmanagementsystem.services.Display;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import static eventmanagementsystem.dataClasses.Database.rooms;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database.initializeData();
        Database.loadData();
        User loggedInUser = null;
        Database database=new Database();

        // Main Dashboard
        Display.clearConsole();
        while (true) {
            System.out.println("\n=== Event Management System ===");
            System.out.println("1. Register as new user");
            System.out.println("2. Login as an existing user");
            System.out.println("3. Exit");
            int choice;
            while (true) {
                System.out.print("Choose an option: ");
                try {
                    choice = Integer.parseInt(scanner.nextLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter 1, 2, or 3.");
                }
            }

            if (choice == 1) {
                loggedInUser = registerUser(scanner);
                if (loggedInUser != null) {
                    Display.clearConsole();
                    System.out.println("Welcome, " + loggedInUser.getUsername() + "!\n");
                    loggedInUser.displayProfile();
                    // Navigate to dashboard
                    pause(scanner);

                    Display.clearConsole();
                    if (loggedInUser instanceof Admin) {
                        Display.adminDashboard((Admin) loggedInUser, scanner, Database.users, Database.events, Database.rooms);
                    } else if (loggedInUser instanceof Organizer) {
                        Display.organizerDashboard((Organizer) loggedInUser, Database.rooms);
                    } else if (loggedInUser instanceof Attendee) {
                        Display.attendeeDashboard(scanner, (Attendee) loggedInUser, Database.events);
                    }
                }
            } else if (choice == 2) {
                loggedInUser = loginUser(scanner);
                if (loggedInUser != null) {
                    Display.clearConsole();
                    if (loggedInUser instanceof Admin) {
                        Display.adminDashboard((Admin) loggedInUser, scanner, Database.users, Database.events, Database.rooms);
                    } else if (loggedInUser instanceof Organizer) {
                        Display.organizerDashboard((Organizer) loggedInUser, Database.rooms);
                    } else if (loggedInUser instanceof Attendee) {
                        Display.attendeeDashboard(scanner, (Attendee) loggedInUser, Database.events);
                    } else {
                        System.out.println("Unrecognized user role.");
                    }
                } else {
                    System.out.println("Login failed. Please try again.");
                }
            } else if (choice == 3) {
                System.out.println("Exiting... See you again soon!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }

    // User Registration
    public static User registerUser(Scanner scanner) {
        // username: can't have spaces or be left empty
        String username = "";
        while (true) {
            System.out.print("Enter Username: ");
            username = scanner.nextLine();

            if (username.isEmpty() || username.contains(" ")) {
                System.out.println("Invalid username. Cannot be empty or contain spaces. Please try again.");
            } else {
                break;
            }
        }
        // pw: cannot be left empty
        // must have minimum length, contain at least one number and one uppercase letter
        String password = "";
        while (true) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();

            if (password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Z].*")) {
                System.out.println("Password must be at least 8 characters long and contain at least one number and one uppercase letter. Please try again.");
            } else {
                break;
            }
        }
        // date of birth: in the format (YYYY-MM-DD)
        String dateOfBirth = "";
        while (true) {
            System.out.print("Enter Date of Birth in Numbers (Year-Month-Day): ");
            dateOfBirth = scanner.nextLine();

            if (!dateOfBirth.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println("Invalid date format. Please enter the date in the format (YYYY-MM-DD).");
            } else {
                LocalDate birthDate = LocalDate.parse(dateOfBirth);
                if (birthDate.isAfter(LocalDate.now())) {
                    System.out.println("Date of birth cannot be in the future. Please try again.");
                } else {
                    break;
                }
            }
        }
        Display.clearConsole();
        System.out.println("Select Role:");
        System.out.println("1. Admin");
        System.out.println("2. Organizer");
        System.out.println("3. Attendee");
        System.out.print("Choose role: ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();

        User newUser = null;
        switch (roleChoice) {
            case 1:
                newUser = new Admin(username, password, dateOfBirth);
                break;
            case 2:
                // balance must be numeric and positive, and not empty
                double orgBalance = 0;
                while (true) {
                    System.out.print("Enter Balance: ");
                    String balanceInput = scanner.nextLine();

                    boolean isValid = true;

                    if (balanceInput.isEmpty()) {
                        isValid = false;
                    } else {
                        try {
                            orgBalance = Double.parseDouble(balanceInput);
                            if (orgBalance < 0) {
                                isValid = false;
                            }
                        } catch (NumberFormatException e) {
                            isValid = false;
                        }
                    }
                    if (isValid) {
                        break;
                    } else {
                        System.out.println("Invalid input for balance. Please try again.");
                    }
                }
                Wallet orgWallet = new Wallet(orgBalance);
                newUser = new Organizer(username, password, dateOfBirth, orgBalance, new ArrayList<>());

                break;
            case 3:
                // balance must be numeric and positive, and not empty
                double attendeeBalance = 0;
                while (true) {
                    System.out.print("Enter Balance: ");
                    String balanceInput = scanner.nextLine();

                    boolean isValid = true;

                    if (balanceInput.isEmpty()) {
                        isValid = false;
                    } else {
                        try {
                            attendeeBalance = Double.parseDouble(balanceInput);
                            if (attendeeBalance < 0) {
                                isValid = false;
                            }
                        } catch (NumberFormatException e) {
                            isValid = false;
                        }
                    }
                    if (isValid) {
                        break;
                    } else {
                        System.out.println("Invalid input for balance. Please try again.");
                    }
                }
                Wallet attendeeWallet = new Wallet(attendeeBalance);

                Gender gender = null;
                while (true) {
                    System.out.print("Enter Gender (MALE/FEMALE): ");
                    String genderInput = scanner.nextLine();
                    try {
                        gender = Gender.valueOf(genderInput.toUpperCase());
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid gender. Please enter MALE or FEMALE.");
                    }
                }

                System.out.print("Enter Address: ");
                String address = scanner.nextLine();

                System.out.print("Enter Interests (comma-separated): ");
                String interestsInput = scanner.nextLine();
                List<String> interests = List.of(interestsInput.split(",\\s*"));

                newUser = new Attendee(username, password, dateOfBirth, attendeeBalance, gender, address, interests);
                break;
            default:
                System.out.println("Invalid role. Registration failed.");
                return null;
        }

        Database.users.add(newUser);
        System.out.println("User registered successfully as " + newUser.getClass().getSimpleName() + ".");
        return newUser;
    }

    // User Login
    public static User loginUser(Scanner scanner) {
        System.out.println("\n=== Login ===");

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : Database.users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
                return user;
            }
        }
        System.out.println("Invalid credentials.");
        return null;
    }


    // Pause Method to
    private static void pause(Scanner scanner) {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
