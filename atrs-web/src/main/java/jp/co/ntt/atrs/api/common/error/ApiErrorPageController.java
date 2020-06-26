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

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

/**
 * REST API用エラー応答コントローラ。
 * <ul>
 * <li>サーブレットコンテナに通知されたエラーのエラー応答を行う。</li>
 * <ul>
 * @author NTT 電電太郎
 */
@RequestMapping("error")
@RestController
public class ApiErrorPageController {

    /**
     * APIエラー情報生成。
     */
    @Inject
    ApiErrorCreator apiErrorCreator;

    /**
     * HTTPステータス-エラーコードマップ。
     */
    private final Map<HttpStatus, String> errorCodeMap = new HashMap<HttpStatus, String>();

    /**
     * コンストラクタ。
     */
    public ApiErrorPageController() {
        errorCodeMap.put(HttpStatus.NOT_FOUND, "e.ar.fw.9999");
    }

    /**
     * エラー応答ハンドラ。
     * <ul>
     * <li>レスポンスコードによってエラーページのハンドリングを行う.</li>
     * </ul>
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ApiError> handleErrorPage(WebRequest request) {
        HttpStatus httpStatus = HttpStatus.valueOf((Integer) request
                .getAttribute(RequestDispatcher.ERROR_STATUS_CODE,
                        RequestAttributes.SCOPE_REQUEST));
        String errorCode = errorCodeMap.get(httpStatus);
        ApiError apiError = apiErrorCreator.createApiError(request, errorCode,
                httpStatus.getReasonPhrase());

        return ResponseEntity.status(httpStatus).body(apiError);
    }
}
