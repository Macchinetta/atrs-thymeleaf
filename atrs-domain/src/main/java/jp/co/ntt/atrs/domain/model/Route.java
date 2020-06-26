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
 * 区間情報。
 * @author NTT 電電太郎
 */
public class Route implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 4868076356251733173L;

    /**
     * 区間番号。
     */
    private Integer routeNo;

    /**
     * 基本料金。
     */
    private Integer basicFare;

    /**
     * 到着空港。
     */
    private Airport arrivalAirport;

    /**
     * 出発空港。
     */
    private Airport departureAirport;

    /**
     * 区間番号を取得する。
     * @return 区間番号
     */
    public Integer getRouteNo() {
        return routeNo;
    }

    /**
     * 区間番号を設定する。
     * @param routeNo 区間番号
     */
    public void setRouteNo(Integer routeNo) {
        this.routeNo = routeNo;
    }

    /**
     * 基本料金を取得する。
     * @return 基本料金
     */
    public Integer getBasicFare() {
        return basicFare;
    }

    /**
     * 基本料金を設定する。
     * @param basicFare 基本料金
     */
    public void setBasicFare(Integer basicFare) {
        this.basicFare = basicFare;
    }

    /**
     * 到着空港を取得する。
     * @return 到着空港
     */
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    /**
     * 到着空港を設定する。
     * @param arrivalAirport 到着空港
     */
    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    /**
     * 出発空港を取得する。
     * @return 出発空港
     */
    public Airport getDepartureAirport() {
        return departureAirport;
    }

    /**
     * 出発空港を設定する。
     * @param departureAirport 出発空港
     */
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
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
