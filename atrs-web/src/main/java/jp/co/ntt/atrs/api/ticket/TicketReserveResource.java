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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

import jp.co.ntt.atrs.domain.common.validate.FixedLength;
import jp.co.ntt.atrs.domain.common.validate.FullWidthKatakana;
import jp.co.ntt.atrs.domain.common.validate.HalfWidth;
import jp.co.ntt.atrs.domain.common.validate.HalfWidthNumber;
import jp.co.ntt.atrs.domain.model.FlightType;
import jp.co.ntt.atrs.domain.model.Gender;

/**
 * チケット予約リソース。
 * @author NTT 電電太郎
 */
public class TicketReserveResource implements Serializable {

    /**
     * serialVersion。
     */
    private static final long serialVersionUID = -4804738473714371755L;

    /**
     * 予約番号。
     */
    private String reserveNo;

    /**
     * 支払期限。
     */
    private Date paymentDate;

    /**
     * 予約チケットの合計金額。
     */
    private Integer totalFare;

    /**
     * 予約代表者姓。
     */
    @NotNull
    @Size(min = 1, max = 10)
    @FullWidthKatakana
    private String repFamilyName;

    /**
     * 予約代表者名。
     */
    @NotNull
    @Size(min = 1, max = 10)
    @FullWidthKatakana
    private String repGivenName;

    /**
     * 予約代表者年齢。
     */
    @NotNull
    @Min(0)
    @Digits(integer = 3, fraction = 1)
    private Integer repAge;

    /**
     * 予約代表者性別。
     */
    @NotNull
    private Gender repGender;

    /**
     * 予約代表者会員番号。
     */
    @FixedLength(10)
    @HalfWidthNumber
    private String repMembershipNumber;

    /**
     * 予約代表者電話番号1。
     */
    @NotNull
    @Size(min = 2, max = 5)
    @HalfWidthNumber
    private String repTel1;

    /**
     * 予約代表者電話番号2。
     */
    @NotNull
    @Size(min = 1, max = 4)
    @HalfWidthNumber
    private String repTel2;

    /**
     * 予約代表者電話番号3。
     */
    @NotNull
    @FixedLength(4)
    @HalfWidthNumber
    private String repTel3;

    /**
     * 予約代表者メールアドレス。
     */
    @NotNull
    @Size(min = 1, max = 256)
    @HalfWidth
    @Email
    private String repMail;

    /**
     * フライト種別。
     */
    @NotNull
    private FlightType flightType;

    /**
     * 選択フライトリソースリスト。
     */
    @NotNull
    @Valid
    private List<SelectFlightResource> selectFlightResourceList = new ArrayList<>();

    /**
     * 搭乗者リソースリスト。
     */
    @NotNull
    @Valid
    private List<PassengerResource> passengerResourceList = new ArrayList<>();

    /**
     * 予約番号を取得する。
     * @return 予約番号
     */
    public String getReserveNo() {
        return reserveNo;
    }

    /**
     * 予約番号を設定する。
     * @param reserveNo 予約番号
     */
    public void setReserveNo(String reserveNo) {
        this.reserveNo = reserveNo;
    }

    /**
     * 支払期限を取得する。
     * @return 支払期限
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * 支払期限を設定する。
     * @param paymentDate 支払期限
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * 予約チケットの合計金額を取得する。
     * @return 予約チケットの合計金額
     */
    public Integer getTotalFare() {
        return totalFare;
    }

    /**
     * 予約チケットの合計金額を設定する。
     * @param totalFare 予約チケットの合計金額
     */
    public void setTotalFare(Integer totalFare) {
        this.totalFare = totalFare;
    }

    /**
     * 予約代表者姓を取得する。
     * @return 予約代表者姓
     */
    public String getRepFamilyName() {
        return repFamilyName;
    }

    /**
     * 予約代表者姓を設定する。
     * @param repFamilyName 予約代表者姓
     */
    public void setRepFamilyName(String repFamilyName) {
        this.repFamilyName = repFamilyName;
    }

