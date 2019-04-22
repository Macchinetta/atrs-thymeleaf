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

import jp.co.ntt.atrs.domain.service.b2.TicketReserveErrorCode;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * 予約フライト選択フォームのバリデータ。
 * <p>
 * 下記の場合をエラーとする。
 * </p>
 * <ul>
 * <li>フライトが正しく選択されていない場合。</li>
 * </ul>
 * @author NTT 電電次郎
 */
@Component
public class ReservationFlightValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return (IReservationFlightForm.class).isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {

        IReservationFlightForm form = (IReservationFlightForm) target;
        List<SelectFlightForm> selectFlightFormList = form
                .getSelectFlightFormList();

        // フライト種別に応じてチェック
        if (!errors.hasFieldErrors("flightType")) {

            switch (form.getFlightType()) {
            case RT:
                // 往復の場合

                // 選択フライト必須チェック
                if (CollectionUtils.isEmpty(selectFlightFormList)) {

                    // 往路、復路共に未入力の場合
                    errors.reject(
                            "NotNull.outwardFlight",
                            new Object[] { new DefaultMessageSourceResolvable("outwardFlight") },
                            "");
                    errors.reject(
                            "NotNull.homewardFlight",
                            new Object[] { new DefaultMessageSourceResolvable("homewardFlight") },
                            "");
                } else {

                    // フライトが2つ選択されていることをチェック
                    if (selectFlightFormList.size() == 2) {
                        // OK
                    } else if (selectFlightFormList.size() == 1) {

                        // 往路か復路のいずれかが未入力の場合
                        errors.reject(TicketReserveErrorCode.E_AR_B2_5001
                                .code());
                    } else {

                        // 往復で選択数が0-2以外は通常操作では設定されないケースであり、
                        // 改ざんとみなす
                        // 選択フライト情報にフィールドとしてエラー設定し、
                        // 後続処理で不正リクエスト例外とする
                        // Invalidは独自エラーコード(対応するメッセージ定義はない)
                        errors.rejectValue("selectFlightFormList", "Invalid");
                    }
                }

                break;

            case OW:
                // 片道の場合

                // 選択フライト必須チェック
                if (CollectionUtils.isEmpty(selectFlightFormList)) {
                    errors.reject(
                            "NotNull.outwardFlight",
                            new Object[] { new DefaultMessageSourceResolvable("outwardFlight") },
                            "");
                } else {

                    // フライトが1つ選択されていることをチェック
                    if (1 == selectFlightFormList.size()) {
                        // OK
                    } else {

                        // 片道で選択数が0-1以外は通常操作では設定されないケースであり、
                        // 改ざんとみなす
                        // 選択フライト情報にフィールドとしてエラー設定し、
                        // 後続処理で不正リクエスト例外とする
                        // Invalidは独自エラーコード(対応するメッセージ定義はない)
                        errors.rejectValue("selectFlightFormList", "Invalid");
                    }
                }

                break;

            default:
                // 処理なし

                break;
            }
        }

    }

}
