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

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.common.exception.ResultMessagesLoggingInterceptor;

import jakarta.inject.Inject;

/**
 * Bean definitions for domain layer.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"jp.co.ntt.atrs.domain"})
@Import({AtrsInfraConfig.class, AtrsCodeListConfig.class})
public class AtrsDomainConfig implements TransactionManagementConfigurer {

    /**
     * default TransactionManager.
     */
    @Inject
    @Qualifier("transactionManager")
    private TransactionManager transactionManager;

    /**
     * トランザクション管理に使用するTransactionManagerを定義する.
     *
     * <pre>
     * ATRSには下記二つのTransactionManagerが存在するため、
     * 本メソッドでどちらのトランザクション管理に使用するTransactionManagerを指定しない場合、
     * トランザクション管理実行時にエラーとなる。
     * ・transactionManager
     * ・jmsSendTransactionManager
     *
     * ※本設定は、TransactionManagementConfigurerを実装し、デフォルトトランザクションを紐づける方法となるが、
     * TransactionManagementConfigurerを実装せず、アプリケーション実装側のTransactionalアノテーションで、
     * 使用するTransactionManagerを明示的に設定する方法でも実装は可能となる。
     *
     * e.g. '@Transactional("transactionManager")'
     * </pre>
     *
     * @return default TransactionManager
     */
    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return transactionManager;
    }

    /**
     * Configure messages logging AOP.
     * @param exceptionLogger Bean defined by ApplicationContextConfig#exceptionLogger
     * @see jp.co.ntt.atrs.config.app.ApplicationContextConfig#exceptionLogger()
     * @return Bean of configured {@link ResultMessagesLoggingInterceptor}
     */
    @Bean
    public ResultMessagesLoggingInterceptor resultMessagesLoggingInterceptor(
            ExceptionLogger exceptionLogger) {
        ResultMessagesLoggingInterceptor bean = new ResultMessagesLoggingInterceptor();
        bean.setExceptionLogger(exceptionLogger);
        return bean;
    }

    /**
     * Configure messages logging AOP advisor.
     * @param resultMessagesLoggingInterceptor Bean defined by #resultMessagesLoggingInterceptor
     * @see #resultMessagesLoggingInterceptor(ExceptionLogger)
     * @return Advisor configured for PointCut
     */
    @Bean
    public Advisor resultMessagesLoggingInterceptorAdvisor(
            ResultMessagesLoggingInterceptor resultMessagesLoggingInterceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("@within(org.springframework.stereotype.Service)");
        return new DefaultPointcutAdvisor(pointcut, resultMessagesLoggingInterceptor);
    }

}
