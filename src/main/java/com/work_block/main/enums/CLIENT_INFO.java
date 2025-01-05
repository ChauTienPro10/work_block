package com.work_block.main.enums;

import com.work_block.main.configuration.ConfigReader;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum CLIENT_INFO {

    CLIENT_ID(ConfigReader.getProperty("spring.security.oauth2.client.registration.github.client-id")),
    CLIENT_SECRET_KEY(ConfigReader.getProperty("spring.security.oauth2.client.registration.github.client-secret"));
    private String value;
    CLIENT_INFO(String value){
        this.value = value;
    }

}
