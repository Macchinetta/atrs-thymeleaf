<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>会員登録完了 | Airline Ticket Reservation System</title>

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
            <h2>会員登録しました。</h2>
            <p>
              会員登録ありがとうございました。<br>
              会員番号は<strong>${f:h(member.membershipNumber)}</strong>です。
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
