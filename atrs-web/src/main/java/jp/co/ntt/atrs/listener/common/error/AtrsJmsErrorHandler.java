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
package jp.co.ntt.atrs.listener.common.error;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.adapter.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;
import org.terasoluna.gfw.common.exception.ExceptionLogger;

/**
 * JMSリスナーで発生した例外を処理する例外ハンドラ
 * @author NTT 電電次郎
 */
@Component
public class AtrsJmsErrorHandler implements ErrorHandler {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory
            .getLogger(AtrsJmsErrorHandler.class);

    /**
     * ExceptionLogger
     */
    @Inject
    ExceptionLogger exceptionLogger;

    /**
     * JMS処理に失敗したことをログ出力して終了する。
     * @param t 発生例外
     */
    @Override
    public void handleError(Throwable t) {

        // Listenerでの例外はListenerExecutionFailedExceptionにwrapされている為、その原因例外を処理ログ出力する
        if (t instanceof ListenerExecutionFailedException) {
            Throwable wrappedException = t.getCause();
            if (wrappedException instanceof Exception) {
                exceptionLogger.error((Exception) wrappedException);
            } else {
                logger.error("UNEXPECTED ERROR", wrappedException);
            }
        } else if (t instanceof Exception) {
            exceptionLogger.error((Exception) t);
        } else {
            logger.error("UNEXPECTEC ERROR", t);
        }
    }

}
