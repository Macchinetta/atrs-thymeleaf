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
package jp.co.ntt.atrs.domain.common.codelist;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import org.terasoluna.gfw.common.codelist.AbstractReloadableCodeList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * 空港コードリストクラス。
 * <p>
 * キー：空港コード, 値：空港名。
 * </p>
 * @author NTT 電電太郎
 */
public class AirportCodeList extends AbstractReloadableCodeList {

    /**
     * 空港名の区切り行の表示順。
     */
    private int airportNopInsertOrder;

    /**
     * 空港名の区切り行の値
     */
    private String airportNopValue = "NOP";

    /**
     * 空港名の区切り行の表示名
     */
    private String airportNopName = "-----------";

    /**
     * DB接続部品。
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * 検索用SQL文。
     */
    private String querySql;

    /**
     * 値のカラム名。
     */
    private String valueColumn;

    /**
     * 表示ラベルのカラム名。
     */
    private String labelColumn;

    /**
     * 表示順のカラム名。
     */
    private String orderColumn;

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, String> retrieveMap() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(querySql);
        Map<String, String> result = new LinkedHashMap<>();
        boolean nopInserted = false;
        for (Map<String, Object> row : rows) {
            Object key = row.get(valueColumn);
            Object value = row.get(labelColumn);
            Object order = row.get(orderColumn);

            if (key == null || value == null || order == null) {
                continue;
            }

            if (!nopInserted) {
                int displayOrder = ((Integer) order).intValue();
                if (airportNopInsertOrder <= displayOrder) {
                    // 区切り行を挿入
                    result.put(airportNopValue, airportNopName);
                    nopInserted = true;
                }
            }

            result.put(key.toString(), value.toString());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() {
        Assert.hasLength(querySql, "querySql is empty");
        Assert.hasLength(valueColumn, "valueColumn is empty");
        Assert.hasLength(labelColumn, "labelColumn is empty");
        Assert.hasLength(orderColumn, "orderColumn is empty");
        Assert.notNull(jdbcTemplate, "jdbcTemplate (or dataSource) is empty");
        Assert.notNull(airportNopValue, "airportNopValue is null");
        Assert.hasLength(airportNopName, "airportNopName is empty");
        super.afterPropertiesSet();
    }

    /**
     * 空港名の区切り行の表示順を設定する。
     * @param airportNopInsertOrder 空港名の区切り行の表示順
     */
    public void setAirportNopInsertOrder(int airportNopInsertOrder) {
        this.airportNopInsertOrder = airportNopInsertOrder;
    }

    /**
     * 空港名の区切り行の値を設定する。
     * <p>
     * デフォルト値は「""」(ブランク)。
     * </p>
     * @param airportNopValue 空港名の区切り行の値
     */
    public void setAirportNopValue(String airportNopValue) {
        this.airportNopValue = airportNopValue;
    }

    /**
     * 空港名の区切り行の表示名を設定する。
     * <p>
     * デフォルト値は「"-----------"」。
     * </p>
     * @param airportNopName 空港名の区切り行の表示名
     */
    public void setAirportNopName(String airportNopName) {
        this.airportNopName = airportNopName;
    }

    /**
     * データソースを設定する。
     * @param dataSource データソース
     */
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * JdbcTemplateを設定する。
     * @param jdbcTemplate JdbcTemplateオブジェクト
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * ラベル表示カラム名を設定する。
     * @param labelColumn ラベル表示カラム名
     */
    public void setLabelColumn(String labelColumn) {
        this.labelColumn = labelColumn;
    }

    /**
     * 値カラム名を設定する。
     * @param valueColumn 値カラム名
     */
    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }

    /**
     * 表示順カラム名を設定する
     * @param orderColumn 表示順カラム名
     */
    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    /**
     * 検索SQL文を設定する。
     * @param querySql 検索SQL
     */
    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

}
