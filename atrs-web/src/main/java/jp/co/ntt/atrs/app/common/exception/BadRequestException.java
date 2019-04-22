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
package jp.co.ntt.atrs.app.common.exception;

import jp.co.ntt.atrs.domain.common.logging.LogMessages;

import org.springframework.validation.Errors;
import org.terasoluna.gfw.common.exception.ExceptionCodeProvider;

/**
 * 不正リクエスト例外。
 * @author NTT 電電太郎
 */
public class BadRequestException extends RuntimeException implements
                                                         ExceptionCodeProvider {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -6131511074347102729L;

    /**
     * コンストラクタ。
     * @param causeMessage エラーメッセージ
     */
    public BadRequestException(String causeMessage) {
        super(LogMessages.E_AR_A0_L9001.getMessage(causeMessage));
    }

    /**
     * コンストラクタ。
     * @param errors エラーオブジェクト
     */
    public BadRequestException(Errors errors) {
        super(LogMessages.E_AR_A0_L9001.getMessage(errors.toString()));
    }

    /**
     * コンストラクタ。
     * @param e 例外オブジェクト
     */
    public BadRequestException(Exception e) {
        super(LogMessages.E_AR_A0_L9001.getMessage(e.getMessage()), e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return LogMessages.E_AR_A0_L9001.getCode();
    }

}
