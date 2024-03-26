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

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.config.ConfigurationUtils;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.impl.ActiveMQServerImpl;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.spi.core.security.ActiveMQJAASSecurityManager;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.transaction.TransactionManager;
import org.terasoluna.gfw.common.time.ClockFactory;
import org.terasoluna.gfw.common.time.DefaultClockFactory;

import jakarta.jms.JMSException;
import jp.co.ntt.atrs.app.ExtendedActiveMQConnectionFactory;

/**
 * Define settings for the environment.
 */
@Configuration
public class AtrsEnvConfig {

    /**
     * DataSource.driverClassName property.
     */
    @Value("${database.driverClassName}")
    private String driverClassName;

    /**
     * DataSource.url property.
     */
    @Value("${database.url}")
    private String url;

    /**
     * DataSource.username property.
     */
    @Value("${database.username}")
    private String username;

    /**
     * DataSource.password property.
     */
    @Value("${database.password}")
    private String password;

    /**
     * DataSource.maxTotal property.
     */
    @Value("${cp.maxActive}")
    private Integer maxActive;

    /**
     * DataSource.maxIdle property.
     */
    @Value("${cp.maxIdle}")
    private Integer maxIdle;

    /**
     * DataSource.minIdle property.
     */
    @Value("${cp.minIdle}")
    private Integer minIdle;

    /**
     * DataSource.maxWaitMillis property.
     */
    @Value("${cp.maxWait}")
    private Integer maxWait;

    /**
     * Property databaseName.
     */
    @Value("${database}")
    private String database;

    /**
     * Property jmsMqHost.
     */
    @Value("${jms.mq.host}")
    private String jmsMqHost;

    /**
     * Property jmsMqPort.
     */
    @Value("${jms.mq.port}")
    private String jmsMqPort;

    /**
     * Configure {@link DataSource} bean.
     * @return Bean of configured {@link BasicDataSource}
     */
    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource bean = new BasicDataSource();
        bean.setDriverClassName(driverClassName);
        bean.setUrl(url);
        bean.setUsername(username);
        bean.setPassword(password);
        bean.setDefaultAutoCommit(false);
        bean.setMaxTotal(maxActive);
        bean.setMaxIdle(maxIdle);
        bean.setMinIdle(minIdle);
        bean.setMaxWait(Duration.ofMillis(maxWait));
        return bean;
    }

//    /**
//     * Configuration to set up database during initialization.
//     * @return Bean of configured {@link DataSourceInitializer}
//     */
//    @Bean
//    public DataSourceInitializer dataSourceInitializer() {
//        DataSourceInitializer bean = new DataSourceInitializer();
//        bean.setDataSource(dataSource());
//        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//        databasePopulator.addScript(new ClassPathResource("/database/"
//                + database + "-schema.sql"));
//        databasePopulator.addScript(new ClassPathResource("/database/"
//                + database + "-dataload.sql"));
//        databasePopulator.setSqlScriptEncoding("UTF-8");
//        databasePopulator.setIgnoreFailedDrops(true);
//        bean.setDatabasePopulator(databasePopulator);
//        return bean;
//    }

    /**
     * Configure {@link TransactionManager} bean.
     * @return Bean of configured {@link DataSourceTransactionManager}
     */
    @Bean("transactionManager")
    public TransactionManager transactionManager() {
        DataSourceTransactionManager bean = new DataSourceTransactionManager();
        bean.setDataSource(dataSource());
        return bean;
    }

    /**
     * Configure {@link ClockFactory} bean.
     * @return Bean of configured {@link DefaultClockFactory}
     */
    @Bean("dateFactory")
    public ClockFactory dateFactory() {
        return new DefaultClockFactory();
    }

    /**
     * Configure {@link ActiveMQServer} bean.
     * @return Bean of configured {@link ActiveMQServer}
     */
    @Bean(name="activeMQServer", initMethod = "start", destroyMethod = "stop")
    public ActiveMQServer activeMQServer() {
        return new ActiveMQServerImpl(activeMQServerConfig(), activeMQJAASSecurityManager());
    }

    /**
     * Configure {@link ConfigurationImpl} bean.
     * @return Bean of configured {@link ConfigurationImpl}
     */
    @Bean("activeMQServerConfig")
    public ConfigurationImpl activeMQServerConfig() {
        ConfigurationImpl config = new ConfigurationImpl();
        config.setAcceptorConfigurations(activeMQServerAcceptorConfig());
        config.setSecurityEnabled(false);
        return config;
    }

    /**
     * Configure {@link TransportConfiguration} bean.
     * @return List of configured {@link TransportConfiguration}
     */
    @Bean("activeMQServerAcceptorConfig")
    public Set<TransportConfiguration> activeMQServerAcceptorConfig() {
        List<TransportConfiguration> list = ConfigurationUtils.parseAcceptorURI("tcp", "tcp://" + jmsMqHost + ":"
                + jmsMqPort);
        return new HashSet<TransportConfiguration>(list);
    }

    /**
     * Configure {@link ActiveMQJAASSecurityManager} bean.
     * @return Bean of configured {@link ActiveMQJAASSecurityManager}
     */
    @Bean("activeMQJAASSecurityManager")
    public ActiveMQJAASSecurityManager activeMQJAASSecurityManager() {
        return new ActiveMQJAASSecurityManager();
    }

    /**
     * Configure {@link ActiveMQConnectionFactory} bean.
     * @return Bean of configured {@link ActiveMQConnectionFactory}
     * @throws JMSException JMS base exception.
     */
    @Bean("atrsJmsConnectionFactory")
    public ActiveMQConnectionFactory atrsJmsConnectionFactory() throws JMSException {
        ActiveMQConnectionFactory bean = new ExtendedActiveMQConnectionFactory();
        bean.setBrokerURL("tcp://" + jmsMqHost + ":" + jmsMqPort
                + "/?jms.blobTransferPolicy.uploadUrl=file:/tmp");
        return bean;
    }

    /**
     * Configure {@link DynamicDestinationResolver} bean.
     * @return Bean of configured {@link DynamicDestinationResolver}
     */
    @Bean("destinationResolver")
    public DynamicDestinationResolver destinationResolver() {
        return new DynamicDestinationResolver();
    }

    /**
     * Configure {@link CachingConnectionFactory} bean.
     * @return Bean of configured {@link CachingConnectionFactory}
     * @throws JMSException JMS base exception.
     */
    @Bean("cachingConnectionFactory")
    public CachingConnectionFactory cachingConnectionFactory() throws JMSException {
        CachingConnectionFactory bean = new CachingConnectionFactory();
        bean.setTargetConnectionFactory(atrsJmsConnectionFactory());
        bean.setSessionCacheSize(1);
        return bean;
    }
}
