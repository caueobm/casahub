package casahubBackend.controller;

import casahubBackend.config.StorageProperties;
import casahubBackend.entity.Imovel;
import casahubBackend.entity.Usuario;
import casahubBackend.service.ImovelService;
import casahubBackend.service.UsuarioService;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/imoveis")
@RequiredArgsConstructor
public class ImovelController {

    private final ImovelService imovelService;
    private final UsuarioService usuarioService; // Para buscar corretor
    private final StorageProperties storageProperties;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<Imovel> criarImovel(@PathVariable Long usuarioId, @RequestBody Imovel imovel) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        imovel.setUsuario(usuario);
        Imovel criado = imovelService.cadastrarImovel(imovel);
        return ResponseEntity.ok(criado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imovel> buscarPorId(@PathVariable Long id) {
        Imovel imovel = imovelService.buscarPorId(id);
        return ResponseEntity.ok(imovel);
    }

    @GetMapping
    public List<Imovel> listarTodos() {
        return imovelService.listarTodos();
    }

    @GetMapping("/disponiveis")
    public List<Imovel> listarDisponiveisPorCidade(@RequestParam String cidade) {
        return imovelService.buscarDisponiveisPorCidade(cidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Imovel> atualizarImovel(@PathVariable Long id, @RequestBody Imovel imovel) {
        Imovel atualizado = imovelService.atualizarImovel(id, imovel);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarImovel(@PathVariable Long id) {
        imovelService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/fotos")
    public ResponseEntity<String> uploadFotos(@PathVariable Long id, @RequestParam("files") List<MultipartFile> files) {
        try {
            imovelService.adicionarFotos(id, files);
            return ResponseEntity.ok("Fotos enviadas com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao enviar fotos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/fotos/{nomeArquivo:.+}")
    public ResponseEntity<Resource> servirFoto(
            @PathVariable Long id,
            @PathVariable String nomeArquivo) {

        try {
            Resource foto = imovelService.carregarFoto(id, nomeArquivo);
            Path caminho = Paths.get(storageProperties.getCaminhoStorage(), id.toString(), nomeArquivo);
            String contentType = imovelService.detectarContentType(caminho);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType != null ? contentType : "application/octet-stream")
                    .body(foto);

        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
