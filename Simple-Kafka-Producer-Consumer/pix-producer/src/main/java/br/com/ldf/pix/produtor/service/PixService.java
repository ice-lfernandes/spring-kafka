package br.com.ldf.pix.produtor.service;

import br.com.ldf.pix.produtor.dto.PixDTO;
import br.com.ldf.pix.produtor.model.Pix;
import br.com.ldf.pix.produtor.repository.PixRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PixService {

    @Autowired
    private final PixRepository pixRepository;

    @Autowired
    private final KafkaTemplate<String, PixDTO> kafkaTemplate;

    public PixDTO save(PixDTO pixDTO) {
        pixRepository.save(Pix.toEntity(pixDTO));
        kafkaTemplate.send("pix-topic", pixDTO.getCode(), pixDTO);
        return pixDTO;
    }

}
