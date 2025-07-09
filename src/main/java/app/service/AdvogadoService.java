package app.service;

import app.model.Advogado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.AdvogadoRepository;

import java.util.List;
import java.util.Optional;


@Service
public class AdvogadoService {

    private final AdvogadoRepository advRep;

    @Autowired
    public AdvogadoService(AdvogadoRepository advogadoRepository){
        this.advRep = advogadoRepository;
    }

    public Advogado salvar(Advogado adv){
        validaAdvogado(adv);

        if (advRep.findByNumeroOAB(adv.getNumeroOAB()).isPresent()) {
            throw new IllegalArgumentException("Número OAB já cadastrado.");
        }

        return advRep.save(adv);
    }

    public List<Advogado> buscarTodos() {
        return advRep.findAll();
    }

    public Optional<Advogado> buscarPorId(Long id) {
        return advRep.findById(id);
    }

    public Advogado atualizar(Advogado advAtualizado) {
        if (!advRep.existsById(advAtualizado.getId())) {
            throw new RuntimeException("Advogado com ID " + advAtualizado.getId() + " não encontrado.");
        }

        validaAdvogado(advAtualizado);

        return advRep.save(advAtualizado);
    }

    public void deletar(Long id) {
        if (!advRep.existsById(id)) {
            throw new RuntimeException("Advogado com ID " + id + " não encontrado.");
        }
        advRep.deleteById(id);
    }

    public void validaAdvogado(Advogado advogado){
        if (advogado.getNome() == null || advogado.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (advogado.getNumeroOAB() == null || !advogado.getNumeroOAB().matches("^[A-Z]{2}\\d{6}$")) {
            throw new IllegalArgumentException("Número OAB inválido. Deve seguir o padrão UF999999.");
        }

        if (advogado.getEspecialidade() == null || advogado.getEspecialidade().trim().isEmpty()) {
            throw new IllegalArgumentException("Especialidade não pode ser vazia");
        }
    }
}

