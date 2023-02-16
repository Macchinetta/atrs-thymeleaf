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
package jp.co.ntt.atrs.app.c0;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;

import jp.co.ntt.atrs.app.c1.MemberRegisterForm;
import jp.co.ntt.atrs.app.c2.MemberUpdateForm;
import jp.co.ntt.atrs.domain.model.Member;

@Mapper
public interface C0Mapper {

    @Mapping(target = "tel1", ignore = true)
    @Mapping(target = "tel2", ignore = true)
    @Mapping(target = "tel3", ignore = true)
    @Mapping(target = "zipCode1", ignore = true)
    @Mapping(target = "zipCode2", ignore = true)
    @Mapping(target = "reEnterMail", ignore = true)
    @Mapping(target = "creditMonth", ignore = true)
    @Mapping(target = "creditYear", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "reEnterPassword", ignore = true)
    @Mapping(target = "currentPassword", ignore = true)
    @Mapping(target = "dateOfBirth", source = "birthday")
    @Mapping(target = "creditTypeCd", source = "creditType.creditTypeCd")
    MemberUpdateForm map(Member member);

    @Mapping(target = "tel", ignore = true)
    @Mapping(target = "zipCode", ignore = true)
    @Mapping(target = "membershipNumber", ignore = true)
    @Mapping(target = "creditTerm", ignore = true)
    @Mapping(target = "birthday", source = "dateOfBirth")
    @Mapping(target = "creditType.creditTypeCd", source = "creditTypeCd")
    @Mapping(target = "memberLogin.password", source = "password")
    Member map(MemberUpdateForm memberForm);

    @Mapping(target = "tel", ignore = true)
    @Mapping(target = "zipCode", ignore = true)
    @Mapping(target = "membershipNumber", ignore = true)
    @Mapping(target = "creditTerm", ignore = true)
    @Mapping(target = "birthday", source = "dateOfBirth")
    @Mapping(target = "creditType.creditTypeCd", source = "creditTypeCd")
    @Mapping(target = "memberLogin.password", source = "password")
    Member map(MemberRegisterForm memberForm);

    @InheritConfiguration(name = "memberFormToMember")
    @SubclassMapping(source = MemberUpdateForm.class, target = Member.class)
    @SubclassMapping(source = MemberRegisterForm.class, target = Member.class)
    @Mapping(target = "membershipNumber", ignore = true)
    @Mapping(target = "kanjiFamilyName", ignore = true)
    @Mapping(target = "kanjiGivenName", ignore = true)
    @Mapping(target = "kanaFamilyName", ignore = true)
    @Mapping(target = "kanaGivenName", ignore = true)
    @Mapping(target = "birthday", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "tel", ignore = true)
    @Mapping(target = "zipCode", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "creditNo", ignore = true)
    @Mapping(target = "creditTerm", ignore = true)
    @Mapping(target = "creditType", ignore = true)
    @Mapping(target = "memberLogin", ignore = true)
    Member map(IMemberForm memberForm);
}
