package br.com.ahoy.xmluploader.application.service;

import br.com.ahoy.xmluploader.adapter.in.rest.AgenteHandler;
import br.com.ahoy.xmluploader.adapter.in.rest.AgenteMapper;
import br.com.ahoy.xmluploader.adapter.out.mongodb.entity.AgenteEntity;
import br.com.ahoy.xmluploader.application.port.in.MaintainAgentUseCase;
import br.com.ahoy.xmluploader.application.port.out.AgentPersistence;
import br.com.ahoy.xmluploader.domain.model.Agente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
public class MaintainAgentService implements MaintainAgentUseCase {

    private AgentPersistence agentPersistence;

    private AgenteMapper mapper;

    @Autowired
    public MaintainAgentService(AgentPersistence agentPersistence,
                                AgenteMapper agenteMapper) {
        this.agentPersistence = agentPersistence;
        this.mapper = agenteMapper;
    }

    @Override
    public Collection<String> create(MultipartFile file) {
        Collection<Agente> agentes = new ArrayList<>();
        Collection<String> resposta = new ArrayList<>();

        if (!file.isEmpty()) {
            String type = file.getContentType();
            if (isMimeTypeXML(type)) {
                try {
                    agentes = lerArquivo(file.getBytes());
                    agentes.forEach(agente -> resposta.add(String.format("agente - codigo: %s", agente.getCodigo())));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return resposta;
    }

    private boolean isMimeTypeXML(String type) {
        String MIME_APPLICATION_XML = "application/xml";
        return MIME_APPLICATION_XML.equals(type);
    }

    private Collection<Agente> lerArquivo(byte[] file) throws IOException {
        Collection<Agente> agentes = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (InputStream is = new ByteArrayInputStream(file)) {
            String xml = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xml)));
            if (doc.hasChildNodes()) {
                agentes = agenteBuilder(xml);
                salvar(agentes);
            }
        } catch (IOException |
                 ParserConfigurationException |
                 SAXException e) {
            throw new RuntimeException(e);
        }
        return agentes;
    }

    private Collection<Agente> agenteBuilder(String is) {
        Collection<Agente> agentes = new ArrayList<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            AgenteHandler agenteHandler = new AgenteHandler();
            saxParser.parse(new InputSource(new StringReader(is)), agenteHandler);
            agentes = agenteHandler.getAgentes();
        } catch (ParserConfigurationException |
                 SAXException |
                 IOException e) {
            throw new RuntimeException(e);
        }

        return agentes;
    }

    private void salvar(Collection<Agente> agentes) {
        if (!CollectionUtils.isEmpty(agentes)) {
            agentes.forEach(agente -> {
                AgenteEntity entity = mapper.contruirEntity(agente);
                agentPersistence.create(entity);
            });

        }
    }
}
