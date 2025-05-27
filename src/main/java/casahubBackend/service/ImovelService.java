package casahubBackend.service;
import casahubBackend.entity.Imovel;
import casahubBackend.entity.Usuario;
import casahubBackend.repository.ImovelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImovelService {

    private final ImovelRepository imovelRepository;

    // Cadastrar novo imóvel
    public Imovel cadastrarImovel(Imovel imovel) {
        return imovelRepository.save(imovel);
    }

    // Buscar por ID
    public Imovel buscarPorId(Long id) {
        return imovelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Imóvel não encontrado com ID: " + id));
    }

    // Listar todos os imóveis
    public List<Imovel> listarTodos() {
        return imovelRepository.findAll();
    }

    // Listar imóveis disponíveis por cidade
    public List<Imovel> buscarDisponiveisPorCidade(String cidade) {
        return imovelRepository.findByCidadeAndDisponivelIsTrue(cidade);
    }

    // Buscar imóveis de um corretor
    public List<Imovel> buscarPorUsuario(Usuario usuario) {
        return imovelRepository.findByUsuario(usuario);
    }

    // Atualizar imóvel (requer que o imóvel já exista)
    public Imovel atualizarImovel(Long id, Imovel atualizado) {
        Imovel existente = buscarPorId(id);
        atualizado.setId(existente.getId());
        return imovelRepository.save(atualizado);
    }

    // Deletar imóvel
    public void deletarPorId(Long id) {
        if (!imovelRepository.existsById(id)) {
            throw new EntityNotFoundException("Imóvel não encontrado para exclusão com ID: " + id);
        }
        imovelRepository.deleteById(id);
    }
}
