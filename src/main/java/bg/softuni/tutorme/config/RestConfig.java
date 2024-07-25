package bg.softuni.tutorme.config;

import bg.softuni.tutorme.service.JwtService;
import bg.softuni.tutorme.service.UserEntityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Configuration
public class RestConfig {
    @Bean("genericRestClient")
    public RestClient genericRestClient() {
        return RestClient.create();
    }

    @Bean("postingRestClient")
    public RestClient offersRestClient() {
        return RestClient
                .builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                .requestInterceptor(requestInterceptor) todo
                .build();
    }

//    @Bean
    public ClientHttpRequestInterceptor requestInterceptor(UserEntityService userService,
                                                           JwtService jwtService) {
        return (r, b, e) -> {
            // put the logged user details into bearer token
            userService
                    .getCurrentUser()
                    .ifPresent(tmud -> {
                        String bearerToken = jwtService.generateToken(
                                tmud.getId() + "",//
                                Map.of(
                                        "roles",
                                        tmud.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                                        "user",
                                        tmud.getId() + ""
                                )
                        );
                        r.getHeaders().setBearerAuth(bearerToken);
                    });

            return e.execute(r, b);
        };
    }

}
