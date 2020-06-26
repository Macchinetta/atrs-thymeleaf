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

import java.util.LinkedHashMap;
import java.util.Map;

import jp.co.ntt.atrs.api.common.util.CastUtil;
import jp.co.ntt.atrs.domain.service.b1.FareTypeVacantInfoDto;

import com.github.dozermapper.core.DozerConverter;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.MapperAware;

/**
 * 空席情報をリソースにマッピングするためのカスタムコンバータ
 * @author NTT 電電太郎
 */
public class FareTypeVacantInfoDtoToFareTypeResourceConverter
                                                             extends
                                                             DozerConverter<LinkedHashMap<String, FareTypeVacantInfoDto>, LinkedHashMap<String, FareTypeResource>>
                                                                                                                                                                  implements
                                                                                                                                                                  MapperAware {

    /**
     * Beanマッパー。
     */
    private Mapper beanMapper;

    /**
     * コンバート対象の2つのクラスを設定するコンストラクタ
     */
    public FareTypeVacantInfoDtoToFareTypeResourceConverter() {
        super(CastUtil.autoCast(LinkedHashMap.class), CastUtil
                .autoCast(LinkedHashMap.class));
    }

    /**
     * LinkedHashMap<String, FareTypeVacantInfoDto>からLinkedHashMap<String, FareTypeResource>に変換する。
     * @param fareTypeMap 運賃種別に対応する空席照会結果を格納するマップ
     * @param fareTypeResourceMap 運賃種別に対応する空席照会結果をマッピングするリソース
     * @return 運賃種別に対応する空席照会結果の一覧のリソース
     */
    @Override
    public LinkedHashMap<String, FareTypeResource> convertTo(
            LinkedHashMap<String, FareTypeVacantInfoDto> fareTypeMap,
            LinkedHashMap<String, FareTypeResource> fareTypeResourceMap) {
        for (Map.Entry<String, FareTypeVacantInfoDto> entry : fareTypeMap
                .entrySet()) {
            FareTypeResource p = beanMapper.map(entry.getValue(),
                    FareTypeResource.class);
            fareTypeResourceMap.put(entry.getKey(), p);
        }

        return fareTypeResourceMap;
    }

    /**
     * LinkedHashMap<String, FareTypeResource>からLinkedHashMap<String, FareTypeVacantInfoDto>に変換する。
     * @param fareTypeResourceMap 運賃種別に対応する空席照会結果をマッピングするリソース
     * @param fareTypeMap 運賃種別に対応する空席照会結果を格納するマップ
     * @return 運賃種別に対応する空席照会結果の一覧マップ
     */
    @Override
    public LinkedHashMap<String, FareTypeVacantInfoDto> convertFrom(
            LinkedHashMap<String, FareTypeResource> fareTypeResourceMap,
            LinkedHashMap<String, FareTypeVacantInfoDto> fareTypeMap) {
        for (Map.Entry<String, FareTypeResource> entry : fareTypeResourceMap
                .entrySet()) {
            FareTypeVacantInfoDto p = beanMapper.map(entry.getValue(),
                    FareTypeVacantInfoDto.class);
            fareTypeMap.put(entry.getKey(), p);
        }
        return fareTypeMap;
    }

    /**
     * Beanマッパーを設定する。
     */
    @Override
    public void setMapper(Mapper mapper) {
        this.beanMapper = mapper;

    }

}
