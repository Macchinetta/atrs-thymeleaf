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
 * クレジットカード種別情報。
 * @author NTT 電電太郎
 */
public class CreditType implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -8051014737368310928L;

    /**
     * クレジットカード種別コード。
     */
    private String creditTypeCd;

    /**
     * クレジットカード会社。
     */
    private String creditFirm;

    /**
     * クレジットカード種別コードを取得する。
     * @return クレジットカード種別コード
     */
    public String getCreditTypeCd() {
        return creditTypeCd;
    }

    /**
     * クレジットカード種別コードを設定する。
     * @param creditTypeCd クレジットカード種別コード
     */
    public void setCreditTypeCd(String creditTypeCd) {
        this.creditTypeCd = creditTypeCd;
    }

    /**
     * クレジットカード会社を取得する。
     * @return クレジットカード会社
     */
    public String getCreditFirm() {
        return creditFirm;
    }

    /**
     * クレジットカード会社を設定する。
     * @param creditFirm クレジットカード会社
     */
    public void setCreditFirm(String creditFirm) {
        this.creditFirm = creditFirm;
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
