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
package jp.co.ntt.atrs.domain.service.b0;

import jp.co.ntt.atrs.domain.common.exception.AtrsBusinessException;
import jp.co.ntt.atrs.domain.common.masterdata.BoardingClassProvider;
import jp.co.ntt.atrs.domain.common.masterdata.PeakTimeProvider;
import jp.co.ntt.atrs.domain.common.util.DateTimeUtil;
import jp.co.ntt.atrs.domain.common.util.FareTypeUtil;
import jp.co.ntt.atrs.domain.common.util.FareUtil;
import jp.co.ntt.atrs.domain.model.BoardingClass;
import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FareType;
import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.PeakTime;
import jp.co.ntt.atrs.domain.model.Route;
import jp.co.ntt.atrs.domain.repository.flight.FlightRepository;
import jp.co.ntt.atrs.domain.service.b1.TicketSearchErrorCode;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveErrorCode;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * チケット予約共通サービス実装クラス。
 * @author NTT 電電次郎
 */
@Service
public class TicketSharedServiceImpl implements TicketSharedService {

    /**
     * 通常時のピーク時期積算比率(%)。
     */
    private static final int MULTIPLICATION_RATIO_IN_NORMAL_TIME = 100;

    /**
     * 往路の到着時刻に対し、復路で予約可能となる出発時刻までの時間間隔(分)。
     */
    @Value("${atrs.reserveIntervalTime}")
    private int reserveIntervalTime;

    /**
     * 予約可能限界日数。
     */
    @Value("${atrs.limitDay}")
    private int limitDay;

    /**
     * 日付、時刻取得インターフェース。
     */
    @Inject
    JodaTimeDateFactory dateFactory;

    /**
     * 搭乗クラス情報提供クラス。
     */
    @Inject
    BoardingClassProvider boardingClassProvider;

    /**
     * ピーク時期情報提供クラス。
     */
    @Inject
    PeakTimeProvider peakTimeProvider;

    /**
     * フライト情報リポジトリ。
     */
    @Inject
    FlightRepository flightRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate getSearchLimitDate() {
        // 照会可能限界日付 = システム日付＋予約可能限界日数
        LocalDate sysDate = dateFactory.newDateTime().toLocalDate();
        LocalDate limitDate = sysDate.plusDays(limitDay);
        return limitDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateFlightList(List<Flight> flightList) throws BusinessException, InvalidFlightException {

        Assert.notEmpty(flightList, "flightList must not empty.");
        Assert.isTrue(flightList.size() <= 2, "flightList size must be 2 or less.");

        // 往路フライトのチェックを行う
        Flight outwardFlight = flightList.get(0);
        Assert.notNull(outwardFlight, "outwardFlight must not null.");
        if (outwardFlight.getFlightMaster() == null) {
            throw new InvalidFlightException("flightMaster is null :"
                    + outwardFlight);
        }
        if (!existsFlight(outwardFlight)) {
            throw new InvalidFlightException("flight not found :"
                    + outwardFlight);
        }

        // 2件の場合、往復路フライトのチェックを行う
        if (flightList.size() == 2) {

            // 復路フライト
            Flight homewardFlight = flightList.get(1);
            Assert.notNull(homewardFlight, "homewardFlight must not null.");
            if (homewardFlight.getFlightMaster() == null) {
                throw new InvalidFlightException("flightMaster is null :"
                        + homewardFlight);
            }
            if (!existsFlight(homewardFlight)) {
                throw new InvalidFlightException("flight not found :"
                        + homewardFlight);
            }

            // 選択した復路のフライトが搭乗範囲内であることを検証
            validateFlightDepartureDateForRoundTripFlight(outwardFlight,
                    homewardFlight);

            // 復路が往路の逆区間であることを検証
            validateFlightRouteForRoundTripFlight(outwardFlight, homewardFlight);

        }

        // 運賃種別が選択可能なものであることを検証
        validateFlightFareType(flightList);

    }

    /**
     * 往路、復路フライトの搭乗日、時刻に関するチェック。
     * @param outwardFlight 往路フライト情報
     * @param homewardFlight 復路フライト情報
     * @throws BusinessException 業務例外
     */
    private void validateFlightDepartureDateForRoundTripFlight(
            Flight outwardFlight, Flight homewardFlight) throws BusinessException {

        // 往路のフライトの到着時刻
        DateTime outwardArriveDateTime = DateTimeUtil.toDateTime(outwardFlight
                .getDepartureDate(), outwardFlight.getFlightMaster()
                .getArrivalTime());

        // 復路のフライト出発時刻
        DateTime homewardDepartureDateTime = DateTimeUtil.toDateTime(
                homewardFlight.getDepartureDate(), homewardFlight
                        .getFlightMaster().getDepartureTime());

        // 選択した復路のフライトが搭乗範囲外の場合、業務例外をスロー
        // (復路のフライトは往路のフライトの到着時刻より指定時間間隔以上経過した
        // 出発時刻から搭乗可能となる)
        Duration flightDuration = new Duration(outwardArriveDateTime, homewardDepartureDateTime);
        if (flightDuration.getStandardMinutes() < reserveIntervalTime) {
            throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2001);
        }
    }

