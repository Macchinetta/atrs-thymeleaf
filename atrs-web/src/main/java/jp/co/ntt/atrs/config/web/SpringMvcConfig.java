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

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.terasoluna.gfw.common.exception.ExceptionCodeResolver;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.web.codelist.CodeListInterceptor;
import org.terasoluna.gfw.web.exception.HandlerExceptionResolverLoggingInterceptor;
import org.terasoluna.gfw.web.exception.SystemExceptionResolver;
import org.terasoluna.gfw.web.logging.TraceLoggingInterceptor;
import org.terasoluna.gfw.web.mvc.support.CompositeRequestDataValueProcessor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenInterceptor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenRequestDataValueProcessor;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

/**
 * Configure SpringMVC.
 */
@ComponentScan(basePackages = { "jp.co.ntt.atrs.app" })
@EnableAspectJAutoProxy
@EnableWebMvc
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    /**
     * {@inheritDoc}
     */
    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(pageableHandlerMethodArgumentResolver());
        argumentResolvers.add(authenticationPrincipalArgumentResolver());
    }

    /**
     * Configure {@link PageableHandlerMethodArgumentResolver} bean.
     * @return Bean of configured {@link PageableHandlerMethodArgumentResolver}
     */
    @Bean
    public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
        PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new PageableHandlerMethodArgumentResolver();
        pageableHandlerMethodArgumentResolver.setMaxPageSize(100);
        pageableHandlerMethodArgumentResolver.setFallbackPageable(
                pageRequest());
        return pageableHandlerMethodArgumentResolver;
    }

    /**
     * Configure {@link PageRequest} bean.
     * @return Configured {@link PageRequest}
     */
    @Bean
    public PageRequest pageRequest() {
        return PageRequest.of(0, 10);
    }

    /**
     * Configure {@link AuthenticationPrincipalArgumentResolver} bean.
     * @return Bean of configured {@link AuthenticationPrincipalArgumentResolver}
     */
    @Bean
    public AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setPathMatcher(pathMatcher());
    }

    /**
     * Configure {@link PropertySourcesPlaceholderConfigurer} bean.
     * @param properties Property files to be read
     * @return Bean of configured {@link PropertySourcesPlaceholderConfigurer}
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(
            @Value("classpath*:/META-INF/spring/*.properties") Resource... properties) {
        PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
        bean.setLocations(properties);
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations(
                "/resources/", "classpath:META-INF/resources/").setCachePeriod(
                        60 * 60);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        addInterceptor(registry, traceLoggingInterceptor());
        addInterceptorPath(registry, codeListInterceptor());
        addInterceptorPath(registry, transactionTokenInterceptor());
    }

    /**
     * Common processes used in #addInterceptors.
     * @param registry {@link InterceptorRegistry}
     * @param interceptor {@link HandlerInterceptor}
     */
    private void addInterceptor(InterceptorRegistry registry,
            HandlerInterceptor interceptor) {
        registry.addInterceptor(interceptor).addPathPatterns("/**")
                .excludePathPatterns("/resources/**");
    }

    /**
     * Common processes used in #addInterceptors.
     * @param registry {@link InterceptorRegistry}
     * @param interceptor {@link HandlerInterceptor}
     */
    private void addInterceptorPath(InterceptorRegistry registry,
            HandlerInterceptor interceptor) {
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }

    /**
     * Configure {@link TraceLoggingInterceptor} bean.
     * @return Bean of configured {@link TraceLoggingInterceptor}
     */
    @Bean
    public TraceLoggingInterceptor traceLoggingInterceptor() {
        return new TraceLoggingInterceptor();
    }

    /**
     * Configure {@link CodeListInterceptor} bean.
     * @return Bean of configured {@link CodeListInterceptor}
     */
    @Bean
    public CodeListInterceptor codeListInterceptor() {
        CodeListInterceptor codeListInterceptor = new CodeListInterceptor();
        codeListInterceptor.setCodeListIdPattern(Pattern.compile("CL_.+"));
        return codeListInterceptor;
    }

    /**
     * Configure {@link TransactionTokenInterceptor} bean.
     * @return Bean of configured {@link TransactionTokenInterceptor}
     */
    @Bean
    public TransactionTokenInterceptor transactionTokenInterceptor() {
        return new TransactionTokenInterceptor(20);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.beanName();
        registry.viewResolver(thymeleafViewResolver());
    }

    /**
     * Configure {@link ThymeleafViewResolver} bean.
     * @see #templateEngine()
     * @return Bean of configured {@link ThymeleafViewResolver}.
     */
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver bean = new ThymeleafViewResolver();
        bean.setTemplateEngine(templateEngine());
        bean.setCharacterEncoding("UTF-8");
        return bean;
    }

    /**
     * Configure {@link SpringResourceTemplateResolver} bean.
     * @return Bean of configured {@link SpringResourceTemplateResolver}.
     */
    @Bean("templateResolver")
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver bean = new SpringResourceTemplateResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".html");
        bean.setTemplateMode("HTML");
        bean.setCharacterEncoding("UTF-8");
        return bean;
    }

    /**
     * Configure {@link SpringTemplateEngine} bean.
     * @return Bean of configured {@link SpringTemplateEngine}.
     */
    @Bean("templateEngine")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine bean = new SpringTemplateEngine();
        bean.setEnableSpringELCompiler(true);
        bean.setTemplateResolver(templateResolver());
        Set<IDialect> set = new HashSet<>();
        set.add(new SpringSecurityDialect());
        bean.setAdditionalDialects(set);
        return bean;
    }

    /**
     * Configure {@link RequestDataValueProcessor} bean.
     * @return Bean of configured {@link CompositeRequestDataValueProcessor}
     */
    @Bean("requestDataValueProcessor")
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new CompositeRequestDataValueProcessor(transactionTokenRequestDataValueProcessor(), csrfRequestDataValueProcessor());
    }

    /**
     * Configure {@link TransactionTokenRequestDataValueProcessor} bean.
     * @return Bean of configured {@link TransactionTokenRequestDataValueProcessor}
     */
    @Bean
    public TransactionTokenRequestDataValueProcessor transactionTokenRequestDataValueProcessor() {
        return new TransactionTokenRequestDataValueProcessor();
    }

    /**
     * Configure {@link CsrfRequestDataValueProcessor} bean.
     * @return Bean of configured {@link CsrfRequestDataValueProcessor}
     */
    @Bean
    public CsrfRequestDataValueProcessor csrfRequestDataValueProcessor() {
        return new CsrfRequestDataValueProcessor();
    }

    /**
     * Configure {@link SystemExceptionResolver} bean.
     * @param exceptionCodeResolver Bean defined by ApplicationContext#exceptionCodeResolver
     * @see jp.co.ntt.atrs.config.app.ApplicationContextConfig#exceptionCodeResolver()
     * @return Bean of configured {@link SystemExceptionResolver}
     */
    @Bean
    public SystemExceptionResolver systemExceptionResolver(
            ExceptionCodeResolver exceptionCodeResolver) {
        SystemExceptionResolver bean = new SystemExceptionResolver();
        bean.setOrder(3);

        Properties exceptionMappings = new Properties();
        exceptionMappings.setProperty("InvalidTransactionTokenException",
                "common/error/token-error");
        exceptionMappings.setProperty("BadRequestException",
                "common/error/badRequest-error");
        exceptionMappings.setProperty("Exception", "common/error/system-error");
        bean.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();
        statusCodes.setProperty("common/error/token-error", String.valueOf(
                HttpStatus.CONFLICT.value()));
        statusCodes.setProperty("common/error/badRequest-error", String.valueOf(
                HttpStatus.BAD_REQUEST.value()));
        bean.setStatusCodes(statusCodes);

        bean.setDefaultStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        bean.setExceptionCodeResolver(exceptionCodeResolver);
        bean.setPreventResponseCaching(true);

        return bean;
    }

    /**
     * Configure messages logging AOP.
     * @param exceptionLogger Bean defined by ApplicationContext#exceptionLogger
     * @see jp.co.ntt.atrs.config.app.ApplicationContextConfig#exceptionLogger()
     * @return Bean of configured {@link HandlerExceptionResolverLoggingInterceptor}
     */
    @Bean("handlerExceptionResolverLoggingInterceptor")
    public HandlerExceptionResolverLoggingInterceptor handlerExceptionResolverLoggingInterceptor(
            ExceptionLogger exceptionLogger) {
        HandlerExceptionResolverLoggingInterceptor bean = new HandlerExceptionResolverLoggingInterceptor();
        bean.setExceptionLogger(exceptionLogger);
        return bean;
    }

    /**
     * Configure messages logging AOP advisor.
     * @param handlerExceptionResolverLoggingInterceptor Bean defined by #handlerExceptionResolverLoggingInterceptor
     * @see #handlerExceptionResolverLoggingInterceptor(ExceptionLogger)
     * @return Advisor configured for PointCut
     */
    @Bean
    public Advisor handlerExceptionResolverLoggingInterceptorAdvisor(
            HandlerExceptionResolverLoggingInterceptor handlerExceptionResolverLoggingInterceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(
                "execution(* org.springframework.web.servlet.HandlerExceptionResolver.resolveException(..))");
        return new DefaultPointcutAdvisor(pointcut, handlerExceptionResolverLoggingInterceptor);
    }

    /**
     * Configure {@link AntPathMatcher} bean.
     * @return Bean of configured {@link AntPathMatcher}
     */
    @Bean
    public AntPathMatcher pathMatcher() {
        AntPathMatcher bean = new AntPathMatcher();
        bean.setTrimTokens(false);
        return bean;
    }

}
