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

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.gfw.common.codelist.JdbcCodeList;
import org.terasoluna.gfw.common.codelist.NumberRangeCodeList;
import org.terasoluna.gfw.common.codelist.SimpleMapCodeList;

import jakarta.inject.Inject;
import jp.co.ntt.atrs.domain.common.codelist.AirportCodeList;

/**
 * Bean definition regarding CodeLists.
 */
@Configuration
public class AtrsCodeListConfig {

    /**
     * JDBC fetchSize property.
     */
    @Value("${codelist.jdbc.fetchSize:1000}")
    private Integer fetchSize;

    /**
     * Bean of DataSource.
     */
    @Inject
    private DataSource dataSource;

    /**
     * Configure {@link JdbcTemplate} bean.
     * @return Bean of configured {@link JdbcTemplate}
     */
    @Bean("jdbcTemplateForCodeList")
    public JdbcTemplate jdbcTemplateForCodeList() {
        JdbcTemplate bean = new JdbcTemplate();
        bean.setDataSource(dataSource);
        bean.setFetchSize(fetchSize);
        return bean;
    }

    /**
     * Common processing of {@link JdbcCodeList}.
     * @return Bean of configured {@link JdbcCodeList}
     */
    private JdbcCodeList abstractJdbcCodeList() {
        JdbcCodeList bean = new JdbcCodeList();
        bean.setJdbcTemplate(jdbcTemplateForCodeList());
        return bean;
    }

    /**
     * 空港コードリスト.
     * @return Bean of configured {@link AirportCodeList}
     */
    @Bean("CL_AIRPORT")
    public CodeList CL_AIRPORT() {
        AirportCodeList bean = new AirportCodeList();
        bean.setDataSource(dataSource);
        bean.setQuerySql(
                "SELECT airport_cd,airport_name,display_order FROM airport order by display_order ASC");
        bean.setValueColumn("airport_cd");
        bean.setLabelColumn("airport_name");
        bean.setOrderColumn("display_order");
        // 区切り行の表示順
        bean.setAirportNopInsertOrder(100);
        return bean;
    }

    /**
     * 搭乗クラスコードリスト.
     * @return Bean of configured {@link JdbcCodeList}
     */
    @Bean("CL_BOARDINGCLASS")
    public CodeList CL_BOARDINGCLASS() {
        JdbcCodeList bean = abstractJdbcCodeList();
        bean.setDataSource(dataSource);
        bean.setQuerySql(
                "SELECT boarding_class_cd,boarding_class_name FROM boarding_class ORDER BY display_order ASC");
        bean.setValueColumn("boarding_class_cd");
        bean.setLabelColumn("boarding_class_name");
        return bean;
    }

    /**
     * 運賃種別コードリスト.
     * @return Bean of configured {@link JdbcCodeList}
     */
    @Bean("CL_FARETYPE")
    public CodeList CL_FARETYPE() {
        JdbcCodeList bean = abstractJdbcCodeList();
        bean.setDataSource(dataSource);
        bean.setQuerySql(
                "SELECT fare_type_cd, fare_type_name FROM fare_type ORDER BY display_order ASC");
        bean.setValueColumn("fare_type_cd");
        bean.setLabelColumn("fare_type_name");
        return bean;
    }

    /**
     * クレジットカード有効期限年コードリスト.
     * @return Bean of configured {@link NumberRangeCodeList}
     */
    @Bean("CL_CREDITYEAR")
    public CodeList CL_CREDITYEAR() {
        NumberRangeCodeList bean = new NumberRangeCodeList();
        bean.setFrom(0);
        bean.setTo(99);
        bean.setValueFormat("%02d");
        bean.setLabelFormat("%02d");
        return bean;
    }

    /**
     * クレジットカード有効期限月コードリスト.
     * @return Bean of configured {@link NumberRangeCodeList}
     */
    @Bean("CL_CREDITMONTH")
    public CodeList CL_CREDITMONTH() {
        NumberRangeCodeList bean = new NumberRangeCodeList();
        bean.setFrom(1);
        bean.setTo(12);
        bean.setValueFormat("%02d");
        bean.setLabelFormat("%02d");
        return bean;
    }

    /**
     * 性別コードリスト.
     * @return Bean of configured {@link SimpleMapCodeList}
     */
    @Bean("CL_GENDER")
    public CodeList CL_GENDER() {
        SimpleMapCodeList bean = new SimpleMapCodeList();
        Map<String, String> map = new LinkedHashMap<>();
        map.put("M", "男性");
        map.put("F", "女性");
        bean.setMap(map);
        return bean;
    }

    /**
     * クレジットカード種別コードリスト.
     * @return Bean of configured {@link JdbcCodeList}
     */
    @Bean("CL_CREDITTYPE")
    public CodeList CL_CREDITTYPE() {
        JdbcCodeList bean = abstractJdbcCodeList();
        bean.setQuerySql(
                "SELECT credit_type_cd, credit_firm FROM credit_type ORDER BY display_order ASC");
        bean.setValueColumn("credit_type_cd");
        bean.setLabelColumn("credit_firm");
        return bean;
    }

    /**
     * フライト種別コードリスト.
     * @return Bean of configured {@link SimpleMapCodeList}
     */
    @Bean("CL_FLIGHTTYPE")
    public CodeList CL_FLIGHTTYPE() {
        SimpleMapCodeList bean = new SimpleMapCodeList();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("RT", "往復");
        map.put("OW", "片道");
        bean.setMap(map);
        return bean;
    }
}
