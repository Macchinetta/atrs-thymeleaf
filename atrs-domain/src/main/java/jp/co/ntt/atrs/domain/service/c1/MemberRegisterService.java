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
package jp.co.ntt.atrs.domain.service.c1;

import jp.co.ntt.atrs.domain.model.Member;

/**
 * 会員情報登録を行うServiceインタフェース。
 * @author NTT 電電花子
 */
public interface MemberRegisterService {

    /**
     * 会員情報を登録する。
     * <p>
     * 登録時に発出された会員番号を格納した会員情報インスタンスが返される。
     * </p>
     * @param member 会員情報
     * @return Member 会員番号が格納された会員情報
     */
    Member register(Member member);

}
