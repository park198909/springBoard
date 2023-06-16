package com.study.configs;

import com.study.models.member.LoginFailureHandler;
import com.study.models.member.LoginSuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {  // 시큐리티 무력화 - url
        // 로그인 기본 설정
        http.formLogin(f->f
                .loginPage("/member/login")
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                 );
        // 특정 URL 접속 권한 설정
        http.authorizeHttpRequests(f->f
                .requestMatchers("/mypage/**").authenticated()  // 회원 전용 페이지로 지정
//                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")  // 관리자 전용 페이지로 지정
                .anyRequest().permitAll()); // 그외 모든 페이지는 모든 회원이 접근 가능
        
        // 예외 발생 시 처리 설정
        http.exceptionHandling(f->f
            .authenticationEntryPoint((req, resp, e) -> {
                String URI = req.getRequestURI();   // 현재 접속 경로
                if (URI.indexOf("/admin") != -1) {  // 관리자 페이지 요청 시
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHORIZED");
                    return;
                }   
                // 회원 전용 페이지 요청 시 - 로그인 페이지로
                String url = req.getContextPath() + "/member/login";
                resp.sendRedirect(url);
            }
        ));
        
        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {  // 시큐리티 무력화 - 정적경로
        return w->w.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/errors/**", "/upload/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
