package com.aliaksei.darapiyevich.runner.listener;

import org.springframework.stereotype.Component;
import twitter4j.HashtagEntity;
import twitter4j.Status;

import java.util.Arrays;
import java.util.function.Function;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

@Component
public class ExtractPlaceIdAndTagsKeyFromTweet implements Function<Status, String> {
    @Override
    public String apply(Status tweet) {
        return tweet.getPlace().getId() + extractHashTags(tweet);
    }

    private String extractHashTags(Status tweet) {
        return ofNullable(tweet.getHashtagEntities())
                .map(this::concatenateText)
                .orElse("");

    }

    private String concatenateText(HashtagEntity[] hashtagEntities) {
        return Arrays.stream(hashtagEntities)
                .map(HashtagEntity::getText)
                .collect(joining());
    }
}
