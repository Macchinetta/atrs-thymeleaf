<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Airline Ticket Reservation System</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap-datepicker/css/bootstrap-datepicker3.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

  </head>
  <body>

    <%@ include file="header.jsp" %>

    <div class="container">

      <div class="row">

        <section class="col-md-4">
          <h2>空席照会・ご予約</h2>
          <%@ include file="../B1/flightSearchForm.jsp" %>
        </section>

        <section class="col-md-8">
          <h2>ATRSについて</h2>

          <section>
            <h3>概要</h3>
            <p>
              ATRS（本アプリケーション）は、Macchinetta オンライン版 フレームワーク、Macchinetta クライアント基本版 フレームワーク（以下、フレームワーク）を用いたサンプルアプリケーションです。
            </p>
            <dl>
              <dt>バージョン</dt>
              <dd>1.7.0</dd>
            </dl>
          </section>

          <section>
            <h3>ATRSの利用方法</h3>
            <ul>
              <li>ATRSにログインする場合は、ログインボタンを押して、会員番号とパスワードを入力してログインしてください。</li>
              <li>ATRSからログアウトする場合は、ログインユーザメニューからログアウトを選択してください。</li>
              <li>フライトの空席状況を照会する場合は、国内線リンクを選択して空席照会へ進むか、本画面よりフライト種別、区間、搭乗日、搭乗クラスを選択の上、照会ボタンを押してください。</li>
              <li>続けてチケットを予約する場合は、フライトの選択、お客様情報を入力した上で、予約内容の確認を行い、予約の確定を実施してください。</li>
              <li>ATRSカード会員に入会する場合は、会員登録を押してください。</li>
              <li>ATRSカード会員情報を変更する場合は、ログインした後、ログインユーザメニューから会員情報変更を選択してください。</li>
            </ul>
          </section>

          <section>
            <h3>ATRSで使用しているフレームワークの機能</h3>
            <p>
              ATRSで使用しているフレームワークの機能については、サンプルアプリケーションマニュアルを参照してください。
            </p>
          </section>
        </section>

      </div><!-- /row -->

    </div><!-- /container -->

    <%@ include file="footer.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap-datepicker/js/locales/bootstrap-datepicker.ja.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/moment/min/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/parsleyjs/parsley.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/atrs.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-config.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-validator-date.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-validator-dateafterto.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-validator-notequalto.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/top.js"></script>
  </body>
</html>
