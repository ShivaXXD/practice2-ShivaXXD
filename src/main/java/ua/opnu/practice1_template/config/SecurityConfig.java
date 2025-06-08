package ua.opnu.practice1_template.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.opnu.practice1_template.security.CustomOAuth2UserService;
import ua.opnu.practice1_template.security.JwtAuthFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Доступ для авторизації/реєстрації
                        .requestMatchers("/api/auth/**", "/oauth2/**").permitAll()

                        // RoomController
                        .requestMatchers(HttpMethod.GET, "/api/rooms/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/rooms/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/rooms/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/rooms/**").hasRole("ADMIN")

                        // EventController
                        .requestMatchers(HttpMethod.GET, "/api/events/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/rooms/**").hasRole("ADMIN")

                        .requestMatchers("/api/events/**").hasRole("ADMIN")

                        // GuestController
                        .requestMatchers(HttpMethod.GET, "/api/guests/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/guests/**").hasRole("ADMIN")

                        // InvitationController
                        .requestMatchers(HttpMethod.GET, "/api/invitations/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/invitations/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/invitations/**").hasRole("ADMIN")

                        // OrganizerController
                        .requestMatchers("/api/organizers/**").hasRole("ADMIN")

                        // UserController
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // TestController
                        .requestMatchers("/api/test/**").authenticated()

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(user -> user.userService(customOAuth2UserService))
                        .defaultSuccessUrl("/api/secure", true)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
