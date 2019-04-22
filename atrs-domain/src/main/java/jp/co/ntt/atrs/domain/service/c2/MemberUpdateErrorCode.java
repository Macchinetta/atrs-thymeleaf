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
package jp.co.ntt.atrs.domain.service.c2;

import jp.co.ntt.atrs.domain.common.exception.AtrsErrorCode;

/**
 * 会員情報変更サービスでのエラーコードを表す列挙型。
 * @author NTT 電電花子
 */
public enum MemberUpdateErrorCode implements AtrsErrorCode {

    /**
     * 入力されたパスワードが現在使用されているパスワードと一致しない事を通知するためのエラーコード。
     */
    E_AR_C2_2001("e.ar.c2.2001"),

    /**
     * パスワードと再入力したパスワードが一致しない事を通知するためのエラーコード。
     */
    E_AR_C2_5001("e.ar.c2.5001"),

    /**
     * パスワード変更する際に、パスワード又はパスワードの再入力がない事を通知するためのエラーコード。
     */
    E_AR_C2_5002("e.ar.c2.5002");

    /**
     * エラーコード。
     */
    private final String code;

    /**
     * コンストラクタ。
     * @param code エラーコード。
     */
    private MemberUpdateErrorCode(String code) {
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
