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
package jp.co.ntt.atrs.domain.service.a1;

import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import jp.co.ntt.atrs.domain.common.logging.LogMessages;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.repository.member.MemberRepository;

/**
 * ログインユーザ情報サービス。
 * @author NTT 電電太郎
 */
@Transactional
public class AtrsUserDetailsService implements UserDetailsService {

    /**
     * ロガー。
     */
    private static final Logger logger = LoggerFactory
            .getLogger(AtrsUserDetailsService.class);

    /**
     * メッセージプロパティ設定。
     */
    @Inject
    MessageSource messageSource;

    /**
     * カード会員情報リポジトリ。
     */
    @Inject
    MemberRepository memberRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Assert.hasText(username, "username must have some text.");

        // 会員情報(ログイン時に必要な情報のみ)を取得
        Member member = memberRepository.findOneForLogin(username);

        if (member == null) {
            if (logger.isInfoEnabled()) {
                logger.info(LogMessages.I_AR_A1_L2001.getMessage(username));
            }
            String errorMessage = messageSource.getMessage(
                    AuthLoginErrorCode.E_AR_A1_2001.code(), null, Locale
                            .getDefault());
            // 該当する会員情報が存在しない場合、例外をスロー
            throw new UsernameNotFoundException(errorMessage);
        }

        return new AtrsUserDetails(member);
    }

}
