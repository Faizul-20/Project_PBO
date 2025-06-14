package API.TestingDb;

public abstract class ConnectionData{
    private final String PENYAKIT_DATA = "jdbc:sqlite:EMUYHealthCare/Database/Engine Data/healthCaree.db";

    public String getPENYAKIT_DATA() {
        return PENYAKIT_DATA;
    }
}
