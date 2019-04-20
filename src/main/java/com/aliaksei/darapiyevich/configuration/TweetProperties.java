package com.aliaksei.darapiyevich.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "kafka-task.tweet")
@Data
public class TweetProperties {
    private List<String> keywords;
    private String languageCode;
    private String countryCode;
}
