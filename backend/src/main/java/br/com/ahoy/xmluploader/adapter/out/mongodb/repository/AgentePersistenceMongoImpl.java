package br.com.ahoy.xmluploader.adapter.out.mongodb.repository;

import br.com.ahoy.xmluploader.adapter.out.mongodb.entity.AgenteEntity;
import br.com.ahoy.xmluploader.application.port.out.AgentPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgentePersistenceMongoImpl implements AgentPersistence {

    @Autowired
    private AgenteRepository repository;

    @Override
    public void create(AgenteEntity entity) {
        if (entity != null) {
            repository.save(entity);
        }
    }
}
