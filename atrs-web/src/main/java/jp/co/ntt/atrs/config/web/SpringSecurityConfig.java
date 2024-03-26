/*
 * Copyright(c) 2015 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.co.ntt.atrs.config.web;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.DelegatingAccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.terasoluna.gfw.security.web.logging.UserIdMDCPutFilter;

import jp.co.ntt.atrs.app.common.security.AtrsAuthenticationFailureHandler;
import jp.co.ntt.atrs.app.common.security.AtrsAuthenticationSuccessHandler;
import jp.co.ntt.atrs.app.common.security.AtrsLogoutSuccessHandler;
import jp.co.ntt.atrs.app.common.security.AtrsUsernamePasswordAuthenticationFilter;
import jp.co.ntt.atrs.domain.service.a1.AtrsUserDetailsService;

/**
 * Bean definition to configure SpringSecurity.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * Configure ignore security pattern.
     * @return Bean of configured {@link WebSecurityCustomizer}
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/resources/**"));
    }

    /**
     * API認証の設定. Configure {@link SecurityFilterChain} bean.
     * @param http Builder class for setting up authentication and authorization
     * @return Bean of configured {@link SecurityFilterChain}
     * @throws Exception Exception that occurs when setting HttpSecurity
     */
    @Bean
    @Order(1)
    public SecurityFilterChain restFilterChain(
            HttpSecurity http) throws Exception {
        http.securityMatcher(new AntPathRequestMatcher("/api/v1/**"));
        http.sessionManagement(session -> session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(authz -> authz.requestMatchers(
                new AntPathRequestMatcher("/**")).permitAll());
        return http.build();
    }

    /**
     * ログイン認証の設定. Configure {@link SecurityFilterChain} bean.
     * @param http Builder class for setting up authentication and authorization
     * @param atrsUsernamePasswordAuthenticationFilter Bean defined by #atrsUsernamePasswordAuthenticationFilter
     * @return Bean of configured {@link SecurityFilterChain}
     * @throws Exception Exception that occurs when setting HttpSecurity
     */
    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http,
            AtrsUsernamePasswordAuthenticationFilter atrsUsernamePasswordAuthenticationFilter) throws Exception {
        http.addFilterAfter(userIdMDCPutFilter(),
                AnonymousAuthenticationFilter.class);
        http.addFilterAt(atrsUsernamePasswordAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);
        http.logout(logout -> logout
                .logoutUrl("/auth/dologout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler()));
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers(new AntPathRequestMatcher("/member/update")).hasRole("MEMBER")
                .requestMatchers(new AntPathRequestMatcher("/HistoryReport/**")).hasRole("MEMBER")
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll());
        http.sessionManagement(Customizer.withDefaults());
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(loginUrlAuthenticationEntryPoint()));
        return http.build();
    }

    /**
     * ログイン入力チェックフィルター. Configure {@link AtrsUsernamePasswordAuthenticationFilter} bean.
     * @param authenticationManager Bean defined by #authenticationManager
     * @return Bean of configured {@link AtrsUsernamePasswordAuthenticationFilter}
     */
    @Bean("atrsUsernamePasswordAuthenticationFilter")
    public AtrsUsernamePasswordAuthenticationFilter atrsUsernamePasswordAuthenticationFilter(
            AuthenticationManager authenticationManager) {
        AtrsUsernamePasswordAuthenticationFilter bean = new AtrsUsernamePasswordAuthenticationFilter();
        bean.setAuthenticationManager(authenticationManager);
        bean.setAuthenticationFailureHandler(authenticationFailureHandler());
        bean.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        bean.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/auth/dologin", "POST"));
        bean.setUsernameParameter("membershipNumber");
        bean.setPasswordParameter("password");
        bean.setSecurityContextRepository(
                httpSessionSecurityContextRepository());
        return bean;
    }

    /**
     * Configure {@link HttpSessionSecurityContextRepository} bean.
     * @return Bean of configured {@link HttpSessionSecurityContextRepository}
     */
    @Bean("httpSessionSecurityContextRepository")
    public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    /**
     * 認証成功した場合画面遷移Handler. Configure {@link AtrsAuthenticationSuccessHandler} bean.
     * @return Bean of configured {@link AtrsAuthenticationSuccessHandler}
     */
    @Bean("authenticationSuccessHandler")
    public AtrsAuthenticationSuccessHandler authenticationSuccessHandler() {
        AtrsAuthenticationSuccessHandler bean = new AtrsAuthenticationSuccessHandler();
        bean.setTargetUrlParameter("redirectTo");
        bean.setAlwaysUseDefaultTargetUrl(false);
        return bean;
    }

    /**
     * 認証失敗した場合画面遷移Handler. Configure {@link AtrsAuthenticationFailureHandler} bean.
     * @return Bean of configured {@link AtrsAuthenticationFailureHandler}
     */
    @Bean("authenticationFailureHandler")
    public AtrsAuthenticationFailureHandler authenticationFailureHandler() {
        AtrsAuthenticationFailureHandler bean = new AtrsAuthenticationFailureHandler();
        bean.setDefaultFailureUrl("/auth/login?error");
        bean.setUseForward(true);
        return bean;
    }

    /**
     * ログイン画面設定. Configure {@link LoginUrlAuthenticationEntryPoint} bean.
     * @return Bean of configured {@link AtrsAuthenticationFailureHandler}
     */
    @Bean("loginUrlAuthenticationEntryPoint")
    public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        LoginUrlAuthenticationEntryPoint bean = new LoginUrlAuthenticationEntryPoint("/auth/login");
        bean.setUseForward(true);
        return bean;
    }

    /**
     * 認証マネージャ. Configure {@link AuthenticationManager} bean.
     * @param authProvider Bean defined by #authProvider
     * @return Bean of configured {@link AuthenticationManager}
     */
    @Bean("authenticationManager")
    public AuthenticationManager authenticationManager(
            AuthenticationProvider authProvider) {
        return new ProviderManager(authProvider);
    }

    /**
     * Configure {@link AuthenticationProvider} bean.
     * @param atrsUserService Bean defined by #atrsUserService
     * @param passwordEncoder Bean defined by ApplicationContextConfig#passwordEncoder
     * @return Bean of configured {@link DaoAuthenticationProvider}
     */
    @Bean
    public AuthenticationProvider authProvider(
            UserDetailsService atrsUserService,
            @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(atrsUserService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    /**
     * ユーザー情報管理サービス Configure {@link UserDetailsService} bean.
     * @return Bean of configured {@link AtrsUserDetailsService}
     */
    @Bean("atrsUserService")
    public UserDetailsService atrsUserService() {
        return new AtrsUserDetailsService();
    }

    /**
     * ログアウト成功Handler Configure {@link AtrsLogoutSuccessHandler} bean.
     * @return Bean of configured {@link AtrsLogoutSuccessHandler}
     */
    @Bean("logoutSuccessHandler")
    public AtrsLogoutSuccessHandler logoutSuccessHandler() {
        AtrsLogoutSuccessHandler bean = new AtrsLogoutSuccessHandler();
        bean.setDefaultTargetUrl("/");
        return bean;
    }

    /**
     * Configure {@link AccessDeniedHandler} bean.
     * @return Bean of configured {@link DelegatingAccessDeniedHandler}
     */
    @Bean("accessDeniedHandler")
    public AccessDeniedHandler accessDeniedHandler() {
        LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> errorHandlers = new LinkedHashMap<>();

        // Invalid CSRF authenticator error handler
        AccessDeniedHandlerImpl invalidCsrfTokenErrorHandler = new AccessDeniedHandlerImpl();
        invalidCsrfTokenErrorHandler.setErrorPage("/common/error/csrf-error");
        errorHandlers.put(InvalidCsrfTokenException.class,
                invalidCsrfTokenErrorHandler);

        // Default error handler
        AccessDeniedHandlerImpl defaultErrorHandler = new AccessDeniedHandlerImpl();
        return new DelegatingAccessDeniedHandler(errorHandlers, defaultErrorHandler);
    }

    /**
     * Configure {@link DefaultWebSecurityExpressionHandler} bean.
     * @return Bean of configured {@link DefaultWebSecurityExpressionHandler}
     */
    @Bean("webSecurityExpressionHandler")
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        return new DefaultWebSecurityExpressionHandler();
    }

    /**
     * Configure {@link UserIdMDCPutFilter} bean.
     * @return Bean of configured {@link UserIdMDCPutFilter}
     */
    @Bean("userIdMDCPutFilter")
    public UserIdMDCPutFilter userIdMDCPutFilter() {
        UserIdMDCPutFilter bean = new UserIdMDCPutFilter();
        bean.setRemoveValue(true);
        return bean;
    }

}
