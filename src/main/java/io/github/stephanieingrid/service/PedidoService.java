package io.github.stephanieingrid.service;

import io.github.stephanieingrid.domain.entity.Pedido;
import io.github.stephanieingrid.rest.dto.PedidoDTO;

public interface PedidoService {

    Pedido salvar ( PedidoDTO pedidoDTO );


}
