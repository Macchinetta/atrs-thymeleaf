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
package jp.co.ntt.atrs.domain.service.b0;

/**
 * フライト情報不正例外。
 * @author NTT 電電次郎
 */
public class InvalidFlightException extends RuntimeException {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -8290953295757837929L;

    /**
     * コンストラクタ。
     * @param message エラーメッセージ
     */
    public InvalidFlightException(String message) {
        super(message);
    }

    /**
     * コンストラクタ。
     * @param e 例外オブジェクト
     */
    public InvalidFlightException(Exception e) {
        super(e);
    }

}
