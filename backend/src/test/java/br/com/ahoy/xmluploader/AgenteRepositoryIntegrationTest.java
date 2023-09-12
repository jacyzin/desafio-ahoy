package br.com.ahoy.xmluploader;

import br.com.ahoy.xmluploader.adapter.out.mongodb.entity.AgenteEntity;
import br.com.ahoy.xmluploader.adapter.out.mongodb.repository.AgenteRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class AgenteRepositoryIntegrationTest {

    @Autowired
    private AgenteRepository repository;

    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    @Test
    @DisplayName("Dado um objeto a persistir"
            + " quando salvo utilizando repository"
            + " o objeto é salvo")
    public void dadoEntidade_salvaEmMongo() {
        repository.insert(agenteEntity());
        Optional<AgenteEntity> entity = repository.findById("1");
        assertEquals(entity.get().getCodigo(), "fool");
        assertNotNull(entity.get().getRegioes());
        assertTrue(agenteEntity().getRegioes().isEmpty());
    }

    private AgenteEntity agenteEntity() {
        return AgenteEntity.builder()
                .id("1")
                .data("fool")
                .codigo("fool")
                .regioes(new ArrayList<>())
                .build();
    }
}
