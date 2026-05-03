package ifpb.app_sistema_gestao_eventos.controller;

import ifpb.app_sistema_gestao_eventos.model.dto.PerfilRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.PerfilResponseDTO;
import ifpb.app_sistema_gestao_eventos.service.PerfilService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sge/perfis")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping
    public ResponseEntity<List<PerfilResponseDTO>> listarPerfis() {
        return ResponseEntity.ok(perfilService.listarPerfis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> buscarPerfilPorId(@PathVariable Long id) {
        return ResponseEntity.ok(perfilService.buscarPerfilPorId(id));
    }

    @PostMapping
    public ResponseEntity<PerfilResponseDTO> salvarPerfil(@Valid @RequestBody PerfilRequestDTO perfil) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.salvarPerfil(perfil));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> atualizarPerfil(
            @PathVariable Long id,
            @Valid @RequestBody PerfilRequestDTO dto) {
        return ResponseEntity.ok(perfilService.atualizarPerfil(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPerfil(@PathVariable Long id) {
        perfilService.deletarPerfil(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
