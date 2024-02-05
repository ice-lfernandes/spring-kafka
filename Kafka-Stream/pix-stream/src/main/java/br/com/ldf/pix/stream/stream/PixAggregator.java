package br.com.ldf.pix.stream.stream;

import br.com.ldf.pix.stream.dto.PixDTO;
import br.com.ldf.pix.stream.serdes.PixSerdes;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PixAggregator {


    /**
     * Implementação de agregador de pix que consome do topico: pix-topic e
     * agrupada o somatório de valores maiores de uma chave origem pix
     *
     * @param streamsBuilder
     */
    @Autowired
    public void sumByOriginKey(StreamsBuilder streamsBuilder) {
        KTable<String, Double> messageStream = streamsBuilder
                .stream("pix-topic", Consumed.with(Serdes.String(), PixSerdes.serdes()))
                .peek((key, value) -> log.info("Received Pix, originKey={}", value.getOriginKey()))
                .groupBy((key, value) -> value.getOriginKey())
                .aggregate(
                        () -> 0.0,
                        (key, value, aggregate) -> (aggregate + value.getAmount()),
                        Materialized.with(Serdes.String(), Serdes.Double())
                );
        messageStream.toStream().print(Printed.toSysOut());
        messageStream.toStream().to("pix-topic-sum-by-origin-key", Produced.with(Serdes.String(), Serdes.Double()));
    }

    /**
     *
     * Implementação de filtro para verificar fraude em pixs
     *
     * @param streamsBuilder
     */
//    @Autowired
//    public void filterToVerifyFraud(StreamsBuilder streamsBuilder) {
//        KStream<String, PixDTO> messageStream = streamsBuilder
//                .stream("pix-topic", Consumed.with(Serdes.String(), PixSerdes.serdes()))
//                .peek((key, value) -> log.info("Received Pix, originKey={}", value.getOriginKey()))
//                .filter((key, value) -> value.getAmount() > 1000);
//
//        messageStream.print(Printed.toSysOut());
//        messageStream.to("pix-topic-verify-fraud", Produced.with(Serdes.String(), PixSerdes.serdes()));
//    }


}
