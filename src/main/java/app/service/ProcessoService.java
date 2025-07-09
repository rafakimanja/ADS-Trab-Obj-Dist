package app.service;

import app.model.Processo;
import org.springframework.beans.factory.annotation.Autowired;
import app.repository.ProcessoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessoService {

    private final ProcessoRepository procRep;

    @Autowired
    public ProcessoService(ProcessoRepository processoRepository){
        this.procRep = processoRepository;
    }

    public Processo salvar(Processo processo) {
        validaProcesso(processo);

        if(procRep.findByNumeroProcesso(processo.getNumeroProcesso()).isPresent()){
            throw new IllegalArgumentException("Número de processo já cadastrado.");
        }

        return procRep.save(processo);
    }

    public List<Processo> buscarTodos() {
        return procRep.findAll();
    }

    public Optional<Processo> buscarPorId(Long id) {
        return procRep.findById(id);
    }

    public Processo atualizar(Processo processoAtualizado) {
        if (!procRep.existsById(processoAtualizado.getId())) {
            throw new RuntimeException("Processo com ID " + processoAtualizado.getId() + " não encontrado.");
        }
        return procRep.save(processoAtualizado);
    }

    public void deletar(Long id) {
        if (!procRep.existsById(id)) {
            throw new RuntimeException("Processo com ID " + id + " não encontrado.");
        }
        procRep.deleteById(id);
    }

    public void validaProcesso(Processo processo) {
        if (processo.getNumeroProcesso() == null || processo.getNumeroProcesso().trim().isEmpty()) {
            throw new IllegalArgumentException("Número do processo não pode ser vazio");
        }
        if (!processo.getNumeroProcesso().matches("[A-Za-z0-9/\\-\\s]+")) {
            throw new IllegalArgumentException("Número do processo contém caracteres inválidos");
        }

        if (processo.getStatus() == null || processo.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Status do processo não pode ser vazio");
        }

        if (processo.getVara() == null || processo.getVara().trim().isEmpty()) {
            throw new IllegalArgumentException("Vara do processo não pode ser vazia");
        }

        if (processo.getCliente() == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }

        if (processo.getAdvogados() == null || processo.getAdvogados().isEmpty()) {
            throw new IllegalArgumentException("O processo deve ter ao menos um advogado associado");
        }
    }
}
