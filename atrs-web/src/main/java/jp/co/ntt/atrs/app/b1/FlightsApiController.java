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

import jp.co.ntt.atrs.app.a0.ErrorResultDto;
import jp.co.ntt.atrs.domain.service.b1.FlightNotFoundException;
import jp.co.ntt.atrs.domain.service.b1.FlightVacantInfoDto;
import jp.co.ntt.atrs.domain.service.b1.TicketSearchCriteriaDto;
import jp.co.ntt.atrs.domain.service.b1.TicketSearchService;

import com.github.dozermapper.core.Mapper;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * 空席状況取得WebAPIコントローラ。
 * @author NTT 電電次郎
 */
@Controller
@RequestMapping("api")
public class FlightsApiController {

    /**
     * メッセージソース。
     */
    @Inject
    MessageSource messageSource;

    /**
     * Beanマッパー。
     */
    @Inject
    Mapper beanMapper;

    /**
     * 空席照会サービス。
     */
    @Inject
    TicketSearchService ticketSearchService;

    /**
     * 空席照会条件フォームのバリデータ。
     */
    @Inject
    FlightSearchCriteriaValidator flightSearchCriteriaValidator;

    /**
     * 空席照会条件フォームのバリデータをバインダに追加する。
     * @param binder バインダ
     */
    @InitBinder("flightSearchCriteriaForm")
    public void initBinderForFlightSearchCriteria(WebDataBinder binder) {
        binder.addValidators(flightSearchCriteriaValidator);
    }

    /**
     * フライトの空席状況を取得する。
     * <ul>
     * <li>空席照会条件に合致するフライト情報から生成した空席状況一覧リストを返却する。</li>
     * <li>空席照会条件が不正な場合、該当するフライトが存在しない場合はエラーメッセージを返却する。</li>
     * </ul>
     * [応答HTTPステータスコード]
     * <ul>
     * <li>正常:200</li>
     * <li>空席照会条件不正:400</li>
     * <li>該当するフライトが存在しない:404</li>
     * <li>システムエラー:500</li>
     * </ul>
     * @param flightSearchCriteriaForm 空席照会条件フォーム
     * @return 空席状況一覧リスト
     */
    @RequestMapping(value = "flights", method = RequestMethod.GET)
    @ResponseBody
    public List<FlightVacantInfoDto> getFlights(
            @Validated FlightSearchCriteriaForm flightSearchCriteriaForm) {

        // 空席照会
        TicketSearchCriteriaDto searchCriteriaDto = beanMapper.map(
                flightSearchCriteriaForm, TicketSearchCriteriaDto.class);
        List<FlightVacantInfoDto> flights = ticketSearchService
                .searchFlight(searchCriteriaDto);

        return flights;
    }

    /**
     * 入力値に不正な値が指定された場合の例外ハンドリングを行う。
     * <p>
     * エラー情報にエラーメッセージを設定して返却する。
     * </p>
     * @param e バインド例外
     * @param locale ロケールオブジェクト
     * @return エラー情報
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResultDto handleBindException(BindException e, Locale locale) {

        ErrorResultDto result = new ErrorResultDto();

        // メッセージ設定
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            result.add(messageSource.getMessage(fieldError, locale));
        }
        for (ObjectError objectError : e.getBindingResult().getGlobalErrors()) {
            result.add(messageSource.getMessage(objectError, locale));
        }

        return result;
    }

    /**
     * 空席照会条件が不正な場合の例外ハンドリングを行う。
     * <p>
     * エラー情報にエラーメッセージを設定して返却する。
     * </p>
     * @param e 業務例外
     * @param locale ロケールオブジェクト
     * @return エラー情報
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResultDto handleBusinessException(BusinessException e,
            Locale locale) {

        ErrorResultDto result = new ErrorResultDto();

        // メッセージ設定
        for (ResultMessage resultMessage : e.getResultMessages().getList()) {
            result.add(messageSource
                    .getMessage(
                            new DefaultMessageSourceResolvable(resultMessage
                                    .getCode()), locale));
        }

        return result;
    }

    /**
     * 空席照会条件に合致するフライト情報が存在しない場合の例外ハンドリングを行う。
     * <p>
     * エラー情報にエラーメッセージを設定して返却する。
     * </p>
     * @param e フライト情報非存在業務例外
     * @param locale ロケールオブジェクト
     * @return エラー情報
     */
    @ExceptionHandler(FlightNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResultDto handleFlightNotFoundException(
            FlightNotFoundException e, Locale locale) {

        ErrorResultDto result = new ErrorResultDto();

        // メッセージ設定
        for (ResultMessage resultMessage : e.getResultMessages().getList()) {
            result.add(messageSource
                    .getMessage(
                            new DefaultMessageSourceResolvable(resultMessage
                                    .getCode()), locale));
        }

        return result;
    }

}
