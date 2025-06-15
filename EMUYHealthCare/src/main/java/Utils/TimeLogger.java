package Utils;

import API.LoginApiV2;

public class TimeLogger extends Thread {

    public TimeLogger() {
        setDaemon(true); // otomatis berhenti kalau program utama selesai
    }

    @Override
    public void run() {
        Runtime runtime = Runtime.getRuntime();
        final int MB = 1024 * 1024;

        while (true) {
            try {
                String timestamp = java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                long totalMemory = runtime.totalMemory() / MB;
                long freeMemory = runtime.freeMemory() / MB;
                long usedMemory = totalMemory - freeMemory;
                long maxMemory = runtime.maxMemory() / MB;

                String username = LoginApiV2.getUsername();
                String info = String.format("[%s] %s | Used Memori : %d MB / Total Memory %d MB / Max Memory %d MB",
                        username != null ? username :"[EMUYLOG] UNKNOWN",
                        timestamp,
                        usedMemory, totalMemory, maxMemory);

                if (usedMemory > 200) {
                    System.out.println("[EMUYLOG] " + info);
                } else {
                    System.out.println(info);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("[LOGGER] Dihentikan.");
                break;
            }
        }
    }
}
