package DataBaseController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserConnecting extends ConnectionData implements SQLConnection {
    private final String INSERT_DATA ="INSERT INTO \"users\" VALUES (NULL,?, ?, ?, ?, ?)";

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



    public void InsertDataUser(String username, String password, int tb, int bb, String ttl) {
        try {
            Connection connection = DriverManager.getConnection(getUserData());
            String query = getINSERT_DATA();
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, tb);
            pstmt.setInt(4, bb);
            pstmt.setString(5, ttl);

            pstmt.executeUpdate();
            System.out.println("Data pengguna berhasil disimpan!");

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Pesan Erorr : " + e.getMessage());
        }
    }

    public String getINSERT_DATA() {
        return INSERT_DATA;
    }
}


