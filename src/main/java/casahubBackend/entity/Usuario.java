package casahubBackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data // Gera getters, setters, toString, equals e hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefone;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Column(name = "tipo_usuario", nullable = false)
    private String tipoUsuario; // "corretor", "inquilino", "admin"

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }
}
