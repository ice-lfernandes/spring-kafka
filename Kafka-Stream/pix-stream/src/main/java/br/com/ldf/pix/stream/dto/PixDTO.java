package br.com.ldf.pix.stream.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PixDTO {
    private String code;
    private String originKey;
    private String destinationKey;
    private Double amount;
    private LocalDateTime date;
    private PixStatus status;
}