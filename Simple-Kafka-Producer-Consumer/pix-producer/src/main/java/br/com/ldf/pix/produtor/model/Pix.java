package br.com.ldf.pix.produtor.model;

import br.com.ldf.pix.produtor.dto.PixDTO;
import br.com.ldf.pix.produtor.dto.PixStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Pix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String originKey;
    private String destinationKey;
    private Double amount;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private PixStatus status;

    public static Pix toEntity(PixDTO pixDTO) {
        var pix = new Pix();
        pix.setCode(pixDTO.getCode());
        pix.setOriginKey(pixDTO.getOriginKey());
        pix.setDestinationKey(pixDTO.getDestinationKey());
        pix.setStatus(pixDTO.getStatus());
        pix.setAmount(pixDTO.getAmount());
        pix.setDate(pixDTO.getDate());
        return pix;
    }
}
