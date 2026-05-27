package ifpb.app_sistema_gestao_eventos.controller;

import ifpb.app_sistema_gestao_eventos.model.dto.NotificacaoRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.NotificacaoResponseDTO;
import ifpb.app_sistema_gestao_eventos.service.NotificacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sge/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @GetMapping
    public ResponseEntity<Page<NotificacaoResponseDTO>> listarNotificacoes(Pageable pageable) {
        return ResponseEntity.ok(notificacaoService.listarNotificacoes(pageable));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<NotificacaoResponseDTO>> listarNotificacoesPorUsuario(
            @PathVariable Long usuarioId,
            Pageable pageable) {
        return ResponseEntity.ok(notificacaoService.listarNotificacoesPorUsuario(usuarioId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacaoResponseDTO> buscarNotificacaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(notificacaoService.buscarNotificacaoPorId(id));
    }

    @PostMapping
    public ResponseEntity<List<NotificacaoResponseDTO>> salvarNotificacao(@Valid @RequestBody NotificacaoRequestDTO notificacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacaoService.salvarNotificacao(notificacao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificacaoResponseDTO> atualizarNotificacao(
            @PathVariable Long id,
            @Valid @RequestBody NotificacaoRequestDTO dto) {
        return ResponseEntity.ok(notificacaoService.atualizarNotificacao(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNotificacao(@PathVariable Long id) {
        notificacaoService.deletarNotificacao(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
