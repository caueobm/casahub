package casahubBackend.controller;
import casahubBackend.entity.Imovel;
import casahubBackend.entity.Usuario;
import casahubBackend.service.ImovelService;
import casahubBackend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/imoveis")
@RequiredArgsConstructor
public class ImovelController {

    private final ImovelService imovelService;
    private final UsuarioService usuarioService; // Para buscar corretor

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
}
