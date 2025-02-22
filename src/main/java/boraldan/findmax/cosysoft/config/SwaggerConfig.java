package boraldan.findmax.cosysoft.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Max Finder API")
                        .version("1.0")
                        .description("API для поиска N-ного максимального числа в файле Excel"))
                .servers(List.of(new Server().url("http://localhost:8080")));
    }
}