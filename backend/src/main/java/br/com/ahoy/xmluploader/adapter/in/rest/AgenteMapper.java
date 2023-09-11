package br.com.ahoy.xmluploader.adapter.in.rest;

import br.com.ahoy.xmluploader.adapter.out.mongodb.entity.*;
import br.com.ahoy.xmluploader.domain.model.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class AgenteMapper {

    public AgenteEntity contruirEntity(Agente agente) {
        if (agente != null) {
            return AgenteEntity.builder()
                    .codigo(agente.getCodigo())
                    .data(agente.getData())
                    .regioes(construirRegiaoEntity(agente.getRegiaoList()))
                    .build();
        }
        return AgenteEntity.builder().build();
    }

    private Collection<RegiaoEntity> construirRegiaoEntity(Collection<Regiao> regioes) {
        Collection<RegiaoEntity> entities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(regioes)) {
            regioes.forEach(regiao -> {
                entities.add(RegiaoEntity.builder()
                                .sigla(regiao.getSigla())
                                .compra(construirCompraEntity(regiao.getCompra()))
                                .geracao(construirGeracaoEntity(regiao.getGeracao()))
                                .precoMedio(PrecoMedioEntity.builder().build())
                        .build());
            });
        }
        return entities;
    }

    private CompraEntity construirCompraEntity(ValoresBase compra) {
        if (compra != null) { return CompraEntity.builder().valores(compra.getValores()).build(); }
        return CompraEntity.builder().build();
    }

    private GeracaoEntity construirGeracaoEntity(ValoresBase geracao) {
        if (geracao != null) { return GeracaoEntity.builder().valores(geracao.getValores()).build(); }
        return GeracaoEntity.builder().build();
    }
}
