package casahubBackend.seeder;

import casahubBackend.entity.RoleType;
import casahubBackend.entity.Usuario;
import casahubBackend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Order(1)
public class UsuarioSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {

            Usuario admin = new Usuario();
            admin.setNome("Admin");
            admin.setEmail("admin@casahub.com");
            admin.setTelefone("11999999999");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRoles(Set.of(RoleType.ROLE_ADMIN));

            Usuario corretor1 = criarUsuario("Carlos Corretor", "corretor@casahub.com", "11888888888", "senha123", RoleType.ROLE_CORRETOR);
            Usuario corretor2 = criarUsuario("Ana Corretora", "ana.corretora@casahub.com", "11877777777", "senha123", RoleType.ROLE_CORRETOR);
            Usuario corretor3 = criarUsuario("João Corretor", "joao.corretor@casahub.com", "11866666666", "senha123", RoleType.ROLE_CORRETOR);

            Usuario inquilino1 = criarUsuario("Isabela Inquilina", "inquilino@casahub.com", "11777777777", "senha123", RoleType.ROLE_USER);
            Usuario inquilino2 = criarUsuario("Marcos Inquilino", "marcos.inquilino@casahub.com", "11766666666", "senha123", RoleType.ROLE_USER);
            Usuario inquilino3 = criarUsuario("Laura Inquilina", "laura.inquilina@casahub.com", "11755555555", "senha123", RoleType.ROLE_USER);
            Usuario inquilino4 = criarUsuario("Felipe Inquilino", "felipe.inquilino@casahub.com", "11744444444", "senha123", RoleType.ROLE_USER);
            Usuario inquilino5 = criarUsuario("Carla Inquilina", "carla.inquilina@casahub.com", "11733333333", "senha123", RoleType.ROLE_USER);

            usuarioRepository.saveAll(List.of(
                    admin,
                    corretor1, corretor2, corretor3,
                    inquilino1, inquilino2, inquilino3, inquilino4, inquilino5
            ));

            System.out.println("Usuários iniciais inseridos no banco.");
        }
    }

    private Usuario criarUsuario(String nome, String email, String telefone, String senha, RoleType role) {
        Usuario u = new Usuario();
        u.setNome(nome);
        u.setEmail(email);
        u.setTelefone(telefone);
        u.setPassword(passwordEncoder.encode(senha));
        u.setRoles(Set.of(role));
        return u;
    }
}
