package API.Testing;

import DataBaseController.ConnectionData;
import DataBaseController.SQLConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserConnectingtest extends ConnectionData implements SQLConnection {
    private final String INSERT_DATA ="INSERT INTO users VALUES (NULL,?, ?, ?, ?, ?)";
    private final String SIGN_IN =
            "SELECT users.*, riwayat.Target, riwayat.Tanggal " +
                    "FROM users LEFT JOIN riwayat ON users.id = riwayat.id " +
                    "WHERE users.Username = ? AND users.Password = ? " +
                    "ORDER BY riwayat.Tanggal ASC";

    @Override
    public void ConnectToDatabase(String Url) {
        try {
            Connection connection = DriverManager.getConnection(Url);
            System.out.println("Data Berhasil Terhubung!!");
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Data Tidak Terhubung Periksa Koneksi Anda");
            System.out.println("Pesan Eror : " + e.getMessage());
        }
    }



    public boolean SignIn(String username,String password){

        try {
            Connection connection =  DriverManager.getConnection(getUserData());
            String login = getSIGN_IN();
            PreparedStatement pstmt = connection.prepareStatement(login);

            pstmt.setString(1,username);
            pstmt.setString(2,password);

            ResultSet rs = pstmt.executeQuery();


            boolean isFirst = true;
            boolean hasData = false;

            int i = 1;
            while (rs.next()) {
                if (isFirst) {
                    TestingDb1.beratBadan = rs.getDouble("Berat_Badan");
                    TestingDb1.tinggiBadan = rs.getDouble("Tinggi_Badan");
                    isFirst = false;
                }
                // Pastikan Target dan Tanggal tidak null
                double target = rs.getDouble("Target");
                String tanggal = rs.getString("Tanggal");
                System.out.println("Ambil data " + i +" : Target = " + target + ", Tanggal = " + tanggal);
                if (tanggal != null) {
                    TestingDb1.Target.put(tanggal, target);
                    i++;
                }
                hasData = true;
            }

            pstmt.close();
            connection.close();

            return hasData;

        }catch (SQLException e){
            System.out.println("Pesan Eror : " + e.getMessage());
        }
        return false;
    }

    public String getINSERT_DATA(){
        return INSERT_DATA;
    }

    public String getSIGN_IN() {
        return SIGN_IN;
    }

}


