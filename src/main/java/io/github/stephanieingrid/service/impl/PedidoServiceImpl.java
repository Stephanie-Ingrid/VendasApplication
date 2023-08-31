package io.github.stephanieingrid.service.impl;


import io.github.stephanieingrid.domain.entity.Cliente;
import io.github.stephanieingrid.domain.entity.ItemPedido;
import io.github.stephanieingrid.domain.entity.Pedido;
import io.github.stephanieingrid.domain.entity.Produto;
import io.github.stephanieingrid.domain.repository.ClientesRepository;
import io.github.stephanieingrid.domain.repository.ItemsPedidoRepository;
import io.github.stephanieingrid.domain.repository.PedidosRepository;
import io.github.stephanieingrid.domain.repository.ProdutosRepository;
import io.github.stephanieingrid.exception.RegraNegocioException;
import io.github.stephanieingrid.rest.dto.ItemPedidoDTO;
import io.github.stephanieingrid.rest.dto.PedidoDTO;
import io.github.stephanieingrid.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidosRepository pedidosRepository;
    private final ClientesRepository clientesRepository;
    private final ProdutosRepository produtosRepository;
    private final ItemsPedidoRepository itemsPedidoRepository;



    @Override
    @Transactional
    public Pedido salvar ( PedidoDTO pedidoDTO ){

        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente =  clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new
                RegraNegocioException("Código de Cliente Inválido."));

        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setTotal(pedidoDTO.getTotal());

        List<ItemPedido> itemsPedido = converterItems(pedido, pedidoDTO.getItems());
        pedidosRepository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items ){
        if (items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items");

        }
        return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: " + idProduto)
                            );

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;


                }).collect(Collectors.toList());

    }
}
