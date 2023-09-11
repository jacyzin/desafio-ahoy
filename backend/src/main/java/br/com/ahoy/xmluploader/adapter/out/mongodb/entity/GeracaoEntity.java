package br.com.ahoy.xmluploader.adapter.out.mongodb.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@Builder
@Document(collection = "Geracao")
public class GeracaoEntity {

    @Id
    private String id;
    private Collection<String> valores;
}
