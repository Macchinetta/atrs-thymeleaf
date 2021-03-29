/*!
 * 会員情報変更画面(C201)スクリプト。
 *
 * Copyright(c) 2015 NTT Corporation.
 */

'use strict';

(function () {

  // ページ初期化
  // 各種イベントハンドラ、入力値チェックの設定を行う
  $(function init() {

    // 入力値チェックの設定
    setValidator();
  });

  /**
   * 入力値チェックの設定を行う。
   */
  function setValidator() {

    // バリデーションを有効化
    $('#membership-form').parsley({
      trigger: 'change',
      errorClass: 'has-error',
      classHandler: function (parsleyField) { return parsleyField.$element.closest('.form-group'); },
      errorsContainer: function (parsleyField) { return parsleyField.$element.closest('.form-group'); },
      errorsWrapper: '<div class="col-md-offset-4 col-md-8"></div>',
      errorTemplate: '<span class="invalid"></span>'
    });
  }

}());
