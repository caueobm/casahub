package casahubBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "imoveis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com Usuario (corretor)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    private String tipo;

    private String endereco;

    private String cidade;

    private String estado;

    private String cep;

    private Float valor;

    @Column(name = "valor_aluguel")
    private Float valorAluguel;

    @Column(name = "numero_quartos")
    private Integer numeroQuartos;

    @Column(name = "numero_banheiros")
    private Integer numeroBanheiros;

    private Integer metragem;

    private Boolean mobiliado;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private Boolean disponivel;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDateTime.now();
    }
}
