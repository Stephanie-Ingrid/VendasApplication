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

    private ClientesRepository clientesRepository;

    public ClienteController( ClientesRepository clientesRepository ) {
        this.clientesRepository = clientesRepository;
    }

    @GetMapping("{id}")
    public Cliente getClienteById( @PathVariable Integer id ) {

       return clientesRepository
               .findById(id)
               .orElseThrow(() -> new ResponseStatusException(
                       HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public Cliente salvar ( @RequestBody Cliente cliente ){
        return clientesRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus ( HttpStatus.NO_CONTENT )
    public  void  deletar ( @PathVariable Integer id ){

        clientesRepository.findById(id)
                .map( cliente -> {
                    clientesRepository.delete( cliente );
                    return cliente;
                })
                .orElseThrow( () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Cliente não encontrado" ));
    }

    @PutMapping("{id}")
    @ResponseStatus ( HttpStatus.NO_CONTENT )
    public void atualizar ( @PathVariable Integer id,
                           @RequestBody Cliente cliente ){

        clientesRepository
                .findById(id)
                .map( clienteExitente ->{
                    cliente.setId( clienteExitente.getId() );
                    clientesRepository.save( cliente );
                    return clienteExitente;
                }).orElseThrow( () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Cliente não encontrado"));
        }

        @GetMapping
        public List<Cliente> listar ( Cliente filtro ){

            ExampleMatcher matcher = ExampleMatcher
                                        .matching()
                                        .withIgnoreCase()
                                        .withStringMatcher
                                                (ExampleMatcher.StringMatcher.CONTAINING);

            Example example = Example.of( filtro, matcher );
            return clientesRepository.findAll( example );
        }


    }
