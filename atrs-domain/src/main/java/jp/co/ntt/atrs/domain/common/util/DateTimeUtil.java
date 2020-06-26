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

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 日時に関するユーティリティクラス。
 * @author NTT 電電太郎
 */
public class DateTimeUtil {

    /**
     * 日付(文字列)のパースに使用するフォーマッタ。
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat
            .forPattern("yyyy/MM/dd");

    /**
     * 時間(文字列)のパースに使用するフォーマッタ。
     */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat
            .forPattern("HHmm");

    /**
     * コンストラクタ。
     */
    private DateTimeUtil() {
        // 処理なし
    }

    /**
     * DateTimeへ変換する。
     * @param date 日付オブジェクト
     * @param timeString 時刻文字列(HHmm)
     * @return 引数で指定された日付および時刻を保持するDateTimeオブジェクト
     */
    public static DateTime toDateTime(Date date, String timeString) {
        return new LocalDate(date).toDateTime(DateTimeUtil
                .toLocalTime(timeString));
    }

    /**
     * DateTimeへ変換する。
     * @param dateString 日付文字列(yyyy/MM/dd)
     * @return 引数で指定された日付を保持するDateTimeオブジェクト
     */
    public static DateTime toDateTime(String dateString) {
        return DATE_FORMATTER.parseDateTime(dateString);
    }

    /**
     * LocalTimeへ変換する。
     * @param timeString 時刻文字列(HHmm)
     * @return 引数で指定された時刻を保持するLocalTimeオブジェクト
     */
    public static LocalTime toLocalTime(String timeString) {
        return TIME_FORMATTER.parseLocalTime(timeString);
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
        return DATE_FORMATTER.print(new DateTime(date));
    }

    /**
     * 整形日付文字列(yyyy/MM/dd)へ変換する。
     * @param dateTime DateTimeオブジェクト
     * @return 日付文字列(yyyy/MM/dd)
     */
    public static String toFormatDateString(DateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return DATE_FORMATTER.print(dateTime);
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
