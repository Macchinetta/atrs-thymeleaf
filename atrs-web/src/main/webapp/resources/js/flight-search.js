/*!
 * 空席照会画面(B102)スクリプト。
 *
 * Copyright(c) 2015 NTT Corporation.
 */

'use strict';

(function () {

  var MSG_SYSTEM_ERROR = 'ご迷惑をおかけして申し訳ありません。エラーが発生しました。お手数ですが、しばらく時間をおいてから再アクセスするか、お問い合わせください。';
  var MSG_TIMEOUT = '接続がタイムアウトになりました。';
  var MSG_UNAUTHORIZED = '会員番号またはパスワードが確認できませんでした。入力情報をご確認ください。';
  var MSG_FORBIDDEN = 'リクエストが許可されていません。';
  var MSG_INVALID_FLIGHT_TIME = '選択された復路のフライトはご利用できません。復路のフライトは往路の搭乗時刻より2時間以上経過している必要があります。';

  /** 日付フォーマット */
  var DATE_FORMAT = 'YYYY/MM/DD';

  /** 日時フォーマット */
  var DATE_TIME_FORMAT = 'YYYY/MM/DD HH:mm';

  /** コンテキストパス */
  var contextPath = $('meta[name="contextPath"]').attr('content');

  /** 往路テーブルオブジェクト */
  var outwardFlightsTable;

  /** 復路テーブルオブジェクト */
  var homewardFlightsTable;

  // ページ初期化
  // 空席照会結果テーブルの初期化と各種イベントハンドラの設定を行う
  $(function init() {

    // 入力値チェックの設定
    setValidator();

    // 空席照会テーブルの初期化
    outwardFlightsTable = new FlightsTable('#outward-flights'); // 往路
    homewardFlightsTable = new FlightsTable('#homeward-flights'); // 復路

    // イベントハンドラの設定
    $('#outwardDate').datepicker().on('changeDate', handlers.onDateChangeDatepicker);
    $('#homewardDate').datepicker().on('changeDate', handlers.onDateChangeDatepicker);
    $('#login-modal').on('show.bs.modal', handlers.onShowLoginModal);
    $(document).on('change', '#flights-search-form input[name=flightType]', handlers.onChangeFlightType);
    $(document).on('submit', '#flights-search-form', handlers.onSubmitFlightsSearchForm);
    $(document).on('submit', '#flights-select-form', handlers.onSubmitFlightsSelectForm);
    $(document).on('submit', '#login-form', handlers.onSubmitLoginForm);
    $(document).on('click', '#reserve-without-login-button', handlers.onClickReserveWithoutLoginButton);

    // 初期化用イベント発火
    // - 選択中のフライト種別に応じて復路搭乗日の表示状態を制御
    $('#flights-search-form input[name=flightType]:checked').trigger('change');

    // - 初期空席照会
    var isInitialSearchUnnecessary = $('meta[name="isInitialSearchUnnecessary"]').attr('content');
    if (!isInitialSearchUnnecessary) {
      $('#flights-search-form').trigger('submit');
    }

  });

  /**
   * イベントハンドラ
   * @namespace
   */
  var handlers = {

    /**
     * 往路日付が変更された際のイベントハンドラ。
     * 復路日付との相関チェックを実行する。
     * なお復路日付変更時の相関チェックは Parsley.js により自動実行されるためハンドラ設定は不要。
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
    },

    /**
     * 照会フォームがサブミットされた際のイベントハンドラ。
     * 空席照会条件の入力コントロールの入力値を用いて空席照会テーブルを更新する。
     */
    onSubmitFlightsSearchForm: function (e) {
      e.preventDefault();

      outwardFlightsTable.update(formatFlightSearchParam());
      homewardFlightsTable.update(formatFlightSearchParam(true));

      // フライト選択フォーム表示
      $('#flights-select-form').toggleClass('hidden', false);
    },

    /**
     * フライト選択フォームが submit された際のイベントハンドラ。
     * 選択フライトの値を送信するフォームを作成し submit する。
     * 未ログイン状態の場合はログインを促すモーダルを表示する。
     */
    onSubmitFlightsSelectForm: function (e) {
      e.preventDefault();

      $.ajax({
        type: 'GET',
        url: contextPath + '/api/auth/status',
        cache: false,
        timeout: 15000
      }).done(function () {

        // ログイン状態であればフライト予約する。
        reserveFlights(true);

      }).fail(function (xhr) {

        switch (xhr.status) {
        case 404:

          // ログイン状態でなければログインを促すモーダルを表示する。
          var $loginModal = $('#login-modal');
          $loginModal.find('#login-messages').toggleClass('hidden', true);
          $loginModal.modal();
          break;

        default:
          var errorText = (xhr.statusText === 'timeout' ? MSG_TIMEOUT : MSG_SYSTEM_ERROR);

          // エラーメッセージ表示
          $('#flights-select-form-messages').empty().toggleClass('hidden', false)
            .append($('<li>').text(errorText));
        }
      });
    },

    /**
     * モーダルダイアログが表示された際のイベントハンドラ。
     * 入力コントロールを初期化する。
     */
    onShowLoginModal: function () {
      $(this).find('input[name=membershipNumber], input[name=password]').val('');
    },

    /**
     * ログインを促すモーダルの「ログインして予約」フォームが submit された際のイベントハンドラ。
     * ログインを試行し、成功したらフライト予約を実行する。
     */
    onSubmitLoginForm: function (e) {
      e.preventDefault();

      var $form = $(this);
      var headers = {};
      var csrfToken = $('meta[name="_csrf"]').attr('content');
      var csrfHeaderName = $('meta[name="_csrf_header"]').attr('content');
      headers[csrfHeaderName] = csrfToken;

      $.ajax({
        type: this.method,
        url: this.action,
        data: $form.serialize(),
        headers: headers,
        cache: false,
        timeout: 15000
      }).done(function () {
        reserveFlights();
      }).fail(function (xhr) {
        var errorText = '';
        switch (xhr.status) {
        case 401:
          errorText = MSG_UNAUTHORIZED;
          break;
        case 403:
          errorText = MSG_FORBIDDEN;
          break;
        default:
          errorText = (xhr.statusText === 'timeout' ? MSG_TIMEOUT : MSG_SYSTEM_ERROR);
        }

        // エラーメッセージ表示
        $('#login-messages')
          .empty().append($('<li>').text(errorText))
          .toggleClass('hidden', false);
      });
    },

    /**
     * モーダルダイアログのログインせず予約ボタンがクリックされた際のイベントハンドラ。
     * フライト予約を実行する。
     */
    onClickReserveWithoutLoginButton: function () {
      reserveFlights(true);
    }

  };

  /**
   * 空席照会条件入力フォームから空席照会Webサービスのパラメータ形式にフォーマットする。
   * @param {Boolean}  homeward 復路のパラメータが欲しい場合はtrue
   * @return {Object} フォーマットされたパラメータ
   */
  function formatFlightSearchParam(homeward) {
    var flightsSearchParam = _.reduce($('#flights-search-form').serializeArray(), function (acc, item) {
      acc[item.name] = item.value;
      return acc;
    }, {});

    if (!homeward) {
      flightsSearchParam.depDate = flightsSearchParam.outwardDate;
    } else {
      var tmpArrAirportCd = flightsSearchParam.arrAirportCd;
      flightsSearchParam.arrAirportCd = flightsSearchParam.depAirportCd;
      flightsSearchParam.depAirportCd = tmpArrAirportCd;
      flightsSearchParam.depDate = flightsSearchParam.homewardDate;
    }
    delete flightsSearchParam.outwardDate;
    delete flightsSearchParam.homewardDate;
    return flightsSearchParam;
  }

  /**
   * 選択フライトのフォームを用いてフライト予約を実行する。
   * @param {Boolean} force true の場合、未ログイン状態であってもリクエストを実行する。
   */
  function reserveFlights(force) {
    var $form = $('#flights-select-form');
    $form.find('input[type=hidden]:not([name=_csrf])').remove();

    // 選択フライト
    var flightPropertyNames = ['flightName', 'fareTypeCd', 'depDate', 'boardingClassCd'];
    $('#outward-flights-table:visible input[name=outward-flight-select]:checked, #homeward-flights-table:visible input[name=homeward-flight-select]:checked').each(function (idx, el) {
      _.forEach(flightPropertyNames, function (propertyName) {
        var inputName = 'selectFlightFormList[' + idx + '].' + propertyName;
        $form.append($('<input>')
          .attr('name', inputName)
          .attr('type', 'hidden')
          .val($(el).data(propertyName)));
      });
    });

    // 次画面からフライト検索条件を復元するため、
    // フライト検索条件をフォームの hidden パラメータにセットする。
    var owCriteria = outwardFlightsTable.getSearchCriteria();
    for (var i in owCriteria) {
      $form.append($('<input>')
                   .attr('type', 'hidden')
                   .attr('name', i)
                   .val(owCriteria[i]));
    }
    $form.append($('<input>')
                 .attr('type', 'hidden')
                 .attr('name', 'outwardDate')
                 .val(owCriteria.depDate));
    var hwCriteria = homewardFlightsTable.getSearchCriteria();
    $form.append($('<input>')
                 .attr('type', 'hidden')
                 .attr('name', 'homewardDate')
                 .val(hwCriteria.depDate));

    // モーダルを除去
    $('#login-modal').modal('hide');
    $('.modal-backdrop').remove();

    if (force)
      $(document).off('submit', '#flights-select-form', handlers.onSubmitFlightsSelectForm);

    $form.submit();
  }

  /**
   * 空席照会結果テーブルを作成する。
   * @constructor
   * @param {String} selector テーブルを作成するDOM要素のID
   */
  function FlightsTable(selector) {

    /** 空席照会条件 */
    var currentParam;

    /**
     * 空席照会条件を返却する。
     * @return 空席照会条件
     */
    this.getSearchCriteria = function () {
      return currentParam;
    };

    var self = this,
        direction =
          selector.match(/outward/) && 'outward' ||
          selector.match(/homeward/) && 'homeward' || '',
        $this = $(selector),
        $table = $(selector + '-table'),
        $pager = $(selector + '-pager'),
        $fightTableMessages = $(selector + '-messages'),
        $dateLabel = $this.find('.pager-current-date'),
        $prevDateButton = $this.find('.prev-date-button'),
        $nextDateButton = $this.find('.next-date-button');

    /**
     * 空席照会結果テーブルでのみ使用するイベントハンドラ
     * @namespace
     */
    var handlers = {

      /**
       * 前日/翌日がクリックされた際のイベントハンドラ。
       * 空席照会日付を -1/+1 して再度照会する。
       */
      onClickDateChangingButton: function (e) {
        e.preventDefault();
        var $this = $(this);

        // 無効状態なら何もしない
        if ($this.parent('.disabled').length) {
          return;
        }

        var offset = $this.is($prevDateButton) ? -1 : 1;
        currentParam.depDate = moment(currentParam.depDate, DATE_FORMAT).add(offset, 'd').format(DATE_FORMAT);
        self.update(currentParam);
      },

      /**
       * 選択フライトが変更された際のイベントハンドラ。
       * クラスを付与してスタイルを変更する。
       * 選択フライトに設定されている data を用いて、フォームの hidden パラメータを生成する。
       * (選択フライトが持つメタデータが多く、直接 value を使用するのが難しいため)。
       */
      onChangeFlightSelect: function () {
        var $this = $(this);
        $this.parents('tbody').find('td').toggleClass('selected', false);
        $this.parents('td').toggleClass('selected', true);

        // 選択フライトの data 属性から hidden 要素を生成
        var index = (direction === 'outward' ? 0 : 1);
        var flightPropertyNames = ['flightName', 'fareTypeCd', 'depDate', 'boardingClassCd'];
        _.forEach(flightPropertyNames, function (propertyName) {
          var inputName = 'selectFlightFormList[' + index + '].' + propertyName;
          var $input = $('#flights-select-form input[name="' + inputName + '"]');
          $input.val($this.data(propertyName));
        });
      }
    };

    /**
     * 空席照会テーブルをAjaxで取得したデータを用いて更新する。
     * @param {Object} param 空席照会条件
     */
    this.update = function (param) {
      currentParam = param;

      // エラーメッセージ初期化、非表示化
      $('#flights-select-form-messages')
        .toggleClass('hidden', true)
        .find('li[id*=' + direction + ']').remove();
      $fightTableMessages.toggleClass('hidden', true);

      // フライト種別が片道の場合、復路テーブルを非表示にし、リクエストは実行しない。
      if (direction === 'homeward' && param.flightType === 'OW') {
        $this.toggle(false);
        $table.empty();
        return;
      }
      $this.toggle(true);

      // 照会可能開始日、終了日に応じて前日ボタン、翌日ボタンを非活性化
      // (照会可能開始日、終了日は往路、復路で同一のため、往路搭乗日から取得)
      var depDate = moment(param.depDate, DATE_FORMAT);
      var startDate = moment($('#outwardDate').data('date-start-date'), DATE_FORMAT);
      var endDate = moment($('#outwardDate').data('date-end-date'), DATE_FORMAT);
      $prevDateButton.parent().toggleClass('disabled', (+depDate <= +startDate));
      $nextDateButton.parent().toggleClass('disabled', (+depDate >= +endDate));

      // 搭乗日ラベル更新
      $dateLabel.text(decodeURIComponent(param.depDate));

      $.ajax({
        type: 'GET',
        url: contextPath + '/api/flights',
        data: param,
        dataType: 'json',
        cache: false,
        timeout: 15000
      }).done(function (data) {

        var html = _.template($('#flights-table-template').html())({
          data: data,
          $table: $table,
          direction: direction
        });
        $table.html(html).trigger('update', [false, function () {
          $table.trigger('updateAll', [false, function () {
            $table.trigger('refreshWidgets');
          }]);
        }]).trigger('pageSet', 1);

        // 選択状態の復元
        var $form = $('#flights-select-form');
        var index = (direction === 'outward' ? 0 : 1);
        var flightName =
              $form.find('input[name="selectFlightFormList[' + index + '].flightName"]').val();
        var fareTypeCd =
              $form.find('input[name="selectFlightFormList[' + index + '].fareTypeCd"]').val();
        var depDate =
              $form.find('input[name="selectFlightFormList[' + index + '].depDate"]').val();
        var boardingClassCd =
              $form.find('input[name="selectFlightFormList[' + index + '].boardingClassCd"]').val();

        var flightSelector = '[data-flight-name="' + flightName + '"]' +
              '[data-fare-type-cd="' + fareTypeCd + '"]' +
              '[data-dep-date="' + depDate + '"]' +
              '[data-boarding-class-cd="' + boardingClassCd + '"]:enabled';
        $form.find('input[name="' + direction + '-flight-select"]' + flightSelector).prop('checked', true);
        $form.find('input[name="' + direction + '-flight-select"]:checked').trigger('change');

        // 空席照会テーブルを表示
        $table.toggleClass('hidden', false);
        $pager.toggleClass('hidden', false);

      }).fail(function (xhr) {

        // 空席照会テーブルを非表示化
        $table.toggleClass('hidden', true);
        $pager.toggleClass('hidden', true);

        var errorTexts = [];
        var contentType = xhr.getResponseHeader('Content-Type');
        if (400 <= xhr.status && xhr.status <= 499 &&
            contentType !== null && contentType.indexOf('json') !== -1) {
          errorTexts = xhr.responseJSON.messages;
        } else {
          errorTexts.push(xhr.statusText === 'timeout' ? MSG_TIMEOUT : MSG_SYSTEM_ERROR);
        }

        // エラーメッセージ表示
        $fightTableMessages.empty().toggleClass('hidden', false);
        _.forEach(errorTexts, function (errorText) {
          $fightTableMessages.append($('<li>').text(errorText));
        });
      });
    };

    initTable();

    /**
     * 空席照会テーブルを初期化する。
     * @private
     */
    function initTable() {

      // イベントハンドラの設定
      $this.on('change', 'input[name=outward-flight-select], input[name=homeward-flight-select]', handlers.onChangeFlightSelect);
      $this.on('click', '.prev-date-button, .next-date-button', handlers.onClickDateChangingButton);

      // Tablesorter化
      $table.tablesorter({
        theme: 'bootstrap',
        cssIcon: 'icon-white',
        cssIconAsc: 'icon-chevron-up',
        cssIconDesc: 'icon-chevron-down',
        headerTemplate: '{content} {icon}',
        textExtraction: {
          '.fare-header': function (node) {
            return $(node).find('.fare').text();
          }
        },
        widgets: ['uitheme'],
        widgetOptions: {}
      }).tablesorterPager({
        size: 10,
        savePages: false,
        container: $pager,
        output: '{startRow} ~ {endRow}件 (全{totalRows}件中)',
        cssPageSize: '.pagesize'
      });
      $pager.find('a').on('click', function (e) { e.preventDefault(); });
    }
  }

  /**
   * 選択した往路・復路フライトの出発時刻が有効な組み合わせかどうか検証する。
   * @return {Boolean} 検証結果
   */
  function validateSelectedTime() {
    var $outwardFlight = $('[name=outward-flight-select]:checked');
    var $homewardFlight = $('[name=homeward-flight-select]:checked');

    // 往路・復路フライトのいずれかが選択されていない場合はチェックしない
    if (!$outwardFlight.length || !$homewardFlight.length) {
      return true;
    }

    var reserveIntervalTime = $('meta[name="reserveIntervalTime"]').attr('content');
    var outwardArrTime = moment($outwardFlight.data('depDate') + ' ' + $outwardFlight.data('arrTime'), DATE_TIME_FORMAT).add(reserveIntervalTime, 'm');
    var homewardDepTime = moment($homewardFlight.data('depDate') + ' ' + $homewardFlight.data('depTime'), DATE_TIME_FORMAT);

    return homewardDepTime.isSame(outwardArrTime) || homewardDepTime.isAfter(outwardArrTime);
  }

  /**
   * 入力値チェックの設定を行う。
   */
  function setValidator() {

    // フライト検索フォームのバリデーションを有効化
    $('#flights-search-form').parsley({
      excluded: 'input[type=button], input[type=submit], input[type=reset], input[type=hidden], :hidden',
      errorClass: 'has-error',
      classHandler: function (parsleyField) { return parsleyField.$element.closest('.form-group'); },
      errorsContainer: function (parsleyField) { return parsleyField.$element.closest('.form-group'); },
      errorsWrapper: '<div class="col-md-offset-4 col-md-8"></div>',
      errorTemplate: '<span class="invalid"></span>'
    });

    // フライト選択フォームのバリデーションを有効化
    $('#flights-select-form').parsley({
      excluded: 'input[type=button], input[type=submit], input[type=reset], input[type=hidden]',
      trigger: 'submit',
      focus: 'none',
      errorsContainer: '#flights-select-form-messages',
      errorsWrapper: '<li></li>',
      errorTemplate: '<span></span>'
    }).on('parsley:form:validate', function () {

      // Parsley外設定メッセージを削除
      $('#flights-select-form-messages').find('li:not([id*=parsley])').remove();
    }).on('parsley:form:validated', function (formInstance) {

      // 復路出発時刻チェック
      if (!validateSelectedTime()) {
        $('#flights-select-form-messages').toggleClass('hidden', false)
          .append($('<li>').text(MSG_INVALID_FLIGHT_TIME));
        formInstance.submitEvent.stopImmediatePropagation();
        formInstance.submitEvent.preventDefault();
      }
    }).on('parsley:field:validated', function () {
      var $flightsSelectFormMessages = $('#flights-select-form-messages');
      var messagesText = $flightsSelectFormMessages.text();
      $flightsSelectFormMessages.toggleClass('hidden', messagesText === '');
    });

  }

}());
