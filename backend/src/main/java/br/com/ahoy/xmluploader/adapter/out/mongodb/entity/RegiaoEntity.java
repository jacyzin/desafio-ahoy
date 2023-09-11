package br.com.ahoy.xmluploader.adapter.out.mongodb.entity;

import br.com.ahoy.xmluploader.domain.model.Compra;
import br.com.ahoy.xmluploader.domain.model.Geracao;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@Builder
@Document(collection = "Regiao")
public class RegiaoEntity {

    @Id
    private String id;
    private String sigla;
    private GeracaoEntity geracao;
    private CompraEntity compra;
    private PrecoMedioEntity precoMedio;

}
