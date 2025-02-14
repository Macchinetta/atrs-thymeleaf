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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import jp.co.ntt.atrs.domain.model.Passenger;
import jp.co.ntt.atrs.domain.model.Reservation;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveDto;

@Mapper
public interface TicketMapper {

    @Mapping(target = "reserveNo", source = "reserveNo")
    @Mapping(target = "paymentDate", source = "paymentDate")
    @Mapping(target = "totalFare", ignore = true)
    @Mapping(target = "repFamilyName", ignore = true)
    @Mapping(target = "repGivenName", ignore = true)
    @Mapping(target = "repAge", ignore = true)
    @Mapping(target = "repGender", ignore = true)
    @Mapping(target = "repMembershipNumber", ignore = true)
    @Mapping(target = "repTel1", ignore = true)
    @Mapping(target = "repTel2", ignore = true)
    @Mapping(target = "repTel3", ignore = true)
    @Mapping(target = "repMail", ignore = true)
    @Mapping(target = "flightType", ignore = true)
    @Mapping(target = "selectFlightResourceList", ignore = true)
    @Mapping(target = "passengerResourceList", ignore = true)
    TicketReserveResource map(@MappingTarget TicketReserveResource ticketReserveResource,
            TicketReserveDto ticketReserveDto);

    @Mapping(target = "member.membershipNumber", source = "membershipNumber")
    @Mapping(target = "passengerNo", ignore = true)
    @Mapping(target = "reserveFlightNo", ignore = true)
    Passenger map(PassengerResource passengerResource);

    @Mapping(target = "repMember.membershipNumber", source = "repMembershipNumber")
    @Mapping(target = "reserveDate", ignore = true)
    @Mapping(target = "repTel", ignore = true)
    @Mapping(target = "reserveFlightList", ignore = true)
    Reservation map(TicketReserveResource ticketReserveResource);
}
