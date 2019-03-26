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
package jp.co.ntt.atrs.app.common.binding;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 全コントローラ共通のバインダ初期化処理を行うクラス。
 * 
 * @author NTT 電電太郎
 */
@ControllerAdvice
public class GlobalBindingInitializer {

    /**
     * バインダーの初期化を行う。
     * <p>
     * Stringフィールドが空欄の場合に、空文字ではなくnullをバインドするよう設定する。
     * </p>
     * 
     * @param binder バインダ
     */
    @InitBinder
    public void initBinderControllerAdvice(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
