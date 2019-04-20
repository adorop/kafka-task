package com.aliaksei.darapiyevich.runner;

import com.aliaksei.darapiyevich.configuration.TweetProperties;
import org.junit.Test;
import twitter4j.FilterQuery;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class TweetPropertiesToFilterQueryConverterTest {
    private static final String[] KEYWORDS = new String[]{"key", "words"};

    private final TweetPropertiesToFilterQueryConverter converter = new TweetPropertiesToFilterQueryConverter();

    @Test
    public void shouldAddKeywordsAsTrack() {
        TweetProperties tweetProperties = getProperties();
        FilterQuery filterQuery = converter.apply(tweetProperties);
        assertThat(filterQuery, equalTo(getExpectedQuery()));
    }

    private TweetProperties getProperties() {
        TweetProperties tweetProperties = new TweetProperties();
        tweetProperties.setKeywords(Arrays.asList(KEYWORDS));
        return tweetProperties;
    }

    private FilterQuery getExpectedQuery() {
        return new FilterQuery(KEYWORDS);
    }
}