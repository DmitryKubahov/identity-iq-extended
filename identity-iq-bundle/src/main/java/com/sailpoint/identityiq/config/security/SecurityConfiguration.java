package com.sailpoint.identityiq.config.security;

import com.sailpoint.identityiq.security.IdentityIQAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import static com.sailpoint.identityiq.ui.component.login.LoginPage.LOGIN_URL;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = IdentityIQAuthenticationProvider.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private IdentityIQAuthenticationProvider authenticationProvider;

    /**
     * Registers our UserDetailsService and the password encoder to be used on login attempts.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * Require login to access internal pages and configure login form.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // CSRF is handled by Vaadin: https://vaadin.com/framework/security
                .exceptionHandling().accessDeniedPage("/")
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/".concat(LOGIN_URL)))
                .and().logout().logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                // allow Vaadin URLs and the login URL without authentication
                .regexMatchers("/".concat(LOGIN_URL)).permitAll()
                .regexMatchers("/\\?v-r=.*").permitAll()
                // deny any other URL until authenticated
                .antMatchers("/**").fullyAuthenticated();
    }

    /**
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                // Vaadin Flow static resources
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",

                // icons and images
                "/icons/**",
                "/images/**",

                // (development mode) static resources
                "/frontend/**",

                // (development mode) webjars
                "/webjars/**",

                // (development mode) H2 debugging console
                "/h2-console/**",

                // (production mode) static resources
                "/frontend-es5/**", "/frontend-es6/**");
    }
}
