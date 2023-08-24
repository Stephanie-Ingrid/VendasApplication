package io.github.stephanieingrid.rest.controller;

import io.github.stephanieingrid.domain.entity.Cliente;
import io.github.stephanieingrid.domain.repository.ClientesRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Controller
@RequestMapping
public class ClienteController {

    private ClientesRepository clientes;

    public ClienteController(ClientesRepository clientes) {
        this.clientes = clientes;
    }

    @GetMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);

        if (cliente.isPresent()) {
            return ResponseEntity.ok( cliente.get() );

        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping ("/api/clientes")
    @ResponseBody
    public ResponseEntity save (@RequestBody Cliente cliente){
        Cliente clienteSalvo = clientes.save(cliente);
        return ResponseEntity.ok(clienteSalvo);

    }
    @DeleteMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity deletar (@PathVariable Integer id ){
        Optional<Cliente> cliente = clientes.findById(id);

        if( cliente.isPresent() ){
            clientes.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity Atualizar (@PathVariable Integer id,
                                     @RequestBody Cliente cliente){

        return clientes
                .findById(id)
                .map( clienteExitente ->{
                    cliente.setId(clienteExitente.getId());
                    clientes.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());

        }
        @GetMapping("/api/clientes")
        @ResponseBody
        public ResponseEntity Listar (Cliente filtro){
            ExampleMatcher matcher = ExampleMatcher
                                        .matching()
                                        .withIgnoreCase()
                                        .withStringMatcher
                                                (ExampleMatcher.StringMatcher.CONTAINING);

            Example example = Example.of(filtro, matcher);
            List<ClientesRepository> lista = clientes.findAll(example);
            return ResponseEntity.ok(lista);
        }

    }
