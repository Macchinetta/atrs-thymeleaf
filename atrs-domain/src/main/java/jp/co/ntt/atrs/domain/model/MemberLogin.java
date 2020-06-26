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
package jp.co.ntt.atrs.domain.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * カード会員ログイン情報。
 * @author NTT 電電太郎
 */
public class MemberLogin implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 4454136798154120517L;

    /**
     * パスワード。
     */
    private String password;

    /**
     * 前回パスワード。
     */
    private String lastPassword;

    /**
     * ログイン時刻。
     */
    private Date loginDateTime;

    /**
     * ログインフラグ。
     */
    private Boolean loginFlg;

    /**
     * パスワード を取得する。
     * @return パスワード
     */
    public String getPassword() {
        return password;
    }

    /**
     * パスワード を設定する。
     * @param password パスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 前回パスワード を取得する。
     * @return 前回パスワード
     */
    public String getLastPassword() {
        return lastPassword;
    }

    /**
     * 前回パスワード を設定する。
     * @param lastPassword 前回パスワード
     */
    public void setLastPassword(String lastPassword) {
        this.lastPassword = lastPassword;
    }

    /**
     * ログイン時刻 を取得する。
     * @return ログイン時刻
     */
    public Date getLoginDateTime() {
        return loginDateTime;
    }

    /**
     * ログイン時刻 を設定する。
     * @param loginDateTime ログイン時刻
     */
    public void setLoginDateTime(Date loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    /**
     * ログインフラグ を取得する。
     * @return ログインフラグ
     */
    public Boolean getLoginFlg() {
        return loginFlg;
    }

    /**
     * ログインフラグ を設定する。
     * @param loginFlg ログインフラグ
     */
    public void setLoginFlg(Boolean loginFlg) {
        this.loginFlg = loginFlg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
