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
package jp.co.ntt.atrs.listener.d1;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.exception.SystemException;

import jp.co.ntt.atrs.domain.common.logging.LogMessages;
import jp.co.ntt.atrs.domain.service.d1.ReservationHistoryReportCriteriaDto;
import jp.co.ntt.atrs.domain.service.d1.ReservationHistoryReportService;

/**
 * レポート作成要求JMSメッセージリスナ
 * @author NTT 電電次郎
 */
@Component
public class ReservationHistoryReportListener {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory
            .getLogger(ReservationHistoryReportListener.class);

    /**
     * お客様番号文字数
     */
    private static final int CUSTOMER_NO_LENGTH = 10;

    /**
     * レポート作成するサービス
     */
    @Inject
    ReservationHistoryReportService createReportService;

    /**
     * 受信したJMSメッセージに格納されたレポート作成条件をレポート作成サービスに渡し、処理を委譲する。
     * @param reqMsg レポート作成要求JMSメッセージ
     */
    @JmsListener(destination = "jms/queue/ReservationHistoryReportRequestQueue")
    public void receive(Message reqMsg) {
        ReservationHistoryReportCriteriaDto criteria = getCreateReportCriteria(reqMsg);
        if (criteria != null) {
            createReportService.createReport(criteria);
        }
    }

    /**
     * 受信したJMSメッセージからレポート作成条件を抽出する。
     * @param reqMsg 受信JMSメッセージ
     * @return レポート作成条件格納オブジェクト
     */
    private ReservationHistoryReportCriteriaDto getCreateReportCriteria(
            Message reqMsg) {
        if (!(reqMsg instanceof TextMessage)) {
            if (logger.isWarnEnabled()) {
                logger.warn(LogMessages.W_AR_D1_L0002.getMessage(reqMsg
                        .getClass().getName(), TextMessage.class.getName()));
            }
            return null;
        }

        String msgBody = null;
        try {
            msgBody = ((TextMessage) reqMsg).getText();
        } catch (JMSException e) {
            throw new SystemException(LogMessages.E_AR_D1_L0004.getCode(), LogMessages.E_AR_D1_L0004
                    .getMessage(), e);
        }

        return convertToDto(msgBody);
    }

    /**
     * 受信JMSメッセージのボディをDTOに変換する。
     * @param msgBody 受信JMSメッセージボディ
     * @return DTO
     */
    private ReservationHistoryReportCriteriaDto convertToDto(String msgBody) {
        String membershipNumber = msgBody;
        if (membershipNumber == null
                || membershipNumber.length() != CUSTOMER_NO_LENGTH) {
            if (logger.isWarnEnabled()) {
                logger.warn(LogMessages.W_AR_D1_L0003
                        .getMessage(membershipNumber));
                return null;
            }
        }
        ReservationHistoryReportCriteriaDto criteria = new ReservationHistoryReportCriteriaDto();
        criteria.setMembershipNumber(membershipNumber);
        return criteria;
    }

}
