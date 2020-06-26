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
package jp.co.ntt.atrs.domain.repository.reservation;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 予約履歴DTO
 * @author NTT 電電太郎
 */
public class ReservationHistoryDto implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 1870559379510676908L;

    /**
     * 予約番号。
     */
    private String reserveNo;

    /**
     * 予約日付。
     */
    private Date reserveDate;

    /**
     * 合計金額。
     */
    private Integer totalFare;

    /**
     * 予約フライト番号。
     */
    private Integer reserveFlightNo;

    /**
     * 便名。
     */
    private String flightName;

    /**
     * 搭乗日。
     */
    private Date departureDate;

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
     * 予約日付を取得する。
     * @return 予約日付
     */
    public Date getReserveDate() {
        return reserveDate;
    }

    /**
     * 予約日付を設定する。
     * @param reserveNo 予約日付
     */
    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    /**
     * 合計金額を取得する。
     * @return 合計金額
     */
    public Integer getTotalFare() {
        return totalFare;
    }

    /**
     * 合計金額を設定する。
     * @param reserveNo 合計金額
     */
    public void setTotalFare(Integer totalFare) {
        this.totalFare = totalFare;
    }

    /**
     * 予約フライト番号を取得する。
     * @return 予約フライト番号
     */
    public Integer getReserveFlightNo() {
        return reserveFlightNo;
    }

    /**
     * 予約フライト番号を設定する。
     * @param reserveNo 予約フライト番号
     */
    public void setReserveFlightNo(Integer reserveFlightNo) {
        this.reserveFlightNo = reserveFlightNo;
    }

    /**
     * 便名を取得する。
     * @return 便名
     */
    public String getFlightName() {
        return flightName;
    }

    /**
     * 便名を設定する。
     * @param reserveNo 便名
     */
    public void setFlightName(String flightName) {
        this.flightName = flightName;
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
     * @param reserveNo 搭乗日
     */
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * csv出力用文字列を取得する。
     * @return csv出力用文字列
     */
    public String toCsvLineString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                reserveNo, dateFormat.format(reserveDate), totalFare,
                dateFormat.format(departureDate), reserveFlightNo, flightName);
    }

}
