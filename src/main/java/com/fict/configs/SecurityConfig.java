package com.fict.configs;

import com.fict.entities.Customer;
import com.fict.repository.CustomerRepository;
import com.fict.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;

/**
 * Created by stevo on 4/30/17.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends GlobalAuthenticationConfigurerAdapter {


    @Autowired
    private CustomerService customerService;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService());
    }



    @Bean
    UserDetailsService userDetailService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                Customer customer = customerService.findCustomerByEmail(email);
                if(customer != null) {
                    return new User(customer.getEmail(), customer.getPassword(), true, true, true, true,
                            AuthorityUtils.createAuthorityList(customer.getRole().getName()));
                } else {
                    throw new UsernameNotFoundException("could not find the user '" + email + "'");
                }
            }
        };
    }



    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                .httpBasic().and()
                .authorizeRequests()
                    .antMatchers("/resources/**", "/").permitAll()
                    .antMatchers("/api/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/api/**").hasAnyAuthority("NORMAL", "ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .csrf().disable().logout().logoutUrl("/api/logout").logoutSuccessUrl("/").invalidateHttpSession(true);

        }

    }


}
