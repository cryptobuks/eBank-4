package com.fict.configs;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

/**
 * Created by stevo on 6/2/17.
 */
@TestConfiguration
public class TestSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
				if (s.equals("kondinskis@gmail.com")) {
					GrantedAuthority authority = new SimpleGrantedAuthority("NORMAL");
					UserDetails userDetails = (UserDetails) new User(s, "123", Arrays.asList(authority));
					return userDetails;
				} else {
					throw new UsernameNotFoundException("Username not found!");
				}
			}
		};
	}

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