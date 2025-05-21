package DataBaseController;

public abstract class ConnectionData {
    private final String USER_DATA = "jdbc:sqlite:EMUYHealthCare/Database/User/UserData.db";
    private final String INSERT_DATA ="INSERT INTO \"users\" VALUES (NULL,?, ?, ?, ?, ?)";


    public String getUserData() {
        return USER_DATA;
    }


    public String getINSERT_DATA() {
        return INSERT_DATA;

    }
}
