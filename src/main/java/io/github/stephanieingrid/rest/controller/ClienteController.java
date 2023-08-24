package io.github.stephanieingrid.rest.controller;

import io.github.stephanieingrid.domain.entity.Cliente;
import io.github.stephanieingrid.domain.repository.ClientesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping
public class ClienteController {

    private ClientesRepository clientes;

    public ClienteController(ClientesRepository clientes) {
        this.clientes = clientes;
    }

    @GetMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);

        if (cliente.isPresent()) {
            return ResponseEntity.ok( cliente.get() );

        }
        return ResponseEntity.notFound().build();

    }
}