package eventmanagementsystem.dataClasses;
import java.util.Scanner;
public abstract class User {
    protected String username;
    protected String password;
    protected String dateOfBirth;

    // Constructor
    public User(String username, String password, String dateOfBirth) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and Setters for protected data
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    // Abstract Method: for subclasses to define their behaviors
    public abstract void displayProfile();

}

