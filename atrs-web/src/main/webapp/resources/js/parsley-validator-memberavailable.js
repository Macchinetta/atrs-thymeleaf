/*!
 * Parsleyのカスタムバリデータ memberavailable を提供するモジュール。
 *
 * Copyright(c) 2015 NTT Corporation.
 */

'use strict';

if ('undefined' !== typeof window.Parsley) {
  window.Parsley
    .addValidator('memberavailable', {
                  /**
                   * 会員番号が有効かどうかを検証する。
                   * 会員情報が見つからない場合は失敗、それ以外はサーバ内部エラーなどであっても成功とする。
                   * @param {String} value 入力値
                   * @return {Boolean} 検証結果
                   */
                  validateString: function(value) {
                    var contextPath = $('meta[name="contextPath"]').attr('content');
                    var status = $.ajax({
                      url: contextPath + '/api/member/available',
                      data: 'membershipNumber=' + value,
                      cache: false,
                      async: false
                    }).status;

                    // Internal Error(500) 時などに「会員がいない」ことになってしまうのを防ぐため、
                    // Found(200) 時に検証成功とするのではなく、Not Found(404) 以外 は検証成功とする。
                    return status !== 404;
                  },
                  messages: {ja: '会員番号が確認できませんでした。'}
    });
}
