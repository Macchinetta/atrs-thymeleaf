/*!
 * Parsleyのカスタムバリデータ dateafterto を提供するモジュール。
 *
 * Copyright(c) 2015 NTT Corporation.
 */

'use strict';

if ('undefined' !== typeof window.Parsley) {
  window.Parsley
    .addValidator('dateafterto', {
                  requirementType: 'string',
                  /**
                   * 対象よりも後の日付であることを検証する。
                   * @param value 入力値
                   * @param requirement オプション文字列。ターゲット要素のセレクタを指定する。
                   */
                  validateString: function(value, requirement) {
                    var targetValue = $(requirement).val();

                    // 日付が入力されていない場合は検証しない
                    if (!value || !targetValue)
                      return true;
                    return  new Date(value).getTime() >= new Date(targetValue).getTime();
                  }
  });
}
