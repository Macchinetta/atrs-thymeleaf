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
package jp.co.ntt.atrs.domain.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 空港情報。
 * @author NTT 電電太郎
 */
public class Airport implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -6766420294658404176L;

    /**
     * 空港コード。
     */
    private String code;

    /**
     * 空港名。
     */
    private String name;

    /**
     * 表示順。
     */
    private Integer displayOrder;

    /**
     * 空港コードを取得する。
     * @return 空港コード
     */
    public String getCode() {
        return code;
    }

    /**
     * 空港コードを設定する。
     * @param code 空港コード
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 空港名を取得する。
     * @return 空港名
     */
    public String getName() {
        return name;
    }

    /**
     * 空港名を設定する。
     * @param name 空港名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 表示順を取得する。
     * @return 空港名
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    /**
     * 表示順を設定する。
     * @param displayOrder 表示順
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

}
