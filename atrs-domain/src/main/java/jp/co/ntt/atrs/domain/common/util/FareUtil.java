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
package jp.co.ntt.atrs.domain.common.util;

import java.math.BigDecimal;

/**
 * 運賃に関するユーティリティクラス。
 * @author NTT 電電太郎
 */
public class FareUtil {

    /**
     * 運賃切り上げに使用するスケール値。
     */
    private static final int CEIL_FARE_SCALE = -2;

    /**
     * コンストラクタ。
     */
    private FareUtil() {
        // 処理なし
    }

    /**
     * 運賃の100円未満の値を切上げる。
     * @param fare 運賃
     * @return 100円未満を切上げた運賃
     */
    public static int ceilFare(int fare) {
        return new BigDecimal(fare).setScale(CEIL_FARE_SCALE,
                BigDecimal.ROUND_UP).intValue();
    }
}
