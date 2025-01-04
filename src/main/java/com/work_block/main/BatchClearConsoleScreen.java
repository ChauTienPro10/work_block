package com.work_block.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchClearConsoleScreen implements Runnable{

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        try {
            // Xóa toàn bộ nội dung của file log
            Files.write(Paths.get("logs/application.log"), new byte[0]);
            System.out.println("Log file has been cleared.");
        } catch (IOException e) {
            System.out.println("Error clearing log file: " + e.getMessage());
        }
    }

    @Scheduled(fixedRate = 3600000)
    public void deleteOldLogs() throws IOException {
        run();
    }
}
