module EMUYHealthCare {
    requires com.jfoenix;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    // Membuka package untuk FXML (agar bisa refleksi controller)
    opens API to javafx.fxml;
    opens chatBotEngine to javafx.fxml;
    opens Controller to javafx.fxml;
    opens DataBaseController to javafx.fxml;
    opens Testing to javafx.fxml;
    opens Utils to javafx.fxml;

    // Mengekspor Main agar JavaFX bisa instantiate Application-mu
    exports Main to javafx.graphics;
}
