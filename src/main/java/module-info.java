module emsFX {
    requires javafx.controls;
    requires javafx.fxml;

    exports eventmanagementsystem;
    opens eventmanagementsystem to javafx.fxml;
}
