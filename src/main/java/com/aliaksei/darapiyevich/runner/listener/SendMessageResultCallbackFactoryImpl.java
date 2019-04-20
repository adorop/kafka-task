package com.aliaksei.darapiyevich.runner.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;
import twitter4j.Status;

@Component
public class SendMessageResultCallbackFactoryImpl implements SendMessageResultCallbackFactory {
    @Override
    public ListenableFutureCallback<SendResult<String, Status>> getInstance(Status tweet) {
        return new LogFailureCallback();
    }

    @Slf4j
    private static class LogFailureCallback implements ListenableFutureCallback<SendResult<String, Status>> {
        @Override
        public void onFailure(Throwable ex) {
            log.error("Failed to send message: {}", ex);
        }

        @Override
        public void onSuccess(SendResult<String, Status> result) {
            //NOP
        }
    }

}
