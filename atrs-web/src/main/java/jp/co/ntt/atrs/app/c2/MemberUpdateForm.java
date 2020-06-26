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
package jp.co.ntt.atrs.app.c2;

import jp.co.ntt.atrs.app.c0.IMemberForm;
import jp.co.ntt.atrs.domain.common.validate.FixedLength;
import jp.co.ntt.atrs.domain.common.validate.FullWidth;
import jp.co.ntt.atrs.domain.common.validate.FullWidthKatakana;
import jp.co.ntt.atrs.domain.common.validate.HalfWidth;
import jp.co.ntt.atrs.domain.common.validate.HalfWidthNumber;
import jp.co.ntt.atrs.domain.model.Gender;

import org.springframework.format.annotation.DateTimeFormat;
import org.terasoluna.gfw.common.codelist.ExistInCodeList;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

/**
 * 会員情報変更フォーム。
 * @author NTT 電電花子
 */
public class MemberUpdateForm implements IMemberForm, Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -7245796929268802831L;

    /**
     * 氏名(姓)。
     */
    @NotNull
    @FullWidth
    @Size(min = 1, max = 10)
    private String kanjiFamilyName;

    /**
     * 氏名(名)。
     */
    @NotNull
    @FullWidth
    @Size(min = 1, max = 10)
    private String kanjiGivenName;

    /**
     * 氏名カタカナ(セイ)。
     */
    @NotNull
    @FullWidthKatakana
    @Size(min = 1, max = 10)
    private String kanaFamilyName;

    /**
     * 氏名カタカナ(メイ)。
     */
    @NotNull
    @FullWidthKatakana
    @Size(min = 1, max = 10)
    private String kanaGivenName;

    /**
     * 性別。
     */
    @NotNull
    private Gender gender;

    /**
     * 生年月日。
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date dateOfBirth;

    /**
     * 電話番号1。
     */
    @NotNull
    @HalfWidthNumber
    @Size(min = 2, max = 5)
    private String tel1;

    /**
     * 電話番号2。
     */
    @NotNull
    @HalfWidthNumber
    @Size(min = 1, max = 4)
    private String tel2;

    /**
     * 電話番号3。
     */
    @NotNull
    @HalfWidthNumber
    @FixedLength(4)
    private String tel3;

    /**
     * 郵便番号1。
     */
    @NotNull
    @HalfWidthNumber
    @FixedLength(3)
    private String zipCode1;

    /**
     * 郵便番号2。
     */
    @NotNull
    @HalfWidthNumber
    @FixedLength(4)
    private String zipCode2;

    /**
     * 住所。
     */
    @NotNull
    @FullWidth
    @Size(min = 1, max = 60)
    private String address;

    /**
     * Eメール。
     */
    @NotNull
    @Email
    @Size(min = 1, max = 256)
    @HalfWidth
    private String mail;

    /**
     * Eメール再入力。
     */
    @NotNull
    @Email
    @Size(min = 1, max = 256)
    @HalfWidth
    private String reEnterMail;

    /**
     * クレジットカード会社。
     */
    @NotNull
    @ExistInCodeList(codeListId = "CL_CREDITTYPE")
    private String creditTypeCd;

    /**
     * クレジットカード番号。
     */
    @NotNull
    @FixedLength(16)
    @HalfWidthNumber
    private String creditNo;

    /**
     * クレジットカード有効期限（月）。
     */
    @NotNull
    @Min(1)
    @Max(12)
    private String creditMonth;

    /**
     * クレジットカード有効期限（年）。
     */
    @NotNull
    @Min(00)
    private String creditYear;

    /**
     * パスワード。
     */
    @Size(min = 8, max = 20)
    @HalfWidth
    private String password;

    /**
     * パスワード再入力。
     */
    @Size(min = 8, max = 20)
    @HalfWidth
    private String reEnterPassword;

    /**
     * 現在のパスワード。
     */
    @Size(min = 8, max = 20)
    @HalfWidth
    private String currentPassword;

    /**
     * 氏名(姓)を取得する。
     * @return 氏名(姓)
     */
    public String getKanjiFamilyName() {
        return kanjiFamilyName;
    }

    /**
     * 氏名(姓)を設定する。
     * @param kanjiFamilyName 氏名(姓)
     */
    public void setKanjiFamilyName(String kanjiFamilyName) {
        this.kanjiFamilyName = kanjiFamilyName;
    }

    /**
     * 氏名(名)を取得する。
     * @return 氏名(名)
     */
    public String getKanjiGivenName() {
        return kanjiGivenName;
    }

    /**
     * 氏名(名)を設定する。
     * @param kanjiGivenName 氏名(名)
     */
    public void setKanjiGivenName(String kanjiGivenName) {
        this.kanjiGivenName = kanjiGivenName;
    }

    /**
     * 氏名カタカナ(セイ)を取得する。
     * @return 氏名カタカナ(セイ)
     */
    public String getKanaFamilyName() {
        return kanaFamilyName;
    }

    /**
     * 氏名カタカナ(セイ)を設定する。
     * @param kanaFamilyName 氏名カタカナ(セイ)
     */
    public void setKanaFamilyName(String kanaFamilyName) {
        this.kanaFamilyName = kanaFamilyName;
    }

    /**
     * 氏名カタカナ(メイ)を取得する。
     * @return 氏名カタカナ(メイ)
     */
    public String getKanaGivenName() {
        return kanaGivenName;
    }

    /**
     * 氏名カタカナ(メイ)を設定する。
     * @param kanaGivenName 氏名カタカナ(メイ)
     */
    public void setKanaGivenName(String kanaGivenName) {
        this.kanaGivenName = kanaGivenName;
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
     * 生年月日を取得する。
     * @return 生年月日
     */
    @Override
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * 生年月日を設定する。
     * @param dateOfBirth 生年月日
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * 電話番号1を取得する。
     * @return 電話番号1
     */
    @Override
    public String getTel1() {
        return tel1;
    }

    /**
     * 電話番号1を設定する。
     * @param tel1 電話番号1
     */
    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    /**
     * 電話番号2を取得する。
     * @return 電話番号2
     */
    @Override
    public String getTel2() {
        return tel2;
    }

    /**
     * 電話番号2を設定する。
     * @param tel2 電話番号2
     */
    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    /**
     * 電話番号3を取得する。
     * @return 電話番号3
     */
    @Override
    public String getTel3() {
        return tel3;
    }

    /**
     * 電話番号3を設定する。
     * @param tel3 電話番号3
     */
    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    /**
     * 郵便番号1を取得する。
     * @return 郵便番号1
     */
    @Override
    public String getZipCode1() {
        return zipCode1;
    }

    /**
     * 郵便番号1を設定する。
     * @param zipCode1 郵便番号1
     */
    public void setZipCode1(String zipCode1) {
        this.zipCode1 = zipCode1;
    }

    /**
     * 郵便番号2を取得する。
     * @return 郵便番号2
     */
    @Override
    public String getZipCode2() {
        return zipCode2;
    }

    /**
     * 郵便番号2を設定する。
     * @param zipCode2 郵便番号2
     */
    public void setZipCode2(String zipCode2) {
        this.zipCode2 = zipCode2;
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
     * Eメールを取得する。
     * @return Eメール
     */
    @Override
    public String getMail() {
        return mail;
    }

    /**
     * Eメールを設定する。
     * @param mail Eメール
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Eメール再入力を取得する。
     * @return Eメール再入力
     */
    @Override
    public String getReEnterMail() {
        return reEnterMail;
    }

    /**
     * Eメール再入力を設定する。
     * @param reEnterMail Eメール再入力
     */
    public void setReEnterMail(String reEnterMail) {
        this.reEnterMail = reEnterMail;
    }

    /**
     * クレジットカード会社を取得する。
     * @return クレジットカード会社
     */
    public String getCreditTypeCd() {
        return creditTypeCd;
    }

    /**
     * クレジットカード会社を設定する。
     * @param creditTypeCd クレジットカード会社
     */
    public void setCreditTypeCd(String creditTypeCd) {
        this.creditTypeCd = creditTypeCd;
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
     * クレジットカード有効期限（月）を取得する。
     * @return クレジットカード有効期限（月）
     */
    @Override
    public String getCreditMonth() {
        return creditMonth;
    }

    /**
     * クレジットカード有効期限（月）を設定する。
     * @param creditMonth クレジットカード有効期限（月）
     */
    public void setCreditMonth(String creditMonth) {
        this.creditMonth = creditMonth;
    }

    /**
     * クレジットカード有効期限（年）を取得する。
     * @return クレジットカード有効期限（年）
     */
    @Override
    public String getCreditYear() {
        return creditYear;
    }

    /**
     * クレジットカード有効期限（年）を設定する。
     * @param creditYear クレジットカード有効期限（年）
     */
    public void setCreditYear(String creditYear) {
        this.creditYear = creditYear;
    }

    /**
     * パスワードを取得する。
     * @return パスワード
     */
    public String getPassword() {
        return password;
    }

    /**
     * パスワードを設定する。
     * @param password パスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * パスワード再入力を取得する。
     * @return パスワード再入力
     */
    public String getReEnterPassword() {
        return reEnterPassword;
    }

    /**
     * パスワード再入力を設定する。
     * @param reEnterPassword パスワード再入力
     */
    public void setReEnterPassword(String reEnterPassword) {
        this.reEnterPassword = reEnterPassword;
    }

    /**
     * 現在のパスワードを取得する。
     * @return 確認用パスワード
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * 現在のパスワードを設定する。
     * @param currentPassword 確認用パスワード
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

}
