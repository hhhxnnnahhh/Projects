package eventmanagementsystem;

import eventmanagementsystem.dataClasses.*;
import eventmanagementsystem.services.Display;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Database.initializeData();
        Display.showMainMenu(stage);

    }
}