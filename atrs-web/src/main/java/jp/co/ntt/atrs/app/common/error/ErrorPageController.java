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
package jp.co.ntt.atrs.app.common.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * エラー画面コントローラ。
 * @author NTT 電電太郎
 */
@Controller
@RequestMapping("common/error")
public class ErrorPageController {

    /**
     * badRequestエラー画面を表示する。
     * @return View論理名
     */
    @RequestMapping("badRequest-error")
    public String badRequestError() {
        return "common/error/badRequest-error";
    }

    /**
     * accessForbiddenエラー画面を表示する。
     * @return View論理名
     */
    @RequestMapping("accessForbidden-error")
    public String accessForbiddenError() {
        return "common/error/accessForbidden-error";
    }

    /**
     * notFoundエラー画面を表示する。
     * @return View論理名
     */
    @RequestMapping("notFound-error")
    public String notFoundError() {
        return "common/error/notFound-error";
    }

    /**
     * systemエラー画面を表示する。
     * @return View論理名
     */
    @RequestMapping("system-error")
    public String systemError() {
        return "common/error/system-error";
    }

    /**
     * csrfエラー画面を表示する。
     * @return View論理名
     */
    @RequestMapping("csrf-error")
    public String csrfError() {
        return "common/error/csrf-error";
    }

    /**
     * tokenエラー画面を表示する。
     * @return View論理名
     */
    @RequestMapping("token-error")
    public String tokenError() {
        return "common/error/token-error";
    }
}
