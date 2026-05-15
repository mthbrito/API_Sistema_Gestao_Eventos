package ifpb.app_sistema_gestao_eventos.model.entity;

import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoPerfil;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_PERFIL")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private TipoPerfil nome;

    @ManyToMany(mappedBy = "perfis")
    private List<Usuario> usuarios;

    public Perfil(TipoPerfil nome) {
        this.nome = nome;
    }
}
