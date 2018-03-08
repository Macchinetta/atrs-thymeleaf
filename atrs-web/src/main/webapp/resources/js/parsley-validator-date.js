/*!
 * Parsleyのカスタムバリデータ date を提供するモジュール。
 * YYYY/MM/DD フォーマットであること、および
 * moment.isValid() に則した有効日付であることを検証する。
 *
 * Copyright(c) 2014-2018 NTT Corporation.
 */

'use strict';

if ('undefined' !== typeof window.ParsleyValidator && 'undefined' !== typeof window.moment) {
  window.ParsleyValidator
    .addValidator('date',
                  /**
                   * 対象が有効な日付であることを検証する。
                   * @param value 入力値
                   */
                  function (value) {
                    return moment(value, 'YYYY/MM/DD', true).isValid();
                  }, 1)
    .addMessage('ja', 'date', '有効な日付ではありません。');
}
