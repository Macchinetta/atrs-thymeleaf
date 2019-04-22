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
package jp.co.ntt.atrs.domain.service.b1;

import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FlightType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 空席照会の検索条件を保持するクラス。
 * @author NTT 電電次郎
 */
public class TicketSearchCriteriaDto implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 5851367537869763864L;

    /**
     * フライト種別。
     */
    private FlightType flightType;

    /**
     * 出発空港コード。
     */
    private String departureAirportCd;

    /**
     * 到着空港コード。
     */
    private String arrivalAirportCd;

    /**
     * 搭乗日。
     */
    private Date depDate;

    /**
     * 搭乗クラスコード。
     */
    private BoardingClassCd boardingClassCd;

    /**
     * フライト種別を取得する。
     * @return フライト種別
     */
    public FlightType getFlightType() {
        return flightType;
    }

    /**
     * フライト種別を設定する。
     * @param flightType フライト種別
     */
    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

    /**
     * 出発空港コードを取得する。
     * @return 出発空港
     */
    public String getDepartureAirportCd() {
        return departureAirportCd;
    }

    /**
     * 出発空港コードを設定する。
     * @param departureAirportCd 出発空港コード
     */
    public void setDepartureAirportCd(String departureAirportCd) {
        this.departureAirportCd = departureAirportCd;
    }

    /**
     * 到着空港コードを取得する。
     * @return 到着空港
     */
    public String getArrivalAirportCd() {
        return arrivalAirportCd;
    }

    /**
     * 到着空港コードを設定する。
     * @param arrivalAirportCd 到着空港コード
     */
    public void setArrivalAirportCd(String arrivalAirportCd) {
        this.arrivalAirportCd = arrivalAirportCd;
    }

    /**
     * 搭乗日を取得する。
     * @return 搭乗日
     */
    public Date getDepDate() {
        return depDate;
    }

    /**
     * 搭乗日を設定する。
     * @param depDate 搭乗日
     */
    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

}
