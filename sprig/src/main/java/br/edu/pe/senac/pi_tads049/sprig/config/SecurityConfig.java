package br.edu.pe.senac.pi_tads049.sprig.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.edu.pe.senac.pi_tads049.sprig.security.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita @PreAuthorize nos métodos
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
               
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() 
                .requestMatchers(HttpMethod.POST, "/usuarios/cadastro").permitAll()
                .requestMatchers("/error").permitAll() // Importante para tratamento de erros
                
                // 2. ROTAS PROTEGIDAS POR PERFIL
                // GESTOR - Acesso total
                .requestMatchers("/usuarios/**").hasRole("GESTOR")
                .requestMatchers("/fornecedores/**").hasRole("GESTOR")
                .requestMatchers("/relatorios/**").hasRole("GESTOR")
                
                // GESTOR e TÉCNICO - Logística e Estoque
                .requestMatchers("/logistica/**").hasAnyRole("GESTOR", "TECNICO")
                .requestMatchers("/estoque/**").hasAnyRole("GESTOR", "TECNICO")
                .requestMatchers("/armazem/**").hasAnyRole("GESTOR", "TECNICO")
                .requestMatchers("/lotes/**").hasAnyRole("GESTOR", "TECNICO")
                .requestMatchers("/motoristas/**").hasAnyRole("GESTOR", "TECNICO")
                .requestMatchers("/veiculos/**").hasAnyRole("GESTOR", "TECNICO")
                .requestMatchers("/rotas/**").hasAnyRole("GESTOR", "TECNICO")
                
                // TÉCNICO e AGRICULTOR - Rastreabilidade
                .requestMatchers(HttpMethod.GET, "/rastreabilidade/**").hasAnyRole("GESTOR", "TECNICO", "AGRICULTOR")
                
                // AGRICULTOR - Suas entregas
                .requestMatchers("/agricultor/**").hasRole("AGRICULTOR")
                .requestMatchers("/minhas-entregas/**").hasRole("AGRICULTOR")
                
                // ENTREGAS - Acesso por perfil
                .requestMatchers(HttpMethod.POST, "/entregas/**").hasAnyRole("GESTOR", "TECNICO")
                .requestMatchers(HttpMethod.GET, "/entregas/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/entregas/**").hasAnyRole("GESTOR", "TECNICO")
                .requestMatchers(HttpMethod.DELETE, "/entregas/**").hasRole("GESTOR")
                
                // 3. QUALQUER OUTRA ROTA REQUER AUTENTICAÇÃO
                .anyRequest().authenticated() 
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}