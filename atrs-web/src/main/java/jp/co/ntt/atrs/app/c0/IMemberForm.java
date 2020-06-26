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
package jp.co.ntt.atrs.app.c0;

import java.util.Date;

/**
 * 会員情報フォームインタフェース。 共通入力値チェック、共通Bean変換処理で使用するメソッドを定義する。
 * @author NTT 電電花子
 * @see MemberValidator#validate(Object, org.springframework.validation.Errors)
 * @see MemberHelper#toMember(IMemberForm)
 */
public interface IMemberForm {

    /**
     * 生年月日を取得する。
     * @return 生年月日
     */
    Date getDateOfBirth();

    /**
     * 電話番号1を取得する。
     * @return 電話番号1
     */
    String getTel1();

    /**
     * 電話番号2を取得する。
     * @return 電話番号2
     */
    String getTel2();

    /**
     * 電話番号3を取得する。
     * @return 電話番号3
     */
    String getTel3();

    /**
     * 郵便番号1を取得する。
     * @return 郵便番号1
     */
    String getZipCode1();

    /**
     * 郵便番号2を取得する。
     * @return 郵便番号2
     */
    String getZipCode2();

    /**
     * Eメールを取得する。
     * @return Eメール
     */
    String getMail();

    /**
     * Eメール再入力を取得する。
     * @return Eメール再入力
     */
    String getReEnterMail();

    /**
     * クレジットカード有効期限（月）を取得する。
     * @return クレジットカード有効期限（月）
     */
    String getCreditMonth();

    /**
     * クレジットカード有効期限（年）を取得する。
     * @return クレジットカード有効期限（年）
     */
    String getCreditYear();

}
