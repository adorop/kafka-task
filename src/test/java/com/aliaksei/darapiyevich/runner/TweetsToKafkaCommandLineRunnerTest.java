package com.aliaksei.darapiyevich.runner;

import com.aliaksei.darapiyevich.configuration.TweetProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;

import java.util.function.Function;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TweetsToKafkaCommandLineRunnerTest {
    private final TweetProperties tweetProperties = new TweetProperties();
    @Mock
    private Function<TweetProperties, FilterQuery> propertiesToFilterQueryConverter;
    @Mock
    private TwitterStream twitterStream;

    private TweetsToKafkaCommandLineRunner runner;

    @Before
    public void setUp() throws Exception {
        runner = new TweetsToKafkaCommandLineRunner(tweetProperties, propertiesToFilterQueryConverter, twitterStream);
    }

    @Test
    public void shouldStartFilterStream() throws Exception {
        FilterQuery filterQuery = getFilterQuery();
        runner.run();
        verify(twitterStream).filter(filterQuery);
    }

    private FilterQuery getFilterQuery() {
        FilterQuery filterQuery = new FilterQuery();
        when(propertiesToFilterQueryConverter.apply(tweetProperties)).thenReturn(filterQuery);
        return filterQuery;
    }
}