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

import jp.co.ntt.atrs.domain.model.Member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * ログインユーザ情報クラス。
 * @author NTT 電電太郎
 */
public class AtrsUserDetails implements UserDetails {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 8105510259984180618L;

    /**
     * デフォルト権限設定。
     */
    private static final List<? extends GrantedAuthority> DEFAULT_AUTHORITIES = Collections
            .singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"));

    /**
     * カード会員情報。
     */
    private Member member;

    /**
     * コンストラクタ。
     * @param member カード会員情報
     */
    public AtrsUserDetails(Member member) {
        this.member = member;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return DEFAULT_AUTHORITIES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPassword() {
        return member.getMemberLogin().getPassword();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return member.getMembershipNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * カード会員情報を取得する。
     * @return カード会員情報
     */
    public Member getMember() {
        return member;
    }

    /**
     * カード会員情報を設定する。
     * @param member カード会員情報
     */
    public void setMember(Member member) {
        this.member = member;
    }

}
