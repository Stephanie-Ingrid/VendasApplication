package io.github.stephanieingrid.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException( ) {
        super("Pedido não encontrado.");
    }
}
