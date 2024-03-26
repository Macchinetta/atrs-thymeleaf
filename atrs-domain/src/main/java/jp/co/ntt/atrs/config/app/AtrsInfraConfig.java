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

import javax.sql.DataSource;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

/**
 * Bean definitions for infrastructure layer.
 */
@Configuration
@Import({ AtrsEnvConfig.class })
public class AtrsInfraConfig {

    /**
     * MyBatis設定 Configure {@link SqlSessionFactory} bean.
     * @param dataSource Bean defined by AtrsEnvConfig#dataSource
     * @see jp.co.ntt.atrs.config.app.AtrsEnvConfig#dataSource()
     * @return Bean of configured {@link SqlSessionFactoryBean}
     */
    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage(
                "jp.co.ntt.atrs.domain.model, jp.co.ntt.atrs.domain.repository");
        return bean;
    }

    /**
     * MyBatisがマッパーを自動スキャンするパッケージを設定 Configure {@link MapperScannerConfigurer} bean.
     * @return Bean of configured {@link MapperScannerConfigurer}
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer bean = new MapperScannerConfigurer();
        bean.setBasePackage("jp.co.ntt.atrs.domain.repository");
        return bean;
    }

    /**
     * JMS送受信用の設定 Configure {@link JmsTransactionManager} bean.
     * @param atrsJmsConnectionFactory Bean defined by AtrsEnvConfig#atrsJmsConnectionFactory
     * @see jp.co.ntt.atrs.config.app.AtrsEnvConfig#atrsJmsConnectionFactory()
     * @return Bean of configured {@link JmsTransactionManager}
     */
    @Bean("jmsSendTransactionManager")
    public JmsTransactionManager jmsTransactionManager(
            ActiveMQConnectionFactory atrsJmsConnectionFactory) {
        JmsTransactionManager bean = new JmsTransactionManager();
        bean.setConnectionFactory(atrsJmsConnectionFactory);
        return bean;
    }

    /**
     * Configure {@link JmsTemplate} bean.
     * @param cachingConnectionFactory Bean defined by AtrsEnvConfig#cachingConnectionFactory
     * @see jp.co.ntt.atrs.config.app.AtrsEnvConfig#cachingConnectionFactory()
     * @param destinationResolver Bean defined by AtrsEnvConfig#destinationResolver
     * @see jp.co.ntt.atrs.config.app.AtrsEnvConfig#destinationResolver()
     * @return Bean of configured {@link JmsTemplate}
     */
    @Bean("jmsTemplate")
    public JmsTemplate jmsTemplate(
            CachingConnectionFactory cachingConnectionFactory,
            DynamicDestinationResolver destinationResolver) {
        JmsTemplate bean = new JmsTemplate();
        bean.setConnectionFactory(cachingConnectionFactory);
        bean.setReceiveTimeout(20000);
        bean.setDestinationResolver(destinationResolver);
        return bean;
    }

    /**
     * Configure {@link JmsMessagingTemplate} bean.
     * @param jmsTemplate Bean defined by #jmsTemplate
     * @see #jmsTemplate(CachingConnectionFactory, DynamicDestinationResolver)
     * @return Bean of configured {@link JmsMessagingTemplate}
     */
    @Bean("jmsMessagingTemplate")
    public JmsMessagingTemplate jmsMessagingTemplate(JmsTemplate jmsTemplate) {
        JmsMessagingTemplate bean = new JmsMessagingTemplate();
        bean.setJmsTemplate(jmsTemplate);
        return bean;
    }
}
