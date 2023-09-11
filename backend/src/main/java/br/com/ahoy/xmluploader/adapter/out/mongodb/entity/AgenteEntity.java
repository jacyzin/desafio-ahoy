package br.com.ahoy.xmluploader.adapter.out.mongodb.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@Builder
@Document(collection = "Agente")
public class AgenteEntity {

    @Id
    private String id;
    private String codigo;
    private String data;
    private Collection<RegiaoEntity> regioes;

}
