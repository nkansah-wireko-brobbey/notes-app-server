package notes.notes.config;

import lombok.RequiredArgsConstructor;
import notes.notes.filter.JwtAuthenticationFilter;
import notes.notes.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

   private final JwtAuthenticationFilter jwtAuthenticationFilter;
   private final PasswordEncoder passwordEncoder;
   private final UserService userService;

   @Bean
   public AuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider authenticationProvider
               = new DaoAuthenticationProvider();
       authenticationProvider
               .setUserDetailsService
                       (userService.userDetailsService());

       authenticationProvider.setPasswordEncoder(passwordEncoder);
       return authenticationProvider;
   }

   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
       return configuration.getAuthenticationManager();
   }

   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       httpSecurity
               .csrf(AbstractHttpConfigurer::disable)
               .sessionManagement(session->session.sessionCreationPolicy
                       (SessionCreationPolicy.STATELESS))
               .authorizeHttpRequests(
                        authorize->authorize
                               .requestMatchers(HttpMethod.POST,"/api/users/signup",
                                       "/api/users/signin").permitAll()
                               .requestMatchers(HttpMethod.GET,"/api/test/**").permitAll()
                               .anyRequest().authenticated())
               .authenticationProvider(authenticationProvider())
               .addFilterBefore(jwtAuthenticationFilter,
                      UsernamePasswordAuthenticationFilter.class);

         return httpSecurity.build();

   }

}
