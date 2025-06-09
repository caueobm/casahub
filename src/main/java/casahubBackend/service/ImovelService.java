package casahubBackend.service;

import casahubBackend.config.StorageProperties;
import casahubBackend.entity.Imovel;
import casahubBackend.entity.Usuario;
import casahubBackend.repository.ImovelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImovelService {

    private final ImovelRepository imovelRepository;
    private final StorageProperties storageProperties;

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


    public Imovel adicionarFotos(Long imovelId, List<MultipartFile> arquivos) {
        Imovel imovel = buscarPorId(imovelId);

        String pastaBase = storageProperties.getCaminhoStorage(); // Ex: "storage"
        Path pastaImovel = Paths.get(pastaBase, String.valueOf(imovelId)); // Ex: "storage/7"
        List<String> novasFotos = new ArrayList<>();

        try {
            Files.createDirectories(pastaImovel); // Garante que a pasta storage/7 exista

            for (MultipartFile arquivo : arquivos) {
                String nomeArquivo = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();
                Path caminhoCompleto = pastaImovel.resolve(nomeArquivo); // storage/7/nome.png
                Files.write(caminhoCompleto, arquivo.getBytes());

                novasFotos.add(nomeArquivo); // ou "7/" + nomeArquivo se quiser armazenar o caminho relativo
            }

            if (imovel.getFotos() == null) {
                imovel.setFotos(new ArrayList<>());
            }

            imovel.getFotos().addAll(novasFotos);
            return imovelRepository.save(imovel);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar fotos do imóvel: " + e.getMessage(), e);
        }
    }


    public Resource carregarFoto(Long imovelId, String nomeArquivo) throws IOException {
        Path caminho = Paths.get(storageProperties.getCaminhoStorage(), imovelId.toString(), nomeArquivo);

        if (!Files.exists(caminho)) {
            throw new FileNotFoundException("Arquivo não encontrado: " + nomeArquivo);
        }

        return new UrlResource(caminho.toUri());
    }

    public String detectarContentType(Path caminho) throws IOException {
        return Files.probeContentType(caminho);
    }

}



