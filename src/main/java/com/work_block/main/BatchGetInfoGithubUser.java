package com.work_block.main;

import com.opencsv.CSVWriter;
import com.work_block.main.dtos.response.github.GithubUserInfo;
import com.work_block.main.enums.CSV_PATHS;
import com.work_block.main.service.GitHubApiService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchGetInfoGithubUser implements Runnable{
    @Autowired
    private GitHubApiService gitHubApiService;

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        String accesToken = "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";
        String path = CSV_PATHS.USER_GITHUB_INFO_CSV_PATH.getPath();

        // Kiểm tra và tạo file nếu chưa tồn tại
        File file = new File(path);
        try {
            if (file.exists()) {
                System.out.println("File CSV đã tồn tại: " + file.getAbsolutePath());
                file.delete();
            }

            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                // Tạo thư mục cha nếu chưa tồn tại
                if (parentDir.mkdirs()) {
                    System.out.println("Thư mục đã được tạo: " + parentDir.getAbsolutePath());
                } else {
                    System.err.println("Không thể tạo thư mục: " + parentDir.getAbsolutePath());
                    return;
                }
            }
            // Tạo file rỗng
            if (file.createNewFile()) {
                System.out.println("File CSV đã được tạo: " + file.getAbsolutePath());
            }


            // Header CSV
            String[] header = {
                    "GET_REPORT_QUEUE_ID",
                    "ACCOUNT_ID",
                    "ETL_AGENCY_ID",
                    "ETL_INPUT_PLATFORM_AUTH_ID",
                    "ADVERTISER_ID",
                    "RANGE_FROM",
                    "RANGE_TO",
                    "REPORT_TYPE",
                    "STATUS",
                    "QUEUE_TYPE",
                    "UPDATE_MICRO_TIME",
                    "CREATED_DATE",
                    "DELETED",
                    "CREATED_BY",
                    "CREATED_AT",
                    "UPDATED_BY",
                    "UPDATED_AT"
            };

            try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) { // true để ghi thêm nếu file đã tồn tại
                // Ghi header nếu file rỗng
                if (file.length() == 0) {
                    writer.writeNext(header);
                }

                GithubUserInfo data = gitHubApiService.getGitHubUser(accesToken);

                // Dữ liệu mẫu
                String[] sampleData = {
                        "1", "101", "201", "301", "adv123", "2023-01-01", "2023-01-31", "1", "0", "1", "1900-01-01 00:00:00.000000", "2023-01-01", "0", "1", "2023-01-01 12:00:00", "1", "2023-01-01 12:00:00"

                };

                // Ghi dữ liệu mẫu
                writer.writeNext(sampleData);
                System.out.println("Dữ liệu đã được ghi vào file CSV: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi xử lý file CSV: " + e.getMessage());
        }

    }

    @Scheduled(fixedRate = 50000)
    public void scheduleTask() {
        log.info("*** Start batch get info of github user ***");
        run();
        log.info("*** Finish batch get info of github user ***");
    }

    public String createUriInfoUserCsvPath(){
        return null;
    }
}
