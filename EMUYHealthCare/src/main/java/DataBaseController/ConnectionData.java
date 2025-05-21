package DataBaseController;

public abstract class ConnectionData {
    private final String USER_DATA = "jdbc:sqlite:EMUYHealthCare/Database/User/UserData.db";
    private final String PENYAKIT_DATA = "jdbc:sqlite:EMUYHealthCare/Database/Engine Data/DaftarPenyakit.db";

    public String getUserData() {
        return USER_DATA;
    }

    public String getPENYAKIT_DATA(){
        return PENYAKIT_DATA;
    }
}
