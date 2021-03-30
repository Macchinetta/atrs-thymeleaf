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
package jp.co.ntt.atrs.domain.service.b2;

import jp.co.ntt.atrs.domain.common.exception.AtrsBusinessException;
import jp.co.ntt.atrs.domain.common.logging.LogMessages;
import jp.co.ntt.atrs.domain.common.util.FareUtil;
import jp.co.ntt.atrs.domain.model.FareType;
import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.Gender;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.Passenger;
import jp.co.ntt.atrs.domain.model.Reservation;
import jp.co.ntt.atrs.domain.model.ReserveFlight;
import jp.co.ntt.atrs.domain.model.Route;
import jp.co.ntt.atrs.domain.repository.flight.FlightRepository;
import jp.co.ntt.atrs.domain.repository.member.MemberRepository;
import jp.co.ntt.atrs.domain.repository.reservation.ReservationRepository;
import jp.co.ntt.atrs.domain.service.b0.TicketSharedService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.SystemException;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * チケット予約のサービス実装クラス。
 * @author NTT 電電三郎
 */
@Service
@Transactional
public class TicketReserveServiceImpl implements TicketReserveService {

    /**
     * 予約代表者に必要な最小年齢。
     */
    @Value("${atrs.representativeMinAge}")
    private int representativeMinAge;

    /**
     * 大人運賃が適用される最小年齢。
     */
    @Value("${atrs.adultPassengerMinAge}")
    private int adultPassengerMinAge;

    /**
     * 大人運賃に対する小児運賃の比率(%)。
     */
    @Value("${atrs.childFareRate}")
    private int childFareRate;

    /**
     * フライト情報リポジトリ。
     */
    @Inject
    FlightRepository flightRepository;

    /**
     * カード会員情報リポジトリ。
     */
    @Inject
    MemberRepository memberRepository;

    /**
     * 予約情報リポジトリ。
     */
    @Inject
    ReservationRepository reservationRepository;

