package com.fict.configs;

import com.fict.entities.Customer;
import com.fict.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
                    return new User(customer.getEmail(), customer.getPassword(), customer.isActive(), true, true, true,
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
                    .antMatchers("/resources/**", "/", "/customer/register").permitAll()
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/**").hasAnyAuthority("NORMAL", "ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .csrf().disable().logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true);

        }

    }


}
