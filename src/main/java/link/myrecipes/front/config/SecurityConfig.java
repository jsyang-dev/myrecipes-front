package link.myrecipes.front.config;

import link.myrecipes.front.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/member/modify", "/recipe/register/**", "/recipe/modify/**", "/recipe/delete/**").authenticated()
                .anyRequest().permitAll()
                .and()

                .formLogin()
                .loginPage("/login").permitAll()
                .and()

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()

                .rememberMe()
                .rememberMeParameter("auto-login")
                .userDetailsService(this.customUserDetailsService)
                .key("auto-login-key");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
