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
package jp.co.ntt.atrs.app.b0;

import jp.co.ntt.atrs.domain.model.FlightType;

import java.util.List;

/**
 * 予約フライトフォームインタフェース。 予約フライト共通入力値チェックで使用するメソッドを定義する。
 * @author NTT 電電次郎
 * @see ReservationFlightValidator#validate(Object, org.springframework.validation.Errors)
 */
public interface IReservationFlightForm {

    /**
     * フライト種別を取得する。
     * @return フライト種別
     */
    FlightType getFlightType();

    /**
     * 選択フライト情報フォームのリストを取得する。
     * @return 選択フライト情報フォームのリスト
     */
    List<SelectFlightForm> getSelectFlightFormList();

}
