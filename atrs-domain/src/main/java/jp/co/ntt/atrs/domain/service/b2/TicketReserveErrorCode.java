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
package jp.co.ntt.atrs.domain.service.b2;

import jp.co.ntt.atrs.domain.common.exception.AtrsErrorCode;

/**
 * チケット予約サービスでのエラーコードを表す列挙型。
 * @author NTT 電電三郎
 */
public enum TicketReserveErrorCode implements AtrsErrorCode {

    /**
     * 往路の到着時刻と復路の出発時刻の間が一定間隔以上経過していない事を通知するためのエラーコード。
     */
    E_AR_B2_2001("e.ar.b2.2001"),

    /**
     * 予約代表者情報に指定された会員番号に一致する会員情報が存在しない事を通知するためのエラーコード。
     */
    E_AR_B2_2002("e.ar.b2.2002"),

    /**
     * 予約代表者情報に指定された会員番号に一致する会員情報と指定された予約代表者情報が一致していない事を通知するためのエラーコード。
     */
    E_AR_B2_2003("e.ar.b2.2003"),

    /**
     * 予約代表者としての許容年齢を超えていない事を通知するためのエラーコード。
     * <p>
     * {0} : 予約代表者としての許容年齢
     * </p>
     */
    E_AR_B2_2004("e.ar.b2.2004"),

    /**
     * 会員情報に指定された会員番号に一致する会員情報が存在しない事を通知するためのエラーコード。
     * <p>
     * {0} : 会員情報の順番
     * </p>
     */
    E_AR_B2_2005("e.ar.b2.2005"),

    /**
     * 会員情報に指定された会員番号に一致する会員情報と指定された会員情報が一致していない事を通知するためのエラーコード。
     * <p>
     * {0} : 会員情報の順番
     * </p>
     */
    E_AR_B2_2006("e.ar.b2.2006"),

    /**
     * レディース割の運賃種別を選択した際に、搭乗者として男性が選択されている事を通知するためのエラーコード。
     */
    E_AR_B2_2007("e.ar.b2.2007"),

    /**
     * 搭乗日が予約可能な期間でない事を通知するためのエラーコード。
     */
    E_AR_B2_2008("e.ar.b2.2008"),

    /**
     * 搭乗者数分の残席数が存在しない事を通知するためのエラーコード。
     */
    E_AR_B2_2009("e.ar.b2.2009"),

    /**
     * 利用可能最少人数を満たしていいない事を通知するためのエラーコード。
     * <p>
     * {0} : 運賃種別名<br>
     * {1} : 利用可能最少人数
     * </p>
     */
    E_AR_B2_2010("e.ar.b2.2010"),

    /**
     * 往復予約時に往路または復路が選択されていない事を通知するためのエラーコード。
     */
    E_AR_B2_5001("e.ar.b2.5001"),

    /**
     * 会員情報（搭乗者情報）が指定されていない事を通知するためのエラーコード。
     */
    E_AR_B2_5002("e.ar.b2.5002"),

    /**
     * 2つの項目値の値が「最小合計桁数～最大合計桁数」でない事を通知するためのエラーコード。
     * <p>
     * {0} : 項目名1<br>
     * {1} : 項目名2<br>
     * {2} : 最小合計桁数<br>
     * {3} : 最大合計桁数
     * </p>
     */
    E_AR_B2_5003("e.ar.b2.5003");

    /**
     * エラーコード。
     */
    private final String code;

    /**
     * コンストラクタ。
     * @param code エラーコード。
     */
    private TicketReserveErrorCode(String code) {
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
