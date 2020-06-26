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
package jp.co.ntt.atrs.domain.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 予約情報。
 * @author NTT 電電太郎
 */
public class Reservation implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 7001285729498235917L;

    /**
     * 予約番号。
     */
    private String reserveNo;

    /**
     * 予約日付。
     */
    private Date reserveDate;

    /**
     * 合計金額。
     */
    private Integer totalFare;

    /**
     * 予約代表者姓。
     */
    private String repFamilyName;

    /**
     * 予約代表者名。
     */
    private String repGivenName;

    /**
     * 予約代表者年齢。
     */
    private Integer repAge;

    /**
     * 予約代表者性別。
     */
    private Gender repGender;

    /**
     * 予約代表者電話番号。
     */
    private String repTel;

    /**
     * 予約代表者メールアドレス。
     */
    private String repMail;

    /**
     * 予約代表者のカード会員情報。
     */
    private Member repMember;

    /**
     * 予約フライト情報リスト。
     */
    private List<ReserveFlight> reserveFlightList;

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
     * 予約日付を取得する。
     * @return 予約日付
     */
    public Date getReserveDate() {
        return reserveDate;
    }

    /**
     * 予約日付を設定する。
     * @param reserveDate 予約日付
     */
    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    /**
     * 合計金額を取得する。
     * @return 合計金額
     */
    public Integer getTotalFare() {
        return totalFare;
    }

    /**
     * 合計金額を設定する。
     * @param totalFare 合計金額
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
     * 予約代表者電話番号を取得する。
     * @return 予約代表者電話番号
     */
    public String getRepTel() {
        return repTel;
    }

    /**
     * 予約代表者電話番号を設定する。
     * @param repTel 予約代表者電話番号
     */
    public void setRepTel(String repTel) {
        this.repTel = repTel;
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
     * 予約代表者のカード会員情報を取得する。
     * @return 予約代表者のカード会員情報
     */
    public Member getRepMember() {
        return repMember;
    }

    /**
     * 予約代表者のカード会員情報を設定する。
     * @param repMember 予約代表者のカード会員情報
     */
    public void setRepMember(Member repMember) {
        this.repMember = repMember;
    }

    /**
     * 予約フライト情報リストを取得する。
     * @return 予約フライト情報リスト
     */
    public List<ReserveFlight> getReserveFlightList() {
        return reserveFlightList;
    }

    /**
     * 予約フライト情報リストを設定する。
     * @param reserveFlightList 予約フライト情報リスト
     */
    public void setReserveFlightList(List<ReserveFlight> reserveFlightList) {
        this.reserveFlightList = new ArrayList<>(reserveFlightList);
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
