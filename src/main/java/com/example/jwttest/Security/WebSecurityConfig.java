package com.example.jwttest.Security;

import com.example.jwttest.Security.jwt.JwtAuthenticationEntryPoint;
import com.example.jwttest.Security.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /* (1) */
    @Value("${jwt.header-name}")
    private String HEADER_NAME;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //https://returnbliss.tistory.com/13?category=467847 참조
        /* (2) */
        http.csrf().disable()                                                                   //CSRF 관련 설정 disable-허용 추후 삭제 필요
                .formLogin().disable()                                                          //폼 로그인 방식 사용 구분 - 현재 사용 안함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);    //세션 생성 관리 - 안씀

        /* (3) */
        http.headers()
                .cacheControl().disable()                                                       //해더 캐시 관련
                .frameOptions().sameOrigin()                                                    //흠...다른 사이트에서 호출 허용?(iframe 등)
                .httpStrictTransportSecurity().disable();                                       //페이지에 https 여부 알려주는 역할

        /* (4) */
        http.authorizeRequests()                                                                //인증 URL 필터?
                .antMatchers("/").permitAll()                                        //base url 허용
                .antMatchers("/login").permitAll()                                //login url 허용
                .antMatchers("/signup").permitAll()                               //signUp url 허용
                .antMatchers("/resources/**").permitAll()                            //resources url 허용
                .anyRequest().authenticated();                                                  //그외 나머지 인증 검증

        /* (5) */
        http.logout().logoutUrl("/logout").permitAll()                                          //logout url 허용
                .deleteCookies("JSESSIONID")
                .deleteCookies(HEADER_NAME)
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);                                                   //인증정보 삭제 및 세션 무효화

        /* (6) */
        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);                         //인증 실패시 처리

        /* (7) */
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);       //JwtFilter (UsernamePasswordAuthenticationFilter 보다 앞에서)
    }

    /* (8) */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring();
        web.httpFirewall(defaultHttpFirewall());                                                // 더블 슬래시 허용?
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }



}