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
import java.util.Date;

/**
 * カード会員情報。
 * @author NTT 電電太郎
 */
public class Member implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 2571142253564057448L;

    /**
     * 会員番号。
     */
    private String membershipNumber;

    /**
     * 漢字姓。
     */
    private String kanjiFamilyName;

    /**
     * 漢字名。
     */
    private String kanjiGivenName;

    /**
     * カナ姓。
     */
    private String kanaFamilyName;

    /**
     * カナ名。
     */
    private String kanaGivenName;

    /**
     * 生年月日。
     */
    private Date birthday;

    /**
     * 性別。
     */
    private Gender gender;

    /**
     * 電話番号。
     */
    private String tel;

    /**
     * 郵便番号。
     */
    private String zipCode;

    /**
     * 住所。
     */
    private String address;

    /**
     * メールアドレス。
     */
    private String mail;

    /**
     * クレジットカード番号。
     */
    private String creditNo;

    /**
     * クレジットカード有効期限。
     */
    private String creditTerm;

    /**
     * クレジットカード種別。
     */
    private CreditType creditType;

    /**
     * カード会員ログイン情報。
     */
    private MemberLogin memberLogin;

    /**
     * 会員番号を取得する。
     * @return 会員番号
     */
    public String getMembershipNumber() {
        return membershipNumber;
    }

    /**
     * 会員番号を設定する。
     * @param membershipNumber 会員番号
     */
    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    /**
     * 漢字姓を取得する。
     * @return 漢字姓
     */
    public String getKanjiFamilyName() {
        return kanjiFamilyName;
    }

    /**
     * 漢字姓を設定する。
     * @param kanjiFamilyName 漢字姓
     */
    public void setKanjiFamilyName(String kanjiFamilyName) {
        this.kanjiFamilyName = kanjiFamilyName;
    }

    /**
     * 漢字名を取得する。
     * @return 漢字名
     */
    public String getKanjiGivenName() {
        return kanjiGivenName;
    }

    /**
     * 漢字名を設定する。
     * @param kanjiGivenName 漢字名
     */
    public void setKanjiGivenName(String kanjiGivenName) {
        this.kanjiGivenName = kanjiGivenName;
    }

    /**
     * カナ姓を取得する。
     * @return カナ姓
     */
    public String getKanaFamilyName() {
        return kanaFamilyName;
    }

    /**
     * カナ姓を設定する。
     * @param kanaFamilyName カナ姓
     */
    public void setKanaFamilyName(String kanaFamilyName) {
        this.kanaFamilyName = kanaFamilyName;
    }

    /**
     * カナ名を取得する。
     * @return カナ名
     */
    public String getKanaGivenName() {
        return kanaGivenName;
    }

    /**
     * カナ名を設定する。
     * @param kanaGivenName カナ名
     */
    public void setKanaGivenName(String kanaGivenName) {
        this.kanaGivenName = kanaGivenName;
    }

    /**
     * 生年月日を取得する。
     * @return 生年月日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 生年月日を設定する。
     * @param birthday 生年月日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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
     * 電話番号を取得する。
     * @return 電話番号
     */
    public String getTel() {
        return tel;
    }

    /**
     * 電話番号を設定する。
     * @param tel 電話番号
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 郵便番号を取得する。
     * @return 郵便番号
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 郵便番号を設定する。
     * @param zipCode 郵便番号
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 住所を取得する。
     * @return 住所
     */
    public String getAddress() {
        return address;
    }

    /**
     * 住所を設定する。
     * @param address 住所
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * メールアドレスを取得する。
     * @return メールアドレス
     */
    public String getMail() {
        return mail;
    }

    /**
     * メールアドレスを設定する。
     * @param mail メールアドレス
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * クレジットカード番号を取得する。
     * @return クレジットカード番号
     */
    public String getCreditNo() {
        return creditNo;
    }

    /**
     * クレジットカード番号を設定する。
     * @param creditNo クレジットカード番号
     */
    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    /**
     * クレジットカード有効期限を取得する。
     * @return クレジットカード有効期限
     */
    public String getCreditTerm() {
        return creditTerm;
    }

    /**
     * クレジットカード有効期限を設定する。
     * @param creditTerm クレジットカード有効期限
     */
    public void setCreditTerm(String creditTerm) {
        this.creditTerm = creditTerm;
    }

    /**
     * クレジットカード種別を取得する。
     * @return クレジットカード種別
     */
    public CreditType getCreditType() {
        return creditType;
    }

    /**
     * クレジットカード種別を設定する。
     * @param creditType クレジットカード種別
     */
    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    /**
     * カード会員ログイン情報を取得する。
     * @return カード会員ログイン情報
     */
    public MemberLogin getMemberLogin() {
        return memberLogin;
    }

    /**
     * カード会員ログイン情報を設定する。
     * @param memberLogin カード会員ログイン情報
     */
    public void setMemberLogin(MemberLogin memberLogin) {
        this.memberLogin = memberLogin;
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
