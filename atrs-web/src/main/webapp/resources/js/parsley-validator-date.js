/*!
 * Parsleyのカスタムバリデータ date を提供するモジュール。
 * YYYY/MM/DD フォーマットであること、および
 * moment.isValid() に則した有効日付であることを検証する。
 *
 * Copyright(c) 2015 NTT Corporation.
 */

'use strict';

if ('undefined' !== typeof window.Parsley && 'undefined' !== typeof window.moment) {
  window.Parsley
    .addValidator('date', {
                  /**
                   * 対象が有効な日付であることを検証する。
                   * @param value 入力値
                   */
                   validateString: function(value) {
                    return moment(value, 'YYYY/MM/DD', true).isValid();
                  },
                  messages: {
                    ja: '有効な日付ではありません。'
                  }
  });
}
