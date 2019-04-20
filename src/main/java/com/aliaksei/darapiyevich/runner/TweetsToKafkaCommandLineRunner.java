package com.aliaksei.darapiyevich.runner;

import com.aliaksei.darapiyevich.configuration.TweetProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;

import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class TweetsToKafkaCommandLineRunner implements CommandLineRunner {
    private final TweetProperties tweetProperties;
    private final Function<TweetProperties, FilterQuery> tweetPropertiesToFilterQueryConverter;
    private final TwitterStream twitterStream;

    @Override
    public void run(String... args) throws Exception {
        FilterQuery filterQuery = tweetPropertiesToFilterQueryConverter.apply(tweetProperties);
        twitterStream.filter(filterQuery);
    }
}
