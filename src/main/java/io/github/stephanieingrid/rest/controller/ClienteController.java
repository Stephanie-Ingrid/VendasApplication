package io.github.stephanieingrid.rest.controller;

import io.github.stephanieingrid.domain.entity.Cliente;
import io.github.stephanieingrid.domain.repository.ClientesRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClientesRepository clientes;

    public ClienteController(ClientesRepository clientes) {
        this.clientes = clientes;
    }

    @GetMapping("{id}")
    public Cliente getClienteById( @PathVariable Integer id ) {
       return clientes
               .findById(id)
               .orElseThrow(() -> new ResponseStatusException
                       (HttpStatus.NOT_FOUND, "Cliente não encontrado"));


    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar (@RequestBody Cliente cliente){
        return clientes.save(cliente);

    }
    @DeleteMapping("{id}")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public  void  deletar (@PathVariable Integer id ){
          clientes.findById(id)
                .map(cliente -> {
                    clientes.delete( cliente);
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus
                        .NOT_FOUND,"Cliente não encontrado"));

    }
    @PutMapping("{id}")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void Atualizar (@PathVariable Integer id,
                           @RequestBody Cliente cliente){

         clientes
                .findById(id)
                .map( clienteExitente ->{
                    cliente.setId(clienteExitente.getId());
                    clientes.save(cliente);
                    return clienteExitente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus
                         .NOT_FOUND,"Cliente não encontrado"));

        }
        @GetMapping
        public List<Cliente> Listar (Cliente filtro){
            ExampleMatcher matcher = ExampleMatcher
                                        .matching()
                                        .withIgnoreCase()
                                        .withStringMatcher
                                                (ExampleMatcher.StringMatcher.CONTAINING);

            Example example = Example.of(filtro, matcher);
            return clientes.findAll(example);
        }

    }
