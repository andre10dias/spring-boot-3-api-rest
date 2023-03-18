package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(securedEnabled = true) Habilita a anotação @Secured
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    /**
     * @Bean
     * Serve para exportar uma classe para o Spring,
     * fazendo com que ele consiga carregá-la e realize a sua
     * injeção de dependência em outras classes.
     * */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                //.requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN") somente podem ser executadas por usuários
                //.requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN") autenticados e cujo perfil de acesso seja ADMIN.
                .anyRequest().authenticated()
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //Definindo a ordem dos filtros
                .build();

        /**
         * Definindo a ordem dos filtros
         * Como a autenticação é feita no filtro securityFilter, ele deve ser chamado
         * antes do filtro do Spring "UsernamePasswordAuthenticationFilter.class",
         * onde é verificado se o usuário está autenticado.
         * */
    }

    /**COnfigurando o AuthenticationManager para poder ser injetado*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() throws Exception {
        return new BCryptPasswordEncoder();
    }

}
