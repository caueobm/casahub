package casahubBackend.controller;
import casahubBackend.dto.ClienteInteressadoDTO;
import casahubBackend.entity.Imovel;
import casahubBackend.entity.ImovelInteresse;
import casahubBackend.entity.Usuario;
import casahubBackend.service.ImovelInteresseService;
import casahubBackend.service.ImovelService;
import casahubBackend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interesses")
@RequiredArgsConstructor
public class ImovelInteresseController {

    private final ImovelInteresseService interesseService;
    private final UsuarioService usuarioService;
    private final ImovelService imovelService;

    @PostMapping("/imovel/{imovelId}")
    public ResponseEntity<?> registrarInteresse(
            @PathVariable Long imovelId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        Usuario usuario = usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));

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

    @GetMapping("/meus-clientes")
    public ResponseEntity<List<ClienteInteressadoDTO>> meusClientes(Authentication authentication) {
        String email = authentication.getName();
        Usuario proprietario = usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));

        List<Imovel> meusImoveis = imovelService.buscarPorUsuario(proprietario);
        if (meusImoveis.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        List<ImovelInteresse> interesses = interesseService.buscarPorImoveis(meusImoveis);

        List<ClienteInteressadoDTO> dtos = interesses.stream()
                .map(interesse -> {
                    Imovel imovel = interesse.getImovel();
                    Usuario cliente = interesse.getUsuario();
                    return new ClienteInteressadoDTO(
                            interesse.getId(),
                            imovel.getId(),
                            imovel.getTipo(),
                            imovel.getEndereco() + ", " + imovel.getCidade() + " - " + imovel.getEstado() + ", " + imovel.getCep(),
                            cliente.getNome(),
                            cliente.getEmail(),
                            cliente.getTelefone()
                    );
                })
                .toList();

        return ResponseEntity.ok(dtos);
    }





}
