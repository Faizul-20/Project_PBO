package com.example.main_project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class LineChartExample extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Contoh LineChart JavaFX");

        // Buat Axis X dan Y
        NumberAxis xAxis = new NumberAxis(2015,2021,1);
        xAxis.setLabel("Tahun");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Pendapatan");

        // Buat LineChart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Pertumbuhan Pendapatan");

        // Buat Series
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data Perusahaan");

        // Tambahkan Data ke Series
        series.getData().add(new XYChart.Data<>(2015, 500));
        series.getData().add(new XYChart.Data<>(2016, 700));
        series.getData().add(new XYChart.Data<>(2017, 1000));
        series.getData().add(new XYChart.Data<>(2021, 1200));

        // Tambahkan Series ke Chart
        lineChart.getData().add(series);

        // Tampilkan Chart
        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
