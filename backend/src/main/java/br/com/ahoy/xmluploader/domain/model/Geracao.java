package br.com.ahoy.xmluploader.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;

/**
 * Geracao
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString
@Data
public class Geracao implements ValoresBase {

    @JsonProperty("valores")
    private Collection<String> valores;

}
