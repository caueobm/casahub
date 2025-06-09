package casahubBackend.seeder;

import casahubBackend.entity.Imovel;
import casahubBackend.entity.RoleType;
import casahubBackend.entity.Usuario;
import casahubBackend.repository.ImovelRepository;
import casahubBackend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(2)
public class ImovelSeeder implements CommandLineRunner {

    private final ImovelRepository imovelRepository;
    private final UsuarioRepository usuarioRepository;

    public ImovelSeeder(ImovelRepository imovelRepository, UsuarioRepository usuarioRepository) {
        this.imovelRepository = imovelRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        if (imovelRepository.count() == 0) {

            List<Usuario> corretores = usuarioRepository.findAll().stream()
                    .filter(u -> u.getRoles().contains(RoleType.ROLE_USER))
                    .toList();

            if (corretores.isEmpty()) {
                System.out.println("Nenhum corretor encontrado para vincular aos imóveis.");
                return;
            }

            // Lista de imóveis "base" (sem corretor ainda)
            List<Imovel> imoveisBase = List.of(
                    new Imovel(null, null, "Apartamento", "Rua A, 123", "São Paulo", "SP", "01000-000",
                            500000f, null, 2, 2, 70, true,
                            "Apartamento mobiliado, ótima localização.", true, LocalDateTime.now(),
                            new ArrayList<>(List.of("foto1.jpg", "foto2.jpg"))),
                    new Imovel(null, null, "Casa", "Rua B, 456", "Campinas", "SP", "13000-000",
                            null, 3200f, 3, 3, 120, false,
                            "Casa espaçosa com quintal.", true, LocalDateTime.now(),
                            List.of("frente.jpg", "quintal.jpg")),
                    new Imovel(null, null, "Cobertura", "Av. Central, 789", "São Paulo", "SP", "01100-000",
                            1200000f, null, 4, 3, 150, true,
                            "Cobertura com vista panorâmica.", true, LocalDateTime.now(),
                            List.of("panoramica.jpg")),
                    new Imovel(null, null, "Apartamento", "Rua das Flores, 321", "Campinas", "SP", "13010-000",
                            600000f, null, 3, 2, 90, false,
                            "Apartamento próximo ao centro.", false, LocalDateTime.now(),
                            List.of("sala.jpg")),
                    new Imovel(null, null, "Casa", "Rua Verde, 654", "Sorocaba", "SP", "18000-000",
                            null, 2500f, 3, 2, 110, true,
                            "Casa com quintal grande e piscina.", true, LocalDateTime.now(),
                            List.of("piscina.jpg", "area-gourmet.jpg")),
                    new Imovel(null, null, "Loft", "Rua Industrial, 987", "São Paulo", "SP", "01200-000",
                            750000f, null, 1, 1, 45, false,
                            "Loft moderno no centro da cidade.", true, LocalDateTime.now(),
                            List.of("loft1.jpg"))
            );
            // Atribuir corretores em round-robin
            List<Imovel> imoveisComCorretores = new ArrayList<>();
            int i = 0;
            for (Imovel imovel : imoveisBase) {
                Usuario corretor = corretores.get(i % corretores.size());
                imovel.setUsuario(corretor);
                imoveisComCorretores.add(imovel);
                i++;
            }

            imovelRepository.saveAll(imoveisComCorretores);
            System.out.println("Imóveis iniciais inseridos no banco com corretores variados.");
        }
    }
}
