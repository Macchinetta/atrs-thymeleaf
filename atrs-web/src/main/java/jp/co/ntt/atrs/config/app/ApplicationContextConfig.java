/*
 * Copyright(c) 2024 NTT Corporation.
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
package jp.co.ntt.atrs.config.app;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.util.ErrorHandler;
import org.terasoluna.gfw.common.exception.ExceptionCodeResolver;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.common.exception.SimpleMappingExceptionCodeResolver;
import org.terasoluna.gfw.web.exception.ExceptionLoggingFilter;

/**
 * Application context.
 */
@Configuration
@EnableAspectJAutoProxy
@EnableJms
@ComponentScan(basePackages = {"jp.co.ntt.atrs.listener"})
@Import({AtrsDomainConfig.class})
public class ApplicationContextConfig {

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
     * Configure {@link MessageSource} bean.
     * @return Bean of configured {@link ResourceBundleMessageSource}
     */
    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource bean = new ResourceBundleMessageSource();
        bean.setBasenames("i18n/atrs-messages", "i18n/atrs-fields");
        return bean;
    }

    /**
     * Configure {@link ExceptionLogger} bean.
     * @return Bean of configured {@link ExceptionLogger}
     */
    @Bean("exceptionLogger")
    public ExceptionLogger exceptionLogger() {
        ExceptionLogger bean = new ExceptionLogger();
        bean.setExceptionCodeResolver(exceptionCodeResolver());
        return bean;
    }

    /**
     * Configure {@link ExceptionCodeResolver} bean.
     * @return Bean of configured {@link SimpleMappingExceptionCodeResolver}
     */
    @Bean("exceptionCodeResolver")
    public ExceptionCodeResolver exceptionCodeResolver() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("BusinessException", "e.ar.fw.8001");
        SimpleMappingExceptionCodeResolver bean = new SimpleMappingExceptionCodeResolver();
        bean.setExceptionMappings(map);
        bean.setDefaultExceptionCode("e.ar.fw.9999");
        return bean;
    }

    /**
     * Configure {@link ExceptionLoggingFilter} bean.
     * @return Bean of configured {@link ExceptionLoggingFilter}
     */
    @Bean("exceptionLoggingFilter")
    public ExceptionLoggingFilter exceptionLoggingFilter() {
        ExceptionLoggingFilter bean = new ExceptionLoggingFilter();
        bean.setExceptionLogger(exceptionLogger());
        return bean;
    }

    /**
     * Configure {@link PasswordEncoder} bean.
     * @return Bean of configured {@link DelegatingPasswordEncoder}
     */
    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> idToPasswordEncoder = new HashMap<>();
        idToPasswordEncoder.put("pbkdf2", pbkdf2PasswordEncoder());
        idToPasswordEncoder.put("bcrypt", bCryptPasswordEncoder());
        return new DelegatingPasswordEncoder("pbkdf2", idToPasswordEncoder);
    }

    /**
     * Configure {@link Pbkdf2PasswordEncoder} bean.
     * @return Bean of configured {@link Pbkdf2PasswordEncoder}
     */
    @Bean
    public Pbkdf2PasswordEncoder pbkdf2PasswordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    /**
     * Configure {@link BCryptPasswordEncoder} bean.
     * @return Bean of configured {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure {@link DefaultJmsListenerContainerFactory} bean.
     * @param atrsJmsConnectionFactory Bean defined by AtrsEnvConfig#atrsJmsConnectionFactory
     * @see jp.co.ntt.atrs.config.app.AtrsEnvConfig#atrsJmsConnectionFactory()
     * @param destinationResolver Bean defined by AtrsEnvConfig#destinationResolver
     * @see jp.co.ntt.atrs.config.app.AtrsEnvConfig#destinationResolver()
     * @param atrsJmsErrorHandler Bean defined by {@link ErrorHandler}
     * @see jp.co.ntt.atrs.listener.common.error.AtrsJmsErrorHandler
     * @return Bean of configured {@link DefaultJmsListenerContainerFactory}
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            ActiveMQConnectionFactory atrsJmsConnectionFactory,
            DestinationResolver destinationResolver, ErrorHandler atrsJmsErrorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(atrsJmsConnectionFactory);
        factory.setDestinationResolver(destinationResolver);
        factory.setConcurrency("1");
        factory.setErrorHandler(atrsJmsErrorHandler);
        factory.setCacheLevel(DefaultMessageListenerContainer.CACHE_CONSUMER);
        factory.setSessionTransacted(true);
        return factory;
    }

}
