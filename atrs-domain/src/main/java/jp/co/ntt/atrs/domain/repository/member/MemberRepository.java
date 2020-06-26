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
package jp.co.ntt.atrs.domain.repository.member;

import jp.co.ntt.atrs.domain.model.Member;

/**
 * カード会員情報テーブルにアクセスするリポジトリインターフェース。
 * @author NTT 電電太郎
 */
public interface MemberRepository {

    /**
     * 会員番号に該当するカード会員情報(ログイン時に必要な情報のみ)を取得する。
     * @param membershipNumber 会員番号
     * @return カード会員情報(ログイン時に必要な情報のみ)
     */
    Member findOneForLogin(String membershipNumber);

    /**
     * ログイン時に会員ログイン情報を更新する。
     * @param member カード会員情報
     * @return 更新件数
     */
    int updateToLoginStatus(Member member);

    /**
     * ログアウト時に会員ログイン情報を更新する。
     * @param member カード会員情報
     * @return 更新件数
     */
    int updateToLogoutStatus(Member member);

    /**
     * 会員番号に該当するカード会員情報を取得する。
     * @param membershipNumber 会員番号
     * @return カード会員情報
     */
    Member findOne(String membershipNumber);

    /**
     * 会員情報を登録する。
     * <p>
     * 登録時に発出された会員番号が引数の会員情報に格納される。
     * </p>
     * @param member 登録する会員情報
     * @return 登録件数
     */
    int insert(Member member);

    /**
     * 会員ログイン情報を登録する。
     * @param member 会員ログイン情報を含む会員情報
     * @return 登録件数
     */
    int insertMemberLogin(Member member);

    /**
     * 会員情報を更新する。
     * @param member 会員情報
     * @return 更新件数
     */
    int update(Member member);

    /**
     * 会員ログイン情報を更新する。
     * @param member 会員ログイン情報を含む会員情報
     * @return 更新件数
     */
    int updateMemberLogin(Member member);
}
