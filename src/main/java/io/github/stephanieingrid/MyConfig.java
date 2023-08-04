package io.github.stephanieingrid;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Development
public class MyConfig {

    @Bean
    public CommandLineRunner executar(){
        return args -> {
            System.out.println("rodando a configuração de desenvolvimento");
        };
    }

}
