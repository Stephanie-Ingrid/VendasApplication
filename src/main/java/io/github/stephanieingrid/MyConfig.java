package io.github.stephanieingrid;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {


    @Bean(name = "applicationName")
    public String applicationName(){
        return "Sistema de Vendas";
    }
}
