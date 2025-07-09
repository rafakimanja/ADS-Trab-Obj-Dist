package app.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Advogado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroOAB;

    @Column(nullable = false, length = 255)
    private String especialidade;

    @ManyToMany(mappedBy = "advogados")
    private List<Processo> processos;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public String getNumeroOAB() { return numeroOAB; }

    public String getEspecialidade() { return especialidade; }
}
