package com.aliaksei.darapiyevich.runner.listener;

import com.aliaksei.darapiyevich.configuration.TweetProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.Place;
import twitter4j.Status;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IsTweetForPublishingToKafkaPredicateTest {
    private static final String LANGUAGE_CODE = "languageCode";
    private static final String COUNTRY_CODE = "countryCode";

    private final TweetProperties tweetProperties = new TweetProperties();
    @Mock
    private Status tweet;
    @Mock
    private Place place;

    private IsTweetForPublishingToKafkaPredicate predicate;

    @Before
    public void setUp() throws Exception {
        initProperties();
        initPlace();
        predicate = new IsTweetForPublishingToKafkaPredicate(tweetProperties);
    }

    private void initProperties() {
        tweetProperties.setLanguageCode(LANGUAGE_CODE);
        tweetProperties.setCountryCode(COUNTRY_CODE);
    }

    private void initPlace() {
        when(tweet.getPlace()).thenReturn(place);
        when(place.getCountryCode()).thenReturn(COUNTRY_CODE);
    }

    @Test
    public void shouldReturnFalseWhenPlaceOfTweetIsNull() {
        placeOfGivenTweetIsNull();
        assertFalse(predicate.test(tweet));
    }

    private void placeOfGivenTweetIsNull() {
        when(tweet.getPlace()).thenReturn(null);
    }

    @Test
    public void shouldReturnFalseWhenCountryCodeOfGivenTweetIsNotEqualToDefinedOne() {
        countryCodeOfGivenTweetIsNotDefinedOne();
        assertFalse(predicate.test(tweet));
    }

    private void countryCodeOfGivenTweetIsNotDefinedOne() {
        when(place.getCountryCode()).thenReturn("another country code");
    }

    @Test
    public void shouldReturnFalseWhenLanguageOfGivenTweetIsNotEqualToDefinedOne() {
        languageOfGivenTweetIsNotDefinedOne();
        assertFalse(predicate.test(tweet));
    }

    private void languageOfGivenTweetIsNotDefinedOne() {
        when(tweet.getLang()).thenReturn("another lang");
    }

    @Test
    public void shouldReturnTrueWhenCountryCodeAndLanguageAreEqualToDefinedOnes() {
        countryCodeAndLanguageAreEqualToDefinedOnes();
        assertTrue(predicate.test(tweet));
    }

    private void countryCodeAndLanguageAreEqualToDefinedOnes() {
        when(tweet.getLang()).thenReturn(LANGUAGE_CODE);
    }
}