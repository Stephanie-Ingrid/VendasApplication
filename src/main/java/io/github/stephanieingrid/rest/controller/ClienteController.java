package io.github.stephanieingrid.rest.controller;

import io.github.stephanieingrid.domain.entity.Cliente;
import io.github.stephanieingrid.domain.repository.ClientesRepository;
import io.swagger.annotations.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Api("Api Clientes")
public class ClienteController {

    private ClientesRepository clientesRepository;

    public ClienteController( ClientesRepository clientesRepository ) {
        this.clientesRepository = clientesRepository;
    }

    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "cliente encontrado"),
            @ApiResponse(code = 404, message = "cliente não encontrado para o ID informado")})
    public Cliente getClienteById( @PathVariable
                                       @ApiParam("Id do cliente") Integer id ) {

       return clientesRepository
               .findById(id)
               .orElseThrow(() -> new ResponseStatusException(
                       HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    @ApiOperation("Salva um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação")})
    public Cliente salvar ( @RequestBody @Valid Cliente cliente ){
        return clientesRepository.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus ( HttpStatus.NO_CONTENT )
    @ApiOperation("Deleta um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente Deletado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")})
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
    @ApiOperation("Atualiza um cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente Atualizado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado")})
    public void atualizar ( @PathVariable Integer id,
                           @RequestBody @Valid Cliente cliente ){

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
