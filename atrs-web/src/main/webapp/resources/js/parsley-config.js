/*!
 * Parsleyの初期設定を提供するモジュール。
 *
 * Copyright(c) 2015 NTT Corporation.
 */

'use strict';

window.ParsleyConfig = window.ParsleyConfig || {};
window.ParsleyConfig.i18n = window.ParsleyConfig.i18n || {};

// Persley.js のデフォルトメッセージ定義
window.ParsleyConfig.i18n.ja = $.extend(window.ParsleyConfig.i18n.ja || {}, {
  type: {
    email:        'Eメールアドレス形式が正しくありません。',
    integer:      '数値で入力してください。'
  },
  required:       '入力必須項目です。',
  minlength:      '%s 文字以上で入力してください。',
  maxlength:      '%s 文字以下で入力してください。',
  length:         '%s 文字以上 %s 文字以下で入力してください。'
});

// メッセージ設定
if ('undefined' !== typeof window.Parsley)
  window.Parsley.addCatalog('ja', window.ParsleyConfig.i18n.ja, true);
