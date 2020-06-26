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
package jp.co.ntt.atrs.domain.common.security;

import jp.co.ntt.atrs.domain.common.logging.LogMessages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * パスワード不正が原因で認証が失敗した事をハンドリングするための認証イベントリスナクラス。
 * @author NTT 電電太郎
 */
@Component
public class AtrsAuthenticationFailureBadCredentialsListener
                                                            implements
                                                            ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    /**
     * ロガー。
     */
    private static final Logger logger = LoggerFactory
            .getLogger(AtrsAuthenticationFailureBadCredentialsListener.class);

    /**
     * {@inheritDoc}
     * <p>
     * パスワード不一致を通知するログを出力する。
     * </p>
     */
    @Override
    public void onApplicationEvent(
            AuthenticationFailureBadCredentialsEvent event) {
        if (!(event.getException() instanceof BadCredentialsException)) {
            return;
        }
        logger.info(LogMessages.I_AR_A1_L2003.getMessage(event
                .getAuthentication().getName()), event.getException());
    }

}
