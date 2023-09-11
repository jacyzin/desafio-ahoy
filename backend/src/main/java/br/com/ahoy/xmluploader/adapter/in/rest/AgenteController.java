package br.com.ahoy.xmluploader.adapter.in.rest;

import br.com.ahoy.xmluploader.application.port.in.MaintainAgentUseCase;
import br.com.ahoy.xmluploader.domain.model.Agente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/agente/v1")
@CrossOrigin(origins = "*")
public class AgenteController {

    @Autowired
    private MaintainAgentUseCase maintainAgentUseCase;

    @PostMapping
    public ResponseEntity<?> create(@RequestParam("file") MultipartFile file) {
        Response<Collection<String>> response = new Response<>();
        Collection<String> agentes = maintainAgentUseCase.create(file);
        response.setData(agentes);
        return ResponseEntity.ok(response);
    }
}
