/*!
 * 共通機能を提供するモジュール。
 *
 * Copyright(c) 2015 NTT Corporation.
 */

'use strict';

if (window._) {

  // Underscore.js のテンプレート範囲を示すことに使用する記号文字列を変更
  // (JSPの特殊タグとの競合を避けるため)
  _.templateSettings.evaluate = /<@([\s\S]+?)@>/g;
  _.templateSettings.interpolate = /<@=([\s\S]+?)@>/g;
  _.templateSettings.escape = /<@-([\s\S]+?)@>/g;
}
