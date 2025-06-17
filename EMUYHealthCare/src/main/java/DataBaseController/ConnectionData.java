package DataBaseController;

import java.io.File;
import java.net.URISyntaxException;

public abstract class ConnectionData {
    private final String USER_DATA;
    private final String PENYAKIT_DATA;

    public ConnectionData() {
        String projectRoot = "";

        try {
            // Ambil lokasi file .jar lalu naik 1 folder
            File jarDir = new File(ConnectionData.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI());

            // Ambil parent dari /out/
            projectRoot = jarDir.getParentFile().getParent();
        } catch (URISyntaxException e) {
            System.out.println("Pesan Eror : " + e.getMessage());
            System.out.println("TIdak Bisa Menemukan Path yang ada");
        }

        USER_DATA = "jdbc:sqlite:" + projectRoot + File.separator +
                "Database" + File.separator + "User" + File.separator + "Users.db";

        PENYAKIT_DATA = "jdbc:sqlite:" + projectRoot + File.separator +
                "Database" + File.separator + "Engine Data" + File.separator + "healthcaree.db";
    }

    public String getUserData() {
        return USER_DATA;
    }

    public String getPENYAKIT_DATA() {
        return PENYAKIT_DATA;
    }
}
