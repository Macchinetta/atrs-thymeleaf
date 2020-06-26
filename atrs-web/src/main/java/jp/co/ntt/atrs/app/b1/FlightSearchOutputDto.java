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
package jp.co.ntt.atrs.app.b1;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 空席照会画面(TOP画面)出力用DTO。
 * @author NTT 電電次郎
 */
public class FlightSearchOutputDto implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 1456659917361826165L;

    /**
     * 空席照会可能時期(始)。
     */
    private Date beginningPeriod;

    /**
     * 空席照会可能時期(終)。
     */
    private Date endingPeriod;

    /**
     * 復路予約可能時間間隔(分)。
     */
    private Integer reserveIntervalTime;

    /**
     * 空席照会可能時期(始)を取得する。
     * @return 空席照会可能時期(始)
     */
    public Date getBeginningPeriod() {
        return beginningPeriod;
    }

    /**
     * 空席照会可能時期(始)を設定する。
     * @param beginningPeriod 空席照会可能時期(始)
     */
    public void setBeginningPeriod(Date beginningPeriod) {
        this.beginningPeriod = beginningPeriod;
    }

    /**
     * 空席照会可能時期(終)を取得する。
     * @return 空席照会可能時期(終)
     */
    public Date getEndingPeriod() {
        return endingPeriod;
    }

    /**
     * 空席照会可能時期(終)を設定する。
     * @param endingPeriod 空席照会可能時期(終)
     */
    public void setEndingPeriod(Date endingPeriod) {
        this.endingPeriod = endingPeriod;
    }

    /**
     * 復路予約可能時間間隔(分)。
     * @return 復路予約可能時間間隔(分)
     */
    public Integer getReserveIntervalTime() {
        return reserveIntervalTime;
    }

    /**
     * 復路予約可能時間間隔(分)。
     * @param reserveIntervalTime 復路予約可能時間間隔(分)
     */
    public void setReserveIntervalTime(Integer reserveIntervalTime) {
        this.reserveIntervalTime = reserveIntervalTime;
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
