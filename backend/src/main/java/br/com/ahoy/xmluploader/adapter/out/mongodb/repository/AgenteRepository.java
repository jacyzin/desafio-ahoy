package br.com.ahoy.xmluploader.adapter.out.mongodb.repository;

import br.com.ahoy.xmluploader.adapter.out.mongodb.entity.AgenteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AgenteRepository extends MongoRepository<AgenteEntity, String> {
}
