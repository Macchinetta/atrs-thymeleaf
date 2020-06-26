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
package jp.co.ntt.atrs.api.common.error;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.WebRequest;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

/**
 * REST API用エラー情報生成クラス。
 */
@Component
public class ApiErrorCreator {

    /**
     * メッセージソース。
     */
    @Inject
    MessageSource messageSource;

    /**
     * APIエラー情報生成。
     * @param request リクエスト
     * @param errorCode エラーコード
     * @param defaultErrorMessage デフォルトエラーメッセージ
     * @param args
     * @return エラー情報
     */
    public ApiError createApiError(WebRequest request, String errorCode,
            String defaultErrorMessage, Object... args) {
        String localizedMessage = messageSource.getMessage(errorCode, args,
                defaultErrorMessage, request.getLocale());

        return new ApiError(errorCode, localizedMessage);
    }

    /**
     * 入力チェック用エラー情報生成。
     * @param request リクエスト
     * @param errorCode エラーコード
     * @param bindingResult 処理結果
     * @param defaultErrorMessage デフォルトエラーメッセージ
     * @return エラー情報
     */
    public ApiError createBindingResultApiError(WebRequest request,
            String errorCode, BindingResult bindingResult,
            String defaultErrorMessage) {
        ApiError apiError = createApiError(request, errorCode,
                defaultErrorMessage);

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            apiError.addDetail(createApiError(request, fieldError, fieldError
                    .getField()));
        }

        for (ObjectError objectError : bindingResult.getGlobalErrors()) {
            apiError.addDetail(createApiError(request, objectError, objectError
                    .getObjectName()));
        }

        return apiError;
    }

    /**
     * エラー情報生成共通処理。
     * @param request リクエスト
     * @param messageResolvable メッセージリゾルバ
     * @param target エラー対象
     * @return エラー情報
     */
    private ApiError createApiError(WebRequest request,
            DefaultMessageSourceResolvable messageResolvable, String target) {
        String localizedMessage = messageSource.getMessage(messageResolvable,
                request.getLocale());

        return new ApiError(messageResolvable.getCode(), localizedMessage, target);
    }

    /**
     * ResultMessage用エラー情報生成。
     * @param request リクエスト
     * @param rootErrorCode エラーコード
     * @param resultMessages 処理結果メッセージ
     * @param defaultErrorMessage デフォルトエラーメッセージ
     * @return エラー情報
     */
    public ApiError createResultMessageApiError(WebRequest request,
            String rootErrorCode, ResultMessages resultMessages,
            String defaultErrorMessage) {
        ApiError apiError;
        if (resultMessages.getList().size() == 1) {
            ResultMessage resultMessage = resultMessages.iterator().next();
            String errorCode = resultMessage.getCode();
            String errorText = resultMessage.getText();

            if (errorCode == null && errorText == null) {
                errorCode = rootErrorCode;
            }

            apiError = createApiError(request, errorCode, errorText,
                    resultMessage.getArgs());
        } else {
            apiError = createApiError(request, rootErrorCode,
                    defaultErrorMessage);
            for (ResultMessage resultMessage : resultMessages.getList()) {
                apiError.addDetail(createApiError(request, resultMessage
                        .getCode(), resultMessage.getText(), resultMessage
                        .getArgs()));
            }
        }

        return apiError;
    }
}
