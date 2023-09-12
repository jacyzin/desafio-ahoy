package br.com.ahoy.xmluploader;

import br.com.ahoy.xmluploader.adapter.in.rest.AgenteMapper;
import br.com.ahoy.xmluploader.application.port.out.AgentPersistence;
import br.com.ahoy.xmluploader.application.service.MaintainAgentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@Slf4j
@DataMongoTest
public class AgenteServiceIntegrationTest {

    @Mock
    private AgenteMapper agenteMapper;

    @Mock
    private AgentPersistence persistence;


    @InjectMocks
    MaintainAgentService service;

    @Test
    @DisplayName("Dado um arquivo xml"
            + " quando enviado via service"
            + " retorna lista de códigos dos agentes")
    public void dadoArquivoXml_entaoRetornaListaString() {
        Collection<String> result = service.create(getArquivoXml());
        assertFalse(result.isEmpty());
        assertEquals(result.stream().findFirst().get(), "agente - codigo: 1");
    }

    private MultipartFile getArquivoXml() {
        try (InputStream is = getClass().getResourceAsStream("/static/xml/exemplo_02.xml");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String xml = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            String content = xml.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
            MultipartFile multipartFile = new MockMultipartFile("arquivo.xml",
                    "arquivo.xml", MediaType.TEXT_XML_VALUE, content.getBytes());
            return multipartFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
