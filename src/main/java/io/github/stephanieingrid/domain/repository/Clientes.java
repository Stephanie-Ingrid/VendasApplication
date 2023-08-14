package io.github.stephanieingrid.domain.repository;


import io.github.stephanieingrid.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

   List<Cliente> findByNomeLike(String nome);
}

