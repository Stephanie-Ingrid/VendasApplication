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

            List<Cliente> todosClientes = clientes.findAll();
            todosClientes.forEach(System.out::println);

            System.out.println("Atualizando clientes");
            todosClientes.forEach(c ->{
                c.setNome(c.getNome() + " atualizado.");
                clientes.save(c);
            });

            todosClientes = clientes.findAll();
            todosClientes.forEach(System.out::println);

            System.out.println("Bucando cliente");
            clientes.findByNomeLike("Cli").forEach(System.out::println);

            System.out.println("Deletando cliente");
            clientes.findAll().forEach(c ->{
                clientes.delete(c);
            });

            todosClientes = clientes.findAll();
            if (todosClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado. ");

            }else {
                todosClientes.forEach(System.out::println);
            }

        };
    }
    public static void main (String[]args){
        SpringApplication.run(VendasApplication.class, args);
    }
}
