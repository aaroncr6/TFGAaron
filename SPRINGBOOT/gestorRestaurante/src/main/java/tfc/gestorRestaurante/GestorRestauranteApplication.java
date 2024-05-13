package tfc.gestorRestaurante;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tfc.gestorRestaurante.models.entity.User;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class GestorRestauranteApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(GestorRestauranteApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}


	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("Gestor Restaurante API")
						.description("TFG API REST")
						.contact(new Contact()
								.name("Aaron")
								.email("aaroncutillas@gmail.com")
								.url("https://portal.edu.gva.es/iesmaciaabela/"))
						.version("1.0"));
	}
}
