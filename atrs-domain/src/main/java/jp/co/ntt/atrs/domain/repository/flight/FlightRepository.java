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

import jp.co.ntt.atrs.domain.model.BoardingClass;
import jp.co.ntt.atrs.domain.model.FareType;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.FlightMaster;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * フライト情報テーブルにアクセスするリポジトリインターフェース。
 * @author NTT 電電太郎
 */
public interface FlightRepository {

    /**
     * 空席情報検索条件に該当する空席情報を取得する。
     * @param criteria 空席情報検索条件
     * @return 空席情報リスト
     */
    List<Flight> findByVacantSeatSearchCriteria(
            @Param("criteria") VacantSeatSearchCriteriaDto criteria);

    /**
     * 指定したフライトのフライト情報を排他ロックをかけて取得する。</p>
     * @param departureDate 搭乗日
     * @param flightName 便名
     * @param boardingClass 搭乗クラス
     * @param fareType 運賃種別
     * @return 指定したフライトのフライト情報
     */
    Flight findOneForUpdate(@Param("departureDate") Date departureDate,
            @Param("flightName") String flightName,
            @Param("boardingClass") BoardingClass boardingClass,
            @Param("fareType") FareType fareType);

    /**
     * フライト情報テーブルの空席数を更新する。
     * @param flight フライト情報
     * @return 更新件数
     */
    int update(Flight flight);

    /**
     * 全てのフライト基本情報を取得する。
     * @return フライト基本情報リスト
     */
    List<FlightMaster> findAllFlightMaster();

    /**
     * 指定したフライトのフライト情報が存在するかチェックする。
     * @param departureDate 搭乗日
     * @param flightName 便名
     * @param boardingClass 搭乗クラス
     * @param fareType 運賃種別
     * @return 存在する場合は true、 存在しない場合はfalse
     */
    boolean exists(@Param("departureDate") Date departureDate,
            @Param("flightName") String flightName,
            @Param("boardingClass") BoardingClass boardingClass,
            @Param("fareType") FareType fareType);
}
