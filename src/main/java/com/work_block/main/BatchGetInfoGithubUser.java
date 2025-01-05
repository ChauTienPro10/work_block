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
                    "login", "id", "node_id", "avatar_url", "url", "html_url", "followers_url", "following_url",
                    "gists_url", "starred_url", "subscriptions_url", "organizations_url", "repos_url", "events_url",
                    "received_events_url", "type", "user_view_type", "site_admin", "name", "company", "blog", "location",
                    "email", "hireable", "bio", "twitter_username", "notification_email", "public_repos", "public_gists",
                    "followers", "following", "created_at", "updated_at", "private_gists", "total_private_repos",
                    "owned_private_repos", "disk_usage", "collaborators", "two_factor_authentication", "plan_name",
                    "plan_space", "plan_collaborators", "plan_private_repos"
            };

            try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) { // true để ghi thêm nếu file đã tồn tại
                // Ghi header nếu file rỗng
                if (file.length() == 0) {
                    writer.writeNext(header);
                }

                GithubUserInfo data = gitHubApiService.getGitHubUser(accesToken);

                // Dữ liệu mẫu
                String[] inputData = {
                        data.getLogin(),                    // login
                        String.valueOf(data.getId()),       // id
                        data.getNode_id(),                  // node_id
                        data.getAvatar_url(),               // avatar_url
                        data.getUrl(),                      // url
                        data.getHtml_url(),                 // html_url
                        data.getFollowers_url(),            // followers_url
                        data.getFollowing_url(),            // following_url
                        data.getGists_url(),                // gists_url
                        data.getStarred_url(),              // starred_url
                        data.getSubscriptions_url(),        // subscriptions_url
                        data.getOrganizations_url(),        // organizations_url
                        data.getRepos_url(),                // repos_url
                        data.getEvents_url(),               // events_url
                        data.getReceived_events_url(),      // received_events_url
                        data.getType(),                     // type
                        data.getUser_view_type(),           // user_view_type
                        String.valueOf(data.isSite_admin()), // site_admin (boolean -> String)
                        data.getName(),                     // name
                        data.getCompany(),                  // company
                        data.getBlog(),                     // blog
                        data.getLocation(),                 // location
                        data.getEmail(),                    // email
                        data.getHireable(),                 // hireable
                        data.getBio(),                      // bio
                        data.getTwitter_username(),         // twitter_username
                        data.getNotification_email(),       // notification_email
                        String.valueOf(data.getPublic_repos()),  // public_repos
                        String.valueOf(data.getPublic_gists()),  // public_gists
                        String.valueOf(data.getFollowers()), // followers
                        String.valueOf(data.getFollowing()), // following
                        data.getCreated_at(),               // created_at
                        data.getUpdated_at(),               // updated_at
                        String.valueOf(data.getPrivate_gists()), // private_gists
                        String.valueOf(data.getTotal_private_repos()), // total_private_repos
                        String.valueOf(data.getOwned_private_repos()), // owned_private_repos
                        String.valueOf(data.getDisk_usage()), // disk_usage
                        String.valueOf(data.getCollaborators()), // collaborators
                        String.valueOf(data.isTwo_factor_authentication()), // two_factor_authentication
                        data.getPlan() != null ? data.getPlan().getName() : "", // plan_name
                        String.valueOf(data.getPlan() != null ? data.getPlan().getSpace() : 0), // plan_space
                        String.valueOf(data.getPlan() != null ? data.getPlan().getCollaborators() : 0), // plan_collaborators
                        String.valueOf(data.getPlan() != null ? data.getPlan().getPrivate_repos() : 0) // plan_private_repos
                };

                // Ghi dữ liệu mẫu
                writer.writeNext(inputData);
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
