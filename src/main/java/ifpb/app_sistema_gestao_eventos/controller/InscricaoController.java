package ifpb.app_sistema_gestao_eventos.controller;

import ifpb.app_sistema_gestao_eventos.model.dto.InscricaoRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.InscricaoResponseDTO;
import ifpb.app_sistema_gestao_eventos.service.InscricaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sge/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @GetMapping
    public ResponseEntity<Page<InscricaoResponseDTO>> listarInscricoes(Pageable pageable) {
        return ResponseEntity.ok(inscricaoService.listarInscricoes(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscricaoResponseDTO> buscarInscricaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inscricaoService.buscarInscricaoPorId(id));
    }

    @PostMapping
    public ResponseEntity<InscricaoResponseDTO> salvarInscricao(@Valid @RequestBody InscricaoRequestDTO inscricao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricaoService.salvarInscricao(inscricao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InscricaoResponseDTO> atualizarInscricao(
            @PathVariable Long id,
            @Valid @RequestBody InscricaoRequestDTO dto) {
        return ResponseEntity.ok(inscricaoService.atualizarInscricao(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarInscricao(@PathVariable Long id) {
        inscricaoService.deletarInscricao(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
