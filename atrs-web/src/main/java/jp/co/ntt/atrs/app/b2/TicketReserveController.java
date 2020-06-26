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
package jp.co.ntt.atrs.app.b2;

import jp.co.ntt.atrs.app.b0.ReservationFlightForm;
import jp.co.ntt.atrs.app.b0.SelectFlightDto;
import jp.co.ntt.atrs.app.b0.SelectFlightForm;
import jp.co.ntt.atrs.app.b0.TicketHelper;
import jp.co.ntt.atrs.app.common.exception.BadRequestException;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.service.a1.AtrsUserDetails;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * チケット予約コントローラ。
 * @author NTT 電電三郎
 */
@Controller
@RequestMapping("ticket/reserve")
@TransactionTokenCheck("ticket/reserve")
public class TicketReserveController {

    /**
     * チケット予約共通Helper。
     */
    @Inject
    TicketHelper ticketHelper;

    /**
     * チケット予約Helper。
     */
    @Inject
    TicketReserveHelper ticketReserveHelper;

    /**
     * チケット予約フォームのバリデータ。
     */
    @Inject
    TicketReserveValidator ticketReserveValidator;

    /**
     * チケット予約フォームのバリデータをバインダに追加する。
     * @param binder バインダ
     */
    @InitBinder("ticketReserveForm")
    public void initBinderForTicketReserve(WebDataBinder binder) {
        binder.addValidators(ticketReserveValidator);
    }

    /**
     * お客様情報入力画面を表示する。
     * @param reservationFlightForm 予約フライト選択フォーム
     * @param userDetails ログイン情報を保持するオブジェクト
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "form")
    public String reserveForm(ReservationFlightForm reservationFlightForm,
            @AuthenticationPrincipal AtrsUserDetails userDetails, Model model) {

        // チケット予約フォームにデフォルト値を設定
        TicketReserveForm ticketReserveForm = ticketReserveHelper
                .createTicketReserveForm(userDetails);

        // 前画面で入力された選択フライトをチケット予約フォームに設定
        ticketReserveForm.setSelectFlightFormList(reservationFlightForm
                .getSelectFlightFormList());
        ticketReserveForm.setFlightType(reservationFlightForm.getFlightType());

        // 表示用選択フライト情報を設定
        List<Flight> flightList = ticketHelper
                .toFlightList(reservationFlightForm.getSelectFlightFormList());
        List<SelectFlightDto> selectFlightDtoList = ticketReserveHelper
                .createSelectFlightDtoList(flightList);

        model.addAttribute(ticketReserveForm);
        model.addAttribute("selectFlightDtoList", selectFlightDtoList);

        return "B2/reserveForm";
    }

    /**
     * お客様情報入力画面を再表示する。
     * @param ticketReserveForm チケット予約フォーム
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.POST, params = "redo")
    public String reserveRedo(TicketReserveForm ticketReserveForm, Model model) {

        // 表示用選択フライト情報を設定
        List<Flight> flightList = ticketHelper.toFlightList(ticketReserveForm
                .getSelectFlightFormList());
        List<SelectFlightDto> selectFlightDtoList = ticketReserveHelper
                .createSelectFlightDtoList(flightList);
        model.addAttribute("selectFlightDtoList", selectFlightDtoList);

        return "B2/reserveForm";
    }

    /**
     * 予約代表者情報、搭乗者情報をチェックし、申し込み内容確認画面を表示する。
     * <ul>
     * <li>トランザクショントークンチェックのトランザクションを開始する。</li>
     * <li>チェックエラーがある場合、お客様情報入力画面を再表示する。</li>
     * <li>チェックOKの場合、申し込み内容確認画面を表示する。</li>
     * </ul>
     * @param ticketReserveForm チケット予約フォーム
     * @param result チェック結果
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     * @throws BadRequestException リクエスト不正例外
     */
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    @RequestMapping(method = RequestMethod.POST, params = "confirm")
    public String reserveConfirm(
            @Validated TicketReserveForm ticketReserveForm,
            BindingResult result, Model model) throws BadRequestException {

        if (result.hasFieldErrors("selectFlightFormList*")
                || result.hasFieldErrors("flightType")) {

            // 非表示項目(選択フライト情報、フライト種別)に検証エラーがある場合は
            // 改ざんとみなす
            throw new BadRequestException("selectFlightFormList is invalid.");
        }

        if (result.hasErrors()) {
            // その他の検証エラーがある場合は画面再表示
            return reserveRedo(ticketReserveForm, model);
        }

        // 選択フライト情報の業務ロジックチェック
        List<Flight> flightList = ticketHelper.toFlightList(ticketReserveForm
                .getSelectFlightFormList());
        ticketReserveHelper.validateFlightList(flightList);

        try {
            // 予約情報の業務ロジックチェック後、申し込み内容確認画面表示情報を設定
            ReserveConfirmOutputDto reserveConfirmOutputDto = ticketReserveHelper
                    .reserveConfirm(ticketReserveForm, flightList);
            model.addAttribute(reserveConfirmOutputDto);
        } catch (BusinessException e) {

            // 業務エラーがある場合、メッセージ設定後に画面再表示
            model.addAttribute(e.getResultMessages());

            return reserveRedo(ticketReserveForm, model);
        }

        // 表示用選択フライト情報を設定
        List<SelectFlightDto> selectFlightDtoList = ticketReserveHelper
                .createSelectFlightDtoList(flightList);
        model.addAttribute("selectFlightDtoList", selectFlightDtoList);

        return "B2/reserveConfirm";
    }

