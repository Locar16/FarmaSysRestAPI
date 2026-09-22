package br.csi.farmasys;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(
        title = "FarmaSys API",
        version = "1.0",
        description = "API REST para gestão de uma farmácia — CRUDs de Remédios, Clientes e Fornecedores",
        contact = @Contact(name = "Suporte", email = "suporte@farmasys.com")
    )
)
@SpringBootApplication
public class FarmaSysApplication {
    public static void main(String[] args) {
        SpringApplication.run(FarmaSysApplication.class, args);
    }
}
