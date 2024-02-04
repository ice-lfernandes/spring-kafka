package br.com.ldf.pix.produtor.controller;

import br.com.ldf.pix.produtor.dto.KeyDTO;
import br.com.ldf.pix.produtor.service.KeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/key")
@RequiredArgsConstructor
public class KeyController {

    private final KeyService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody KeyDTO keyDTO) {
        service.createKey(keyDTO);
    }
}
