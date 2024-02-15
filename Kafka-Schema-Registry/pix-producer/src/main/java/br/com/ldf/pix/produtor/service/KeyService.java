package br.com.ldf.pix.produtor.service;

import br.com.ldf.pix.produtor.dto.KeyDTO;
import br.com.ldf.pix.produtor.model.Key;
import br.com.ldf.pix.produtor.repository.KeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeyService {

    private final KeyRepository repository;

    public void createKey(final KeyDTO keyDTO) {
        repository.save(Key.builder()
                .value(keyDTO.value())
                .build()
        );
    }
}
