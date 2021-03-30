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

import jp.co.ntt.atrs.domain.common.exception.AtrsBusinessException;
import jp.co.ntt.atrs.domain.common.masterdata.BoardingClassProvider;
import jp.co.ntt.atrs.domain.common.masterdata.FareTypeProvider;
import jp.co.ntt.atrs.domain.common.masterdata.FlightMasterProvider;
import jp.co.ntt.atrs.domain.common.masterdata.RouteProvider;
import jp.co.ntt.atrs.domain.common.util.DateTimeUtil;
import jp.co.ntt.atrs.domain.common.util.FareTypeUtil;
import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FareType;
import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.FlightMaster;
import jp.co.ntt.atrs.domain.model.FlightType;
import jp.co.ntt.atrs.domain.model.Route;
import jp.co.ntt.atrs.domain.repository.flight.FlightRepository;
import jp.co.ntt.atrs.domain.repository.flight.VacantSeatSearchCriteriaDto;
import jp.co.ntt.atrs.domain.service.b0.TicketSharedService;

import org.joda.time.LocalDate;
import org.joda.time.Days;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 空席照会サービス実装クラス。
 * @author NTT 電電次郎
 */
@Service
@Transactional
public class TicketSearchServiceImpl implements TicketSearchService {

    /**
     * 日付、時刻取得インターフェース。
     */
    @Inject
    JodaTimeDateFactory dateFactory;

    /**
     * フライト情報リポジトリ。
     */
    @Inject
    FlightRepository flightRepository;

    /**
     * 区間情報提供クラス。
     */
    @Inject
    RouteProvider routeProvider;

    /**
     * 運賃種別情報提供クラス。
     */
    @Inject
    FareTypeProvider fareTypeProvider;

    /**
     * フライト基本情報提供クラス。
     */
    @Inject
    FlightMasterProvider flightMasterProvider;

    /**
     * 搭乗クラス情報提供クラス。
     */
    @Inject
    BoardingClassProvider boardingClassProvider;

    /**
     * チケット予約共通サービス。
     */
    @Inject
    TicketSharedService ticketSharedService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FlightVacantInfoDto> searchFlight(
            TicketSearchCriteriaDto searchCriteria) throws BusinessException {

        // 引数チェック
        Assert.notNull(searchCriteria, "searchCriteria must not null.");

        Date depDate = searchCriteria.getDepDate();
        BoardingClassCd boardingClassCd = searchCriteria.getBoardingClassCd();
        String depAirportCd = searchCriteria.getDepartureAirportCd();
        String arrAirportCd = searchCriteria.getArrivalAirportCd();
        FlightType flightType = searchCriteria.getFlightType();

        Assert.notNull(depDate, "depDate must not null.");
        Assert.notNull(boardingClassCd, "boardingClassCd must not null.");
        Assert.hasText(depAirportCd, "depAirportCd must have some text.");
        Assert.hasText(arrAirportCd, "arrAirportCd must have some text.");
        Assert.notNull(flightType, "flightType must not null.");

        // 搭乗日が照会可能な範囲かチェック
        ticketSharedService.validateDepatureDate(depDate);

        // 指定された出発空港・到着空港に該当する区間が存在するかどうかチェック
        Route route = routeProvider.getRouteByAirportCd(depAirportCd,
                arrAirportCd);
        if (route == null) {
            throw new AtrsBusinessException(TicketSearchErrorCode.E_AR_B1_2002);
        }

        // システム日付が搭乗日から何日前かを計算
        LocalDate sysLocalDate = dateFactory.newDateTime().toLocalDate();
        LocalDate depLocalDate = new LocalDate(depDate);
        int beforeDayNum = Days.daysBetween(sysLocalDate, depLocalDate)
                .getDays();

        // フライト種別に応じて運賃種別コードを空席照会条件Dtoに設定
        List<FareTypeCd> fareTypeList = FareTypeUtil
                .getFareTypeCdList(flightType);

        VacantSeatSearchCriteriaDto criteria = new VacantSeatSearchCriteriaDto(depDate, route, boardingClassCd, beforeDayNum, fareTypeList);

        // リポジトリから照会結果を取得
        List<Flight> flightList = flightRepository
                .findByVacantSeatSearchCriteria(criteria);

        // 照会結果件数をチェック
        if (flightList.isEmpty()) {
            throw new FlightNotFoundException();
        }

        // 取得したフライトに関連するエンティティを設定
        for (Flight flight : flightList) {
            FareTypeCd fareTypeCd = flight.getFareType().getFareTypeCd();
            flight.setFareType(fareTypeProvider.getFareType(fareTypeCd));
            flight.setFlightMaster(flightMasterProvider.getFlightMaster(flight
                    .getFlightMaster().getFlightName()));
            flight.setBoardingClass(boardingClassProvider
                    .getBoardingClass(flight.getBoardingClass()
                            .getBoardingClassCd()));
        }

        // 基本運賃の計算
        int basicFare = ticketSharedService.calculateBasicFare(route
                .getBasicFare(), boardingClassCd, depDate);

        // 照会結果のリストを作成
        List<FlightVacantInfoDto> flightVacantInfoList = createFlightVacantInfoList(
                flightList, basicFare);

        return flightVacantInfoList;
    }

