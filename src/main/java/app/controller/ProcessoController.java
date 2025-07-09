package app.controller;

import app.model.Processo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.service.ProcessoService;

import java.util.List;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    private final ProcessoService processoService;

    @Autowired
    public ProcessoController(ProcessoService processoService) {
        this.processoService = processoService;
    }

    @PostMapping
    public ResponseEntity<Processo> cadastrar(@RequestBody Processo processo) {
        Processo salvo = processoService.salvar(processo);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Processo>> listar() {
        List<Processo> processos = processoService.buscarTodos();
        return ResponseEntity.ok(processos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processo> buscaProcesso(@PathVariable Long id) {
        return processoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Processo> atualizar(@PathVariable Long id, @RequestBody Processo processoAtualizado) {
        return processoService.buscarPorId(id)
                .map(existing -> {
                    processoAtualizado.setId(id);
                    Processo atualizado = processoService.atualizar(processoAtualizado);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return processoService.buscarPorId(id)
                .map(existing -> {
                    processoService.deletar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
