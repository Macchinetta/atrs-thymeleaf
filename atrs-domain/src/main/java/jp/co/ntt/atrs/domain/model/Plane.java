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
 * 航空機情報。
 * @author NTT 電電太郎
 */
public class Plane implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 4639680714811348816L;

    /**
     * 機種。
     */
    private String craftType;

    /**
     * 席数(一般席)
     */
    private Integer nSeatNum;

    /**
     * 席数(特別席)
     */
    private Integer sSeatNum;

    /**
     * 機種を取得する。
     * @return 機種
     */
    public String getCraftType() {
        return craftType;
    }

    /**
     * 機種を設定する。
     * @param craftType 機種
     */
    public void setCraftType(String craftType) {
        this.craftType = craftType;
    }

    /**
     * 席数(一般席)を取得する。
     * @return 席数(一般席)
     */
    public Integer getNSeatNum() {
        return nSeatNum;
    }

    /**
     * 席数(一般席)を設定する。
     * @param seatNum 席数(一般席)
     */
    public void setNSeatNum(Integer seatNum) {
        nSeatNum = seatNum;
    }

    /**
     * 席数(特別席)を取得する。
     * @return 席数(特別席)
     */
    public Integer getSSeatNum() {
        return sSeatNum;
    }

    /**
     * 席数(特別席)を設定する。
     * @param seatNum 席数(特別席)
     */
    public void setSSeatNum(Integer seatNum) {
        sSeatNum = seatNum;
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
