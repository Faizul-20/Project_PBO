package com.example.main_project.GUI_Example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("com/example/main_project/hello-view.fxml"));
            primaryStage.setTitle("Latihan");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("FXML gagal dimuat: " + e.getMessage());
        }
    }
}
