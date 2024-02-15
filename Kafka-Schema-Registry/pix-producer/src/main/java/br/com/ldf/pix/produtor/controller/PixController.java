package br.com.ldf.pix.produtor.controller;

import br.com.ldf.pix.produtor.dto.PixDTO;
import br.com.ldf.pix.produtor.dto.PixStatus;
import br.com.ldf.pix.produtor.service.PixService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/pix")
@RequiredArgsConstructor
public class PixController {

    private final PixService pixService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PixDTO> createPix(@RequestBody PixDTO pixDTO) {
        pixDTO.setCode(UUID.randomUUID().toString());
        pixDTO.setDate(LocalDateTime.now());
        pixDTO.setStatus(PixStatus.PROCESSING);
        pixService.save(pixDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pixDTO);
    }
}
