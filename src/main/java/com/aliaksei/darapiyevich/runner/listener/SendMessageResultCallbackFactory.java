package com.aliaksei.darapiyevich.runner.listener;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import twitter4j.Status;

public interface SendMessageResultCallbackFactory {
    ListenableFutureCallback<SendResult<String, Status>> getInstance(Status tweet);
}
