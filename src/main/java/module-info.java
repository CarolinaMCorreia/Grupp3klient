module org.campusmolndal {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.campusmolndal to javafx.fxml;
    exports org.campusmolndal;
    exports org.campusmolndal.controllers;
    opens org.campusmolndal.controllers to javafx.fxml;
    exports org.campusmolndal.services;
    opens org.campusmolndal.services to javafx.fxml;
}
