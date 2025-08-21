package com.seed.global.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file.upload.s3")
@Data
public class S3Properties {
    private String bucket;
    private String folder;
    private String region;
}
