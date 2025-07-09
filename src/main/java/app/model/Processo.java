package app.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String numeroProcesso;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false, length = 50)
    private String vara;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
            name = "processo_advogado",
            joinColumns = @JoinColumn(name = "processo_id"),
            inverseJoinColumns = @JoinColumn(name = "advogado_id")
    )
    private List<Advogado> advogados;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroProcesso() { return numeroProcesso; }

    public String getStatus() { return status; }

    public String getVara() { return vara; }

    public Cliente getCliente() { return cliente; }

    public List<Advogado> getAdvogados() { return advogados; }
}
