package app.controller;

import app.model.Advogado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.service.AdvogadoService;

import java.util.List;

@RestController
@RequestMapping("/advogados")
public class AdvogadoController {

    private final AdvogadoService advogadoService;

    @Autowired
    public AdvogadoController(AdvogadoService advogadoService){
        this.advogadoService = advogadoService;
    }

    @PostMapping
    public ResponseEntity<Advogado> cadastrar(@RequestBody Advogado advogado){
        Advogado salvo = advogadoService.salvar(advogado);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Advogado>> listar(){
        List<Advogado> advogados = advogadoService.buscarTodos();
        return ResponseEntity.ok(advogados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Advogado> buscaAdvogado(@PathVariable Long id){
        return advogadoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Advogado> atualizar(@PathVariable Long id, @RequestBody Advogado advogadoAtualizado){
        return advogadoService.buscarPorId(id)
                .map(existing -> {
                    advogadoAtualizado.setId(id);
                    Advogado atualizado = advogadoService.atualizar(advogadoAtualizado);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        return advogadoService.buscarPorId(id)
                .map(existing -> {
                    advogadoService.deletar(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
