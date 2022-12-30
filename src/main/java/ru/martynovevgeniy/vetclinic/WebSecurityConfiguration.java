package ru.martynovevgeniy.vetclinic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .antMatchers("/").permitAll()

                .antMatchers("/user/**").hasAnyRole("USER")

                .antMatchers("/employee/**").hasAnyRole("ADMIN")
                .antMatchers("/animal/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/treatment/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/client/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/report/**").hasAnyRole("ADMIN", "OPERATOR")

                .antMatchers("/visit/list").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/visit/details/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/visit/create/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/visit/delete/**").hasAnyRole("ADMIN")

                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("ZekaZak220@mail.ru")
                        .password("Ctrannik2")
                        .roles("ADMIN")
                        .build();
        UserDetails user1 =
                User.withDefaultPasswordEncoder()
                        .username("Filipph@mail.ru")
                        .password("Filipph")
                        .roles("USER")
                        .build();
        UserDetails user2 =
                User.withDefaultPasswordEncoder()
                        .username("Morozova_Aleks@mail.ru")
                        .password("Morozova_Aleks")
                        .roles("USER")
                        .build();
        UserDetails user3 =
                User.withDefaultPasswordEncoder()
                        .username("Pomidor228@mail.ru")
                        .password("Pomidor228")
                        .roles("OPERATOR")
                        .build();
        UserDetails user4 =
                User.withDefaultPasswordEncoder()
                        .username("Kostya@mail.ru")
                        .password("Kostya")
                        .roles("OPERATOR")
                        .build();
        UserDetails user5 =
                User.withDefaultPasswordEncoder()
                        .username("Novikov1987@mail.ru")
                        .password("Novikov1987")
                        .roles("USER")
                        .build();
        UserDetails user6 =
                User.withDefaultPasswordEncoder()
                        .username("Katya92@mail.ru")
                        .password("Katya92")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user, user1, user2, user3, user4, user5, user6);
    }
}
