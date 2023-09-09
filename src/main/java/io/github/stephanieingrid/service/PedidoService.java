package io.github.stephanieingrid.service;

import io.github.stephanieingrid.domain.entity.Pedido;
import io.github.stephanieingrid.domain.enums.StatusPedido;
import io.github.stephanieingrid.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar ( PedidoDTO pedidoDTO );
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);



}
