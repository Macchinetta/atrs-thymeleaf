/*!
 * お客様情報入力画面(B202)スクリプト。
 *
 * Copyright(c) 2015 NTT Corporation.
 */

'use strict';

(function () {

  // ページ初期化
  // 各種イベントハンドラ、入力値チェックの設定を行う
  $(function init() {
    $('#add-passenger-button').on('click', handlers.onClickAddPassengerButton);
    $('#copy-to-representive-button').on('click', handlers.onClickCopyToRepresentiveButton);

    // 入力値チェックの設定
    setValidator();
  });

  /**
   * イベントハンドラ
   * @namespace
   */
  var handlers = {

    /**
     * 搭乗者追加ボタンがクリックされた際のハンドラ。
     * 搭乗者入力フォームを追加する。
     */
    onClickAddPassengerButton: function () {
      $('#passenger4, #passenger5, #passenger6').show();
      $('#add-passenger-button').hide();
    },

    /**
     * 搭乗者1をコピーボタンがクリックされた際のハンドラ。
     * 代表者情報に搭乗者1の入力情報をコピーする。
     */
    onClickCopyToRepresentiveButton: function () {
      var $firstPassenger = $('#passenger1');
      var $representive = $('#representive');
      _.each(['.family-name', '.given-name', '.age', '.gender', '.membership-number'], function (item) {
        var $dest = $representive.find(item);
        var $src = $firstPassenger.find(item);
        if ($src.attr('type') === 'radio') {
          $dest.val([$src.filter(':checked').val()]);
        } else {
          $dest.val($src.val());
        }
      });
    }
  };

  /**
   * 入力値チェックの設定を行う。
   */
  function setValidator() {

    // バリデーションを有効化
    $('#customerinfo-form').parsley({
      trigger: 'change',
      errorClass: 'has-error',
      classHandler: function (parsleyField) { return parsleyField.$element.closest('.form-group'); },
      errorsContainer: function (parsleyField) { return parsleyField.$element.closest('.form-group'); },
      errorsWrapper: '<div class="col-md-offset-2 col-md-8"></div>',
      errorTemplate: '<span class="invalid"></span>'
    });

    // 「空席一覧に戻る」ボタンではバリデーションを無効化
    $('[name=backToSearch]').on('click', function () {
      $('#customerinfo-form').off('submit.Parsley');
    });

  }

}());
