package Controller;

import API.LoginApiV2;

public class TimeLogger extends Thread {

    public TimeLogger() {
        setDaemon(true); // otomatis berhenti kalau program utama selesai
    }

    @Override
    public void run() {
        Runtime runtime = Runtime.getRuntime();

        while (true) {
            try {
                String timestamp = java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                long usedMemory = runtime.totalMemory() - runtime.freeMemory();
                String formattedMemory = String.format("%.2f MB", usedMemory / (1024.0 * 1024.0));

                System.out.println("[" + LoginApiV2.getUsername() + "] " + timestamp +
                        " | Memori digunakan: " + formattedMemory);

                Thread.sleep(1000); // setiap 1 detik
            } catch (InterruptedException e) {
                System.out.println("[LOGGER] Dihentikan.");
                break;
            }
        }
    }
}