    /**
     * チケットを予約する。
     * <ul>
     * <li>トランザクショントークンチェックを行う。</li>
     * <li>業務エラーがある場合、予約失敗画面をリダイレクトで表示する。</li>
     * <li>予約に成功した場合、予約完了画面をリダイレクトで表示する。</li>
     * </ul>
     * @param ticketReserveForm チケット予約フォーム
     * @param result チェック結果
     * @param model 出力情報を保持するオブジェクト
     * @param redirectAttributes フラッシュスコープ格納用オブジェクト
     * @return View論理名
     * @throws BadRequestException リクエスト不正例外
     */
    @TransactionTokenCheck
    @RequestMapping(method = RequestMethod.POST)
    public String reserve(@Validated TicketReserveForm ticketReserveForm,
            BindingResult result, Model model,
            RedirectAttributes redirectAttributes) throws BadRequestException {

        if (result.hasErrors()) {
            // 非表示項目(選択フライト情報、予約代表者情報、搭乗者情報)に
            // 検証エラーがある場合は改ざんとみなす
            throw new BadRequestException(result);
        }

        // 選択フライト情報の業務ロジックチェック
        List<Flight> flightList = ticketHelper.toFlightList(ticketReserveForm
                .getSelectFlightFormList());
        ticketReserveHelper.validateFlightList(flightList);

        try {
            // チケット予約
            ReserveCompleteOutputDto reserveCompleteOutputDto = ticketReserveHelper
                    .reserve(ticketReserveForm, flightList);
            redirectAttributes.addFlashAttribute(reserveCompleteOutputDto);
        } catch (BusinessException e) {

            // 業務エラーがある場合

            // メッセージ設定
            redirectAttributes.addFlashAttribute(e.getResultMessages());

            // 表示用選択フライト情報を設定
            List<SelectFlightDto> selectFlightDtoList = ticketReserveHelper
                    .createSelectFlightDtoList(flightList);
            redirectAttributes.addFlashAttribute("selectFlightDtoList",
                    selectFlightDtoList);

            // 予約失敗画面にリダイレクト
            return "redirect:/ticket/reserve?fail";
        }

        // 予約完了画面にリダイレクト
        return "redirect:/ticket/reserve?complete";
    }

    /**
     * 予約完了画面を表示する。
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "complete")
    public String reserveComplete() {

        return "B2/reserveComplete";
    }

    /**
     * 予約失敗画面を表示する。
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "fail")
    public String reserveFail() {

        return "B2/reserveFail";
    }

    /**
     * 空席照会画面を表示する。
     * <ul>
     * <li>空席照会条件、選択フライト情報をパラメータ(クエリ文字列)に設定し、 空席照会画面をリダイレクトで表示する。</li>
     * </ul>
     * @param reservationFlightForm 予約フライト選択フォーム
     * @param model 出力情報を保持するオブジェクト
     * @param redirectAttributes フラッシュスコープ格納用オブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.POST, params = "backToSearch")
    public String reserveBackToSearch(
            ReservationFlightForm reservationFlightForm, Model model,
            RedirectAttributes redirectAttributes) {

        // 空席照会条件、選択フライト情報をリダイレクトパラメータに設定
        Map<String, String> params = new LinkedHashMap<>();
        List<SelectFlightForm> flightFormList = reservationFlightForm
                .getSelectFlightFormList();
        params.putAll(ticketReserveHelper
                .createParameterMapForFlightSearch(flightFormList));
        params.putAll(ticketHelper
                .createParameterMapForSelectFlight(reservationFlightForm));

        for (String key : params.keySet()) {
            redirectAttributes.addAttribute(key, params.get(key));
        }

        // 空席照会画面にリダイレクト
        return "redirect:/ticket/search?redo";
    }

}
