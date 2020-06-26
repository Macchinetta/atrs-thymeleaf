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

import jp.co.ntt.atrs.app.b0.IReservationFlightForm;
import jp.co.ntt.atrs.app.b0.SelectFlightForm;
import jp.co.ntt.atrs.domain.common.validate.FixedLength;
import jp.co.ntt.atrs.domain.common.validate.FullWidthKatakana;
import jp.co.ntt.atrs.domain.common.validate.HalfWidth;
import jp.co.ntt.atrs.domain.common.validate.HalfWidthNumber;
import jp.co.ntt.atrs.domain.model.FlightType;
import jp.co.ntt.atrs.domain.model.Gender;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

/**
 * チケット予約フォーム。
 * <p>
 * 入力した搭乗者と予約代表者の情報を受け取る。
 * </p>
 * <p>
 * 以下のフォームを内包する。
 * </p>
 * <ul>
 * <li>搭乗者情報フォームのリスト</li>
 * <li>選択フライト情報フォームリスト</li>
 * </ul>
 * @author NTT 電電三郎
 */
public class TicketReserveForm implements IReservationFlightForm, Serializable {

    /**
     * serialVersion。
     */
    private static final long serialVersionUID = -1793020435504468122L;

    /**
     * 初期表示搭乗者数。
     */
    private static final int INITIAL_PASSENGERS_NUMBER = 3;

    /**
     * 最大搭乗者数。
     */
    private static final int MAX_PASSENGERS_NUMBER = 6;

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
     * 搭乗者情報フォームリスト。
     */
    @Valid
    private List<PassengerForm> passengerFormList = new ArrayList<>();

    /**
     * フライト種別(非表示項目)。
     */
    @NotNull
    private FlightType flightType;

    /**
     * 選択フライト情報フォームリスト(非表示項目)。
     */
    @Valid
    private List<SelectFlightForm> selectFlightFormList = new ArrayList<>();

    /**
     * コンストラクタ。
     * <p>
     * 搭乗者情報フォームリストの要素数を最大搭乗者数に設定する。
     * </p>
     */
    public TicketReserveForm() {
        for (int i = 0; i < MAX_PASSENGERS_NUMBER; i++) {
            passengerFormList.add(new PassengerForm());
        }
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
     * 搭乗者情報フォームリストを取得する。
     * @return 搭乗者情報フォームリスト
     */
    public List<PassengerForm> getPassengerFormList() {
        return passengerFormList;
    }

    /**
     * 搭乗者情報フォームリストを設定する。
     * @param passengerFormList 搭乗者情報フォームリスト
     */
    public void setPassengerFormList(List<PassengerForm> passengerFormList) {
        this.passengerFormList = passengerFormList;
    }

    /**
     * フライト種別を取得する。
     * @return フライト種別
     */
    @Override
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
     * 選択フライト情報フォームリストを取得する。
     * @return 選択フライト情報フォームリスト
     */
    @Override
    public List<SelectFlightForm> getSelectFlightFormList() {
        return selectFlightFormList;
    }

    /**
     * 選択フライト情報フォームリストを設定する。
     * @param selectFlightFormList 選択フライト情報フォームリスト
     */
    public void setSelectFlightFormList(
            List<SelectFlightForm> selectFlightFormList) {
        this.selectFlightFormList = selectFlightFormList;
    }

    /**
     * 搭乗者情報フォームリストに搭乗者を設定する。
     * @param index 設定位置
     * @param passengerForm 搭乗者情報フォーム
     */
    public void setPassenger(int index, PassengerForm passengerForm) {
        passengerFormList.set(index, passengerForm);
    }

    /**
     * 値の存在する搭乗者がに先になるよう、搭乗者情報フォームリストのインデックス位置を 再設定する。
     */
    public void resetPassengersIndex() {

        Iterator<PassengerForm> iterator = passengerFormList.iterator();
        while (iterator.hasNext()) {
            PassengerForm passengerform = iterator.next();
            if (passengerform.isEmpty()) {
                iterator.remove();
            }
        }
        for (int i = passengerFormList.size(); i < MAX_PASSENGERS_NUMBER; i++) {
            passengerFormList.add(new PassengerForm());
        }
    }

    /**
     * 追加搭乗者(4人目以降の搭乗者)が存在するか判定する。
     * @return 存在する場合true
     */
    public boolean hasAdditionalPassenger() {
        if (passengerFormList != null
                && INITIAL_PASSENGERS_NUMBER < passengerFormList.size()) {
            for (int i = INITIAL_PASSENGERS_NUMBER; i < passengerFormList
                    .size(); i++) {
                PassengerForm passenger = passengerFormList.get(i);
                if (passenger != null && !passenger.isEmpty()) {
                    return true;
                }
            }
        }

        return false;
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
