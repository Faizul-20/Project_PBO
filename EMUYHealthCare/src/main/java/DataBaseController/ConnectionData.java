package DataBaseController;

public abstract class ConnectionData {
    private final String USER_DATA = "jdbc:sqlite:EMUYHealthCare/Database/User/UserData.db";
<<<<<<< HEAD
    private final String INSERT_DATA ="INSERT INTO \"users\" VALUES (NULL,?, ?, ?, ?, ?)";
=======
    private final String PENYAKIT_DATA = "jdbc:sqlite:EMUYHealthCare/Database/Engine Data/DaftarPenyakit.db";
>>>>>>> 23b1b4107e9c4adc70443b423b3bc0c48e536243

    public String getUserData() {
        return USER_DATA;
    }

<<<<<<< HEAD
    public String getINSERT_DATA(){
        return INSERT_DATA;
=======
    public String getPENYAKIT_DATA(){
        return PENYAKIT_DATA;
>>>>>>> 23b1b4107e9c4adc70443b423b3bc0c48e536243
    }
}
