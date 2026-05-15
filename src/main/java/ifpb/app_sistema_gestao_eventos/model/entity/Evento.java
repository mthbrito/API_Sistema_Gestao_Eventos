package ifpb.app_sistema_gestao_eventos.model.entity;

import ifpb.app_sistema_gestao_eventos.model.enumeration.TipoEvento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name="TB_EVENTO")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataTermino;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario organizador;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @OneToMany(mappedBy = "evento")
    private List<Inscricao> inscricoes;

    public Evento(String titulo, String descricao, LocalDate dataInicio, LocalDate dataTermino, TipoEvento tipoEvento, Usuario organizador, Sala sala) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.tipoEvento = tipoEvento;
        this.organizador = organizador;
        this.sala = sala;
        this.inscricoes = new ArrayList<>();
    }
}
