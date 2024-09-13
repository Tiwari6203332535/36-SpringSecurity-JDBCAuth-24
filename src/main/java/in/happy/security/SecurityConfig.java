package in.happy.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;  // Corrected variable name for consistency

    // Configures JDBC authentication
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)  // Use corrected variable name
            .passwordEncoder(new BCryptPasswordEncoder())
            .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
            .authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {

        System.out.println("Request filter executed");

        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/welcome").permitAll()  // Public access
                .requestMatchers("/user").hasAnyRole("ADMIN", "USER")  // Admins and users only
                .requestMatchers("/admin").hasRole("ADMIN")  // Admins only
                .anyRequest().authenticated())  // All other requests need authentication
            .formLogin();  // Login form

        return http.build();
    }
}
