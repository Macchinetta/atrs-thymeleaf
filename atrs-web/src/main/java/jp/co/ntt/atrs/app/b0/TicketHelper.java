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
package jp.co.ntt.atrs.app.b0;

import jp.co.ntt.atrs.app.common.exception.BadRequestException;
import jp.co.ntt.atrs.domain.common.masterdata.BoardingClassProvider;
import jp.co.ntt.atrs.domain.common.masterdata.FareTypeProvider;
import jp.co.ntt.atrs.domain.common.masterdata.FlightMasterProvider;
import jp.co.ntt.atrs.domain.common.util.DateTimeUtil;
import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.FlightType;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * チケット予約共通Helper。
 * @author NTT 電電次郎
 */
@Component
public class TicketHelper {

    /**
     * 搭乗クラス情報提供クラス。
     */
    @Inject
    BoardingClassProvider boardingClassProvider;

    /**
     * フライト基本情報提供クラス。
     */
    @Inject
    FlightMasterProvider flightMasterProvider;

    /**
     * 運賃種別情報提供クラス。
     */
    @Inject
    FareTypeProvider fareTypeProvider;

    /**
     * 選択フライト情報フォームをフライト情報に変換する。
     * @param selectFlightFormList 選択フライト情報フォームのリスト
     * @return フライト情報のリスト
     */
    public List<Flight> toFlightList(List<SelectFlightForm> selectFlightFormList) {

        List<Flight> flightList = new ArrayList<>();

        for (SelectFlightForm selectFlightForm : selectFlightFormList) {
            Flight flight = new Flight();
            flight.setDepartureDate(selectFlightForm.getDepDate());
            String flightName = selectFlightForm.getFlightName();
            flight.setFlightMaster(flightMasterProvider
                    .getFlightMaster(flightName));
            FareTypeCd fareTypeCd = selectFlightForm.getFareTypeCd();
            flight.setFareType(fareTypeProvider.getFareType(fareTypeCd));
            BoardingClassCd boardingClassCd = selectFlightForm
                    .getBoardingClassCd();
            flight.setBoardingClass(boardingClassProvider
                    .getBoardingClass(boardingClassCd));

            flightList.add(flight);
        }

        return flightList;
    }

    /**
     * 選択フライト情報からリダイレクト時の選択フライト情報パラメータマップを生成する。
     * @param reservationFlightForm 予約フライトフォーム
     * @return パラメータマップ
     */
    public Map<String, String> createParameterMapForSelectFlight(
            IReservationFlightForm reservationFlightForm) {

        List<SelectFlightForm> selectFlightFormList = reservationFlightForm
                .getSelectFlightFormList();

        // 選択フライト情報がない場合はエラーとする
        if (CollectionUtils.isEmpty(selectFlightFormList)) {
            throw new BadRequestException("select flight is empty.");
        }

        // フライト種別がない場合はエラーとする
        FlightType flightType = reservationFlightForm.getFlightType();
        if (flightType == null) {
            throw new BadRequestException("flightType is null.");
        }

        // 選択フライト情報をマップに設定
        Map<String, String> params = new LinkedHashMap<>();
        params.put("flightType", flightType.getCode());
        for (int i = 0; i < selectFlightFormList.size(); i++) {
            SelectFlightForm selectFlight = selectFlightFormList.get(i);
            String paramName = "selectFlightFormList[" + i + "]";
            params.put(paramName + ".flightName", selectFlight.getFlightName());
            params.put(paramName + ".fareTypeCd", selectFlight.getFareTypeCd()
                    .getCode());
            params.put(paramName + ".depDate", DateTimeUtil
                    .toFormatDateString(selectFlight.getDepDate()));
            params.put(paramName + ".boardingClassCd", selectFlight
                    .getBoardingClassCd().getCode());
        }

        return params;
    }

}
