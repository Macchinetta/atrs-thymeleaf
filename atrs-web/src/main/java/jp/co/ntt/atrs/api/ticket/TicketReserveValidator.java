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
package jp.co.ntt.atrs.api.ticket;

import java.util.List;

import javax.inject.Inject;

import jp.co.ntt.atrs.domain.common.validate.ValidationUtil;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveErrorCode;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * チケット予約リソースバリデータ。
 * <p>
 * 以下のチェックを行う。
 * </p>
 * <ul>
 * <li>約代表者電話番号1と予約代表者電話番号2の指定合計文字数チェック。</li>
 * <li>搭乗者情報の必須チェック。</li>
 * <li>搭乗者情報が少なくとも1件以上入力されていることのチェック。</li>
 * </ul>
 * @author NTT 電電太郎
 */
@Component
public class TicketReserveValidator implements Validator {

    /**
     * 予約フライト選択リソースバリデータ。
     */
    @Inject
    ReservationFlightValidator reservationFlightValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return (TicketReserveResource.class).isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {
        // チケット予約リソースの相関チェックを実施
        TicketReserveResource resource = (TicketReserveResource) target;

        // 予約代表者電話番号チェック
        if (!(errors.hasFieldErrors("repTel1") || errors
                .hasFieldErrors("repTel2"))) {
            checkRepresentativeTel(errors, resource.getRepTel1(), resource
                    .getRepTel2());
        }

        // 搭乗者必須チェック(入力値が1つもない搭乗者情報は対象外)
        List<PassengerResource> passengerResourceList = resource
                .getPassengerResourceList();
        int inputtedPassengerCount = 0;
        for (int i = 0; i < passengerResourceList.size(); i++) {
            PassengerResource passengerForm = passengerResourceList.get(i);

            if (!passengerForm.isEmpty()) {
                inputtedPassengerCount++;
                checkRequired(errors, "familyName", i);
                checkRequired(errors, "givenName", i);
                checkRequired(errors, "age", i);
                checkRequired(errors, "gender", i);
            }
        }

        // 搭乗者が1件以上入力されていることをチェック
        if (inputtedPassengerCount == 0) {
            errors.reject(TicketReserveErrorCode.E_AR_B2_5002.code());
            return;
        }

        // 予約フライトチェック
        ValidationUtils.invokeValidator(reservationFlightValidator, resource,
                errors);
    }

    /**
     * 予約代表者電話番号の相関チェックを行う。
     * @param errors エラーメッセージを保持するクラス
     * @param repTel1 予約代表者電話番号1
     * @param repTel2 予約代表者電話番号2
     */
    private void checkRepresentativeTel(Errors errors, String repTel1,
            String repTel2) {
        if (!ValidationUtil.isValidTelNum(repTel1, repTel2)) {
            Object[] errorArgs = new Object[] {
                    new DefaultMessageSourceResolvable("repTel1"),
                    new DefaultMessageSourceResolvable("repTel2"),
                    ValidationUtil.TEL1_AND_TEL2_MIN_LENGTH,
                    ValidationUtil.TEL1_AND_TEL2_MAX_LENGTH };
            errors.reject(TicketReserveErrorCode.E_AR_B2_5003.code(),
                    errorArgs, "");
        }
    }

    /**
     * 搭乗者情報フォームリストのフィールド必須チェックを行う。
     * @param errors エラーメッセージを保持するクラス。
     * @param itemName チェック項目名
     * @param index リストのインデックス値
     */
    private void checkRequired(Errors errors, String itemName, int index) {
        String target = "passengerResourceList[" + index + "]." + itemName;
        ValidationUtils.rejectIfEmpty(errors, target, "NotNull",
                new Object[] { new DefaultMessageSourceResolvable(target) });
    }
}
