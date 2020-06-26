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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.ntt.atrs.app.common.exception.BadRequestException;
import jp.co.ntt.atrs.domain.common.masterdata.BoardingClassProvider;
import jp.co.ntt.atrs.domain.common.masterdata.FareTypeProvider;
import jp.co.ntt.atrs.domain.common.masterdata.FlightMasterProvider;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.Passenger;
import jp.co.ntt.atrs.domain.model.Reservation;
import jp.co.ntt.atrs.domain.model.ReserveFlight;
import jp.co.ntt.atrs.domain.service.b0.InvalidFlightException;
import jp.co.ntt.atrs.domain.service.b0.TicketSharedService;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveDto;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveService;

import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;

/**
 * チケット予約API Helper。
 * @author NTT 電電太郎
 */
@Component
public class TicketHelper {

    /**
     * 搭乗クラス情報提供クラス。
     */
    @Inject
    BoardingClassProvider boardingClassProvider;

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
     * Beanマッパー。
     */
    @Inject
    Mapper beanMapper;

    /**
     * 日付、時刻取得インターフェース。
     */
    @Inject
    JodaTimeDateFactory dateFactory;

    /**
     * チケット予約サービス。
     */
    @Inject
    TicketReserveService ticketReserveService;

    /**
     * チケット予約共通サービス。
     */
    @Inject
    TicketSharedService ticketSharedService;

    /**
     * 選択フライトリソースをフライト情報に変換する。
     * @param selectFlightResourceList 選択フライトリソースのリスト
     * @return フライト情報リスト
     */
    public List<Flight> toFlightList(
            List<SelectFlightResource> selectFlightResourceList) {

        List<Flight> flightList = new ArrayList<>();
        for (SelectFlightResource selectFlightResource : selectFlightResourceList) {
            Flight flight = new Flight();
            flight.setBoardingClass(boardingClassProvider
                    .getBoardingClass(selectFlightResource.getBoardingClassCd()));
            flight.setDepartureDate(selectFlightResource.getDepDate());
            flight.setFareType(fareTypeProvider
                    .getFareType(selectFlightResource.getFareTypeCd()));
            flight.setFlightMaster(flightMasterProvider
                    .getFlightMaster(selectFlightResource.getFlightName()));
            flightList.add(flight);
        }

        return flightList;
    }

    /**
     * チケットを予約する。
     * @param ticketReserveResource チケット予約リソース
     * @param flightList フライト情報リスト
     */
    public TicketReserveResource reserve(
            TicketReserveResource ticketReserveResource, List<Flight> flightList) {
        // 予約情報生成
        Reservation reservation = createReservation(ticketReserveResource,
                flightList);

        // 予約情報の業務ロジックチェック
        ticketReserveService.validateReservation(reservation);

        // 予約情報登録
        reservation.setReserveDate(dateFactory.newDate());
        reservation.setTotalFare(calculateTotalFare(flightList, reservation));
        TicketReserveDto ticketReserveDto = ticketReserveService
                .registerReservation(reservation);

        // レスポンス用チケット予約リソース生成
        TicketReserveResource createdReserveResource = beanMapper.map(
                ticketReserveResource, TicketReserveResource.class);
        beanMapper.map(ticketReserveDto, createdReserveResource);
        createdReserveResource.setTotalFare(reservation.getTotalFare());

        return createdReserveResource;

    }

    /**
     * 予約チケットの合計金額を計算する。
     * @param flightList フライト情報リスト
     * @param reservation 予約情報
     * @return 予約チケットの合計金額
     */
    private Integer calculateTotalFare(List<Flight> flightList,
            Reservation reservation) {
        ReserveFlight reserveFlight = reservation.getReserveFlightList().get(0);
        List<Passenger> passengerList = reserveFlight.getPassengerList();
        int totalFare = ticketReserveService.calculateTotalFare(flightList,
                passengerList);

        return totalFare;
    }

    /**
     * 予約情報を生成する。
     * @param ticketReserveResource チケット予約リソース
     * @param flightList フライト情報リスト
     * @return 予約情報
     */
    private Reservation createReservation(
            TicketReserveResource ticketReserveResource, List<Flight> flightList) {
        // 搭乗者情報リスト生成
        List<Passenger> passengerList = new ArrayList<>();
        for (PassengerResource passengerResource : ticketReserveResource
                .getPassengerResourceList()) {
            if (!passengerResource.isEmpty()) {
                Passenger passenger = beanMapper.map(passengerResource,
                        Passenger.class);
                passengerList.add(passenger);
            }
        }

        // 予約フライト情報リスト生成
        List<ReserveFlight> reserveFlightList = new ArrayList<>();
        for (Flight flight : flightList) {
            ReserveFlight reserveFlight = new ReserveFlight();
            reserveFlight.setFlight(flight);
            reserveFlight.setPassengerList(passengerList);
            reserveFlightList.add(reserveFlight);
        }

        // 予約情報生成
        Reservation reservation = new Reservation();
        beanMapper.map(ticketReserveResource, reservation);
        String repTel = String.format("%s-%s-%s", ticketReserveResource
                .getRepTel1(), ticketReserveResource.getRepTel2(),
                ticketReserveResource.getRepTel3());
        reservation.setRepTel(repTel);
        reservation.setReserveFlightList(reserveFlightList);

        return reservation;
    }

    /**
     * フライト情報リストをチェックする。
     * <p>
     * チェックエラーがある場合(業務エラー含む)、不正リクエスト例外をスローする。
     * </p>
     * @param flightList フライト情報リスト
     * @throws BadRequestException 不正リクエスト例外
     */
    public void validateFlightList(List<Flight> flightList) throws BadRequestException {
        // フライト情報チェック
        try {
            ticketSharedService.validateFlightList(flightList);
        } catch (InvalidFlightException | BusinessException e) {

            // 不正リクエスト例外をスロー(業務エラーがある場合は改ざんとみなす)
            throw new BadRequestException(e);
        }
    }
}
