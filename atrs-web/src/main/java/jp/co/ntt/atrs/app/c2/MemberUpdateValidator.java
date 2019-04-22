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
package jp.co.ntt.atrs.app.c2;

import jp.co.ntt.atrs.app.c0.MemberValidator;
import jp.co.ntt.atrs.domain.service.c2.MemberUpdateErrorCode;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.inject.Inject;

/**
 * 会員情報変更フォームのバリデータ。
 * @author NTT 電電花子
 */
@Component
public class MemberUpdateValidator implements Validator {

    /**
     * 会員情報バリデータ。
     */
    @Inject
    MemberValidator memberValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return MemberUpdateForm.class.isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {

        MemberUpdateForm form = (MemberUpdateForm) target;

        // パスワードチェック
        if (!errors.hasFieldErrors("currentPassword")
                && !errors.hasFieldErrors("password")
                && !errors.hasFieldErrors("reEnterPassword")) {

            String currentPassword = form.getCurrentPassword();
            String password = form.getPassword();
            String reEnterPassword = form.getReEnterPassword();

            // 現在のパスワード、パスワード、パスワード再入力のいずれかが入力されているか
            if (StringUtils.hasLength(currentPassword)
                    || StringUtils.hasLength(password)
                    || StringUtils.hasLength(reEnterPassword)) {

                if (!StringUtils.hasLength(currentPassword)
                        || !StringUtils.hasLength(password)
                        || !StringUtils.hasLength(reEnterPassword)) {

                    // 空欄がある場合エラー
                    errors.reject(MemberUpdateErrorCode.E_AR_C2_5002.code());

                } else if (!password.equals(reEnterPassword)) {

                    // パスワードと再入力パスワードが異なる場合エラー
                    errors.reject(MemberUpdateErrorCode.E_AR_C2_5001.code());
                }
            }
        }

        // 共通チェック
        ValidationUtils.invokeValidator(memberValidator, form, errors);

    }

}
