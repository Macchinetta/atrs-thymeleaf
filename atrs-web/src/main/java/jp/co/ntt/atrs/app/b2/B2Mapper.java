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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import jp.co.ntt.atrs.app.b0.SelectFlightDto;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.Passenger;
import jp.co.ntt.atrs.domain.model.Reservation;

@Mapper
public interface B2Mapper {

    @Mapping(target = "flightName", source = "flightMaster.flightName")
    @Mapping(target = "departureTime", source = "flightMaster.departureTime")
    @Mapping(target = "arrivalTime", source = "flightMaster.arrivalTime")
    @Mapping(target = "depAirportCd", source = "flightMaster.route.departureAirport.code")
    @Mapping(target = "arrAirportCd", source = "flightMaster.route.arrivalAirport.code")
    @Mapping(target = "boardingClassCd", source = "boardingClass.boardingClassCd")
    @Mapping(target = "fareTypeCd", source = "fareType.fareTypeCd")
    @Mapping(target = "lineType", ignore = true)
    @Mapping(target = "fare", ignore = true)
    SelectFlightDto map(Flight flight);

    @Mapping(target = "repMember.membershipNumber", source = "repMembershipNumber")
    @Mapping(target = "reserveNo", ignore = true)
    @Mapping(target = "reserveDate", ignore = true)
    @Mapping(target = "totalFare", ignore = true)
    @Mapping(target = "repTel", ignore = true)
    @Mapping(target = "reserveFlightList", ignore = true)
    void map(TicketReserveForm ticketReserveForm, @MappingTarget Reservation reservation);

    @Mapping(target = "repFamilyName", source = "kanaFamilyName")
    @Mapping(target = "repGivenName", source = "kanaGivenName")
    @Mapping(target = "repGender", source = "gender")
    @Mapping(target = "repMail", source = "mail")
    @Mapping(target = "repMembershipNumber", source = "membershipNumber")
    @Mapping(target = "repAge", ignore = true)
    @Mapping(target = "repTel1", ignore = true)
    @Mapping(target = "repTel2", ignore = true)
    @Mapping(target = "repTel3", ignore = true)
    @Mapping(target = "flightType", ignore = true)
    @Mapping(target = "passengerFormList", ignore = true)
    @Mapping(target = "selectFlightFormList", ignore = true)
    void map(Member member, @MappingTarget TicketReserveForm ticketReserveForm);

    @Mapping(target = "familyName", source = "repFamilyName")
    @Mapping(target = "givenName", source = "repGivenName")
    @Mapping(target = "age", source = "repAge")
    @Mapping(target = "gender", source = "repGender")
    @Mapping(target = "membershipNumber", source = "repMembershipNumber")
    PassengerForm map(TicketReserveForm ticketReserveForm);

    @Mapping(target = "member.membershipNumber", source = "membershipNumber")
    @Mapping(target = "passengerNo", ignore = true)
    @Mapping(target = "reserveFlightNo", ignore = true)
    Passenger map(PassengerForm passengerForm);

}
