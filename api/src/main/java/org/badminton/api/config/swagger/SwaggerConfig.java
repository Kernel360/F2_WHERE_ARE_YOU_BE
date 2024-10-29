package org.badminton.api.config.swagger;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	@Value("${custom.server.https}")
	private String https;
	@Value("${custom.server.local}")
	private String localhost;

	@Bean
	public OpenAPI openAPI() {
		SecurityScheme securityScheme = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")
			.in(SecurityScheme.In.HEADER)
			.name("Authorization");

		Server httpsServer = new Server();
		httpsServer.setUrl(https);
		httpsServer.setDescription("badminton https 서버입니다.");

		Server localServer = new Server();
		localServer.setUrl(localhost);
		localServer.setDescription("local 서버입니다.");

		SecurityRequirement securityRequirement = new SecurityRequirement().addList("Authorization");

		return new OpenAPI()
			.components(new Components().addSecuritySchemes("Authorization", securityScheme))
			.addSecurityItem(securityRequirement)
			.info(apiInfo()).servers(List.of(httpsServer, localServer));
	}

	private Info apiInfo() {
		return new Info()
			.title("배드민턴 칠까 서비스 API 명세서")
			.description("배드민턴 칠까? API 명세서<br><br>" +
				"인증이 필요한 API의 경우, 우측 상단의 'Authorize' 버튼을 클릭하여 " +
				"액세스 토큰을 입력해야 합니다. 토큰 형식: Bearer [your_token_here]")
			.version("1.0.0");
	}

	@Bean
	public ModelResolver modelResolver(ObjectMapper objectMapper) {
		return new ModelResolver(objectMapper);
	}
}