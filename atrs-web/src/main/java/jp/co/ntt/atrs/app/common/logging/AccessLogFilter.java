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
package jp.co.ntt.atrs.app.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * アクセスログを出力するフィルタ。
 * @author NTT 電電太郎
 */
public class AccessLogFilter extends OncePerRequestFilter {

    /**
     * ロガー。
     */
    private static final Logger logger = LoggerFactory
            .getLogger(AccessLogFilter.class);

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String logMessage = getLogMessage(request);
        logger.info("ACCESS START {}", logMessage);
        filterChain.doFilter(request, response);
        logger.info("ACCESS FINISH {}", logMessage);
    }

    /**
     * ログメッセージを取得する。
     * @param request リクエスト
     * @return ログメッセージ
     */
    private String getLogMessage(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder();

        sb.append("[RequestURL:").append(request.getRequestURL().toString());
        String queryString = request.getQueryString();
        if (queryString != null) {
            sb.append("?").append(queryString);
        }
        sb.append("], ");

        HttpSession session = request.getSession(false);
        if (session != null) {
            sb.append("[SessionID:").append(session.getId()).append("], ");
        }

        sb.append("[RemoteAddress:").append(request.getRemoteAddr()).append(
                "], ");
        sb.append("[RemoteHost:").append(request.getRemoteHost()).append("] ");

        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String uri = request.getRequestURI();
        if (uri.startsWith(request.getContextPath() + "/resources/")) {
            return true;
        }

        return false;
    }
}
