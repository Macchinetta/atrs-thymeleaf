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
package jp.co.ntt.atrs.domain.service.c0;

import jp.co.ntt.atrs.domain.common.exception.AtrsErrorCode;

/**
 * 会員サービス共通のエラーコードを表す列挙型。
 * @author NTT 電電花子
 */
public enum MemberErrorCode implements AtrsErrorCode {

    /**
     * メールアドレスと再入力したメールアドレスが一致しない事を通知するためのエラーコード。
     */
    E_AR_C0_5001("e.ar.c0.5001"),

    /**
     * 市外局番と市内局番の合計桁数が許容範囲(6～7桁)外である事を通知するためのエラーコード。
     */
    E_AR_C0_5002("e.ar.c0.5002"),

    /**
     * 生年月日の入力許容範囲(1900年1月1日から現在まで)外である事を通知するためのエラーコード。
     */
    E_AR_C0_5003("e.ar.c0.5003");

    /**
     * エラーコード。
     */
    private final String code;

    /**
     * コンストラクタ。
     * @param code エラーコード。
     */
    private MemberErrorCode(String code) {
        this.code = code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String code() {
        return code;
    }

}
