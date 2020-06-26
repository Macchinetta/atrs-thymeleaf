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
package jp.co.ntt.atrs.api.flight;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.ntt.atrs.domain.service.b1.FlightVacantInfoDto;
import jp.co.ntt.atrs.domain.service.b1.TicketSearchCriteriaDto;
import jp.co.ntt.atrs.domain.service.b1.TicketSearchService;

import com.github.dozermapper.core.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 空席状況取得RESTAPIコントローラ。
 * @author NTT 電電太郎
 */
@RestController
@RequestMapping("/flight")
public class FlightRestController {

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
     * 空席照会条件クエリのバリデータ。
     */
    @Inject
    FlightValidator flightValidator;

    /**
     * 空席照会条件クエリのバリデータをバインダに追加する。
     * @param binder バインダ
     */
    @InitBinder("flightSearchQuery")
    public void initBinderForFlightSearchCriteria(WebDataBinder binder) {
        binder.addValidators(flightValidator);
    }

    /**
     * フライトの空席状況を取得する。
     * @param flightSearchQuery 空席照会条件クエリ
     * @return 空席状況一覧リスト
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<FlightResource> getFlights(
            @Validated FlightSearchQuery flightSearchQuery) {

        TicketSearchCriteriaDto searchCriteriaDto = beanMapper.map(
                flightSearchQuery, TicketSearchCriteriaDto.class);
        // 空席照会
        List<FlightVacantInfoDto> flights = ticketSearchService
                .searchFlight(searchCriteriaDto);
        List<FlightResource> flightResourceList = new ArrayList<>();
        for (FlightVacantInfoDto flight : flights) {
            flightResourceList
                    .add(beanMapper.map(flight, FlightResource.class));
        }
        return flightResourceList;
    }

}
