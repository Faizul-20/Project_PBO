package DataBaseController;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserConnecting extends ConnectionData implements SQLConnection {
    private final String INSERT_DATA ="INSERT INTO users VALUES (NULL,?, ?, ?, ?, ?)";
    private final String SIGN_IN ="SELECT * FROM users WHERE Username = ? AND Password = ?";

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

            String checkQuery = "SELECT * FROM users WHERE Username = ?";
            PreparedStatement CheckStmt = connection.prepareStatement(checkQuery);
            CheckStmt.setString(1,username);
            ResultSet CheckResult = CheckStmt.executeQuery();

            if (CheckResult.next()){
                System.out.println("Username '" + username + "' sudah terdaftar. Gunakan username lain!");
                CheckResult.close();
                CheckStmt.close();
                connection.close();
            }

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

    public boolean SignIn(String username,String password){

        try {
            Connection connection =  DriverManager.getConnection(getUserData());
            String login = getSIGN_IN();
            PreparedStatement pstmt = connection.prepareStatement(login);

            pstmt.setString(1,username);
            pstmt.setString(2,password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next() ) {
                return true;
            }

            pstmt.close();
            connection.close();

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


