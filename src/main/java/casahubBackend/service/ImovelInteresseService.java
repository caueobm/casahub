package casahubBackend.service;
import casahubBackend.entity.Imovel;
import casahubBackend.entity.ImovelInteresse;
import casahubBackend.entity.Usuario;
import casahubBackend.repository.ImovelInteresseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImovelInteresseService {

    private final ImovelInteresseRepository interesseRepository;

    // Registrar interesse de um usuário por um imóvel
    public ImovelInteresse registrarInteresse(ImovelInteresse interesse) {
        if (interesseRepository.existsByUsuarioAndImovel(interesse.getUsuario(), interesse.getImovel())) {
            throw new IllegalArgumentException("O usuário já demonstrou interesse por este imóvel.");
        }
        return interesseRepository.save(interesse);
    }

    // Buscar interesses de um usuário
    public List<ImovelInteresse> buscarPorUsuario(Usuario usuario) {
        return interesseRepository.findByUsuario(usuario);
    }
    // Buscar interesses de um imovel
    public List<ImovelInteresse> buscarPorImoveis(List<Imovel> imoveis) {
        return interesseRepository.findByImovelInWithFetch(imoveis);
    }

    // Buscar usuários interessados em um imóvel
    public List<ImovelInteresse> buscarPorImovel(Imovel imovel) {
        return interesseRepository.findByImovel(imovel);
    }

    // Deletar interesse por ID
    public void deletarPorId(Long id) {
        if (!interesseRepository.existsById(id)) {
            throw new EntityNotFoundException("Interesse não encontrado com ID: " + id);
        }
        interesseRepository.deleteById(id);
    }



}
