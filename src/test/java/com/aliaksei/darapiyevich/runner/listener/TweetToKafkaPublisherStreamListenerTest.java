package com.aliaksei.darapiyevich.runner.listener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import twitter4j.Status;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TweetToKafkaPublisherStreamListenerTest {
    private static final String TOPIC = "topic";
    private static final String KEY = "key";

    @Mock
    private Status tweet;
    @Mock
    private Predicate<Status> isForPublishing;
    @Mock
    private Function<Status, String> extractKeyFromTweet;
    @Mock
    private KafkaTemplate<String, Status> kafkaTemplate;
    @Mock
    private ListenableFuture<SendResult<String, Status>> sendResult;
    @Mock
    private SendMessageResultCallbackFactory sendMessageResultCallbackFactory;
    @Mock
    private ListenableFutureCallback<SendResult<String, Status>> resultCallback;

    private TweetToKafkaPublisherStreamListener listener;

    @Before
    public void setUp() throws Exception {
        initMessageKey();
        initSendResult();
        initResultCallback();
        listener = new TweetToKafkaPublisherStreamListener(isForPublishing, TOPIC, extractKeyFromTweet, kafkaTemplate, sendMessageResultCallbackFactory);
    }

    private void initMessageKey() {
        when(extractKeyFromTweet.apply(tweet)).thenReturn(KEY);
    }

    private void initSendResult() {
        when(kafkaTemplate.send(TOPIC, KEY, tweet)).thenReturn(sendResult);
    }

    private void initResultCallback() {
        when(sendMessageResultCallbackFactory.getInstance(tweet)).thenReturn(resultCallback);
    }

    @Test
    public void onStatusShouldSendTweetToKafkaWhenItSatisfiesPredicate() {
        givenTweetSatisfiesPredicate();
        listener.onStatus(tweet);
        verify(kafkaTemplate).send(TOPIC, KEY, tweet);
    }

    private void givenTweetSatisfiesPredicate() {
        when(isForPublishing.test(tweet)).thenReturn(true);
    }

    @Test
    public void onStatusShouldAddCallbackToSendResult() {
        givenTweetSatisfiesPredicate();
        listener.onStatus(tweet);
        verify(sendResult).addCallback(resultCallback);
    }

    @Test
    public void onStatusShouldNotSendTweetToKafkaWhenItDoesNotSatisfyPredicate() {
        givenTweetDoesNotSatisfyPredicate();
        listener.onStatus(tweet);
        verify(kafkaTemplate, never()).send(anyString(), anyString(), any());
    }

    private void givenTweetDoesNotSatisfyPredicate() {
        when(isForPublishing.test(tweet)).thenReturn(false);
    }
}