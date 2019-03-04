/*
 * Copyright(c) 2014 NTT Corporation.
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
package jp.co.ntt.atrs.domain.service.b1;

import jp.co.ntt.atrs.domain.common.exception.AtrsBusinessException;

/**
 * フライト情報非存在業務例外クラス。
 * 
 * @author NTT 電電次郎
 */
public class FlightNotFoundException extends AtrsBusinessException {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -7145777012040116550L;

    /**
     * コンストラクタ。
     */
    public FlightNotFoundException() {
        super(TicketSearchErrorCode.E_AR_B1_2003);
    }

}
