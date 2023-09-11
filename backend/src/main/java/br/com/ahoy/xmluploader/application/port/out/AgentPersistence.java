package br.com.ahoy.xmluploader.application.port.out;

import br.com.ahoy.xmluploader.adapter.out.mongodb.entity.AgenteEntity;
import br.com.ahoy.xmluploader.domain.model.Agente;
import org.springframework.stereotype.Component;

@Component
public interface AgentPersistence {

    void create(AgenteEntity entity);
}
