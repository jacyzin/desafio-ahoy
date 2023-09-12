package br.com.ahoy.xmluploader;

import br.com.ahoy.xmluploader.adapter.in.rest.AgenteController;
import br.com.ahoy.xmluploader.application.service.MaintainAgentService;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AgenteController.class)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        MongoAutoConfiguration.class})
public class AgenteRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MaintainAgentService service;

    @Test
    @DisplayName("Dado um arquivo xml"
            + " quando enviado via http request post"
            + " então os objetos são persistidos")
    public void dadoArquivoXml_entaoRetornaStatus200() throws Exception {
        String  xml = getArquivoXml();
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "file",
                MediaType.TEXT_XML_VALUE,
                xml.getBytes()
        );
        assertNotNull(xml);
        mvc.perform(multipart("/api/agente/v1")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errors", is(IsNull.nullValue())))
                .andDo(print());

    }

    private String getArquivoXml() {
        try (InputStream is = getClass().getResourceAsStream("/static/xml/exemplo_02.xml");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String xml = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return xml.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

