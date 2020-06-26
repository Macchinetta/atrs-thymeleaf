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
          <h2>予約履歴レポート作成確認</h2>
          <p>
            ${f:h(member.membershipNumber)}&nbsp;の予約履歴レポートを作成します。よろしいですか？<br>
          </p>

          <div class="col-md-12 text-center">
            <form:form method="post" action="${pageContext.request.contextPath}/HistoryReport/create">
              <a href="${pageContext.request.contextPath}/" class="btn btn-default btn-lg">トップに戻る</a>
　　　　　　　　　　　　<input type="submit" class="btn btn-success btn-lg" value="レポート作成" />
            </form:form>
          </div>
        </section>

      </div><!-- /row -->

    </div><!-- /container -->

    <%@ include file="../A0/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/atrs.js"></script>
  </body>
</html>
