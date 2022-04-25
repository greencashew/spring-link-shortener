package dev.greencashew.link_shortener.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DocumentationConfiguration {
    @Bean
    public OpenAPI springLinkShortenerDocumentation() {
        return new OpenAPI()
                .info(new Info().title("Link Shortener")
                        .description("""
                                It is fully featured link shortener written in Java 17 and Spring Framework.
                                                        
                                Supported features:
                                - Create/Read/Update/Delete shortened link
                                - Redirect to specific page by short link identifier
                                - Handle business exception like: LinkNotFound, LinkAlreadyExists or IncorrectAdminVerification
                                - Application automatically delete expired links within specified period
                                """)
                        .version("v0.0.1")
                        .contact(new Contact().name("Jan Górkiewicz").url("https://greencashew.dev"))
                        .license(new License().name("Apache 2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project created as educational material of spring course 'Warsztaty Podstawy Springa'.")
                        .url("https://github.com/greencashew/warsztaty-podstawy-springa"));
    }
}
