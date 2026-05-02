package ifpb.app_sistema_gestao_eventos.repository;

import ifpb.app_sistema_gestao_eventos.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    <T> ScopedValue<T> findByEmail(String email);
}
