package ifpb.app_sistema_gestao_eventos.model.entity;

import ifpb.app_sistema_gestao_eventos.model.enumeration.StatusInscricao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_INSCRICAO")
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate dataInscricao;

    @Enumerated(EnumType.STRING)
    private StatusInscricao status;

    private boolean presente;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    public Inscricao(Usuario usuario, Evento evento) {
        this.dataInscricao = LocalDate.now();
        this.status = StatusInscricao.CONFIRMADA;
        this.presente = false;
        this.usuario = usuario;
        this.evento = evento;
    }
}
