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
package jp.co.ntt.atrs.app.b0;

/**
 * 路線種別の列挙型。
 * @author NTT 電電次郎
 */
public enum LineType {

    /**
     * 往路。
     */
    OUTWARD("outward", "往路"),

    /**
     * 復路。
     */
    HOMEWARD("homeward", "復路");

    /**
     * 路線種別コード。
     */
    private final String code;

    /**
     * 路線種別名称。
     */
    private final String name;

    /**
     * コンストラクタ。
     * @param code 路線種別コード
     * @param name 路線種別名称
     */
    private LineType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 路線種別コードを取得する。
     * @return 路線種別コード
     */
    public String getCode() {
        return code;
    }

    /**
     * 路線種別名称を取得する。
     * @return 路線種別名称
     */
    public String getName() {
        return name;
    }

}
