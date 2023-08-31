package io.github.stephanieingrid.rest.controller;

import io.github.stephanieingrid.domain.entity.Pedido;
import io.github.stephanieingrid.rest.dto.PedidoDTO;
import io.github.stephanieingrid.service.PedidoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.*;

@Getter@Setter
@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    private PedidoService pedidoService;


    public PedidoController( PedidoService pedidoService ) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus( CREATED )
    public Integer save ( @RequestBody PedidoDTO pedidoDTO ){
        Pedido pedido = pedidoService.salvar( pedidoDTO );
        return pedido.getId();

    }

}
