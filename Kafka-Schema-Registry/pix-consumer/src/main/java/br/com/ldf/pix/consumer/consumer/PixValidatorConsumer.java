package br.com.ldf.pix.consumer.consumer;

import br.com.ldf.pix.avro.PixRecord;
import br.com.ldf.pix.consumer.dto.PixDTO;
import br.com.ldf.pix.consumer.dto.PixStatus;
import br.com.ldf.pix.consumer.exception.KeyNotFoundException;
import br.com.ldf.pix.consumer.model.Key;
import br.com.ldf.pix.consumer.model.Pix;
import br.com.ldf.pix.consumer.repository.KeyRepository;
import br.com.ldf.pix.consumer.repository.PixRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class PixValidatorConsumer {

    private final KeyRepository keyRepository;
    private final PixRepository pixRepository;

    @KafkaListener(topics = "pix-topic-avro", groupId = "consumer-group-pix-validator",
            autoStartup = "true",
            containerFactory = "kafkaListenerContainerFactory")
    @RetryableTopic(
            backoff = @Backoff(delay = 1000), // quantidade de tempo entre as tentativas
            attempts = "5", // quantidade maxima de tentativas
            autoCreateTopics = "true", // cria novo tóico de retentativas
            include = KeyNotFoundException.class // exception que irá disparar a tentativa. Obs: Idealmente, deveria ser uma exception mais específica para cenários de erros que podem ser recuperáveis
    )
    public void process(final PixRecord pixRecord) {
        log.info("stage=pix-process-init, code={}, status={}", pixRecord.getCode(), pixRecord.getStatus());

        Pix pix = pixRepository.findByCode(pixRecord.getCode());

        Key origin = keyRepository.findByValue(pixRecord.getOriginKey());
        Key destination = keyRepository.findByValue(pixRecord.getDestinationKey());

        if (isNull(origin) || isNull(destination)) {
            pix.setStatus(PixStatus.ERROR);
            throw new KeyNotFoundException();
        } else {
            pix.setStatus(PixStatus.PROCESSED);
        }

        log.info("stage=pix-process-finish, code={}, status={}", pix.getCode(), pix.getStatus());
        pixRepository.save(pix);
    }

}
