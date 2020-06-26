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

/**
 * 搭乗者情報。
 * @author NTT 電電太郎
 */
public class Passenger implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -7641636452098370026L;

    /**
     * 搭乗者番号。
     */
    private Integer passengerNo;

    /**
     * 予約フライト番号。
     */
    private Integer reserveFlightNo;

    /**
     * 姓。
     */
    private String familyName;

    /**
     * 名。
     */
    private String givenName;

    /**
     * 年齢。
     */
    private Integer age;

    /**
     * 性別。
     */
    private Gender gender;

    /**
     * カード会員情報。
     */
    private Member member;

    /**
     * 搭乗者番号を取得する。
     * @return 搭乗者番号
     */
    public Integer getPassengerNo() {
        return passengerNo;
    }

    /**
     * 搭乗者番号を設定する。
     * @param passengerNo 搭乗者番号
     */
    public void setPassengerNo(Integer passengerNo) {
        this.passengerNo = passengerNo;
    }

    /**
     * 予約フライト番号を取得する。
     * @return 予約フライト番号
     */
    public Integer getReserveFlightNo() {
        return reserveFlightNo;
    }

    /**
     * 予約フライト番号を設定する。
     * @param reserveFlightNo 予約フライト番号
     */
    public void setReserveFlightNo(Integer reserveFlightNo) {
        this.reserveFlightNo = reserveFlightNo;
    }

    /**
     * 姓を取得する。
     * @return 姓
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * 姓を設定する。
     * @param familyName 姓
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * 名を取得する。
     * @return 名
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * 名を設定する。
     * @param givenName 名
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * 年齢を取得する。
     * @return 年齢
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 年齢を設定する。
     * @param age 年齢
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 性別を取得する。
     * @return 性別
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * 性別を設定する。
     * @param gender 性別
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * カード会員情報を取得する。
     * @return カード会員情報
     */
    public Member getMember() {
        return member;
    }

    /**
     * カード会員情報を設定する。
     * @param member カード会員情報
     */
    public void setMember(Member member) {
        this.member = member;
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
