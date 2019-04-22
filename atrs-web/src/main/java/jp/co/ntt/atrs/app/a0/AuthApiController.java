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
package jp.co.ntt.atrs.app.a0;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.ntt.atrs.domain.service.a1.AtrsUserDetails;

/**
 * 認証WebAPIコントローラ。
 * @author NTT 電電太郎
 */
@Controller
@RequestMapping("api/auth")
public class AuthApiController {

    /**
     * 認証共通Helper。
     */
    @Inject
    AuthenticationHelper authenticationHelper;

    /**
     * ログイン状態を取得する。
     * @param principal ログイン情報を保持するオブジェクト
     * @return OKステータス:ログイン中、NOT_FOUNDステータス:未ログイン
     */
    @RequestMapping(value = "status", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getStatus(
            @AuthenticationPrincipal AtrsUserDetails userDetails) {

        // ログイン状態を返却
        return authenticationHelper.isAuthenticatedPrincipal(userDetails) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
