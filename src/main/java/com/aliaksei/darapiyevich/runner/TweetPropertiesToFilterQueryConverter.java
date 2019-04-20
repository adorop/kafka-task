package com.aliaksei.darapiyevich.runner;

import com.aliaksei.darapiyevich.configuration.TweetProperties;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;

import java.util.function.Function;

@Component
public class TweetPropertiesToFilterQueryConverter implements Function<TweetProperties, FilterQuery> {
    @Override
    public FilterQuery apply(TweetProperties tweetProperties) {
        return new FilterQuery(getKeyWords(tweetProperties));
    }

    private String[] getKeyWords(TweetProperties tweetProperties) {
        return tweetProperties.getKeywords().toArray(new String[0]);
    }
}
