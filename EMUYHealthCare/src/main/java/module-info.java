module com.example.emuyhealthcare {
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires javafx.controls;


    opens com.example.emuyhealthcare to javafx.fxml;
    opens Controller to javafx.fxml;
    opens DataBaseController to javafx.fxml;

    exports Main;
    exports Controller;
    exports DataBaseController;
    exports Utils;
    opens Utils to javafx.fxml;
    exports Testing;
}
