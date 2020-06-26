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
import java.util.ArrayList;
import java.util.List;

/**
 * 予約フライト情報。
 * @author NTT 電電太郎
 */
public class ReserveFlight implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 7545117277955464624L;

    /**
     * 予約フライト番号。
     */
    private Integer reserveFlightNo;

    /**
     * 予約番号。
     */
    private String reserveNo;

    /**
     * フライト情報。
     */
    private Flight flight;

    /**
     * 搭乗者リスト。
     */
    private List<Passenger> passengerList;

    /**
     * 予約フライト番号を取得する。
     * @return 予約フライト番号
     */
    public Integer getReserveFlightNo() {
        return reserveFlightNo;
    }

    /**
     * 予約フライト番号を設定する。
     * @param reserveFlightNo 予約フライト番号
     */
    public void setReserveFlightNo(Integer reserveFlightNo) {
        this.reserveFlightNo = reserveFlightNo;
    }

    /**
     * 予約番号を取得する。
     * @return 予約番号
     */
    public String getReserveNo() {
        return reserveNo;
    }

    /**
     * 予約番号を設定する。
     * @param reserveNo 予約番号
     */
    public void setReserveNo(String reserveNo) {
        this.reserveNo = reserveNo;
    }

    /**
     * フライト情報を取得する。
     * @return フライト情報
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * フライト情報を設定する。
     * @param flight フライト情報
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * 搭乗者リストを取得する。
     * @return 搭乗者リスト
     */
    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    /**
     * 搭乗者リストを設定する。
     * @param passengerList 搭乗者リスト
     */
    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = new ArrayList<>(passengerList);
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
