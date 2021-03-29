/*!
 * Parsleyのカスタムバリデータ notequalto を提供するモジュール。
 *
 * Copyright(c) 2015 NTT Corporation.
 */

'use strict';

if ('undefined' !== typeof window.Parsley) {
  window.Parsley
    .addValidator('notequalto', {
                  requirementType: 'string',
                  /**
                   * 対象の要素と入力値が等しくないことを検証する。
                   * @param value 入力値
                   * @param requirement オプション文字列。ターゲット要素のセレクタを指定する。
                   */
                  validateString: function(value, requirement) {
                    return value !== $(requirement).val();
                  }

    });
}
