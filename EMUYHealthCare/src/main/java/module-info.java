module com.example.emuyhealthcare {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    // Buka ke javafx.fxml jika ada file FXML di package tersebut
    opens com.example.emuyhealthcare to javafx.fxml;
    opens Main to javafx.graphics; // FIX utama: membuka akses untuk class utama JavaFX
    //opens Controller to javafx.fxml;
    //opens DatabaseController to javafx.fxml;

    // Ekspor package yang butuh diakses luar modul
    exports Main;
}
