package casahubBackend.seeder;

import casahubBackend.entity.Usuario;
import casahubBackend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Order(1)
public class UsuarioSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    public UsuarioSeeder(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario(null, "Admin", "admin@casahub.com", "11999999999", "123456", "admin", LocalDateTime.now());
            Usuario corretor1 = new Usuario(null, "Carlos Corretor", "corretor@casahub.com", "11888888888", "senha123", "corretor", LocalDateTime.now());
            Usuario corretor2 = new Usuario(null, "Ana Corretora", "ana.corretora@casahub.com", "11877777777", "senha123", "corretor", LocalDateTime.now());
            Usuario corretor3 = new Usuario(null, "João Corretor", "joao.corretor@casahub.com", "11866666666", "senha123", "corretor", LocalDateTime.now());

            Usuario inquilino1 = new Usuario(null, "Isabela Inquilina", "inquilino@casahub.com", "11777777777", "senha123", "inquilino", LocalDateTime.now());
            Usuario inquilino2 = new Usuario(null, "Marcos Inquilino", "marcos.inquilino@casahub.com", "11766666666", "senha123", "inquilino", LocalDateTime.now());
            Usuario inquilino3 = new Usuario(null, "Laura Inquilina", "laura.inquilina@casahub.com", "11755555555", "senha123", "inquilino", LocalDateTime.now());
            Usuario inquilino4 = new Usuario(null, "Felipe Inquilino", "felipe.inquilino@casahub.com", "11744444444", "senha123", "inquilino", LocalDateTime.now());
            Usuario inquilino5 = new Usuario(null, "Carla Inquilina", "carla.inquilina@casahub.com", "11733333333", "senha123", "inquilino", LocalDateTime.now());

            usuarioRepository.saveAll(List.of(
                    admin,
                    corretor1, corretor2, corretor3,
                    inquilino1, inquilino2, inquilino3, inquilino4, inquilino5
            ));

            System.out.println("Usuários iniciais inseridos no banco.");
        }
    }
}
