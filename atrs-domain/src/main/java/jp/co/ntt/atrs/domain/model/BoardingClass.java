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
 * 搭乗クラス情報。
 * @author NTT 電電太郎
 */
public class BoardingClass implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -6948735775882604527L;

    /**
     * 搭乗クラスコード。
     */
    private BoardingClassCd boardingClassCd;

    /**
     * 搭乗クラス名。
     */
    private String boardingClassName;

    /**
     * 加算料金。
     */
    private Integer extraCharge;

    /**
     * 搭乗クラスコードを取得する。
     * @return 搭乗クラスコード
     */
    public BoardingClassCd getBoardingClassCd() {
        return boardingClassCd;
    }

    /**
     * 搭乗クラスコードを設定する。
     * @param boardingClassCd 搭乗クラスコード
     */
    public void setBoardingClassCd(BoardingClassCd boardingClassCd) {
        this.boardingClassCd = boardingClassCd;
    }

    /**
     * 搭乗クラス名を取得する。
     * @return 搭乗クラス名
     */
    public String getBoardingClassName() {
        return boardingClassName;
    }

    /**
     * 搭乗クラス名を設定する。
     * @param boardingClassName 搭乗クラス名
     */
    public void setBoardingClassName(String boardingClassName) {
        this.boardingClassName = boardingClassName;
    }

    /**
     * 加算料金を取得する。
     * @return 加算料金
     */
    public Integer getExtraCharge() {
        return extraCharge;
    }

    /**
     * 加算料金を設定する。
     * @param extraCharge 加算料金
     */
    public void setExtraCharge(Integer extraCharge) {
        this.extraCharge = extraCharge;
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