    /**
     * チケット共通サービス。
     */
    @Inject
    TicketSharedService ticketSharedService;

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateTotalFare(List<Flight> flightList,
            List<Passenger> passengerList) {

        Assert.notEmpty(flightList, "flightList must not empty.");
        Assert.notEmpty(passengerList, "passengerList must not empty.");

        // 小児搭乗者数(12歳未満の搭乗者数)
        int childNum = 0;
        // 小児搭乗者数をカウント
        for (Passenger passenger : passengerList) {
            // リスト要素の null チェック
            Assert.notNull(passenger, "passenger must not null.");
            if (passenger.getAge() < adultPassengerMinAge) {
                childNum++;
            }
        }

        // 12歳以上搭乗者数
        int adultNum = passengerList.size() - childNum;

        // 運賃から合計金額を算出
        // 合計金額 = 往路の合計金額 + 復路の合計金額
        // フライト単位の合計金額 = 運賃 × 搭乗者数(12歳以上) +
        // (基本運賃 × 小児運賃の比率 - 運賃種別ごとの割引額) × 搭乗者数（12歳未満)
        // 運賃種別ごとの割引額 = 基本運賃 × 割引率

        // 合計金額にフライト単位の合計金額を加算
        int totalFare = 0;
        for (Flight flight : flightList) {
            // リスト要素の null チェック
            Assert.notNull(flight, "flight must not null.");

            Route route = flight.getFlightMaster().getRoute();
            int baseFare = ticketSharedService.calculateBasicFare(route
                    .getBasicFare(), flight.getBoardingClass()
                    .getBoardingClassCd(), flight.getDepartureDate());

            int discountRate = flight.getFareType().getDiscountRate();
            int boardingFare = ticketSharedService.calculateFare(baseFare,
                    discountRate);

            int fare = boardingFare * adultNum + baseFare
                    * (childFareRate - discountRate) / 100 * childNum;

            // 合計金額
            totalFare += fare;
        }

        // 合計金額の100円未満を切り上げ
        totalFare = FareUtil.ceilFare(totalFare);

        return totalFare;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateReservation(Reservation reservation) throws BusinessException {

        Assert.notNull(reservation, "reservation must not null.");
        Assert.notEmpty(reservation.getReserveFlightList(), "reserveFlightList must not empty.");

        // 予約代表者の年齢が18歳以上であることを検証
        validateRepresentativeAge(reservation.getRepAge());

        // 予約フライト情報一覧を取得
        List<ReserveFlight> reserveFlightList = reservation
                .getReserveFlightList();

        // 運賃種別の適用可否を検証
        validateFareType(reserveFlightList);

        // 予約者代表者の検証
        validateRepresentativeMemberInfo(reservation);

        // 搭乗者情報と登録されている会員情報を照合
        validatePassengerMemberInfo(reserveFlightList);
    }

    /**
     * 搭乗者情報とカード会員情報の照合を行う。
     * @param reserveFlightList 予約フライト情報一覧
     * @throws AtrsBusinessException 照合失敗例外
     */
    private void validatePassengerMemberInfo(
            List<ReserveFlight> reserveFlightList) throws AtrsBusinessException {

        for (ReserveFlight reserveFlight : reserveFlightList) {

            Assert.notNull(reserveFlight, "reserveFlight must not null.");

            // 搭乗者情報が登録されている会員情報と同一であることを確認

            // 搭乗者情報一覧
            List<Passenger> passengerList = reserveFlight.getPassengerList();
            Assert.notEmpty(passengerList, "passengerList must not empty.");

            int position = 1;
            for (Passenger passenger : passengerList) {
                Assert.notNull(passenger, "passenger must not null.");

                String membershipNumber = passenger.getMember()
                        .getMembershipNumber();

                // 搭乗者会員番号が入力されている場合のみ照合
                if (StringUtils.hasLength(membershipNumber)) {

                    // 搭乗者のカード会員情報取得
                    Member passengerMember = memberRepository
                            .findOne(membershipNumber);

                    // 会員情報が存在することを確認
                    if (passengerMember == null) {
                        throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2005, position);
                    }

                    // 取得した搭乗者のカード会員情報と搭乗者情報が同一であることを確認
                    if (!(passenger.getFamilyName().equals(
                            passengerMember.getKanaFamilyName())
                            && passenger.getGivenName().equals(
                                    passengerMember.getKanaGivenName()) && passenger
                            .getGender().equals(passengerMember.getGender()))) {
                        throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2006, position);
                    }
                }
                position++;
            }

        }
    }

    /**
     * 予約代表者の情報をチェックする。
     * @param reservation 予約情報
     * @throws AtrsBusinessException チェック失敗例外
     */
    private void validateRepresentativeMemberInfo(Reservation reservation) throws AtrsBusinessException {

        String repMembershipNumber = reservation.getRepMember()
                .getMembershipNumber();

        // 予約代表者会員番号が入力されている場合のみチェック
        if (StringUtils.hasLength(repMembershipNumber)) {

            // 予約代表者の会員情報を取得
            Member repMember = memberRepository.findOne(repMembershipNumber);

            // 該当する会員情報が存在することを確認
            if (repMember == null) {
                throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2002);
            }

            // 取得した会員情報と予約代表者情報が同一であることを確認
            if (!(reservation.getRepFamilyName().equals(
                    repMember.getKanaFamilyName())
                    && reservation.getRepGivenName().equals(
                            repMember.getKanaGivenName()) && reservation
                    .getRepGender().equals(repMember.getGender()))) {
                throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2003);
            }
        }
    }

    /**
     * 運賃種別の適用可否を確認する。
     * @param reserveFlightList 予約フライト情報一覧
     * @throws AtrsBusinessException チェック失敗例外
     */
    private void validateFareType(List<ReserveFlight> reserveFlightList) throws AtrsBusinessException {

        for (ReserveFlight reserveFlight : reserveFlightList) {

            Assert.notNull(reserveFlight, "reserveFlight must not null.");

            // 運賃種別
            FareType fareType = reserveFlight.getFlight().getFareType();

            // 運賃種別コード
            FareTypeCd fareTypeCd = fareType.getFareTypeCd();

            // 搭乗者情報一覧
            List<Passenger> passengerList = reserveFlight.getPassengerList();
            Assert.notEmpty(passengerList, "passengerList must not empty.");

            if (fareTypeCd == FareTypeCd.LD) {
                // 運賃種別がレディース割の場合

                for (Passenger passenger : passengerList) {
                    Assert.notNull(passenger, "passenger must not null.");
                    if (passenger.getGender() == Gender.M) {
                        // 男性の搭乗者がいる場合、業務例外をスロー
                        throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2007);
                    }
                }
            } else if (fareTypeCd == FareTypeCd.GD) {
                // 運賃種別がグループ割の場合

                int passengerMinNum = fareType.getPassengerMinNum();
                if (passengerList.size() < passengerMinNum) {
                    // 搭乗者数が利用可能最少人数未満の場合、業務例外をスロー
                    throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2010, fareType
                            .getFareTypeName(), passengerMinNum);
                }
            }
        }
    }

    /**
     * 予約代表者の年齢が予約代表者最小年齢以上であることをチェックする。
     * @param age 予約代表者の年齢
     */
    private void validateRepresentativeAge(int age) {

        if (age < representativeMinAge) {
            throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2004, representativeMinAge);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TicketReserveDto registerReservation(Reservation reservation) throws BusinessException {

        Assert.notNull(reservation, "reservation must not null.");

        // 予約フライト情報一覧
        List<ReserveFlight> reserveFlightList = reservation
                .getReserveFlightList();
        Assert.notEmpty(reserveFlightList, "reserveFlightList must not empty.");

        // 予約フライト情報に対して空席数の確認および更新を実施
        for (ReserveFlight reserveFlight : reserveFlightList) {
            Assert.notNull(reserveFlight, "reserveFlight must not null.");

            Flight flight = reserveFlight.getFlight();
            Assert.notNull(flight, "flight must not null.");

            // 搭乗日が運賃種別予約可能時期範囲内かチェック
            if (!ticketSharedService.isAvailableFareType(flight.getFareType(),
                    flight.getDepartureDate())) {
                throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2008);
            }

            // 空席数を更新するために、フライト情報を取得する(排他)
            flight = flightRepository.findOneForUpdate(flight
                    .getDepartureDate(), flight.getFlightMaster()
                    .getFlightName(), flight.getBoardingClass(), flight
                    .getFareType());
            int vacantNum = flight.getVacantNum();

            // 搭乗者数
            int passengerNum = reserveFlight.getPassengerList().size();

            // 取得した空席数が搭乗者数以上であることを確認
            if (vacantNum < passengerNum) {
                // 空席数が搭乗者数未満の場合、業務例外をスロー
                throw new AtrsBusinessException(TicketReserveErrorCode.E_AR_B2_2009);
            }

            // 取得した空席数から搭乗者数を引いた数を、フライト情報の空席数に設定
            flight.setVacantNum(vacantNum - passengerNum);

            // 空席数を更新
            int flightUpdateCount = flightRepository.update(flight);
            if (flightUpdateCount != 1) {
                throw new SystemException(LogMessages.E_AR_A0_L9002.getCode(), LogMessages.E_AR_A0_L9002
                        .getMessage(flightUpdateCount, 1));
            }
        }

        // 予約情報を登録
        // (パラメータの予約情報に予約番号が格納される)
        int reservationInsertCount = reservationRepository.insert(reservation);
        if (reservationInsertCount != 1) {
            throw new SystemException(LogMessages.E_AR_A0_L9002.getCode(), LogMessages.E_AR_A0_L9002
                    .getMessage(reservationInsertCount, 1));
        }
        // 予約番号を取得
        String reserveNo = reservation.getReserveNo();

        // 予約フライト情報、搭乗者情報の登録
        for (ReserveFlight reserveFlight : reserveFlightList) {

            // 予約フライト情報に予約番号を設定
            reserveFlight.setReserveNo(reserveNo);

            // 予約フライト情報を登録
            int reserveFlightInsertCount = reservationRepository
                    .insertReserveFlight(reserveFlight);
            if (reserveFlightInsertCount != 1) {
                throw new SystemException(LogMessages.E_AR_A0_L9002.getCode(), LogMessages.E_AR_A0_L9002
                        .getMessage(reserveFlightInsertCount, 1));
            }

            // 全搭乗者情報を登録
            for (Passenger passenger : reserveFlight.getPassengerList()) {
                passenger
                        .setReserveFlightNo(reserveFlight.getReserveFlightNo());
                int passengerInsertCount = reservationRepository
                        .insertPassenger(passenger);
                if (passengerInsertCount != 1) {
                    throw new SystemException(LogMessages.E_AR_A0_L9002
                            .getCode(), LogMessages.E_AR_A0_L9002.getMessage(
                            passengerInsertCount, 1));
                }
            }
        }

        // 往路搭乗日を支払期限とする
        Date paymentDate = reserveFlightList.get(0).getFlight()
                .getDepartureDate();

        return new TicketReserveDto(reserveNo, paymentDate);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Member findMember(String membershipNumber) {

        Assert.hasText(membershipNumber, "membershipNumber must have some text.");

        return memberRepository.findOne(membershipNumber);
    }

}
