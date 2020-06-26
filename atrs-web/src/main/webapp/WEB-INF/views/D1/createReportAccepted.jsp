<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>履歴レポート作成 | Airline Ticket Reservation System</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

  </head>
  <body>

    <%@ include file="../A0/header.jsp" %>

    <div class="container">

      <div class="row">

        <section class="col-md-12">

          <div class="alert alert-success">
            <h2>予約履歴レポートの作成要求を受け付けました。</h2>
            <p>
                              作成されたレポートは暫くしてから「履歴レポートDL」ページよりダウンロード頂けます。
            </p>
          </div>
        </section>

        <div class="text-center">
          <a href="${pageContext.request.contextPath}/" class="btn btn-default">トップに戻る</a>
        </div>

      </div><!-- /row -->

    </div><!-- /container -->

    <%@ include file="../A0/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/atrs.js"></script>
  </body>
</html>
