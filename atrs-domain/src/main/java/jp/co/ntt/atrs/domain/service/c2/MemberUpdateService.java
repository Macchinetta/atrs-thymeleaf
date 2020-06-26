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

import jp.co.ntt.atrs.domain.model.Member;

/**
 * 会員情報変更を行うServiceインタフェース。
 * @author NTT 電電花子
 */
public interface MemberUpdateService {

    /**
     * 会員番号に該当する会員情報を取得する。
     * @param membershipNumber 会員番号
     * @return 会員番号に該当するユーザの会員情報
     */
    Member findMember(String membershipNumber);

    /**
     * 会員情報を更新する。
     * @param member 会員情報
     */
    void updateMember(Member member);

    /**
     * 引数に渡されたパスワードがDBに登録されているパスワードと同一かチェックする。
     * @param password チェックするパスワード
     * @param membershipNumber パスワードを確認する会員の会員番号
     */
    void checkMemberPassword(String password, String membershipNumber);

    /**
     * 会員番号に該当するカード会員情報(ログイン時に必要な情報のみ)を取得する。
     * @param membershipNumber 会員番号
     * @return カード会員情報(ログイン時に必要な情報のみ)
     */
    Member findMemberForLogin(String membershipNumber);

}
