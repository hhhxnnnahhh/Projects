package eventmanagementsystem.servers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.*;

public class AdminChatServer extends Application {
    private static final int PORT = 12345;
    private static final List<PrintWriter> clientWriters = Collections.synchronizedList(new ArrayList<>());

    private TextArea chatArea;
    private TextField inputField;
    private PrintWriter out;

    public static void main(String[] args) {
        new Thread(AdminChatServer::startServer).start(); // Start server first
        launch(args); // Then launch JavaFX GUI
    }

    @Override
    public void start(Stage stage) {
        chatArea = new TextArea();
        chatArea.setEditable(false);

        inputField = new TextField();
        inputField.setOnAction(e -> sendMessage());

        VBox root = new VBox(10, chatArea, inputField);
        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Admin Chat Server");
        stage.show();

        connectToServer();
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty() && out != null) {
            out.println("Admin: " + message);
            inputField.clear();
        }
    }

    private void connectToServer() {
        new Thread(() -> {
            try (Socket socket = new Socket("localhost", PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out = new PrintWriter(socket.getOutputStream(), true);

                String line;
                while ((line = in.readLine()) != null) {
                    final String msg = line;
                    Platform.runLater(() -> chatArea.appendText(msg + "\n"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            clientWriters.add(out);

            String message;
            while ((message = in.readLine()) != null) {
                broadcast(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcast(String message) {
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }
}
