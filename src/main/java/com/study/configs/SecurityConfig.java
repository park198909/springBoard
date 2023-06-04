package com.study.configs;

import com.study.models.member.LoginFailureHandler;
import com.study.models.member.LoginSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {  // 시큐리티 무력화 - url
        http.formLogin()
                .loginPage("/member/login")
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/member/login");

        // 특정 URL 접속 권한 설정
        http.authorizeHttpRequests()
                .requestMatchers("/mypage/**").authenticated()  // 회원 전용 페이지로 지정
                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")  // 관리자 전용 페이지로 지정
                .anyRequest().permitAll(); // 그외 모든 페이지는 모든 회원이 접근 가능

        http.exceptionHandling()
                        .authenticationEntryPoint((req, resp, e) -> {
                            String URI = req.getRequestURI();

                            if (URI.indexOf("/admin") != -1) {  // 관리자 페이지 요청 시
                                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHORIZED");
                            } else {    // 회원 전용 페이지 요청 시
                                String redirectURL = req.getContextPath() + "/member/login";
                                resp.sendRedirect(redirectURL);
                            }
                        });

        http.headers().frameOptions().sameOrigin();
        
        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {  // 시큐리티 무력화 - 정적경로
        return w->w.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/errors/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
