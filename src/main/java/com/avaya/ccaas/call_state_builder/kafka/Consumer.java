package com.avaya.ccaas.call_state_builder.kafka;

import com.avaya.ccaas.call_state_builder.exception.HandlerException;
import com.avaya.ccaas.call_state_builder.handler.EventOrchestrator;
import io.confluent.parallelconsumer.ParallelStreamProcessor;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    private final EventOrchestrator eventOrchestrator;
    private final ParallelStreamProcessor<String, GenericRecord> parallelConsumer;

    @Autowired
    public Consumer(final ParallelStreamProcessor<String, GenericRecord> parallelConsumer, final EventOrchestrator eventOrchestrator) {
        this.parallelConsumer = parallelConsumer;
        this.eventOrchestrator = eventOrchestrator;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void listen() {

        parallelConsumer.poll(record -> {
            try {
                GenericRecord value = record.value();
                LOGGER.info("Concurrently processing a record: {}", value);
                eventOrchestrator.handle(value);
            } catch (HandlerException ex) {
                LOGGER.error(ex.getMessage());
            }

        });
    }
}
