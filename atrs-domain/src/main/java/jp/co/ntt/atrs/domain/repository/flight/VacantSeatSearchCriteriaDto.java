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
package jp.co.ntt.atrs.domain.repository.flight;

import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.Route;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 空席情報検索条件を保持するクラス。
 * @author NTT 電電太郎
 */
public class VacantSeatSearchCriteriaDto implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -5941791348349458604L;

    /**
     * 搭乗クラス。
     **/
    private BoardingClassCd boardingClass;

    /**
     * 搭乗日。
     **/
    private Date depDate;

    /**
     * 区間情報。
     */
    private Route route;

    /**
     * 搭乗日前日数。
     */
    private Integer beforeDayNum;

    /**
     * 運賃種別コードリスト。
     */
    private List<FareTypeCd> fareTypeList;

    /**
     * コンストラクタ。
     * @param depDate 出発日
     * @param route 区間情報
     * @param boardingClass 搭乗クラス
     * @param beforeDayNum 搭乗日前日数
     * @param fareTypeList 運賃種別コードリスト
     */
    public VacantSeatSearchCriteriaDto(Date depDate, Route route,
            BoardingClassCd boardingClass, Integer beforeDayNum,
            List<FareTypeCd> fareTypeList) {

        this.depDate = depDate;
        this.route = route;
        this.boardingClass = boardingClass;
        this.beforeDayNum = beforeDayNum;
        this.fareTypeList = fareTypeList;
    }

    /**
     * 搭乗クラスを取得する。
     * @return 搭乗クラス
     */
    public BoardingClassCd getBoardingClass() {
        return boardingClass;
    }

    /**
     * 出発日を取得する。
     * @return 出発日
     */
    public Date getDepDate() {
        return depDate;
    }

    /**
     * 区間情報を取得する。
     * @return 区間情報
     */
    public Route getRoute() {
        return route;
    }

    /**
     * 搭乗日前日数を取得する。
     * @return 区間情報
     */
    public Integer getBeforeDayNum() {
        return beforeDayNum;
    }

    /**
     * 運賃種別コードリストを取得する。
     * @return 運賃種別コードリスト
     */
    public List<FareTypeCd> getFareTypeList() {
        return fareTypeList;
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
