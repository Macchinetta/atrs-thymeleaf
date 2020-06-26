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
 * 運賃種別情報。
 * @author NTT 電電太郎
 */
public class FareType implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 2499341940346451445L;

    /**
     * 運賃種別コード。
     */
    private FareTypeCd fareTypeCd;

    /**
     * 運賃種別名。
     */
    private String fareTypeName;

    /**
     * 割引率(%)。
     */
    private Integer discountRate;

    /**
     * 予約可能前日数(始)。
     */
    private Integer rsrvAvailableStartDayNum;

    /**
     * 予約可能前日数(終)。
     */
    private Integer rsrvAvailableEndDayNum;

    /**
     * 利用可能最少人数。
     */
    private Integer passengerMinNum;

    /**
     * 運賃種別コードを取得する。
     * @return 運賃種別コード
     */
    public FareTypeCd getFareTypeCd() {
        return fareTypeCd;
    }

    /**
     * 運賃種別コードを設定する。
     * @param fareTypeCd 運賃種別コード
     */
    public void setFareTypeCd(FareTypeCd fareTypeCd) {
        this.fareTypeCd = fareTypeCd;
    }

    /**
     * 運賃種別名を取得する。
     * @return 運賃種別名
     */
    public String getFareTypeName() {
        return fareTypeName;
    }

    /**
     * 運賃種別名を設定する。
     * @param fareTypeName 運賃種別名
     */
    public void setFareTypeName(String fareTypeName) {
        this.fareTypeName = fareTypeName;
    }

    /**
     * 割引率(%)を取得する。
     * @return 割引率(%)
     */
    public Integer getDiscountRate() {
        return discountRate;
    }

    /**
     * 割引率(%)を設定する。
     * @param discountRate 割引率(%)
     */
    public void setDiscountRate(Integer discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * 予約可能前日数(始)を取得する。
     * @return 予約可能前日数(始)
     */
    public Integer getRsrvAvailableStartDayNum() {
        return rsrvAvailableStartDayNum;
    }

    /**
     * 予約可能前日数(始)を設定する。
     * @param rsrvAvailableStartDayNum 予約可能前日数(始)
     */
    public void setRsrvAvailableStartDayNum(Integer rsrvAvailableStartDayNum) {
        this.rsrvAvailableStartDayNum = rsrvAvailableStartDayNum;
    }

    /**
     * 予約可能前日数(終)を取得する。
     * @return 予約可能前日数(終)
     */
    public Integer getRsrvAvailableEndDayNum() {
        return rsrvAvailableEndDayNum;
    }

    /**
     * 予約可能前日数(終)を設定する。
     * @param rsrvAvailableEndDayNum 予約可能前日数(終)
     */
    public void setRsrvAvailableEndDayNum(Integer rsrvAvailableEndDayNum) {
        this.rsrvAvailableEndDayNum = rsrvAvailableEndDayNum;
    }

    /**
     * 利用可能最少人数を取得する。
     * @return 利用可能最少人数
     */
    public Integer getPassengerMinNum() {
        return passengerMinNum;
    }

    /**
     * 利用可能最少人数を設定する。
     * @param passengerMinNum 利用可能最少人数
     */
    public void setPassengerMinNum(Integer passengerMinNum) {
        this.passengerMinNum = passengerMinNum;
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
