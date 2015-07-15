package org.revo.spring_angular.config;

import org.revo.spring_angular.config.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;

/**
 * Created by ashraf on 6/3/15.
 */
@Configuration
@EnableWebMvcSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetails;
    private String key = "revo_key_remember";

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/scripts/**/*.{js,html}")
                .antMatchers("/bower_components/**")
                .antMatchers("/i18n/**")
                .antMatchers("/assets/**")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/test/**");
    }

    @Autowired
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Autowired
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Autowired
    private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Autowired
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices())
                .key(key)
                .and()
                .formLogin()
                .loginProcessingUrl("/api/authentication")
                .successHandler(ajaxAuthenticationSuccessHandler)
                .failureHandler(ajaxAuthenticationFailureHandler)
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(ajaxLogoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/activate").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/signup/**", "/auth/**").permitAll()
                .and()
                .headers()
                .frameOptions()
                .disable();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices services = new TokenBasedRememberMeServices(key, userDetails);
        services.setAlwaysRemember(true);
        return services;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
    }
}
