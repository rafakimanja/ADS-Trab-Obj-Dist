package app.service;

import app.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository cliRep;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository){
        this.cliRep = clienteRepository;
    }

    public Cliente salvar(Cliente cliente){
        validaCliente(cliente);

        if(cliRep.findByCpfCnpj(cliente.getCpfCnpj()).isPresent()){
            throw new IllegalArgumentException("Número CPF/CNPJ já cadastrado.");
        }

        return cliRep.save(cliente);
    }

    public List<Cliente> buscarTodos() {
        return cliRep.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return cliRep.findById(id);
    }

    public Cliente atualizar(Cliente clienteAtualizado) {
        if (!cliRep.existsById(clienteAtualizado.getId())) {
            throw new RuntimeException("Cliente com ID " + clienteAtualizado.getId() + " não encontrado.");
        }
        return cliRep.save(clienteAtualizado);
    }

    public void deletar(Long id) {
        if (!cliRep.existsById(id)) {
            throw new RuntimeException("Cliente com ID " + id + " não encontrado.");
        }
        cliRep.deleteById(id);
    }

    public void validaCliente(Cliente cliente){

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (cliente.getCpfCnpj() == null || cliente.getCpfCnpj().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF/CNPJ não pode ser vazio");
        }

        String cpfCnpjLimpo = cliente.getCpfCnpj().replaceAll("[^\\d]", "");

        if (!(cpfCnpjLimpo.length() == 11 || cpfCnpjLimpo.length() == 14)) {
            throw new IllegalArgumentException("CPF/CNPJ inválido. Deve conter 11 (CPF) ou 14 (CNPJ) dígitos numéricos");
        }

        if (cliente.getTelefone() == null || cliente.getTelefone() <= 0) {
            throw new IllegalArgumentException("Telefone não e valido");
        }
    }

}
