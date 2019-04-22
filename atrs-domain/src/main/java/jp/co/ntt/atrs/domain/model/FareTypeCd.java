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

/**
 * 運賃種別コードの列挙型。
 * @author NTT 電電太郎
 */
public enum FareTypeCd {

    /**
     * 片道運賃の運賃種別コード。
     */
    OW,

    /**
     * 往復運賃の運賃種別コード。
     */
    RT,

    /**
     * 予約割1の運賃種別コード。
     */
    RD1,

    /**
     * 予約割7の運賃種別コード。
     */
    RD7,

    /**
     * 早期割の運賃種別コード。
     */
    ED,

    /**
     * レディース割の運賃種別コード。
     */
    LD,

    /**
     * グループ割の運賃種別コード。
     */
    GD,

    /**
     * 特別片道運賃の運賃種別コード。
     */
    SOW,

    /**
     * 特別往復運賃の運賃種別コード。
     */
    SRT,

    /**
     * 特別予約割の運賃種別コード。
     */
    SRD;

    /**
     * 運賃種別コードを取得する。
     * @return 運賃種別コード
     */
    public String getCode() {
        return this.name();
    }

}
