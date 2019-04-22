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

import jp.co.ntt.atrs.domain.model.FlightMaster;
import jp.co.ntt.atrs.domain.repository.flight.FlightRepository;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * フライト基本情報を提供するクラス。
 * @author NTT 電電太郎
 */
@Component
public class FlightMasterProvider {

    /**
     * フライト基本情報リポジトリ。
     */
    @Inject
    FlightRepository flightRepository;

    /**
     * 便名とフライト基本情報の関係を保持するキャッシュ。
     */
    private final Map<String, FlightMaster> flightMasterMap = new HashMap<>();

    /**
     * フライト基本情報をロードし、キャッシュする。
     */
    @PostConstruct
    public void load() {
        List<FlightMaster> flightMasterList = flightRepository
                .findAllFlightMaster();
        for (FlightMaster flightMaster : flightMasterList) {
            flightMasterMap.put(flightMaster.getFlightName(), flightMaster);
        }
    }

    /**
     * 指定便名に該当するフライト基本情報を取得する。
     * @param flightName 便名
     * @return フライト基本情報。該当するフライト基本情報がない場合はnull。
     */
    public FlightMaster getFlightMaster(String flightName) {
        Assert.hasText(flightName);
        return this.flightMasterMap.get(flightName);
    }
}
