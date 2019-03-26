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
package jp.co.ntt.atrs.domain.service.d1;

import java.nio.file.Path;
import java.util.List;

/**
 * 履歴レポート作成サービス
 * @author NTT 電電次郎
 */
public interface ReservationHistoryReportService {

    /**
     * 指定の顧客の予約履歴のレポート作成を電文送付して要求する。
     * @param membershipNumber 対象の会員番号
     */
    void sendRequest(String membershipNumber);

    /**
     * レポートを作成する。
     * @param criteria レポート作成条件保持オブジェクト
     */
    void createReport(ReservationHistoryReportCriteriaDto criteria);

    /**
     * 当該レポート名一覧を取得する。
     * @param membershipNumber
     * @return レポート名リスト
     */
    List<String> getExistingReportNameList(String membershipNumber);

    /**
     * 会員番号に紐づくファイルのパスを取得する。
     * @param membershipNumber 会員番号
     * @param reportName ファイル名
     * @return ファイルパス
     */
    public Path getReportFilePath(String membershipNumber, String reportName);

}
