package me.rhys.uploader.config;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConfigValues {
    private String ftpIP, ftpUsername, ftpPassword;
    private int ftpPort;
}
