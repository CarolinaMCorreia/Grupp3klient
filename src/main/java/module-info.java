module org.campusmolndal {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.campusmolndal to javafx.fxml;
    exports org.campusmolndal;
}
