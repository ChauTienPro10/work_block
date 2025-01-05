package com.work_block.main.enums;

public enum GITHUB_URI_API {

    GET_USER_INFO("https://api.github.com/user", "Thông tin cơ bản về người dùng"),
    GET_USER_EMAILS("https://api.github.com/user/emails", "Email của người dùng"),
    GET_USER_REPOS("https://api.github.com/user/repos", "Danh sách kho lưu trữ của người dùng"),
    USER_ORGS("https://api.github.com/user/orgs", "Danh sách tổ chức của người dùng"),
    USER_FOLLOWERS("https://api.github.com/users/{username}/followers", "Danh sách người theo dõi"),
    USER_FOLLOWING("https://api.github.com/users/{username}/following", "Danh sách người dùng đang theo dõi"),
    USER_GISTS("https://api.github.com/users/{username}/gists", "Danh sách Gists của người dùng"),
    USER_EVENTS("https://api.github.com/users/{username}/events", "Danh sách sự kiện của người dùng"),
    USER_KEYS("https://api.github.com/user/keys", "Danh sách khóa SSH của người dùng"),
    NOTIFICATIONS("https://api.github.com/notifications", "Thông tin thông báo của người dùng"),
    REPO_PULLS("https://api.github.com/repos/{owner}/{repo}/pulls", "Danh sách Pull Requests"),
    REPO_ISSUES("https://api.github.com/repos/{owner}/{repo}/issues", "Danh sách Issues"),
    REPO_COMMITS("https://api.github.com/repos/{owner}/{repo}/commits", "Danh sách Commits"),
    REPO_BRANCHES("https://api.github.com/repos/{owner}/{repo}/branches", "Danh sách Branches"),
    GET_TOKEN_INFO("https://api.github.com/applications/", "Lấy các thông tin liên quan đến access token"),
    INVITE_COLLABORATIOR("https://api.github.com/repos/%s/%s/collaborators/%s", "mời người thàm gia phát triển"),
    GET_COLLABORATIORS("https://api.github.com/repos/%s/%s/collaborators", "lấy danh sách người tham gia"),

    ;

    private final String uri;
    private final String description;

    GITHUB_URI_API(String uri, String description) {
        this.uri = uri;
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public String getDescription() {
        return description;
    }
}
