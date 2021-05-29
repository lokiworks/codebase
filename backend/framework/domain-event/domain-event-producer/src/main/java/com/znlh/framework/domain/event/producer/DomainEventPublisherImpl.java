package com.znlh.framework.domain.event.producer;



import com.alibaba.fastjson.JSON;
import com.znlh.framework.domain.event.api.DomainEvent;
import com.znlh.framework.domain.event.api.DomainEventPublisher;
import com.znlh.framework.domain.event.api.DomainEventStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;
import java.util.Objects;


@Slf4j
public class DomainEventPublisherImpl implements DomainEventPublisher {
    RetryTemplate retryTemplate;
    MessageChannel channel;
    DomainEventStore store;

    private static final String EVENT_TYPE_HEADER ="eventType";
    private static final Integer MAX_RETRY_COUNT = 3;
    private static final Integer TIMEOUT = 3; // minutes
    private static final Integer DEFAULT_SIZE = 20;

    public DomainEventPublisherImpl(MessageChannel channel, DomainEventStore store){
        this.retryTemplate = retryTemplate();
        this.channel = channel;
        this.store = store;
    }

    private RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(200);
        policy.setMaxInterval(2000);
        policy.setMultiplier(2.0);
        retryTemplate.setBackOffPolicy(policy);
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

    @Override
    public void publish(DomainEvent event) {
        int updateSucceed =   store.markStatus(event.eventId(), DomainEventStatus.PROCESSING, DomainEventStatus.NEW);
        // 更新成功
        if (updateSucceed > 0){
            Message<String> message = MessageBuilder.withPayload(JSON.toJSONString(event))
                    .setHeader(EVENT_TYPE_HEADER, event.eventType()).build();
            try {
                boolean sendSucceed =  channel.send(message, 1000);
                if (sendSucceed){
                    store.markStatus(event.eventId(), DomainEventStatus.SUCCESS, DomainEventStatus.PROCESSING);
                }else {
                    store.incRetryCnt(event.eventId(), MAX_RETRY_COUNT);
                }
            }catch (Throwable e){
                log.warn("Error while send message:{}", e.getMessage());
            }

        }
    }

    @Override
    public void batchPublish(int size) {
        try {
            retryTemplate.execute(context->{
                doPublish(size);
                return null;
            });
        }catch (Throwable e){
            log.warn("Error while publish domain events:{}", e.getMessage());
        }
    }

    private void doPublish(int size){
        // 重置超时的数据
        store.resetProcessTimeout(TIMEOUT, MAX_RETRY_COUNT);
        // 将超过阈值的数据状态重置为failed
        store.resetFailed(MAX_RETRY_COUNT);

        List<DomainEvent> events = store.batchPublish(size);
        if (Objects.isNull(events) || events.isEmpty()){
            return;
        }
        for (DomainEvent event: events){
              int updateSucceed =   store.markStatus(event.eventId(), DomainEventStatus.PROCESSING, DomainEventStatus.NEW);
              // 更新成功
              if (updateSucceed > 0){
                  Message<String> message = MessageBuilder.withPayload(JSON.toJSONString(event))
                          .setHeader(EVENT_TYPE_HEADER, event.eventType()).build();
                  try {
                      boolean sendSucceed =  channel.send(message, 1000);
                      if (sendSucceed){
                          store.markStatus(event.eventId(), DomainEventStatus.SUCCESS, DomainEventStatus.PROCESSING);
                      }else {
                          store.incRetryCnt(event.eventId(), MAX_RETRY_COUNT);
                      }
                  }catch (Throwable e){
                      log.warn("Error while send message:{}", e.getMessage());
                  }

              }
        }
    }

    @Override
    public void batchPublish() {
        batchPublish(DEFAULT_SIZE);
    }
}
