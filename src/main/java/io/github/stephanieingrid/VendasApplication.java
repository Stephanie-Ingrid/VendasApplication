package io.github.stephanieingrid;


import io.github.stephanieingrid.domain.entity.Cliente;
import io.github.stephanieingrid.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes) {
        return args -> {

            System.out.println("Salvando clientes");

            clientes.save(new Cliente("Stephanie"));
            clientes.save(new Cliente("Cliente 2"));

            boolean existe = clientes.existsByNome("Stephanie");
            System.out.println("existe um cliente com o nome stephanie? " + existe);


        };
    }
    public static void main (String[]args){
        SpringApplication.run(VendasApplication.class, args);
    }
}
