package br.com.mkcf.personapi.config;

import java.util.Arrays;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("bearer-jwt", new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.description("Api Key access")
								.scheme("bearer")
								.bearerFormat("JWT")
								.in(SecurityScheme.In.HEADER)
								.name("Authorization")
						))
				.addSecurityItem(
						new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")))
				.info(new Info()
						.title("RESTful API Person With Java 11 and Spring Boot 2.4.3")
						.version("v1")
						.description("Some description about your API.")
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));

		/*
		return new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("spring_oauth", new SecurityScheme()
								.type(SecurityScheme.Type.OAUTH2)
								.description("Oauth2 flow")
								.flows(new OAuthFlows()
										.authorizationCode(new OAuthFlow()
												.authorizationUrl("/auth/signin")
												.refreshUrl("/auth/signin")
												.tokenUrl("/auth/signin")
												.scopes(new Scopes())
										)))
						.addSecuritySchemes("api_key", new SecurityScheme()
								.type(SecurityScheme.Type.APIKEY)
								.description("Api Key access")
								.in(SecurityScheme.In.HEADER)
								.name("API-KEY")
						)
						.addParameters("Version", new Parameter()
								.in("header")
								.name("Version")
								.schema(new StringSchema())
								.required(false)))
				.security(Arrays.asList(
						new SecurityRequirement().addList("spring_oauth"),
						new SecurityRequirement().addList("api_key")))

				.info(new Info()
						.title("RESTful API Person With Java 15 and Spring Boot 2.4.1")
						.version("v1")
						.description("Some description about your API.")
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));*/
	}

}
