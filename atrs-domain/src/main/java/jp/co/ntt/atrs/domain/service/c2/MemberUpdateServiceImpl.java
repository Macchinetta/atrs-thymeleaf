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
package jp.co.ntt.atrs.domain.service.c2;

import jp.co.ntt.atrs.domain.common.exception.AtrsBusinessException;
import jp.co.ntt.atrs.domain.common.logging.LogMessages;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.MemberLogin;
import jp.co.ntt.atrs.domain.repository.member.MemberRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.terasoluna.gfw.common.exception.SystemException;

import javax.inject.Inject;

/**
 * 会員情報変更を行うService実装クラス。
 * @author NTT 電電花子
 */
@Service
@Transactional
public class MemberUpdateServiceImpl implements MemberUpdateService {

    /**
     * 会員情報リポジトリ。
     */
    @Inject
    MemberRepository memberRepository;

    /**
     * パスワードをハッシュ化するためのエンコーダ。
     */
    @Inject
    PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Member findMember(String membershipNumber) {

        Assert.hasText(membershipNumber, "membershipNumber must have some text.");

        return memberRepository.findOne(membershipNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateMember(Member member) {

        Assert.notNull(member, "member must not null.");
        MemberLogin memberLogin = member.getMemberLogin();
        Assert.notNull(memberLogin, "memberLogin must not null.");

        // 会員情報更新
        int updateMemberCount = memberRepository.update(member);
        if (updateMemberCount != 1) {
            throw new SystemException(LogMessages.E_AR_A0_L9002.getCode(), LogMessages.E_AR_A0_L9002
                    .getMessage(updateMemberCount, 1));
        }

        // パスワードの変更がある場合のみ会員ログイン情報を更新
        if (StringUtils.hasLength(memberLogin.getPassword())) {

            // パスワードのハッシュ化
            memberLogin.setPassword(passwordEncoder.encode(member
                    .getMemberLogin().getPassword()));

            // 会員ログイン情報更新
            int updateMemberLoginCount = memberRepository
                    .updateMemberLogin(member);
            if (updateMemberLoginCount != 1) {
                throw new SystemException(LogMessages.E_AR_A0_L9002.getCode(), LogMessages.E_AR_A0_L9002
                        .getMessage(updateMemberLoginCount, 1));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkMemberPassword(String password, String membershipNumber) {

        // パスワードの変更がある場合のみパスワードを比較
        if (StringUtils.hasLength(password)) {

            // 登録パスワードを取得
            Member member = memberRepository.findOne(membershipNumber);
            String currentPassword = member.getMemberLogin().getPassword();

            // パスワード不一致の場合、業務例外をスロー
            if (!passwordEncoder.matches(password, currentPassword)) {
                throw new AtrsBusinessException(MemberUpdateErrorCode.E_AR_C2_2001);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Member findMemberForLogin(String membershipNumber) {

        Assert.hasText(membershipNumber, "membershipNumber must have some text.");

        return memberRepository.findOneForLogin(membershipNumber);
    }

}
