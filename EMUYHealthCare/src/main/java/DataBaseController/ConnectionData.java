package DataBaseController;

public abstract class ConnectionData {
    private final String USER_DATA = "jdbc:sqlite:EMUYHealthCare/Database/User/Users.db";
    private final String PENYAKIT_DATA = "jdbc:sqlite:EMUYHealthCare/Database/Engine Data/DaftarPenyakit.db";
    private final String INSERT_DATA ="INSERT INTO users VALUES (NULL,?, ?, ?, ?, ?)";
    private final String SIGN_IN ="SELECT * FROM users WHERE Username = ? AND Password = ?";


    public String getUserData() {
        return USER_DATA;
    }

    public String getPENYAKIT_DATA() {
        return PENYAKIT_DATA;
    }

    public String getINSERT_DATA(){
        return INSERT_DATA;
    }

    public String getSIGN_IN() {
        return SIGN_IN;
    }
}
