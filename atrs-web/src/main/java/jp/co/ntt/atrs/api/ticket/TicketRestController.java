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
package jp.co.ntt.atrs.api.ticket;

import java.util.List;

import javax.inject.Inject;

import jp.co.ntt.atrs.domain.model.Flight;

import com.github.dozermapper.core.Mapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * チケット予約RestAPIコントローラ
 * @author NTT 電電太郎
 */
@RestController
@RequestMapping("ticket")
public class TicketRestController {

    /**
     * チケット予約API Helper。
     */
    @Inject
    TicketHelper ticketHelper;

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
     * チケット予約リソースのバリデータ。
     */
    @Inject
    TicketReserveValidator ticketReserveValidator;

    /**
     * チケット予約リソースのバリデータをバインダに追加する。
     * @param binder バインダ
     */
    @InitBinder("ticketReserveForm")
    public void initBinderForTicketReserve(WebDataBinder binder) {
        binder.addValidators(ticketReserveValidator);
    }

    /**
     * チケットを予約する。
     * @param ticketReserveResource チケット予約リソース
     * @return チケット予約結果
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TicketReserveResource postTicket(
            @RequestBody @Validated TicketReserveResource ticketReserveResource) {
        // 選択フライト情報の業務ロジックチェック
        List<Flight> flightList = ticketHelper
                .toFlightList(ticketReserveResource
                        .getSelectFlightResourceList());
        ticketHelper.validateFlightList(flightList);

        // チケット予約
        TicketReserveResource createdReserveResource = ticketHelper.reserve(
                ticketReserveResource, flightList);

        return createdReserveResource;
    }

}
