package com.devh.common.api.configuration;

import com.devh.common.api.filter.JwtAuthFilter;
import com.devh.common.api.service.WebUserDetailsService;
import com.devh.common.util.component.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <pre>
 * Description :
 *     스프링 시큐리티 설정
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021/03/25
 * </pre>
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final WebUserDetailsService webUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.authorizeRequests().anyRequest().permitAll(); /* permit any request */
        // http.authorizeRequests().anyRequest().authenticated(); /* any request need authentication */

        // http.authorizeRequests()
        //     .antMatchers("/api/auth/**").permitAll()
        //     .antMatchers("/api/**").hasRole("USER");
        // /* http basic auth */
        // http.httpBasic();
        /* */
        http
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .authorizeRequests()
                .antMatchers("/auth/webuser/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                // .antMatchers("/api/**").hasRole("USER")
            .and()
            .addFilterBefore(new JwtAuthFilter(webUserDetailsService, jwtTokenUtils), UsernamePasswordAuthenticationFilter.class)
                .cors()
            .and()
            .csrf()
                .disable().headers().frameOptions().disable();
        /* */
        // http.formLogin();
        // http.csrf().disable();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* AuthenticationManagerBuilder : 코드를 통해 인증 매니저를 설정 */
        /* test를 위해 inmemory에 유저를 저장 */
        auth.inMemoryAuthentication()
            .withUser("user")
            // .password("$2a$10$HPsXOkWUd52wnMx37JseMOxcmZezEJDS9uwSH01WVBzOw3CfPK9ry")
            .password(passwordEncoder().encode("1234"))
            // .authorities("USER", "ADMIN")
            .roles("guest");

        /* Database Authentication */
        auth.userDetailsService(webUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    // @Override
    // protected AuthenticationManager authenticationManager() throws Exception {
    //     // TODO Auto-generated method stub
    //     return super.authenticationManager();
    // }
}