    /**
     * 往路、復路フライトの区間に関するチェック。
     * @param outwardFlight 往路フライト情報
     * @param homewardFlight 復路フライト情報
     * @throws BusinessException 業務例外
     */
    private void validateFlightRouteForRoundTripFlight(Flight outwardFlight,
            Flight homewardFlight) throws BusinessException {

        // 復路が往路の逆区間であることを確認
        Route outwardRoute = outwardFlight.getFlightMaster().getRoute();
        Route homewardRoute = homewardFlight.getFlightMaster().getRoute();
        if (!outwardRoute.getDepartureAirport().getCode().equals(
                homewardRoute.getArrivalAirport().getCode())
                || !outwardRoute.getArrivalAirport().getCode().equals(
                        homewardRoute.getDepartureAirport().getCode())) {

            // 復路が往路の逆区間でない場合、フライト不正例外をスロー
            throw new InvalidFlightException("homeward route is not outward route reverse");
        }
    }

    /**
     * フライトの運賃種別に関するチェック。
     * @param flightList フライト情報リスト
     * @throws BusinessException 業務例外
     */
    private void validateFlightFareType(List<Flight> flightList) throws BusinessException {

        FareTypeCd owFareTypeCd;
        FareTypeCd hwFareTypeCd;

        switch (flightList.size()) {
        case 1:
            // 片道の場合

            // 往路の運賃種別は片道運賃であることを検証
            owFareTypeCd = flightList.get(0).getFareType().getFareTypeCd();
            if (!FareTypeUtil.isOneWay(owFareTypeCd)) {
                throw new InvalidFlightException("outward flight fareType is invalid :"
                        + owFareTypeCd);
            }
            break;

        case 2:
            // 往復の場合

            // 往路の運賃種別は往復運賃または特別往復運賃であることを検証
            owFareTypeCd = flightList.get(0).getFareType().getFareTypeCd();
            if (!FareTypeUtil.isRoundTrip(owFareTypeCd)) {
                throw new InvalidFlightException("outward flight fareType is invalid :"
                        + owFareTypeCd);
            }

            // 復路の運賃種別が往復運賃または特別往復運賃でない場合、フライト不正例外をスロー
            hwFareTypeCd = flightList.get(1).getFareType().getFareTypeCd();
            if (!FareTypeUtil.isRoundTrip(hwFareTypeCd)) {
                throw new InvalidFlightException("homeward flight fareType is invalid :"
                        + hwFareTypeCd);
            }
            break;

        default:
            throw new InvalidFlightException("flightList size must be between 1 and 2");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateDepatureDate(Date departureDate) throws BusinessException {
        Assert.notNull(departureDate, "departureDate must not null.");

        DateTime sysDateMidnight = dateFactory.newDateTime()
                .withTimeAtStartOfDay();
        DateTime limitDateMidnight = getSearchLimitDate()
                .toDateTimeAtStartOfDay();

        // 指定された搭乗日が本日から照会可能限界日迄の間にあるかチェック
        Interval reservationAvailableInterval = new Interval(sysDateMidnight, limitDateMidnight
                .plusDays(1));
        if (!reservationAvailableInterval.contains(departureDate.getTime())) {
            throw new AtrsBusinessException(TicketSearchErrorCode.E_AR_B1_2001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailableFareType(FareType fareType, Date depDate) {
        Assert.notNull(fareType, "fareType must not null.");
        Assert.notNull(depDate, "depDate must not null.");

        DateTime depDateMidnight = new DateTime(depDate).withTimeAtStartOfDay();

        // 予約開始日付
        DateTime rsrvAvailableStartDate = depDateMidnight.minusDays(fareType
                .getRsrvAvailableStartDayNum());

        // 予約終了日付
        DateTime rsrvAvailableEndDate = depDateMidnight.minusDays(fareType
                .getRsrvAvailableEndDayNum());

        // 現在日付
        DateTime sysDateMidnight = dateFactory.newDateTime()
                .withTimeAtStartOfDay();

        // 現在日付が予約可能開始日付～予約可能終了日付の間であるかチェック
        return new Interval(rsrvAvailableStartDate, rsrvAvailableEndDate
                .plusDays(1)).contains(sysDateMidnight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateBasicFare(int basicFareOfRoute,
            BoardingClassCd boardingClassCd, Date depDate) {
        Assert.isTrue(basicFareOfRoute >= 0, "basicFareOfRoute must be 0 or higher.");
        Assert.notNull(boardingClassCd, "boardingClassCd must not null.");
        Assert.notNull(depDate, "depDate must not null.");

        // 搭乗クラスの加算料金の取得
        BoardingClass boardingClass = boardingClassProvider
                .getBoardingClass(boardingClassCd);
        int boardingClassFare = boardingClass.getExtraCharge();

        // 搭乗日の料金積算比率の取得
        int multiplicationRatio = getMultiplicationRatio(depDate);

        // 基本運賃の計算
        int basicFare = (int) ((basicFareOfRoute + boardingClassFare) * (multiplicationRatio * 0.01));

        return basicFare;
    }

    /**
     * 搭乗日の料金積算比率を取得する。
     * @param departureDate 搭乗日
     * @return 搭乗日の料金積算比率
     */
    private int getMultiplicationRatio(Date departureDate) {

        // 該当するピーク時期情報を取得
        PeakTime peakTime = peakTimeProvider.getPeakTime(departureDate);

        // 該当するピーク時期が存在する場合、積算比率を返却
        if (peakTime != null) {
            return peakTime.getMultiplicationRatio();
        }

        // 該当するピーク時期が存在しない場合は、通常時の積算比率を返却
        return MULTIPLICATION_RATIO_IN_NORMAL_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateFare(int basicFare, int discountRate) {
        Assert.isTrue(basicFare >= 0, "basicFare must be 0 or higher.");
        Assert.isTrue(discountRate >= 0, "discountRate must be 0 or higher.");
        Assert.isTrue(discountRate <= 100, "discountRate must be 100 or less.");

        // 運賃の計算
        int fare = (int) (basicFare * (1 - (discountRate * 0.01)));

        // 運賃の100円未満を切上げて返却
        return FareUtil.ceilFare(fare);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsFlight(Flight flight) {
        Assert.notNull(flight, "flight must not null.");
        Assert.notNull(flight.getFlightMaster(), "flightMaster must not null.");
        return flightRepository.exists(flight.getDepartureDate(), flight
                .getFlightMaster().getFlightName(), flight.getBoardingClass(),
                flight.getFareType());
    }
}
