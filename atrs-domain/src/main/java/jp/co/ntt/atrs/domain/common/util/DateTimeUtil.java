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
package jp.co.ntt.atrs.domain.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日時に関するユーティリティクラス。
 * @author NTT 電電太郎
 */
public class DateTimeUtil {

    /**
     * 日付(文字列)のパースに使用するフォーマッタ。
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy/MM/dd");

    /**
     * 時間(文字列)のパースに使用するフォーマッタ。
     */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter
            .ofPattern("HHmm");

    /**
     * コンストラクタ。
     */
    private DateTimeUtil() {
        // 処理なし
    }

    /**
     * Dateへ変換する。
     * @param localDateTime LocalDateTimeオブジェクト
     * @return Dateオブジェクト
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.ofHours(9)));
    }

    /**
     * Dateへ変換する。
     * @param localDate LocalDateオブジェクト
     * @return Dateオブジェクト
     */
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * LocalDateTimeへ変換する。
     * @param date 日付オブジェクト
     * @return 引数で指定された時刻を保持するLocalDateTimeオブジェクト
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId
                .systemDefault());
    }

    /**
     * LocalDateTimeへ変換する。
     * @param date 日付オブジェクト
     * @param timeString 時刻文字列(HHmm)
     * @return 引数で指定された日付および時刻を保持するLocalDateTimeオブジェクト
     */
    public static LocalDateTime toLocalDateTime(Date date, String timeString) {
        LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId
                .systemDefault());
        LocalTime localTime = DateTimeUtil.toLocalTime(timeString);
        return localDate.atTime(localTime);
    }

    /**
     * LocalDateへ変換する。
     * @param dateString 日付文字列(yyyy/MM/dd)
     * @return 引数で指定された日付を保持するLocalDateオブジェクト
     */
    public static LocalDate toLocalDate(String dateString) {
        return LocalDate.from(DATE_FORMATTER.parse(dateString));
    }

    /**
     * LocalDateへ変換する。
     * @param date 日付オブジェクト
     * @return 引数で指定された時刻を保持するLocalDateオブジェクト
     */
    public static LocalDate toLocalDate(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalTimeへ変換する。
     * @param date 日付オブジェクト
     * @return 引数で指定された時刻を保持するLocalTimeオブジェクト
     */
    public static LocalTime toLocalTime(Date date) {
        return LocalTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalTimeへ変換する。
     * @param timeString 時刻文字列(HHmm)
     * @return 引数で指定された時刻を保持するLocalTimeオブジェクト
     */
    public static LocalTime toLocalTime(String timeString) {
        return LocalTime.from(TIME_FORMATTER.parse(timeString));
    }

    /**
     * 整形日付文字列(yyyy/MM/dd)へ変換する。
     * @param date 日付オブジェクト
     * @return 日付文字列(yyyy/MM/dd)
     */
    public static String toFormatDateString(Date date) {
        if (date == null) {
            return "";
        }
        return DATE_FORMATTER.format(toLocalDate(date));
    }

    /**
     * 整形日付文字列(yyyy/MM/dd)へ変換する。
     * @param localDate LocalDateオブジェクト
     * @return 日付文字列(yyyy/MM/dd)
     */
    public static String toFormatDateString(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        return DATE_FORMATTER.format(localDate);
    }

    /**
     * 整形時刻文字列(HH:mm)へ変換する。
     * @param timeString 時刻文字列(HHmm)
     * @return 時刻文字列(HH:mm)
     */
    public static String toFormatTimeString(String timeString) {
        String result = timeString;
        if (timeString != null && 2 < timeString.length()) {
            result = String.format("%s:%s", timeString.substring(0, 2),
                    timeString.substring(2));
        }
        return result;
    }

}
