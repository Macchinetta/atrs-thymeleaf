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
 * ピーク時期情報。
 * @author NTT 電電太郎
 */
public class PeakTime implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -924355455452624527L;

    /**
     * ピーク時期コード。
     */
    private String peakTimeCd;

    /**
     * ピーク時期開始日。
     */
    private Date peakStartDate;

    /**
     * ピーク時期終了日。
     */
    private Date peakEndDate;

    /**
     * 積算比率。
     */
    private Integer multiplicationRatio;

    /**
     * ピーク時期コードを取得する。
     * @return ピーク時期コード
     */
    public String getPeakTimeCd() {
        return peakTimeCd;
    }

    /**
     * ピーク時期コードを設定する。
     * @param peakTimeCd ピーク時期コード
     */
    public void setPeakTimeCd(String peakTimeCd) {
        this.peakTimeCd = peakTimeCd;
    }

    /**
     * ピーク時期開始日を取得する。
     * @return ピーク時期開始日
     */
    public Date getPeakStartDate() {
        return peakStartDate;
    }

    /**
     * ピーク時期開始日を設定する。
     * @param peakStartDate ピーク時期開始日
     */
    public void setPeakStartDate(Date peakStartDate) {
        this.peakStartDate = peakStartDate;
    }

    /**
     * ピーク時期終了日を取得する。
     * @return ピーク時期終了日
     */
    public Date getPeakEndDate() {
        return peakEndDate;
    }

    /**
     * ピーク時期終了日を設定する。
     * @param peakEndDate ピーク時期終了日
     */
    public void setPeakEndDate(Date peakEndDate) {
        this.peakEndDate = peakEndDate;
    }

    /**
     * 積算比率を取得する。
     * @return 積算比率
     */
    public Integer getMultiplicationRatio() {
        return multiplicationRatio;
    }

    /**
     * 積算比率を設定する。
     * @param multiplicationRatio 積算比率
     */
    public void setMultiplicationRatio(Integer multiplicationRatio) {
        this.multiplicationRatio = multiplicationRatio;
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
