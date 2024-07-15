package bg.softuni.tutorme.config;

import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.CustomAuthenticationSuccessHandler;
import bg.softuni.tutorme.service.session.TutorMeUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfiguration(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .authorizeHttpRequests(
            registry -> {
            //Set up which urls are available to whom
                registry
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/courses/add").hasRole("TUTOR")
                    .requestMatchers("/login", "/register").hasRole("ANONYMOUS")
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers("/","/login/login-error", "access-denied", "start-tutoring", "courses/all/{pageNo}").permitAll()
                    .anyRequest().authenticated();
        })
            .formLogin(formLogin ->
                formLogin
                    //where is our custom login form
                    .loginPage("/login")
                    // what is the name of username in the post mapping
                    .usernameParameter("username")
                    // what is the name of password in the post mapping
                    .passwordParameter("password")
                    .successHandler(customAuthenticationSuccessHandler)
//                    .defaultSuccessUrl("/user/dashboard", true)
                    .failureUrl("/login/login-error")
            )
            .logout(logout ->
                // what is the logout url
                logout
                    .logoutUrl("/logout")
                // where to go after successful logout
                    .logoutSuccessUrl("/")
                // invalidate session after logout.
                    .invalidateHttpSession(true)
            )
            .exceptionHandling(
                    exceptionHandling -> exceptionHandling.accessDeniedPage("/access-denied")
            )
            .build();
    }

    @Bean
    public TutorMeUserDetailsService tutorMeUserDetailsService(UserRepository userRepository){
        return new TutorMeUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
