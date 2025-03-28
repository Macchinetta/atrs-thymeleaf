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

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.terasoluna.gfw.common.exception.SystemException;
import org.terasoluna.gfw.common.time.ClockFactory;

import jakarta.inject.Inject;
import jp.co.ntt.atrs.domain.common.logging.LogMessages;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.MemberLogin;
import jp.co.ntt.atrs.domain.repository.member.MemberRepository;

/**
 * 会員ログインサービス実装クラス。
 * @author NTT 電電太郎
 */
@Service
@Transactional
public class AuthLoginServiceImpl implements AuthLoginService {

    /**
     * ロガー。
     */
    private static final Logger logger = LoggerFactory.getLogger(AuthLoginServiceImpl.class);

    /**
     * 日付生成インターフェース。
     */
    @Inject
    ClockFactory dateFactory;

    /**
     * カード会員情報リポジトリ。
     */
    @Inject
    MemberRepository memberRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(Member member) {

        // パラメータチェック
        Assert.notNull(member, "member must not null.");

        // ログインフラグ、ログイン日時を更新
        MemberLogin memberLogin = member.getMemberLogin();
        memberLogin.setLoginDateTime(Date.from(dateFactory.tick().instant()));
        memberLogin.setLoginFlg(true);
        int updateCount = memberRepository.updateToLoginStatus(member);
        if (updateCount != 1) {
            throw new SystemException(LogMessages.E_AR_A0_L9002.getCode(),
                    LogMessages.E_AR_A0_L9002.getMessage(updateCount, 1));
        }

        logger.info(LogMessages.I_AR_A1_L0001.getMessage(member.getMembershipNumber()));
    }

}
