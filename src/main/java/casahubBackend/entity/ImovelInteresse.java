package casahubBackend.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "imovel_usuarios") // Nome da tabela pivot
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImovelInteresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relacionamento com Imovel
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imovel_id", nullable = false)
    private Imovel imovel;

    private LocalDateTime data;

    @PrePersist
    protected void onCreate() {
        this.data = LocalDateTime.now();
    }
}
