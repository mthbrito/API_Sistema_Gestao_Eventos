package ifpb.app_sistema_gestao_eventos.controller;

import ifpb.app_sistema_gestao_eventos.model.dto.EventoRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.EventoResponseDTO;
import ifpb.app_sistema_gestao_eventos.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sge/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listarEventos() {
        return ResponseEntity.ok(eventoService.listarEventos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarEventoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.buscarEventoPorId(id));
    }


    @PostMapping
    public ResponseEntity<EventoResponseDTO> salvarEvento(@Valid @RequestBody EventoRequestDTO evento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.salvarEvento(evento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> atualizarEvento(
            @PathVariable Long id,
            @Valid @RequestBody EventoRequestDTO eventoRequestDTO) {
        return ResponseEntity.ok(eventoService.atualizarEvento(id, eventoRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEvento(@PathVariable Long id) {
        eventoService.deletarEvento(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
