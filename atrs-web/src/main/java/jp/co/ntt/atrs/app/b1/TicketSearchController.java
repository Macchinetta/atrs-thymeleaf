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
package jp.co.ntt.atrs.app.b1;

import jp.co.ntt.atrs.app.b0.ReservationFlightForm;
import jp.co.ntt.atrs.app.b0.ReservationFlightValidator;
import jp.co.ntt.atrs.app.b0.TicketHelper;
import jp.co.ntt.atrs.app.common.exception.BadRequestException;
import jp.co.ntt.atrs.domain.model.Flight;

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

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 空席照会コントローラ。
 * @author NTT 電電次郎
 */
@Controller
@RequestMapping("ticket/search")
public class TicketSearchController {

    /**
     * チケット予約共通Helper。
     */
    @Inject
    TicketHelper ticketHelper;

    /**
     * 空席照会Helper。
     */
    @Inject
    TicketSearchHelper ticketSearchHelper;

    /**
     * 空席照会フォームのバリデータ。
     */
    @Inject
    TicketSearchValidator ticketSearchValidator;

    /**
     * 予約フライト選択フォームのバリデータ。
     */
    @Inject
    ReservationFlightValidator reservationFlightValidator;

    /**
     * 空席照会フォームのバリデータをバインダに追加する。
     * @param binder バインダ
     */
    @InitBinder("ticketSearchForm")
    public void initBinderForTicketSearch(WebDataBinder binder) {
        binder.addValidators(ticketSearchValidator);
    }

    /**
     * 予約フライト選択フォームのバリデータをバインダに追加する。
     * @param binder バインダ
     */
    @InitBinder("reservationFlightForm")
    public void initBinderForReservationFlight(WebDataBinder binder) {
        binder.addValidators(reservationFlightValidator);
    }

    /**
     * TOP画面を表示する。
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "topForm")
    public String searchTopForm(Model model) {

        // 空席照会フォームにデフォルト値を設定
        model.addAttribute(ticketSearchHelper.createDefaultTicketSearchForm());

        // 空席照会画面(TOP画面)表示情報設定
        model.addAttribute(ticketSearchHelper.createFlightSearchOutputDto());

        // ※本来はユースケース外の画面へ直接遷移することはしないが、
        // TOP画面は空席照会条件入力部品を含むため、例外的に直接遷移する
        return "A0/top";
    }

    /**
     * 空席照会画面を表示する。
     * <ul>
     * <li>空席照会条件はデフォルト値。</li>
     * <li>初期空席照会はしない。</li>
     * </ul>
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "form")
    public String searchForm(Model model) {

        // 空席照会フォームにデフォルト値を設定
        model.addAttribute(ticketSearchHelper.createDefaultTicketSearchForm());

        // 空席照会画面(TOP画面)表示情報設定
        model.addAttribute(ticketSearchHelper.createFlightSearchOutputDto());

        // 国内線リンクから遷移時は初期空席照会を実施しない
        model.addAttribute("isInitialSearchUnnecessary", true);

        return "B1/flightSearch";
    }

    /**
     * 空席照会画面を表示する。
     * <ul>
     * <li>空席照会条件はTOP画面入力値。</li>
     * <li>入力値エラーがある場合を除き初期空席照会を実施。</li>
     * </ul>
     * @param ticketSearchForm 空席照会フォーム
     * @param result チェック結果
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "flightForm")
    public String searchFlightForm(
            @Validated TicketSearchForm ticketSearchForm, BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            // 検証エラーがある場合初期空席照会を実施しない
            model.addAttribute("isInitialSearchUnnecessary", true);
        }

        // 空席照会画面(TOP画面)表示情報設定
        model.addAttribute(ticketSearchHelper.createFlightSearchOutputDto());

        return "B1/flightSearch";
    }

    /**
     * 空席照会画面を再表示する。
     * @param ticketSearchForm 空席照会フォーム
     * @param reservationFlightForm 予約フライト選択フォーム
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "redo")
    public String searchRedo(TicketSearchForm ticketSearchForm,
            ReservationFlightForm reservationFlightForm, Model model) {

        // 空席照会画面(TOP画面)表示情報設定
        model.addAttribute(ticketSearchHelper.createFlightSearchOutputDto());

        return "B1/flightSearch";
    }

    /**
     * 選択フライト情報をチェックし、お客様情報入力画面を表示する。
     * <ul>
     * <li>チェックエラーがある場合、空席照会画面を再表示する。</li>
     * <li>チェックOKの場合、お客様情報入力画面をリダイレクトで表示する。</li>
     * </ul>
     * @param ticketSearchForm 空席照会フォーム
     * @param reservationFlightForm 予約フライト選択フォーム
     * @param result チェック結果
     * @param model 出力情報を保持するオブジェクト
     * @param redirectAttributes フラッシュスコープ格納用オブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.POST)
    public String searchSelect(TicketSearchForm ticketSearchForm,
            @Validated ReservationFlightForm reservationFlightForm,
            BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasFieldErrors("selectFlightFormList*")
                || result.hasFieldErrors("flightType")) {

            // 非表示項目(選択フライト情報、フライト種別)に検証エラーがある場合は
            // 改ざんとみなす
            throw new BadRequestException("selectFlightFormList is invalid.");
        }

        if (result.hasErrors()) {
            // その他の検証エラーがある場合は画面再表示
            return searchRedo(ticketSearchForm, reservationFlightForm, model);
        }

        // 選択フライト情報の業務ロジックチェック
        List<Flight> flightList = ticketHelper
                .toFlightList(reservationFlightForm.getSelectFlightFormList());
        try {
            ticketSearchHelper.validateFlightList(flightList);
        } catch (BusinessException e) {

            // 業務エラーがある場合、メッセージ設定後に画面再表示
            model.addAttribute(e.getResultMessages());

            return searchRedo(ticketSearchForm, reservationFlightForm, model);
        }

        // 選択フライト情報をリダイレクトパラメータに設定
        Map<String, String> params = ticketHelper
                .createParameterMapForSelectFlight(reservationFlightForm);
        for (String key : params.keySet()) {
            redirectAttributes.addAttribute(key, params.get(key));
        }

        // お客様情報入力画面にリダイレクト
        return "redirect:/ticket/reserve?form";
    }

}
