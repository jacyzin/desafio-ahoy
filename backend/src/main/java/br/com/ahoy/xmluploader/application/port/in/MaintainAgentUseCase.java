package br.com.ahoy.xmluploader.application.port.in;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Component
public interface MaintainAgentUseCase {
    Collection<String> create(MultipartFile file);

}
