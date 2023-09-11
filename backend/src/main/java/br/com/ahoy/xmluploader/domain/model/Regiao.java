package br.com.ahoy.xmluploader.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Regiao
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString
@Data
public class Regiao {

    @JsonProperty("sigla")
    private String sigla;

    @JsonProperty("geracao")
    private ValoresBase geracao;
    @JsonProperty("compra")
    private ValoresBase compra;

    @JsonProperty("precoMedio")
    private ValoresBase precoMedio;

}
