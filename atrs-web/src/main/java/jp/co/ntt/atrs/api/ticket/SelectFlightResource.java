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
package jp.co.ntt.atrs.api.ticket;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FareTypeCd;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 選択フライトリソース
 * @author NTT 電電太郎
 */
public class SelectFlightResource implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 7028288211464509709L;

    /**
     * 搭乗日。
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date depDate;

    /**
     * 便名。
     */
    @NotNull
    private String flightName;

    /**
     * 搭乗クラスコード。
     */
    @NotNull
    private BoardingClassCd boardingClassCd;

    /**
     * 運賃種別コード。
     */
    @NotNull
    private FareTypeCd fareTypeCd;

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
     * 便名を取得する。
     * @return 便名
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
}
