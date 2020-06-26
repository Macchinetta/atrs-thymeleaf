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
package jp.co.ntt.atrs.api.flight;

import java.io.Serializable;

/**
 * 運賃種別リソース
 * @author NTT 電電太郎
 */
public class FareTypeResource implements Serializable  {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 730209437933903652L;

    /**
     * 運賃種別名。
     */
    private String fareTypeName;

    /**
     * 運賃。
     */
    private String fare;

    /**
     * 空席数。
     */
    private Integer vacantNum;

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
     * 運賃を取得する。
     * @return 運賃
     */
    public String getFare() {
        return fare;
    }

    /**
     * 運賃を設定する。
     * @param fare 運賃
     */
    public void setFare(String fare) {
        this.fare = fare;
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

}
