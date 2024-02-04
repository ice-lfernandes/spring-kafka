package br.com.ldf.pix.produtor.model;

import br.com.ldf.pix.produtor.dto.PixDTO;
import br.com.ldf.pix.produtor.dto.PixStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
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

}
