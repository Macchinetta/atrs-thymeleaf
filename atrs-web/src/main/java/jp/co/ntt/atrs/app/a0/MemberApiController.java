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

import jp.co.ntt.atrs.domain.service.a0.MembershipSharedService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * 会員WebAPIコントローラ。
 * @author NTT 電電太郎
 */
@Controller
@RequestMapping("api/member")
public class MemberApiController {

    /**
     * 会員共通サービス。
     */
    @Inject
    MembershipSharedService membershipSharedService;

    /**
     * 存在する会員番号かを判定する。
     * @param membershipNumber 会員番号
     * @return OKステータス:存在する、NOT_FOUNDステータス:存在しない
     */
    @RequestMapping(value = "available", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getAvailable(
            @RequestParam("membershipNumber") String membershipNumber) {

        // 値がない場合は、メッセージ表示不要のため OK を返却
        if (!StringUtils.hasText(membershipNumber)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return membershipSharedService.isMember(membershipNumber) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
