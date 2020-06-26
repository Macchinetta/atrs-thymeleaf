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
package jp.co.ntt.atrs.app.d1;

import java.nio.file.Path;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import jp.co.ntt.atrs.domain.common.logging.LogMessages;
import jp.co.ntt.atrs.domain.service.a1.AtrsUserDetails;
import jp.co.ntt.atrs.domain.service.d1.ReservationHistoryReportService;

/**
 * 履歴レポート作成コントローラ。
 * @author NTT 電電次郎
 */
@Controller
@TransactionTokenCheck("HistoryReport/create")
public class ReservationHistoryReportController {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory
            .getLogger(ReservationHistoryReportController.class);

    /**
     * レポートパス名の属性名
     */
    private static final String REPORT_FILE_PATH = "reportFilePath";

    /**
     * 履歴レポート作成サービス
     */
    @Inject
    ReservationHistoryReportService historyReportCreationService;

    /**
     * 履歴レポート作成条件入力画面を表示する。
     * @return View論理名
     */
    @RequestMapping(value = { "HistoryReport/create" }, method = RequestMethod.GET, params = "form")
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    public String createReportForm() {
        return "D1/createReportForm";
    }

    /**
     * 履歴レポート作成要求送付する。
     * @param userDetails ログインユーザ情報
     * @return リダイレクト先のURL
     */
    @RequestMapping(value = { "HistoryReport/create" }, method = RequestMethod.POST)
    @TransactionTokenCheck
    public String create(@AuthenticationPrincipal AtrsUserDetails userDetails) {
        historyReportCreationService.sendRequest(userDetails.getMember()
                .getMembershipNumber());
        return "redirect:/HistoryReport/create?accepted";
    }

    /**
     * 履歴レポート作成要求受付完了画面を表示する。
     * @return View論理名
     */
    @RequestMapping(value = { "HistoryReport/create" }, method = RequestMethod.GET, params = "accepted")
    public String accepted() {
        return "D1/createReportAccepted";
    }

    /**
     * ダウンロードする履歴レポート選択画面を表示する。
     * @param userDetails ログインユーザ情報
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(value = { "HistoryReport/download" }, method = RequestMethod.GET, params = "reportList")
    public String dispReportList(
            @AuthenticationPrincipal AtrsUserDetails userDetails, Model model) {

        String membershipNumber = userDetails.getMember().getMembershipNumber();
        // 指定の会員番号に紐付くレポート一覧を取得する
        List<String> reportNameList = historyReportCreationService
                .getExistingReportNameList(membershipNumber);
        model.addAttribute("reportNameList", reportNameList);

        return "D1/downloadReportList";
    }

    /**
     * 指定のレポートをダウンロードさせる。
     * <p>
     * ディレクトリトラバーサル攻撃を受けた場合に備え、要求されたレポート名が既存するものかを確認し、既存した場合にダウンロードViewに遷移する。
     * </p>
     * @param userDetails ログインユーザ情報
     * @param reportName レポート名
     * @param model 出力情報を保持するオブジェクト
     * @return ダウンロードView
     */
    @RequestMapping(value = { "HistoryReport/download" }, method = RequestMethod.GET)
    public String download(
            @AuthenticationPrincipal AtrsUserDetails userDetails,
            @RequestParam("reportName") String reportName, Model model) {

        // DL対象のレポートパス名の取得
        String membershipNumber = userDetails.getMember().getMembershipNumber();
        Path reportFilePath = historyReportCreationService.getReportFilePath(
                membershipNumber, reportName);
        if (reportFilePath == null) {
            if (logger.isWarnEnabled()) {
                logger.warn(LogMessages.W_AR_D1_L0001.getMessage(reportName));
            }
            return "D1/downloadReportFailed";
        }

        model.addAttribute(REPORT_FILE_PATH, reportFilePath);

        return "historyReportDownload";
    }

}
