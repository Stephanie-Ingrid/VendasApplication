package io.github.stephanieingrid.rest.controller;

import io.github.stephanieingrid.domain.entity.Produto;
import io.github.stephanieingrid.domain.repository.ProdutosRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutosRepository produtosRepository;

    public ProdutoController( ProdutosRepository produtosRepository ){
        this.produtosRepository = produtosRepository;
    }

    @GetMapping("{id}")
    public Produto getProdutoById( @PathVariable Integer id ){

        return produtosRepository
                .findById(id)
                .orElseThrow(() ->new ResponseStatusException
                        ( HttpStatus.NOT_FOUND, "Produto não encontrado" ));
    }

    @PostMapping
    @ResponseStatus ( CREATED )
    public Produto salvar ( @RequestBody Produto produto ){
        return produtosRepository.save(produto);
    }

    @DeleteMapping ("{id}")
    @ResponseStatus ( NO_CONTENT )
    public void deletar ( @PathVariable Integer id ){

        produtosRepository
                .findById(id)
                .map( produto -> {
                    produtosRepository.delete( produto );
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException
                        ( HttpStatus.NOT_FOUND, "Produto não encontrado" ));

    }


    @PutMapping("{id}")
    @ResponseStatus ( NO_CONTENT )
    public void atualizar ( @PathVariable Integer id,
                            @RequestBody  Produto produto ) {

        produtosRepository
                .findById(id)
                .map( produtoExistente -> {
                    produto.setId( produtoExistente.getId() );
                    produtosRepository.save( produto );
                    return produtoExistente;
                }).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto não encontrado" ));
    }

    @GetMapping
    public List<Produto> listar ( Produto filtro ){

        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of( filtro, matcher );
        return produtosRepository.findAll( example );




    }



}
