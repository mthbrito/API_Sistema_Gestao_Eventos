package ifpb.app_sistema_gestao_eventos.repository;

import ifpb.app_sistema_gestao_eventos.model.entity.Inscricao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    boolean existsByUsuarioIdAndEventoId(Long usuarioId, Long eventoId);

    long countByEventoId(Long eventoId);

    Page<Inscricao> findAllByUsuarioId(Long usuarioId, Pageable pageable);
}
