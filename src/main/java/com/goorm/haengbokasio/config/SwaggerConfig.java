package com.goorm.haengbokasio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo For Haengbokasio")
                        .description("애플리케이션 API 문서")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("행복하시오")
                                .email("aden0119@naver.com")));
    }
}
