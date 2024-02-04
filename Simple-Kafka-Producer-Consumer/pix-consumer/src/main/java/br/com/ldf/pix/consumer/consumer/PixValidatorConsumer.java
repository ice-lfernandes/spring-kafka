package br.com.ldf.pix.consumer.consumer;

import br.com.ldf.pix.consumer.dto.PixDTO;
import br.com.ldf.pix.consumer.dto.PixStatus;
import br.com.ldf.pix.consumer.model.Key;
import br.com.ldf.pix.consumer.model.Pix;
import br.com.ldf.pix.consumer.repository.KeyRepository;
import br.com.ldf.pix.consumer.repository.PixRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class PixValidatorConsumer {

    private final KeyRepository keyRepository;
    private final PixRepository pixRepository;

    @KafkaListener(topics = "pix-topic", groupId = "consumer-group-pix-validator",
            containerFactory = "kafkaListenerContainerFactory")
    public void process(PixDTO pixDTO) {
        log.info("stage=pix-process-init, code={}, status={}", pixDTO.getCode(), pixDTO.getStatus());

        Pix pix = pixRepository.findByCode(pixDTO.getCode());

        Key origin = keyRepository.findByValue(pixDTO.getOriginKey());
        Key destination = keyRepository.findByValue(pixDTO.getDestinationKey());

        if (isNull(origin) || isNull(destination)) {
            pix.setStatus(PixStatus.ERROR);
        } else {
            pix.setStatus(PixStatus.PROCESSED);
        }

        log.info("stage=pix-process-finish, code={}, status={}", pix.getCode(), pix.getStatus());
        pixRepository.save(pix);
    }

}
