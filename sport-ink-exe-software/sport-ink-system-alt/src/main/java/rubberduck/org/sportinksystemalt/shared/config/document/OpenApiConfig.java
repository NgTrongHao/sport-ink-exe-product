package rubberduck.org.sportinksystemalt.shared.config.document;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rubberduck.org.sportinksystemalt.shared.common.annotation.CurrentUser;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sportink System API")
                        .version("1.0")
                        .description("Sportink System API Documentation")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .addSecurityItem(new SecurityRequirement().addList("Bear Authentication"))
                .schemaRequirement("Bear Authentication",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));
    }

    @Bean
    public OperationCustomizer hideCurrentUserParam() {
        return (operation, handlerMethod) -> {
            List<Parameter> parameters = operation.getParameters();
            if (parameters != null) {
                java.lang.reflect.Parameter[] methodParameters = handlerMethod.getMethod().getParameters();
                for (java.lang.reflect.Parameter methodParameter : methodParameters) {
                    if (methodParameter.isAnnotationPresent(CurrentUser.class)) {
                        String paramName = methodParameter.getName();
                        parameters.removeIf(p -> p.getName().equals(paramName));
                    }
                }
            }
            return operation;
        };
    }
}
