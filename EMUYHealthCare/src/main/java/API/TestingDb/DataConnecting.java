package main.java.API.TestingDb;

import DataBaseController.SQLConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnecting extends DataBaseController.ConnectionData implements SQLConnection {
    @Override
    public void ConnectToDatabase(String Url) {
        try {
            Connection connection = DriverManager.getConnection(Url);
            System.out.println("Data Berhasil Terhubung!!");
        } catch (SQLException e) {
            System.out.println("Data Tidak Berhasil Terhubung!!");
            System.out.println("Pesan Eror : " + e.getMessage());
        }
    }
}
