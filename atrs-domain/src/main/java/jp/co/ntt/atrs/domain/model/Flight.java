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
import java.util.Date;

/**
 * フライト情報。
 * @author NTT 電電太郎
 */
public class Flight implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 6473729377909085221L;

    /**
     * 運賃種別。
     */
    private FareType fareType;

    /**
     * 搭乗日。
     */
    private Date departureDate;

    /**
     * 空席数。
     */
    private Integer vacantNum;

    /**
     * フライト基本情報。
     */
    private FlightMaster flightMaster;

    /**
     * 搭乗クラス。
     */
    private BoardingClass boardingClass;

    /**
     * 運賃種別を取得する。
     * @return 運賃種別
     */
    public FareType getFareType() {
        return fareType;
    }

    /**
     * 運賃種別を設定する。
     * @param fareType 運賃種別
     */
    public void setFareType(FareType fareType) {
        this.fareType = fareType;
    }

    /**
     * 搭乗日を取得する。
     * @return 搭乗日
     */
    public Date getDepartureDate() {
        return departureDate;
    }

    /**
     * 搭乗日を設定する。
     * @param departureDate 搭乗日
     */
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * 空席数を取得する。
     * @return 空席数
     */
    public Integer getVacantNum() {
        return vacantNum;
    }

    /**
     * 空席数を設定する。
     * @param vacantNum 空席数
     */
    public void setVacantNum(Integer vacantNum) {
        this.vacantNum = vacantNum;
    }

    /**
     * フライト基本情報を取得する。
     * @return フライト基本情報
     */
    public FlightMaster getFlightMaster() {
        return flightMaster;
    }

    /**
     * フライト基本情報を設定する。
     * @param flightMaster フライト基本情報
     */
    public void setFlightMaster(FlightMaster flightMaster) {
        this.flightMaster = flightMaster;
    }

    /**
     * 搭乗クラスを取得する。</p>
     * @return 搭乗クラス
     */
    public BoardingClass getBoardingClass() {
        return boardingClass;
    }

    /**
     * 搭乗クラスを設定する。
     * @param boardingClass 搭乗クラス
     */
    public void setBoardingClass(BoardingClass boardingClass) {
        this.boardingClass = boardingClass;
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
