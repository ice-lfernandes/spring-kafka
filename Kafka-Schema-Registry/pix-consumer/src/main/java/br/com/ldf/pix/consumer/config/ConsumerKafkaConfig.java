package br.com.ldf.pix.consumer.config;

import br.com.ldf.pix.avro.PixRecord;
import br.com.ldf.pix.consumer.dto.PixDTO;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerKafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapAddress;

    @Bean
    public ConsumerFactory<String, PixRecord> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        // configuração do número máximo de registros que o consumidor pode buscar por vez que bater no cluster
        // cenário aonde depende da latência de rede para se comunicar ao cluster
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        // configuração para que o consumidor busque os registros mais recentes a partir de quando ele foi iniciado
        // pode ser utilizado também o "earliest" para buscar desde o início do tópico
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        // configuração para que o consumidor não crie tópicos automaticamente
        // por default ele é true
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, true);
        // configuração para que o consumidor não comite automaticamente
        // por default ele é true e ele só da o ACK quando finaliza o método que está consumindo o evento
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // configuração que adiciona intercetpor para monitoramento no confluence control-center
        props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG,
                "io.confluent.monitoring.clients.interceptor.MonitoringConsumerInterceptor");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PixRecord> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PixRecord> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
