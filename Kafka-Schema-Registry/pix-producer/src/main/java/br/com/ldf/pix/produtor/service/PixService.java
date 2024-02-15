package br.com.ldf.pix.produtor.service;

import br.com.ldf.pix.avro.PixRecord;
import br.com.ldf.pix.produtor.dto.PixDTO;
import br.com.ldf.pix.produtor.model.Pix;
import br.com.ldf.pix.produtor.repository.PixRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PixService {

    private final PixRepository pixRepository;
    private final KafkaTemplate<String, PixRecord> kafkaTemplate;

    public PixDTO save(final PixDTO pixDTO) {
        pixRepository.save(Pix.builder()
                .code(pixDTO.getCode())
                .originKey(pixDTO.getOriginKey())
                .destinationKey(pixDTO.getDestinationKey())
                .amount(pixDTO.getAmount())
                .status(pixDTO.getStatus())
                .date(pixDTO.getDate())
                .build()
        );
        kafkaTemplate.send("pix-topic-avro", pixDTO.getCode(), buildRecord(pixDTO));
        log.info("stage=pix-created, code={}", pixDTO.getCode());
        return pixDTO;
    }

    private PixRecord buildRecord(PixDTO pixDTO) {
        return PixRecord.newBuilder()
                .setCode(pixDTO.getCode())
                .setOriginKey(pixDTO.getOriginKey())
                .setDestinationKey(pixDTO.getDestinationKey())
                .setAmount(pixDTO.getAmount())
                .setStatus(pixDTO.getStatus().name())
                .setDate(pixDTO.getDate().toString())
                .build();
    }

}
