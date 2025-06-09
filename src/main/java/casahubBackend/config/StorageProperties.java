package casahubBackend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class StorageProperties {

    @Value("${casahub.storage.caminho}")
    private String caminhoStorage;
}
