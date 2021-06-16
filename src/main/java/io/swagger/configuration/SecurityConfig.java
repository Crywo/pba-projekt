package io.swagger.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    UserDetailsService userDetailsService;
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/task").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET,"/task/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.DELETE,"/task/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PUT,"/task/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST,"/task").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST,"/user").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/user").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PUT,"/user/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET,"/user/**").hasAnyRole("USER","ADMIN");

        httpSecurity.headers().frameOptions().disable();
    }
    
}
