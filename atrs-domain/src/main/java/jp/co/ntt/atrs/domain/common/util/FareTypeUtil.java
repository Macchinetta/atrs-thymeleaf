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

import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.FlightType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 運賃種別に関するユーティリティクラス。
 * @author NTT 電電太郎
 */
public class FareTypeUtil {

    /**
     * 往復予約の運賃種別。
     */
    private static final Set<FareTypeCd> ROUND_TRIP_FARETYPES;
    static {
        ROUND_TRIP_FARETYPES = new LinkedHashSet<FareTypeCd>();
        ROUND_TRIP_FARETYPES.add(FareTypeCd.RT);
        ROUND_TRIP_FARETYPES.add(FareTypeCd.SRT);
    }

    /**
     * 片道予約の運賃種別。
     */
    private static final Set<FareTypeCd> ONE_WAY_FARETYPES;
    static {
        ONE_WAY_FARETYPES = new LinkedHashSet<FareTypeCd>();
        ONE_WAY_FARETYPES.add(FareTypeCd.OW);
        ONE_WAY_FARETYPES.add(FareTypeCd.RD1);
        ONE_WAY_FARETYPES.add(FareTypeCd.RD7);
        ONE_WAY_FARETYPES.add(FareTypeCd.ED);
        ONE_WAY_FARETYPES.add(FareTypeCd.LD);
        ONE_WAY_FARETYPES.add(FareTypeCd.GD);
        ONE_WAY_FARETYPES.add(FareTypeCd.SOW);
        ONE_WAY_FARETYPES.add(FareTypeCd.SRD);
    }

    /**
     * コンストラクタ。
     */
    private FareTypeUtil() {
        // 処理なし
    }

    /**
     * 運賃種別が往復の運賃種別かを判定する。
     * @param fareTypeCd 運賃種別
     * @return 往復の運賃種別の場合true
     */
    public static boolean isRoundTrip(FareTypeCd fareTypeCd) {
        return ROUND_TRIP_FARETYPES.contains(fareTypeCd);
    }

    /**
     * 運賃種別が片道の運賃種別かを判定する。
     * @param fareTypeCd 運賃種別
     * @return 片道の運賃種別の場合true
     */
    public static boolean isOneWay(FareTypeCd fareTypeCd) {
        return ONE_WAY_FARETYPES.contains(fareTypeCd);
    }

    /**
     * フライト種別に応じた運賃種別リストを取得する。
     * @param flightType フライト種別
     * @return 運賃種別リスト
     */
    public static List<FareTypeCd> getFareTypeCdList(FlightType flightType) {

        List<FareTypeCd> fareTypeList = new ArrayList<>();

        if (FlightType.OW.equals(flightType)) {
            fareTypeList.addAll(ONE_WAY_FARETYPES);
        } else {
            fareTypeList.addAll(ROUND_TRIP_FARETYPES);
        }

        return fareTypeList;
    }

}
