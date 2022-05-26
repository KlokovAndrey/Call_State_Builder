package com.avaya.ccaas.call_state_builder.kafka;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.subject.RecordNameStrategy;
import io.confluent.parallelconsumer.ParallelConsumerOptions;
import io.confluent.parallelconsumer.ParallelStreamProcessor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static io.confluent.parallelconsumer.ParallelConsumerOptions.ProcessingOrder.KEY;
import static java.util.List.of;

@Configuration
public class KafkaConsumerConfig {

    private String kafkaBootstrap;
    private String schemaRegistryUrl;
    private String inputCallTopic;
    private String inputParticipantTopic;

    public KafkaConsumerConfig(
        @Value("${kafka.bootstrap}") final String kafkaBootstrap,
        @Value("${schema.registry.url}") final String schemaRegistryUrl,
        @Value("${kafka.call.topic}") final String inputCallTopic,
        @Value("${kafka.participant.topic}") final String inputParticipantTopic
    ) {
        this.kafkaBootstrap = kafkaBootstrap;
        this.schemaRegistryUrl = schemaRegistryUrl;
        this.inputCallTopic = inputCallTopic;
        this.inputParticipantTopic = inputParticipantTopic;
    }

    @Bean
    public Properties properties() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrap);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "avro");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(AbstractKafkaAvroSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY, RecordNameStrategy.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, Boolean.TRUE);
        return props;
    }

    @Bean
    public Consumer<String, GenericRecord> getKafkaConsumer() {
        return new KafkaConsumer<>(properties());
    }

    @Bean
    public ParallelStreamProcessor<String, GenericRecord> setupParallelConsumer() {
        Consumer<String, GenericRecord> kafkaConsumer = getKafkaConsumer();

        var options = ParallelConsumerOptions.<String, GenericRecord>builder().ordering(KEY).consumer(kafkaConsumer).build();
        ParallelStreamProcessor<String, GenericRecord> eosStreamProcessor = ParallelStreamProcessor.createEosStreamProcessor(options);
        eosStreamProcessor.subscribe(of(inputCallTopic, inputParticipantTopic));
        return eosStreamProcessor;
    }
}
