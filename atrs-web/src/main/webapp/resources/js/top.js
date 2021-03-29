/*!
 * TOP画面(A001)スクリプト。
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

    // イベントハンドラの設定
    $('#outwardDate').datepicker().on('changeDate', handlers.onDateChangeDatepicker);
    $('#homewardDate').datepicker().on('changeDate', handlers.onDateChangeDatepicker);
    $(document).on('change', '#flights-search-form input[name=flightType]', handlers.onChangeFlightType);

    // 初期化用イベント発火
    // - 選択中のフライト種別に応じて復路搭乗日の表示状態を制御
    $('#flights-search-form input[name=flightType]:checked').trigger('change');
  });

  /**
   * イベントハンドラ
   * @namespace
   */
  var handlers = {

    /**
     * 往路日付が変更された際のイベントハンドラ。
     * 復路日付との相関チェックを実行する。
     * なお復路日付変更時の相関チェックは Parsley.js により自動実行されるため不要。
     */
    onDateChangeDatepicker: function () {
      $('#homewardDate').parsley().validate();
    },

    /**
     * フライト種別が変更された際のイベントハンドラ。
     * 復路搭乗日の入力コントロールの表示状態を切り替える。
     */
    onChangeFlightType: function () {
      $('#date-for-homeward').toggle(this.value === 'RT');
    }

  };

  /**
   * 入力値チェックの設定を行う。
   */
  function setValidator() {

    // バリデーションを有効化
    $('#flights-search-form').parsley({
      excluded: 'input[type=button], input[type=submit], input[type=reset], input[type=hidden], :hidden',
      errorClass: 'has-error',
      classHandler: function (parsleyField) { return parsleyField.$element.closest('.form-group'); },
      errorsContainer: function (parsleyField) { return parsleyField.$element.closest('.form-group'); },
      errorsWrapper: '<div class="col-md-offset-4 col-md-8"></div>',
      errorTemplate: '<span class="invalid"></span>'
    });
  }

}());
