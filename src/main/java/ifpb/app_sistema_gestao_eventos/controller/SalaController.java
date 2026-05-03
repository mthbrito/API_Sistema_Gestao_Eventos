package ifpb.app_sistema_gestao_eventos.controller;

import ifpb.app_sistema_gestao_eventos.model.dto.SalaRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.SalaResponseDTO;
import ifpb.app_sistema_gestao_eventos.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sge/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public ResponseEntity<List<SalaResponseDTO>> listarSalas() {
        return ResponseEntity.ok(salaService.listarSalas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> buscarSalaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.buscarSalaPorId(id));
    }

    @PostMapping
    public ResponseEntity<SalaResponseDTO> salvarSala(@Valid @RequestBody SalaRequestDTO sala) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salaService.salvarSala(sala));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> atualizarSala(
            @PathVariable Long id,
            @Valid @RequestBody SalaRequestDTO dto) {
        return ResponseEntity.ok(salaService.atualizarSala(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSala(@PathVariable Long id) {
        salaService.deletarSala(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
