package spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//используются для настройки и включения безопасности веб-приложения.

/**
 * @Configuration указывает, что класс является конфигурационным классом Spring,
 * который содержит одну или несколько методов, помеченных аннотацией @Bean.
 * Методы, помеченные аннотацией @Bean, создают и конфигурируют объекты,
 * которые затем добавляются в контейнер Spring и могут использоваться в других частях приложения.
 */
@Configuration
/**
 * @EnableWebSecurity включает поддержку веб-безопасности в приложении Spring.
 * Он активирует класс WebSecurityConfigurerAdapter, который позволяет настроить
 * безопасность приложения путем переопределения его методов. Например, вы можете настроить
 * аутентификацию и авторизацию пользователей, указать правила доступа к конечным точкам (URL),
 * настроить кросс-доменную аутентификацию и т. д.
 */
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .regexMatchers("/", "/home")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}