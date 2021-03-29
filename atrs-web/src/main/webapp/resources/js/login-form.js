/*!
 * ログイン画面(A101)スクリプト。
 *
 * Copyright(c) 2015 NTT Corporation.
 */

'use strict';

(function () {

  // ページ初期化
  // 入力値チェックの設定を行う
  $(function init() {

    // 入力値チェックの設定
    setValidator();
  });

  /**
   * 入力値チェックの設定を行う。
   */
  function setValidator() {

    // バリデーションを有効化
    $('#login-form').parsley({
      trigger: 'change',
      errorClass: 'has-error',
      classHandler: function (parsleyField) { return parsleyField.$element.closest('.form-group'); },
      errorsContainer: function (parsleyField) { return parsleyField.$element.closest('.form-group'); },
      errorsWrapper: '<div></div>',
      errorTemplate: '<span class="invalid"></span>'
    });
  }

}());
