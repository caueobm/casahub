package casahubBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteInteressadoDTO {

    private Long idInteresse;
    private Long idImovel;
    private String tipoImovel;
    private String enderecoCompleto;
    private String nomeCliente;
    private String emailCliente;
    private String telefoneCliente;

    // Getters e setters (pode usar Lombok se preferir)
}
