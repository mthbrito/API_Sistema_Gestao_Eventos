package ifpb.app_sistema_gestao_eventos.service;

import ifpb.app_sistema_gestao_eventos.mapper.EventoMapper;
import ifpb.app_sistema_gestao_eventos.model.dto.EventoRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.EventoResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Evento;
import ifpb.app_sistema_gestao_eventos.repository.EventoRepository;
import ifpb.app_sistema_gestao_eventos.repository.SalaRepository;
import ifpb.app_sistema_gestao_eventos.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static ifpb.app_sistema_gestao_eventos.mapper.EventoMapper.toEvento;
import static ifpb.app_sistema_gestao_eventos.mapper.EventoMapper.toEventoResponseDTO;


@Service
public class EventoService {

    private final EventoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;


    public EventoService(EventoRepository eventoRepository, UsuarioRepository usuarioRepository, SalaRepository salaRepository) {
        this.repository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
    }

    public List<EventoResponseDTO> listarEventos() {
        return repository.findAll()
                .stream()
                .map(EventoMapper::toEventoResponseDTO)
                .toList();
    }

    public EventoResponseDTO buscarEventoPorId(Long id) {
        return repository.findById(id)
                .map(EventoMapper::toEventoResponseDTO)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
    }

    public EventoResponseDTO salvarEvento(EventoRequestDTO evento) {
        Evento novoEvento = toEvento(evento);
        novoEvento.setOrganizador(usuarioRepository.findById(evento.organizadorId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
        novoEvento.setSala(salaRepository.findById(evento.salaId()).orElseThrow(() -> new RuntimeException("Sala não encontrada")));
        repository.save(novoEvento);
        return toEventoResponseDTO(novoEvento);
    }

    public EventoResponseDTO atualizarEvento(Long id, EventoRequestDTO evento) {
        Evento eventoAtualizado = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        eventoAtualizado.setTitulo(evento.titulo());
        eventoAtualizado.setDescricao(evento.descricao());
        eventoAtualizado.setDataInicio(evento.dataInicio());
        eventoAtualizado.setDataTermino(evento.dataTermino());
        eventoAtualizado.setTipoEvento(evento.tipoEvento());
        return EventoMapper.toEventoResponseDTO(repository.save(eventoAtualizado));
    }

    public void deletarEvento(Long id) {
        repository.deleteById(id);
    }
}
