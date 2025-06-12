package API.Testing;

import java.util.ArrayList;
import java.util.List;

public class Riwayat {
    public double target;
    public String tanggal;
    public static List<Riwayat> riwayatList = new ArrayList<>();


    public Riwayat(double target, String tanggal) {
        this.target = target;
        this.tanggal = tanggal;
    }

    public static List<Riwayat> getRiwayatList() {
        return riwayatList;
    }

    @Override
    public String toString() {
        return "Target: " + target + ", Tanggal: " + tanggal;
    }
}

