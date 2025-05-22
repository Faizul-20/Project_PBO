package DataBaseController;

public abstract class ConnectionData {
    private final String USER_DATA = "jdbc:sqlite:EMUYHealthCare/Database/User/UserData.db";



    public String getUserData() {
        return USER_DATA;
    }



}
