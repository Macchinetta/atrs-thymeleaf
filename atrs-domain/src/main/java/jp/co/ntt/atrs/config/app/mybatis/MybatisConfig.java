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
package jp.co.ntt.atrs.config.app.mybatis;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeAliasRegistry;

/**
 * Mybatis config.
 */
public class MybatisConfig {

    /**
     * Settings Mybatis config.
     * @return Configured {@link Configuration}
     */
    public static Configuration configuration() {
        Configuration configuration = new Configuration();
        setSettings(configuration);
        setTypeAliases(configuration.getTypeAliasRegistry());
        return configuration;
    }

    /**
     * Settings MyBatis behaves.
     * @param configuration Accepted at configuration
     */
    private static void setSettings(Configuration configuration) {
        // キャメルケースのJavaBeanのプロパティに自動マッピング
        configuration.setMapUnderscoreToCamelCase(true);
        // true: “Lazy Load”/false: “Eager Load” (デフォルト)
        configuration.setLazyLoadingEnabled(true);
        // ”Lazy Load”を実行するタイミングを制御
        configuration.setAggressiveLazyLoading(false);
        // アプリケーション全体のFetchSizeのデフォルト値
        configuration.setDefaultFetchSize(100);
    }

    /**
     * Settings type aliases.
     * @param typeAliasRegistry Accepted at configuration
     */
    private static void setTypeAliases(TypeAliasRegistry typeAliasRegistry) {
        typeAliasRegistry.registerAliases("jp.co.ntt.atrs.domain.model");
        typeAliasRegistry.registerAliases("jp.co.ntt.atrs.domain.repository");
    }
}
