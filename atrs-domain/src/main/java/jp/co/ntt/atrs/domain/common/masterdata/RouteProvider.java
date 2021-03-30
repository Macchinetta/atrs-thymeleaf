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
package jp.co.ntt.atrs.domain.common.masterdata;

import jp.co.ntt.atrs.domain.model.Route;
import jp.co.ntt.atrs.domain.repository.route.RouteRepository;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * 区間情報を提供するクラス。
 * @author NTT 電電太郎
 */
@Component
public class RouteProvider {

    /**
     * 区間情報リポジトリ。
     */
    @Inject
    RouteRepository routeRepository;

    /**
     * 区間情報マップ。
     */
    private final Map<String, Route> routeMap = new HashMap<>();

    /**
     * 区間情報をロードし、キャッシュする。
     */
    @PostConstruct
    public void load() {
        List<Route> routeList = routeRepository.findAll();
        for (Route route : routeList) {
            String cacheKey = makeCacheKey(route.getDepartureAirport()
                    .getCode(), route.getArrivalAirport().getCode());
            routeMap.put(cacheKey, route);
        }
    }

    /**
     * 出発空港コード、到着空港コードに該当する区間情報を取得する。
     * @param departureAirportCd 出発空港コード
     * @param arrivalAirportCd 到着空港コード
     * @return 区間情報。該当する区間情報が見つからない場合はnull。
     */
    public Route getRouteByAirportCd(String departureAirportCd,
            String arrivalAirportCd) {

        Assert.hasText(departureAirportCd, "departureAirportCd must have some text.");
        Assert.hasText(arrivalAirportCd, "arrivalAirportCd must have some text.");

        String searchKey = makeCacheKey(departureAirportCd, arrivalAirportCd);
        return routeMap.get(searchKey);
    }

    /**
     * 区間情報マップにキャッシュするためのキー値を生成する。
     * <p>
     * キー値は、"[出発空港コード]-[到着空港コード]"形式の文字列となる。
     * </p>
     * @param departureAirportCd 出発空港コード
     * @param arrivalAirportCd 到着空港コード
     * @return 区間情報マップにキャッシュするためのキー値
     */
    private String makeCacheKey(String departureAirportCd,
            String arrivalAirportCd) {
        return departureAirportCd + "-" + arrivalAirportCd;
    }

}
