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

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

import jp.co.ntt.atrs.domain.common.validate.FixedLength;
import jp.co.ntt.atrs.domain.common.validate.FullWidthKatakana;
import jp.co.ntt.atrs.domain.common.validate.HalfWidthNumber;
import jp.co.ntt.atrs.domain.model.Gender;

/**
 * 搭乗者リソース
 * @author NTT 電電太郎
 */
public class PassengerResource implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -2531341409003246329L;

    /**
     * 搭乗者姓。
     */
    @NotNull
    @Size(min = 1, max = 10)
    @FullWidthKatakana
    private String familyName;

    /**
     * 搭乗者名。
     */
    @NotNull
    @Size(min = 1, max = 10)
    @FullWidthKatakana
    private String givenName;

    /**
     * 搭乗者年齢。
     */
    @NotNull
    @Min(0)
    @Digits(integer = 3, fraction = 1)
    private Integer age;

    /**
     * 搭乗者性別。
     */
    @NotNull
    private Gender gender;

    /**
     * 搭乗者の会員番号。
     */
    @FixedLength(10)
    @HalfWidthNumber
    private String membershipNumber;

    /**
     * 搭乗者姓を取得する。
     * @return 搭乗者姓
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * 搭乗者姓を設定する。
     * @param familyName 搭乗者姓
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * 搭乗者名を取得する。
     * @return 搭乗者名
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * 搭乗者名を設定する。
     * @param givenName 搭乗者名
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * 搭乗者年齢を取得する。
     * @return 搭乗者年齢
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 搭乗者年齢を設定する。
     * @param age 搭乗者年齢
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 搭乗者性別を取得する。
     * @return 搭乗者性別
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * 搭乗者性別を設定する。
     * @param gender 搭乗者性別
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * 搭乗者の会員番号を取得する。
     * @return 搭乗者の会員番号
     */
    public String getMembershipNumber() {
        return membershipNumber;
    }

    /**
     * 搭乗者の会員番号を設定する。
     * @param membershipNumber 搭乗者の会員番号
     */
    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    /**
     * 情報を保持しているか判定する。
     * @return 保持プロパティが全てnullまたは空文字の場合true
     */
    public boolean isEmpty() {
        return !StringUtils.hasText(familyName)
                && !StringUtils.hasText(givenName) && age == null
                && gender == null && !StringUtils.hasText(membershipNumber);
    }

}
