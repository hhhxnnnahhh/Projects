package eventmanagementsystem.servers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class OrganizerChatClient extends Application {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    private TextArea chatArea;
    private TextField inputField;
    private PrintWriter out;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        chatArea = new TextArea();
        chatArea.setEditable(false);

        inputField = new TextField();
        inputField.setOnAction(e -> sendMessage());

        VBox root = new VBox(10, chatArea, inputField);
        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Organizer Chat Client");
        stage.show();

        connectToServer();
    }

    private void connectToServer() {
        new Thread(() -> {
            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out = new PrintWriter(socket.getOutputStream(), true);

                String line;
                while ((line = in.readLine()) != null) {
                    final String msg = line;
                    javafx.application.Platform.runLater(() -> chatArea.appendText(msg + "\n"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty() && out != null) {
            out.println("Organizer: " + message);
            inputField.clear();
        }
    }
}
