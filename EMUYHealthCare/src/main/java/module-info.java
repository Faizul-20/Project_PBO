module com.example.emuyhealthcare {
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires javafx.controls;

    // membuka akses ke FXML loader
    opens com.example.emuyhealthcare to javafx.fxml;
    opens Controller to javafx.fxml;
    opens DataBaseController to javafx.fxml;
    opens API.ChatbotTesting.Controller to javafx.fxml;

    exports API.ChatbotTesting.Controller;
    exports Main;
    exports Controller;
    exports DataBaseController;
    exports API.ChatbotTesting; // ⬅️ Tambahkan ini
}
