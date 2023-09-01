package io.github.stephanieingrid.rest.controller;

import io.github.stephanieingrid.exception.PedidoNaoEncontradoException;
import io.github.stephanieingrid.exception.RegraNegocioException;
import io.github.stephanieingrid.rest.ApiErrors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler( RegraNegocioException.class )
    @ResponseStatus ( BAD_REQUEST )
    public ApiErrors handleRegraNegocioException ( RegraNegocioException exception ){
        String mensagemErro = exception.getMessage();
        return new ApiErrors( mensagemErro );
    }

    @ExceptionHandler( PedidoNaoEncontradoException.class )
    @ResponseStatus ( NOT_FOUND )
    public ApiErrors hendlePedidoNotFoundException( PedidoNaoEncontradoException exception ){
        return new ApiErrors(exception.getMessage());
    }



}