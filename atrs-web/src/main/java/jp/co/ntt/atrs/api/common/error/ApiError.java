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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * REST API用エラー情報クラス。
 * @author NTT 電電太郎
 */
public class ApiError implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 2204988829496702658L;

    /**
     * エラーコード。
     */
    private final String code;

    /**
     * エラーメッセージ。
     */
    private final String message;

    /**
     * エラー対象。
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String target;

    /**
     * エラー詳細情報リスト。
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ApiError> details = new ArrayList<>();

    /**
     * コンストラクタ。
     * @param code エラーコード
     * @param message エラーメッセージ
     */
    public ApiError(String code, String message) {
        this(code, message, null);
    }

    /**
     * コンストラクタ。
     * @param code エラーコード
     * @param message エラーメッセージ
     * @param target エラー対象
     */
    public ApiError(String code, String message, String target) {
        this.code = code;
        this.message = message;
        this.target = target;
    }

    /**
     * エラーコードを取得する。
     * @return エラーコード
     */
    public String getCode() {
        return code;
    }

    /**
     * エラーメッセージを設定する。
     * @return エラーメッセージ
     */
    public String getMessage() {
        return message;
    }

    /**
     * エラー対象を取得する。
     * @return エラー対象
     */
    public String getTarget() {
        return target;
    }

    /**
     * エラー詳細情報リストを取得する。
     * @return エラー詳細情報リスト
     */
    public List<ApiError> getDetails() {
        return details;
    }

    /**
     * エラー詳細情報を設定する。
     * @param detail エラー詳細情報
     */
    public void addDetail(ApiError detail) {
        details.add(detail);
    }
}
