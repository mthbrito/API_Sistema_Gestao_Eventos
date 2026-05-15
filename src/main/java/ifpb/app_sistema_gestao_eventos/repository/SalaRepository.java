package ifpb.app_sistema_gestao_eventos.repository;

import ifpb.app_sistema_gestao_eventos.model.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    boolean existsByNomeAndLocalizacao(String nome, String localizacao);
    boolean existsByNomeAndLocalizacaoAndIdNot(String nome, String localizacao, Long id);
}
