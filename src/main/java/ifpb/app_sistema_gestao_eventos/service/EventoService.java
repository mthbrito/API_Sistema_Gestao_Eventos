package ifpb.app_sistema_gestao_eventos.service;

import ifpb.app_sistema_gestao_eventos.exception.EntidadeJaCadastradaException;
import ifpb.app_sistema_gestao_eventos.exception.EntidadeNaoEncontradaException;
import ifpb.app_sistema_gestao_eventos.exception.RegraDeNegocioException;
import ifpb.app_sistema_gestao_eventos.mapper.EventoMapper;
import ifpb.app_sistema_gestao_eventos.model.dto.EventoRequestDTO;
import ifpb.app_sistema_gestao_eventos.model.dto.EventoResponseDTO;
import ifpb.app_sistema_gestao_eventos.model.entity.Evento;
import ifpb.app_sistema_gestao_eventos.repository.EventoRepository;
import ifpb.app_sistema_gestao_eventos.repository.SalaRepository;
import ifpb.app_sistema_gestao_eventos.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static ifpb.app_sistema_gestao_eventos.mapper.EventoMapper.toEvento;
import static ifpb.app_sistema_gestao_eventos.mapper.EventoMapper.toEventoResponseDTO;


@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;


    public EventoService(EventoRepository eventoRepository, UsuarioRepository usuarioRepository, SalaRepository salaRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
    }

    public Page<EventoResponseDTO> listarEventos(Pageable pageable) {
        return eventoRepository.findAll(pageable)
                .map(EventoMapper::toEventoResponseDTO);
    }

    public EventoResponseDTO buscarEventoPorId(Long id) {
        return eventoRepository.findById(id)
                .map(EventoMapper::toEventoResponseDTO)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado"));
    }

    public EventoResponseDTO salvarEvento(EventoRequestDTO evento) {
        validarDatas(evento.dataInicio(), evento.dataTermino());
        validarDisponibilidaDeSala(evento.salaId(), evento.dataInicio(), evento.dataTermino(), 0L);
        Evento novoEvento = toEvento(evento);
        novoEvento.setOrganizador(usuarioRepository.findById(evento.organizadorId()).orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado")));
        novoEvento.setSala(salaRepository.findById(evento.salaId()).orElseThrow(() -> new EntidadeNaoEncontradaException("Sala não encontrada")));
        return toEventoResponseDTO(eventoRepository.save(novoEvento));
    }

    public EventoResponseDTO atualizarEvento(Long id, EventoRequestDTO evento) {
        Evento eventoAtualizado = eventoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado"));
        validarDatas(evento.dataInicio(), evento.dataTermino());
        validarDisponibilidaDeSala(evento.salaId(), evento.dataInicio(), evento.dataTermino(), id);
        eventoAtualizado.setTitulo(evento.titulo());
        eventoAtualizado.setDescricao(evento.descricao());
        eventoAtualizado.setDataInicio(evento.dataInicio());
        eventoAtualizado.setDataTermino(evento.dataTermino());
        eventoAtualizado.setTipoEvento(evento.tipoEvento());
        eventoAtualizado.setOrganizador(
                usuarioRepository.findById(evento.organizadorId())
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"))
        );
        eventoAtualizado.setSala(
                salaRepository.findById(evento.salaId())
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Sala não encontrada"))
        );
        return EventoMapper.toEventoResponseDTO(eventoRepository.save(eventoAtualizado));
    }

    @Transactional
    public void deletarEvento(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Evento não encontrado");
        }
        eventoRepository.deleteById(id);
    }

    private void validarDatas(LocalDate inicio, LocalDate termino) {
        if (!inicio.isBefore(termino)) {
            throw new RegraDeNegocioException("Data de início deve ser anterior à data de término");
        }
    }

    private void validarDisponibilidaDeSala(Long salaId, LocalDate inicio, LocalDate termino, Long idIgnorar) {
        boolean ocupada = eventoRepository.existsBySalaIdAndHorarioConflitante(salaId, inicio, termino, idIgnorar);
        if (ocupada) {
            throw new EntidadeJaCadastradaException("Sala já está ocupada nesse horário");
        }
    }
}
