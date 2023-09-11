package br.com.ahoy.xmluploader.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Agente
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString
@Data
public class Agente {

    @JsonProperty("codigo")
    private String codigo;
    @JsonProperty("data")
    private String data;
    @JsonProperty("regiao")
    private Collection<Regiao> regiaoList;

}
