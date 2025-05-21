module com.example.emuyhealthcare {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.emuyhealthcare to javafx.fxml;
    opens Controller to javafx.fxml;
    opens DataBaseController to javafx.fxml;

    exports Main;
    exports Controller;
    exports DataBaseController;
}
