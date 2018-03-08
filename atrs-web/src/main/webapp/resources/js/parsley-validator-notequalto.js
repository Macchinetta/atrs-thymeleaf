/*!
 * Parsleyのカスタムバリデータ notequalto を提供するモジュール。
 *
 * Copyright(c) 2014-2018 NTT Corporation.
 */

'use strict';

if ('undefined' !== typeof window.ParsleyValidator) {
  window.ParsleyValidator
    .addValidator('notequalto',
                  /**
                   * 対象の要素と入力値が等しくないことを検証する。
                   * @param value 入力値
                   * @param requirement オプション文字列。ターゲット要素のセレクタを指定する。
                   */
                  function (value, requirement) {
                    return value !== $(requirement).val();
                  }, 1);
}
