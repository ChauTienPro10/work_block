package com.work_block.main.enums;

import lombok.Getter;

@Getter
public enum CSV_PATHS {

    USER_GITHUB_INFO_CSV_PATH("src/main/resources/csv_data/github_user_info.csv"),

    ;

    private String path;
    CSV_PATHS(String path){
        this.path = path;
    }

}
