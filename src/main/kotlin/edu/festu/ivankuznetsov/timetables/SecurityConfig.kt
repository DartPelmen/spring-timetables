package edu.festu.ivankuznetsov.timetables

import org.springframework.aot.generate.ValueCodeGenerator.withDefaults
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.web.server.SecurityWebFilterChain


@EnableWebFluxSecurity

@Configuration
class SecurityConfig {


    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
                .authorizeExchange { exchanges: AuthorizeExchangeSpec ->
                    exchanges
                            .anyExchange().permitAll()
                }
        return http.build()
    }

}