    /**
     * フライトリストから空席状況一覧リストを作成する。
     * @param flightList フライトリスト
     * @param basicFare 基本運賃
     * @return 空席状況一覧リスト
     */
    private List<FlightVacantInfoDto> createFlightVacantInfoList(
            List<Flight> flightList, int basicFare) {

        // 空席状況一覧Map
        Map<String, FlightVacantInfoDto> vacantInfoMap = new LinkedHashMap<>();

        DecimalFormat fareFormatter = new DecimalFormat("###,###");

        for (Flight flight : flightList) {

            FlightMaster flightMaster = flight.getFlightMaster();
            String departureTime = flightMaster.getDepartureTime();
            FlightVacantInfoDto vacantInfo = vacantInfoMap.get(departureTime);
            if (vacantInfo == null) {
                vacantInfo = createFlightVacantInfo(flight);
                vacantInfoMap.put(departureTime, vacantInfo);
            }

            // 運賃種別情報を設定
            FareType fareType = flight.getFareType();
            int fare = ticketSharedService.calculateFare(basicFare, fareType
                    .getDiscountRate());
            FareTypeVacantInfoDto fareTypeVacantInfo = new FareTypeVacantInfoDto(fareType
                    .getFareTypeName(), fareFormatter.format(fare), flight
                    .getVacantNum());

            vacantInfo.addFareTypeVacantInfo(fareType.getFareTypeCd(),
                    fareTypeVacantInfo);
        }

        // リストに変換して返却
        return new ArrayList<>(vacantInfoMap.values());
    }

    /**
     * 空席状況情報を作成する。
     * @param flight フライト情報
     * @return 空席照会結果
     */
    private FlightVacantInfoDto createFlightVacantInfo(Flight flight) {

        FlightVacantInfoDto vacantInfo = new FlightVacantInfoDto();

        FlightMaster flightMaster = flight.getFlightMaster();
        vacantInfo.setFlightName(flightMaster.getFlightName());
        Route route = flightMaster.getRoute();
        vacantInfo.setDepAirportName(route.getDepartureAirport().getName());
        vacantInfo.setArrAirportName(route.getArrivalAirport().getName());
        String depTime = DateTimeUtil.toFormatTimeString(flightMaster
                .getDepartureTime());
        vacantInfo.setDepTime(depTime);
        String arrTime = DateTimeUtil.toFormatTimeString(flightMaster
                .getArrivalTime());
        vacantInfo.setArrTime(arrTime);
        vacantInfo.setDepDate(DateTimeUtil.toFormatDateString(flight
                .getDepartureDate()));
        vacantInfo.setBoardingClassCd(flight.getBoardingClass()
                .getBoardingClassCd());

        return vacantInfo;
    }

}
