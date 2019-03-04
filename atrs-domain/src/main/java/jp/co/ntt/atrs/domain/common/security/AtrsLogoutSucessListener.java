/*
 * Copyright(c) 2014 NTT Corporation.
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

import jp.co.ntt.atrs.domain.service.a1.AtrsUserDetails;
import jp.co.ntt.atrs.domain.service.a2.AuthLogoutService;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * ログアウト成功イベントハンドリングクラス。
 * 
 * @author NTT 電電太郎
 */
@Component
public class AtrsLogoutSucessListener implements
    ApplicationListener<AtrsLogoutSuccessEvent> {

    /**
     * 会員ログアウトサービス。
     */
    @Inject
    AuthLogoutService authLogoutService;

    /**
     * {@inheritDoc}
     * 
     * <p>ログアウト成功後に会員ログインステータスを更新する。</p>
     */
    @Override
    public void onApplicationEvent(AtrsLogoutSuccessEvent event) {
        Authentication authentication = event.getAuthentication();

        AtrsUserDetails userDetails = (AtrsUserDetails) authentication.getPrincipal();
        authLogoutService.logout(userDetails.getMember());

    }
}
