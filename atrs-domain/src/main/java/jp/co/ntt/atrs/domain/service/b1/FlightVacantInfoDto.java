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
import jp.co.ntt.atrs.domain.model.FareTypeCd;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 便ごとの空席状況を格納するDTO。
 * @author NTT 電電次郎
 */
public class FlightVacantInfoDto implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 6319431230898145620L;

    /**
     * 便名。
     */
    private String flightName;

    /**
     * 出発空港。
     */
    private String depAirportName;

    /**
     * 到着空港。
     */
    private String arrAirportName;

    /**
     * 出発時刻。
     */
    private String depTime;

    /**
     * 到着時刻。
     */
    private String arrTime;

    /**
     * 搭乗日。
     */
    private String depDate;

    /**
     * 搭乗クラスコード。
     */
    private BoardingClassCd boardingClassCd;

    /**
     * 運賃種別群。
     */
    private Map<String, FareTypeVacantInfoDto> fareTypes = new LinkedHashMap<>();

    /**
     * 便名を取得する。
     * @return 搭乗日
     */
    public String getFlightName() {
        return flightName;
    }

    /**
     * 便名を設定する。
     * @param flightName 便名
     */
    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    /**
     * 出発空港を取得する。
     * @return 出発空港
     */
    public String getDepAirportName() {
        return depAirportName;
    }

    /**
     * 出発空港を設定する。
     * @param depAirportName 出発空港
     */
    public void setDepAirportName(String depAirportName) {
        this.depAirportName = depAirportName;
    }

    /**
     * 到着空港を取得する。
     * @return 到着空港
     */
    public String getArrAirportName() {
        return arrAirportName;
    }

    /**
     * 到着空港を設定する。
     * @param arrAirportName 到着空港
     */
    public void setArrAirportName(String arrAirportName) {
        this.arrAirportName = arrAirportName;
    }

    /**
     * 出発時刻を取得する。
     * @return 出発時刻
     */
    public String getDepTime() {
        return depTime;
    }

    /**
     * 出発時刻を設定する。
     * @param depTime 出発時刻
     */
    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    /**
     * 到着時刻を取得する。
     * @return 到着時刻
     */
    public String getArrTime() {
        return arrTime;
    }

    /**
     * 到着時刻を設定する。
     * @param arrTime 到着時刻
     */
    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    /**
     * 搭乗日を取得する。
     * @return 搭乗日
     */
    public String getDepDate() {
        return depDate;
    }

    /**
     * 搭乗日を設定する。
     * @param depDate 搭乗日
     */
    public void setDepDate(String depDate) {
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
     * 運賃種別群を取得する。
     * @return 運賃種別群
     */
    public Map<String, FareTypeVacantInfoDto> getFareTypes() {
        return fareTypes;
    }

    /**
     * 運賃種別群を設定する。
     * @param fareTypes 運賃種別群
     */
    public void setFareTypes(Map<String, FareTypeVacantInfoDto> fareTypes) {
        this.fareTypes = fareTypes;
    }

    /**
     * 運賃種別群に運賃種別を追加する。
     * @param fareTypeCd 運賃種別コード
     * @param fareTypeVacantInfo 運賃種別に対応する空席照会結果DTO
     */
    public void addFareTypeVacantInfo(FareTypeCd fareTypeCd,
            FareTypeVacantInfoDto fareTypeVacantInfo) {

        fareTypes.put(fareTypeCd.getCode(), fareTypeVacantInfo);
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
