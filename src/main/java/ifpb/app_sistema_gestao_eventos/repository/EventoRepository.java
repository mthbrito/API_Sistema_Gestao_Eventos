package ifpb.app_sistema_gestao_eventos.repository;

import ifpb.app_sistema_gestao_eventos.model.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query("""
                SELECT COUNT(e) > 0 FROM Evento e
                WHERE e.sala.id = :salaId
                AND e.id <> :idIgnorar
                AND e.dataInicio < :termino
                AND e.dataTermino > :inicio
            """)
    boolean existsBySalaIdAndHorarioConflitante(
            @Param("salaId") Long salaId,
            @Param("inicio") LocalDate inicio,
            @Param("termino") LocalDate termino,
            @Param("idIgnorar") Long idIgnorar
    );
}
