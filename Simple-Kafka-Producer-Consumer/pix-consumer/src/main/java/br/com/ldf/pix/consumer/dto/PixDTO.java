package br.com.ldf.pix.consumer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PixDTO {
    private String code;
    private String originKey;
    private String destinationKey;
    private Double amount;
    private LocalDateTime date;
    private PixStatus status;
}
