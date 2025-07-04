package DataBaseController;

import API.LoginApiV2;

import java.sql.*;

public class UserConnecting extends ConnectionData  implements SQLConnection {
    private final String INSERT_DATA ="INSERT INTO users VALUES (NULL,?, ?, ?, ?, ?)";
    private final String SIGN_IN ="SELECT * FROM users WHERE Username = ? AND Password = ?";
    private final String SIGN_INV2 =
            "SELECT users.*, riwayat.Target, riwayat.Tanggal, " +
                    "rgt.gula_darah,rgt.tekanan_darah  "+
                    "FROM users " +
                    "LEFT JOIN riwayat ON users.id = riwayat.id " +
                    "LEFT JOIN riwayat_gula_tekanan rgt ON users.id = rgt.id "+
                    "WHERE users.Username = ? AND users.Password = ? " +
                    "ORDER BY riwayat.Tanggal ASC" ;

    private final String INSERT_DARAH = "INSERT INTO riwayat_gula_tekanan VALUES (NULL,?,?,?)";


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
            System.out.println("\nTerjadi Kesalahan");
            System.err.println("Pesan Eror : " + e.getMessage() + "\n");
        }
    }


    /**
     * @deprecated method sudah tidak dapat di pakai,Gunakan method {@link #SIGN_INV2} sebagai gantinya
     * */
    @Deprecated
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


    //Yang Baru di buat
    public boolean SignInV2(String username,String password){

        try {
            Connection connection =  DriverManager.getConnection(getUserData());
            String login = getSIGN_INV2();
            PreparedStatement pstmt = connection.prepareStatement(login);

            pstmt.setString(1,username);
            pstmt.setString(2,password);

            ResultSet rs = pstmt.executeQuery();


            boolean isFirst = true;
            boolean hasData = false;

            int i = 1;
            while (rs.next()) {
                if (isFirst) {
                    LoginApiV2.ID = rs.getInt("id");
                    LoginApiV2.beratBadan = rs.getDouble("Berat_Badan");
                    LoginApiV2.tinggiBadan = rs.getDouble("Tinggi_Badan");
                    isFirst = false;
                }
                // Pastikan Target dan Tanggal tidak null
                double target = rs.getDouble("Target");
                String tanggal = rs.getString("Tanggal");
                double gula = rs.getDouble("gula_darah");
                double tekanan = rs.getDouble("tekanan_darah");
                if (gula != 0 && tekanan != 0) {
                    LoginApiV2.gulaDarah = gula;
                    LoginApiV2.TekananDarah = tekanan;
                }

                if (tanggal != null) {
                    LoginApiV2.Target.put(tanggal, target);
                    i++;
                }else {
                    LoginApiV2.Target.put("Null",0.0);
                }
                hasData = true;
            }

            pstmt.close();
            connection.close();

            return hasData;

        }catch (SQLException e){
            System.out.println("\nTerjadi Kesalahan");
            System.err.println("Pesan Eror : " + e.getMessage() + "\n");
        }
        return false;
    }

    //Untuk Menambah Data Gula Darah dan Tekanan
    public void InsertDarah(double GulaDarah, double TekananDarah){
        try {
        Connection connection =  DriverManager.getConnection(getUserData());
        PreparedStatement pstmt = connection.prepareStatement(INSERT_DARAH);
        pstmt.setDouble(1,LoginApiV2.ID);
        pstmt.setDouble(2,GulaDarah);
        pstmt.setDouble(3,TekananDarah);
        pstmt.executeUpdate();
        pstmt.close();
            System.out.println("Data darah berhasil disimpan!");

        } catch (SQLException e) {
            System.out.println("\nData darah tidak berhasil disimpan!");
            System.err.println("Pesan Eror : " + e.getMessage() + "\n");
        }
    }


    public String getINSERT_DATA(){
        return INSERT_DATA;
    }

    public String getSIGN_IN() {
        return SIGN_IN;
    }

    public String getSIGN_INV2() {
        return SIGN_INV2;
    }
    public String getINSERT_DARAH() {
        return INSERT_DARAH;
    }

}


