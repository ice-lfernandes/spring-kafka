package br.com.ldf.pix.produtor.controller;

import br.com.ldf.pix.produtor.dto.PixDTO;
import br.com.ldf.pix.produtor.dto.PixStatus;
import br.com.ldf.pix.produtor.service.PixService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/pix")
@RequiredArgsConstructor
public class PixController {

    private final PixService pixService;

    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PixDTO createPix(@RequestBody PixDTO pixDTO) {
        pixDTO.setCode(UUID.randomUUID().toString());
        pixDTO.setDate(LocalDateTime.now());
        pixDTO.setStatus(PixStatus.PROCESSING);
        return pixService.save(pixDTO);
    }
}
