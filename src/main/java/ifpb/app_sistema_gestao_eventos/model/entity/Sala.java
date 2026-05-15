package ifpb.app_sistema_gestao_eventos.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_SALA", uniqueConstraints = {@UniqueConstraint(columnNames = {"nome", "localizacao"})})
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 15)
    private String nome;

    @Column(nullable = false)
    private int capacidade;

    @Column(nullable = false, length = 50)
    private String localizacao;

    public Sala(String nome, int capacidade, String localizacao) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
    }
}
