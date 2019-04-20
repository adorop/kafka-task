package com.aliaksei.darapiyevich.runner.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import twitter4j.Status;
import twitter4j.StatusAdapter;

import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class TweetToKafkaPublisherStreamListener extends StatusAdapter {
    private final Predicate<Status> isForPublishing;
    private final String topic;
    private final Function<Status, String> extractKeyFromTweet;
    private final KafkaTemplate<String, Status> kafkaTemplate;
    private final SendMessageResultCallbackFactory sendMessageResultCallbackFactory;

    @Autowired
    public TweetToKafkaPublisherStreamListener(Predicate<Status> isForPublishing,
                                               @Value("${kafka-task.topic}") String topic,
                                               Function<Status, String> extractKeyFromTweet,
                                               KafkaTemplate<String, Status> kafkaTemplate,
                                               SendMessageResultCallbackFactory sendMessageResultCallbackFactory) {
        this.isForPublishing = isForPublishing;
        this.topic = topic;
        this.extractKeyFromTweet = extractKeyFromTweet;
        this.kafkaTemplate = kafkaTemplate;
        this.sendMessageResultCallbackFactory = sendMessageResultCallbackFactory;
    }

    @Override
    public void onStatus(Status tweet) {
        if (isForPublishing.test(tweet)) {
            String key = extractKeyFromTweet.apply(tweet);
            ListenableFuture<SendResult<String, Status>> future = kafkaTemplate.send(topic, key, tweet);
            future.addCallback(sendMessageResultCallbackFactory.getInstance(tweet));
        }
    }
}
