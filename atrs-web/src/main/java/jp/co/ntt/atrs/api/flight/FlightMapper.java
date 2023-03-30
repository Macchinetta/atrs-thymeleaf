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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import jp.co.ntt.atrs.domain.service.b1.FlightVacantInfoDto;
import jp.co.ntt.atrs.domain.service.b1.TicketSearchCriteriaDto;

@Mapper
public interface FlightMapper {

    @Mapping(target = "departureAirportCd", source = "depAirportCd")
    @Mapping(target = "arrivalAirportCd", source = "arrAirportCd")
    TicketSearchCriteriaDto map(FlightSearchQuery flightSearchQuery);

    FlightResource map(FlightVacantInfoDto flightVacantInfoDto);
}
