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
package jp.co.ntt.atrs.domain.common.validate;

/**
 * 入力値チェックの共通ユーティリティ。
 * @author NTT 電電太郎
 */
public class ValidationUtil {

    /**
     * 電話番号1と電話番号2の最大合計桁数。
     */
    public static final int TEL1_AND_TEL2_MAX_LENGTH = 7;

    /**
     * 電話番号1と電話番号2の最小合計桁数。
     */
    public static final int TEL1_AND_TEL2_MIN_LENGTH = 6;

    /**
     * コンストラクタ。
     */
    private ValidationUtil() {
        // 実装必要なし
    }

    /**
     * 電話番号の市外局番＋市内局番の合計桁数が6-7桁になるかをチェックする。
     * @param tel1 市外局番
     * @param tel2 市内局番
     * @return 6-7桁:true それ以外:false
     */
    public static boolean isValidTelNum(String tel1, String tel2) {
        int telLength = tel1.length() + tel2.length();
        return TEL1_AND_TEL2_MIN_LENGTH <= telLength
                && telLength <= TEL1_AND_TEL2_MAX_LENGTH;
    }

}
