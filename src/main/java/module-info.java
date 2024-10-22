module org.campusmolndal {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.json;

    opens org.campusmolndal to javafx.fxml;
    exports org.campusmolndal;
    exports org.campusmolndal.controllers;
    opens org.campusmolndal.controllers to javafx.fxml;
    exports org.campusmolndal.services;
    opens org.campusmolndal.services to javafx.fxml;
    exports org.campusmolndal.models;
    opens org.campusmolndal.models to java.fxml;
}
