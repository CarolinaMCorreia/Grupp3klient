module org.campusmolndal {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.json;

    exports org.campusmolndal;
    exports org.campusmolndal.controllers;
    exports org.campusmolndal.services;
    exports org.campusmolndal.models;

    opens org.campusmolndal to javafx.fxml;
    opens org.campusmolndal.controllers to javafx.fxml;
    opens org.campusmolndal.services to javafx.fxml;
    opens org.campusmolndal.models to java.fxml, com.fasterxml.jackson.databind;
}
