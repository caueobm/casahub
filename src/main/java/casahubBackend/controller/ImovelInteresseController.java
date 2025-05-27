package casahubBackend.controller;
import casahubBackend.entity.Imovel;
import casahubBackend.entity.ImovelInteresse;
import casahubBackend.entity.Usuario;
import casahubBackend.service.ImovelInteresseService;
import casahubBackend.service.ImovelService;
import casahubBackend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interesses")
@RequiredArgsConstructor
public class ImovelInteresseController {

    private final ImovelInteresseService interesseService;
    private final UsuarioService usuarioService;
    private final ImovelService imovelService;

    @PostMapping("/usuario/{usuarioId}/imovel/{imovelId}")
    public ResponseEntity<ImovelInteresse> registrarInteresse(
            @PathVariable Long usuarioId,
            @PathVariable Long imovelId
    ) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        Imovel imovel = imovelService.buscarPorId(imovelId);

        ImovelInteresse interesse = ImovelInteresse.builder()
                .usuario(usuario)
                .imovel(imovel)
                .build();

        ImovelInteresse salvo = interesseService.registrarInteresse(interesse);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<ImovelInteresse> listarPorUsuario(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        return interesseService.buscarPorUsuario(usuario);
    }

    @GetMapping("/imovel/{imovelId}")
    public List<ImovelInteresse> listarPorImovel(@PathVariable Long imovelId) {
        Imovel imovel = imovelService.buscarPorId(imovelId);
        return interesseService.buscarPorImovel(imovel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarInteresse(@PathVariable Long id) {
        interesseService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
