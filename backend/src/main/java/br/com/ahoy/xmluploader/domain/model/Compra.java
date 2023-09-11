package br.com.ahoy.xmluploader.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;

/**
 * Compra
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString
@Data
public class Compra implements ValoresBase {
    @JsonProperty("valores")
    private Collection<String> valores;
}
