package app.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String cpfCnpj;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false)
    private Long telefone;

    @OneToMany(mappedBy = "cliente")
    private List<Processo> processos;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCpfCnpj() { return cpfCnpj; }

    public String getNome() { return nome; }

    public Long getTelefone() { return telefone; }

}
