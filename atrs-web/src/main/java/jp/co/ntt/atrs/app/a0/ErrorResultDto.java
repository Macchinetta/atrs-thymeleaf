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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * エラー情報DTOクラス。
 * 
 * @author NTT 電電太郎
 */
public class ErrorResultDto implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 5765200265354207181L;

    /**
     * メッセージリスト。
     */
    private List<String> messages = new ArrayList<>();

    /**
     * メッセージリストを取得する。
     * 
     * @return 処理結果
     */
    public List<String> getMessages() {
        return messages;
    }

    /**
     * メッセージを追加する。
     * 
     * @param message メッセージ
     */
    public void add(String message) {
        this.messages.add(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
