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
package jp.co.ntt.atrs.app.d1;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.web.download.AbstractFileDownloadView;

/**
 * ダウンロードカスタムビュー
 * @author NTT 電電次郎
 */
@Component
public class HistoryReportDownload extends AbstractFileDownloadView {

    /**
     * レポートパス名の属性名
     */
    private static final String REPORT_FILE_PATH = "reportFilePath";

    /*
     * (non-Javadoc)
     * @see org.terasoluna.gfw.web.download.AbstractFileDownloadView#getInputStream( java.util.Map,
     * javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected InputStream getInputStream(Map<String, Object> model,
            HttpServletRequest request) throws IOException {

        Path path = (Path) model.get(REPORT_FILE_PATH);
        Resource resource = new FileSystemResource(path.toAbsolutePath()
                .toString());
        return resource.getInputStream();
    }

    /*
     * (non-Javadoc)
     * @see org.terasoluna.gfw.web.download.AbstractFileDownloadView# addResponseHeader (java.util.Map,
     * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void addResponseHeader(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) {

        Path path = (Path) model.get(REPORT_FILE_PATH);
        response.setHeader("Content-Disposition", "attachment;filename="
                + path.getFileName());

        response.setContentType("text/csv");
    }

}
