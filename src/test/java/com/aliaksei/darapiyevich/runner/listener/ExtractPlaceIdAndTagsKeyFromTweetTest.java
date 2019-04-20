package com.aliaksei.darapiyevich.runner.listener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.HashtagEntity;
import twitter4j.Place;
import twitter4j.Status;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExtractPlaceIdAndTagsKeyFromTweetTest {
    private static final String FIRST_HASH_TAG = "first";
    private static final String SECOND_HASH_TAG = "second";
    private static final String[] HASH_TAGS = new String[]{FIRST_HASH_TAG, SECOND_HASH_TAG};

    private static final String PLACE_ID = "placeId";

    @Mock
    private Status tweet;

    private final ExtractPlaceIdAndTagsKeyFromTweet extractKey = new ExtractPlaceIdAndTagsKeyFromTweet();

    @Before
    public void setUp() throws Exception {
        initPlace();
    }

    private void initPlace() {
        Place place = mock(Place.class);
        when(tweet.getPlace()).thenReturn(place);
        when(place.getId()).thenReturn(PLACE_ID);
    }

    @Test
    public void shouldReturnPlaceIdOnlyWhenGivenTweetDoesNotHaveHashTags() {
        givenTweetDoesNotHaveHashTags();
        String result = extractKey.apply(tweet);
        assertThat(result, equalTo(PLACE_ID));
    }

    private void givenTweetDoesNotHaveHashTags() {
        when(tweet.getHashtagEntities()).thenReturn(null);
    }

    @Test
    public void shouldReturnPlaceIdConcatenatedWithHashTagsWhenPresent() {
        givenTweetHasHashTags();
        String result = extractKey.apply(tweet);
        assertThat(result, equalTo(PLACE_ID + FIRST_HASH_TAG + SECOND_HASH_TAG));
    }

    private void givenTweetHasHashTags() {
        HashtagEntity[] hashTagEntities = getHashTagEntities();
        when(tweet.getHashtagEntities()).thenReturn(hashTagEntities);
    }

    private HashtagEntity[] getHashTagEntities() {
        return Arrays.stream(HASH_TAGS)
                .map(this::toEntity)
                .toArray(HashtagEntity[]::new);
    }

    private HashtagEntity toEntity(String text) {
        HashtagEntity hashtagEntity = mock(HashtagEntity.class);
        when(hashtagEntity.getText()).thenReturn(text);
        return hashtagEntity;
    }
}