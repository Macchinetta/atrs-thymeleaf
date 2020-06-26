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
package jp.co.ntt.atrs.app.b2;

import jp.co.ntt.atrs.app.a0.AuthenticationHelper;
import jp.co.ntt.atrs.app.b0.LineType;
import jp.co.ntt.atrs.app.b0.SelectFlightDto;
import jp.co.ntt.atrs.app.b0.SelectFlightForm;
import jp.co.ntt.atrs.app.common.exception.BadRequestException;
import jp.co.ntt.atrs.domain.common.masterdata.FlightMasterProvider;
import jp.co.ntt.atrs.domain.common.util.DateTimeUtil;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.FlightMaster;
import jp.co.ntt.atrs.domain.model.FlightType;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.Passenger;
import jp.co.ntt.atrs.domain.model.Reservation;
import jp.co.ntt.atrs.domain.model.ReserveFlight;
import jp.co.ntt.atrs.domain.model.Route;
import jp.co.ntt.atrs.domain.service.a1.AtrsUserDetails;
import jp.co.ntt.atrs.domain.service.b0.InvalidFlightException;
import jp.co.ntt.atrs.domain.service.b0.TicketSharedService;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveDto;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveService;

import org.apache.commons.collections.CollectionUtils;
import com.github.dozermapper.core.Mapper;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * チケット予約Helper。
 * @author NTT 電電三郎
 */
@Component
public class TicketReserveHelper {

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
     * 認証共通Helper。
     */
    @Inject
    AuthenticationHelper authenticationHelper;

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
     * フライト基本情報提供クラス。
     */
    @Inject
    FlightMasterProvider flightMasterProvider;

    /**
     * チケット情報の妥当性をチェックし、申し込み内容確認画面に表示するデータを生成する。
     * @param ticketReserveForm チケット予約フォーム
     * @param flightList フライト情報リスト
     * @return 申し込み内容確認画面出力用DTO
     */
    public ReserveConfirmOutputDto reserveConfirm(
            TicketReserveForm ticketReserveForm, List<Flight> flightList) {

        // 値の存在する搭乗者が先になるよう設定
        ticketReserveForm.resetPassengersIndex();

        // 予約情報生成
        Reservation reservation = createReservation(ticketReserveForm,
                flightList);

        // 予約情報の業務ロジックチェック
        ticketReserveService.validateReservation(reservation);

        // 予約チケットの合計金額取得
        int totalFare = calculateTotalFare(flightList, reservation);

        // 画面出力DTO生成
        ReserveConfirmOutputDto outputDto = new ReserveConfirmOutputDto();
        outputDto.setTotalFare(totalFare);

        return outputDto;
    }

    /**
     * チケットを予約する。
     * @param ticketReserveForm チケット予約フォーム
     * @param flightList フライト情報リスト
     * @return チケット予約完了画面出力用DTO
     */
    public ReserveCompleteOutputDto reserve(
            TicketReserveForm ticketReserveForm, List<Flight> flightList) {

        // 予約情報生成
        Reservation reservation = createReservation(ticketReserveForm,
                flightList);

        // 予約情報の業務ロジックチェック
        ticketReserveService.validateReservation(reservation);

        // 予約情報登録
        reservation.setReserveDate(dateFactory.newDate());
        reservation.setTotalFare(calculateTotalFare(flightList, reservation));
        TicketReserveDto ticketReserveDto = ticketReserveService
                .registerReservation(reservation);

        // 画面出力DTO生成
        ReserveCompleteOutputDto outputDto = new ReserveCompleteOutputDto();
        outputDto.setReserveNo(ticketReserveDto.getReserveNo());
        outputDto.setPaymentDate(ticketReserveDto.getPaymentDate());
        outputDto.setTotalFare(reservation.getTotalFare());

        return outputDto;
    }

    /**
     * フライト情報リストから画面表示に使用する選択フライト情報DTOのリストを作成する。
     * @param flightList フライト情報リスト
     * @return 選択フライト情報DTOのリスト
     */
    public List<SelectFlightDto> createSelectFlightDtoList(
            List<Flight> flightList) {

        List<SelectFlightDto> selectFlightDtoList = new ArrayList<>();
        for (int i = 0; i < flightList.size(); i++) {
            // フライト情報から選択フライト情報DTOを生成
            SelectFlightDto selectFlight = beanMapper.map(flightList.get(i),
                    SelectFlightDto.class);

            // 路線種別を設定
            selectFlight.setLineType(i == 0 ? LineType.OUTWARD
                    : LineType.HOMEWARD);

            // 運賃を算出し設定
            int basicFare = flightList.get(i).getFlightMaster().getRoute()
                    .getBasicFare();
            int baseFare = ticketSharedService.calculateBasicFare(basicFare,
                    selectFlight.getBoardingClassCd(), selectFlight
                            .getDepartureDate());
            int discountRate = flightList.get(i).getFareType()
                    .getDiscountRate();
            int fare = ticketSharedService
                    .calculateFare(baseFare, discountRate);
            selectFlight.setFare(fare);

            selectFlightDtoList.add(selectFlight);
        }

        return selectFlightDtoList;
    }

