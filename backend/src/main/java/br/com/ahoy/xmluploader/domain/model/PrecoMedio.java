package br.com.ahoy.xmluploader.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Collection;

/**
 * PrecoMedio
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString
@Data
public class PrecoMedio implements ValoresBase {
    @JsonProperty("valor")
    private Collection<String> valores;
}
