module com.example.emuyhealthcare {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.main_project.GUI_Example to javafx.fxml;
    exports com.example.main_project.GUI_Example;
}