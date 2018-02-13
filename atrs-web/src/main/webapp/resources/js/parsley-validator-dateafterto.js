/*!
 * Parsleyのカスタムバリデータ dateafterto を提供するモジュール。
 *
 * Copyright(c) 2014-2017 NTT Corporation.
 */

'use strict';

if ('undefined' !== typeof window.ParsleyValidator) {
  window.ParsleyValidator
    .addValidator('dateafterto',
                  /**
                   * 対象よりも後の日付であることを検証する。
                   * @param value 入力値
                   * @param requirement オプション文字列。ターゲット要素のセレクタを指定する。
                   */
                  function (value, requirement) {
                    var targetValue = $(requirement).val();

                    // 日付が入力されていない場合は検証しない
                    if (!value || !targetValue)
                      return true;
                    return  new Date(value).getTime() >= new Date(targetValue).getTime();
                  }, 1);
}