    /**
     * デフォルト値を持つチケット予約フォームを生成する。
     * @param userDetails ログイン情報を保持するオブジェクト
     * @return チケット予約フォーム
     */
    public TicketReserveForm createTicketReserveForm(AtrsUserDetails userDetails) {

        TicketReserveForm ticketReserveForm = new TicketReserveForm();

        if (authenticationHelper.isAuthenticatedPrincipal(userDetails)) {
            // ログイン中の場合

            // ログインユーザに該当する会員情報取得
            Member member = ticketReserveService.findMember(userDetails
                    .getUsername());

            // 予約代表者情報にログインユーザの会員情報を設定
            beanMapper.map(member, ticketReserveForm);
            // 電話番号設定
            String[] tel = member.getTel().split("-");
            if (tel.length == 3) {
                ticketReserveForm.setRepTel1(tel[0]);
                ticketReserveForm.setRepTel2(tel[1]);
                ticketReserveForm.setRepTel3(tel[2]);
            }
            // 生年月日から年齢を算出し設定
            ticketReserveForm.setRepAge(calculateAge(member.getBirthday()));

            // 一番目の搭乗者情報に予約代表者情報(ログインユーザ情報)を設定
            PassengerForm passengerForm = beanMapper.map(ticketReserveForm,
                    PassengerForm.class);
            ticketReserveForm.setPassenger(0, passengerForm);
        }

        return ticketReserveForm;
    }

    /**
     * 予約情報オブジェクトを生成する。
     * @param flightList フライト情報のリスト
     * @param ticketReserveForm チケット予約フォーム
     * @return reservation 予約情報
     */
    private Reservation createReservation(TicketReserveForm ticketReserveForm,
            List<Flight> flightList) {

        // 搭乗者情報リスト生成
        List<Passenger> passengerList = new ArrayList<>();
        for (PassengerForm passengerForm : ticketReserveForm
                .getPassengerFormList()) {
            if (!passengerForm.isEmpty()) {
                Passenger passenger = beanMapper.map(passengerForm,
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
        beanMapper.map(ticketReserveForm, reservation);
        String repTel = String.format("%s-%s-%s", ticketReserveForm
                .getRepTel1(), ticketReserveForm.getRepTel2(),
                ticketReserveForm.getRepTel3());
        reservation.setRepTel(repTel);
        reservation.setReserveFlightList(reserveFlightList);

        return reservation;
    }

    /**
     * 予約チケットの合計金額を計算する。
     * @param flightList フライト情報のリスト
     * @param reservation 予約情報
     * @return 予約チケットの合計金額
     */
    private int calculateTotalFare(List<Flight> flightList,
            Reservation reservation) {

        ReserveFlight reserveFlight = reservation.getReserveFlightList().get(0);
        List<Passenger> passengers = reserveFlight.getPassengerList();
        int totalFare = ticketReserveService.calculateTotalFare(flightList,
                passengers);

        return totalFare;
    }

    /**
     * 生年月日より年齢を計算する。
     * @param birthday 生年月日
     * @return 年齢
     */
    private Integer calculateAge(Date birthday) {

        DateTime today = dateFactory.newDateTime();
        DateTime birthdayDateTime = new DateTime(birthday);

        return Years.yearsBetween(birthdayDateTime, today).getYears();
    }

    /**
     * 選択フライト情報からリダイレクト時の照会条件パラメータマップを生成する。
     * @param selectFlightFormList 選択フライト情報リスト
     * @return パラメータマップ
     */
    public Map<String, String> createParameterMapForFlightSearch(
            List<SelectFlightForm> selectFlightFormList) {

        if (CollectionUtils.isEmpty(selectFlightFormList)) {
            throw new BadRequestException("select flight is empty.");
        }

        SelectFlightForm owFlight = selectFlightFormList.get(0);
        String flightName = owFlight.getFlightName();
        FlightMaster flightMaster = flightMasterProvider
                .getFlightMaster(flightName);
        if (flightMaster == null) {
            throw new BadRequestException("flight is null. value:" + flightName);
        }

        Map<String, String> params = new LinkedHashMap<>();

        String outwardDate = DateTimeUtil.toFormatDateString(owFlight
                .getDepDate());
        String homewardDate = "";
        if (selectFlightFormList.size() == 2) {
            // 往復の場合
            params.put("flightType", FlightType.RT.getCode());
            SelectFlightForm hwFlight = selectFlightFormList.get(1);
            homewardDate = DateTimeUtil.toFormatDateString(hwFlight
                    .getDepDate());
        } else {
            // 片道の場合
            params.put("flightType", FlightType.OW.getCode());
        }

        Route route = flightMaster.getRoute();
        params.put("depAirportCd", route.getDepartureAirport().getCode());
        params.put("arrAirportCd", route.getArrivalAirport().getCode());
        params.put("outwardDate", outwardDate);
        params.put("homewardDate", homewardDate);
        params.put("boardingClassCd", owFlight.getBoardingClassCd().getCode());

        return params;
    }

    /**
     * フライト情報のリストをチェックする。
     * <p>
     * チェックエラーがある場合(業務エラー含む)、不正リクエスト例外をスローする。
     * </p>
     * @param flightList フライト情報のリスト
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
