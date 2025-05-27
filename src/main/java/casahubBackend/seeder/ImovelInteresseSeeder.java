package casahubBackend.seeder;

import casahubBackend.entity.Imovel;
import casahubBackend.entity.ImovelInteresse;
import casahubBackend.entity.Usuario;
import casahubBackend.repository.ImovelInteresseRepository;
import casahubBackend.repository.ImovelRepository;
import casahubBackend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(3)
public class ImovelInteresseSeeder implements CommandLineRunner {

    private final ImovelInteresseRepository interesseRepository;
    private final UsuarioRepository usuarioRepository;
    private final ImovelRepository imovelRepository;

    public ImovelInteresseSeeder(ImovelInteresseRepository interesseRepository,
                                 UsuarioRepository usuarioRepository,
                                 ImovelRepository imovelRepository) {
        this.interesseRepository = interesseRepository;
        this.usuarioRepository = usuarioRepository;
        this.imovelRepository = imovelRepository;
    }

    @Override
    public void run(String... args) {
        if (interesseRepository.count() == 0) {

            List<Usuario> inquilinos = usuarioRepository.findAll().stream()
                    .filter(u -> "inquilino".equalsIgnoreCase(u.getTipoUsuario()))
                    .toList();

            List<Imovel> imoveis = imovelRepository.findAll();

            if (inquilinos.isEmpty() || imoveis.isEmpty()) {
                System.out.println("Nenhum inquilino ou imóvel encontrado para criar interesses.");
                return;
            }

            List<ImovelInteresse> interesses = new ArrayList<>();

            for (int i = 0; i < inquilinos.size(); i++) {
                Usuario inquilino = inquilinos.get(i);
                Imovel imovel = imoveis.get(i % imoveis.size());

                ImovelInteresse interesse = ImovelInteresse.builder()
                        .usuario(inquilino)
                        .imovel(imovel)
                        .data(LocalDateTime.now())
                        .build();

                interesses.add(interesse);
            }

            interesseRepository.saveAll(interesses);
            System.out.println(interesses.size() + " interesses em imóveis inseridos com sucesso.");
        }
    }
}
