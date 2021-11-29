package springboot.v2.security.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import springboot.v2.security.example.user.CustomAuthenticationProvider;
import springboot.v2.security.example.user.CustomPasswordEncoder;
import springboot.v2.security.example.user.CustomUserDetailService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService userService;
    private final CustomAuthenticationProvider authProvider;
    private final CustomPasswordEncoder passEncoder;

    public CustomWebSecurityConfigurerAdapter(CustomUserDetailService userService, CustomAuthenticationProvider authProvider, CustomPasswordEncoder passEncoder) {
        this.userService = userService;
        this.authProvider = authProvider;
        this.passEncoder = passEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/", "/auth/*", "/error", "/static/**", "/pages/create"
                ).permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin().loginPage("/auth/login").defaultSuccessUrl("/")
                .permitAll()
            .and()
                .logout().logoutSuccessUrl("/")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
        auth.userDetailsService(userService).passwordEncoder(passEncoder);
    }

}