    /**
     * 予約代表者名を取得する。
     * @return 予約代表者名
     */
    public String getRepGivenName() {
        return repGivenName;
    }

    /**
     * 予約代表者名を設定する。
     * @param repGivenName 予約代表者名
     */
    public void setRepGivenName(String repGivenName) {
        this.repGivenName = repGivenName;
    }

    /**
     * 予約代表者年齢を取得する。
     * @return 予約代表者年齢
     */
    public Integer getRepAge() {
        return repAge;
    }

    /**
     * 予約代表者年齢を設定する。
     * @param repAge 予約代表者年齢
     */
    public void setRepAge(Integer repAge) {
        this.repAge = repAge;
    }

    /**
     * 予約代表者性別を取得する。
     * @return 予約代表者性別
     */
    public Gender getRepGender() {
        return repGender;
    }

    /**
     * 予約代表者性別を設定する。
     * @param repGender 予約代表者性別
     */
    public void setRepGender(Gender repGender) {
        this.repGender = repGender;
    }

    /**
     * 予約代表者会員番号を取得する。
     * @return 予約代表者会員番号
     */
    public String getRepMembershipNumber() {
        return repMembershipNumber;
    }

    /**
     * 予約代表者会員番号を設定する。
     * @param repMembershipNumber 予約代表者会員番号
     */
    public void setRepMembershipNumber(String repMembershipNumber) {
        this.repMembershipNumber = repMembershipNumber;
    }

    /**
     * 予約代表者電話番号1を取得する。
     * @return 予約代表者電話番号1
     */
    public String getRepTel1() {
        return repTel1;
    }

    /**
     * 予約代表者電話番号1を設定する。
     * @param repTel1 予約代表者電話番号1
     */
    public void setRepTel1(String repTel1) {
        this.repTel1 = repTel1;
    }

    /**
     * 予約代表者電話番号2を取得する。
     * @return 予約代表者電話番号2
     */
    public String getRepTel2() {
        return repTel2;
    }

    /**
     * 予約代表者電話番号2を設定する。
     * @param repTel2 予約代表者電話番号2
     */
    public void setRepTel2(String repTel2) {
        this.repTel2 = repTel2;
    }

    /**
     * 予約代表者電話番号3を取得する。
     * @return 予約代表者電話番号3
     */
    public String getRepTel3() {
        return repTel3;
    }

    /**
     * 予約代表者電話番号3を設定する。
     * @param repTel3 予約代表者電話番号3
     */
    public void setRepTel3(String repTel3) {
        this.repTel3 = repTel3;
    }

    /**
     * 予約代表者メールアドレスを取得する。
     * @return 予約代表者メールアドレス
     */
    public String getRepMail() {
        return repMail;
    }

    /**
     * 予約代表者メールアドレスを設定する。
     * @param repMail 予約代表者メールアドレス
     */
    public void setRepMail(String repMail) {
        this.repMail = repMail;
    }

    /**
     * フライト種別を取得する。
     * @return フライト種別
     */
    public FlightType getFlightType() {
        return flightType;
    }

    /**
     * フライト種別を設定する。
     * @param flightType フライト種別
     */
    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

    /**
     * 選択フライトリソースリストを取得する。
     * @return 選択フライトリソースリスト
     */
    public List<SelectFlightResource> getSelectFlightResourceList() {
        return selectFlightResourceList;
    }

    /**
     * 選択フライトリソースリストを設定する。
     * @param selectedflightResource 選択フライトリソースリスト
     */
    public void setSelectFlightResourceList(
            List<SelectFlightResource> selectFlightResourceList) {
        this.selectFlightResourceList = selectFlightResourceList;
    }

    /**
     * 搭乗者リソースリストを取得する。
     * @return 搭乗者リソースリスト
     */
    public List<PassengerResource> getPassengerResourceList() {
        return passengerResourceList;
    }

    /**
     * 搭乗者リソースリストを設定する。
     * @param passengerResource 搭乗者リソースリスト
     */
    public void setPassengerResourceList(
            List<PassengerResource> passengerResourceList) {
        this.passengerResourceList = passengerResourceList;
    }
}
