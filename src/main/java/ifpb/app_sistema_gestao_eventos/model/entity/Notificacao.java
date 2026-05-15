package ifpb.app_sistema_gestao_eventos.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_NOTIFICACAO")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String mensagem;

    private LocalDate dataEnvio;

    private boolean lida;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Notificacao(String mensagem, boolean lida, Usuario usuario) {
        this.mensagem = mensagem;
        this.dataEnvio = LocalDate.now();
        this.lida = lida;
        this.usuario = usuario;
    }
}
