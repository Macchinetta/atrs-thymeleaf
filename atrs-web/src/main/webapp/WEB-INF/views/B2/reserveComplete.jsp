<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>予約完了 | Airline Ticket Reservation System</title>

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
            <h2>ご予約を受け付けました。</h2>
            <p>
              ご予約ありがとうございました。<br>
              期限までにご購入いただけない場合、すべてのフライトが自動的にキャンセルされます。
            </p>
          </div>

          <h3>予約情報</h3>
          <table class="table table-bordered">
            <tbody>
              <tr>
                <th class="col-md-4">予約番号</th>
                <td class="col-md-8">${f:h(reserveCompleteOutputDto.reserveNo)}</td>
              </tr>
              <tr>
                <th>合計金額</th>
                <td><fmt:formatNumber value="${reserveCompleteOutputDto.totalFare}" pattern="###,###"/>円</td>
              </tr>
              <tr>
                <th>お支払期限</th>
                <td><fmt:formatDate value="${reserveCompleteOutputDto.paymentDate}" pattern="MM月dd日(E)"/></td>
              </tr>
            </tbody>
          </table>
        </section>

        <div class="text-center">
          <a href="${pageContext.request.contextPath}/" class="btn btn-default">トップに戻る</a>
        </div>

      </div><!-- /row -->

    </div><!--/container  -->

    <%@ include file="../A0/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/atrs.js"></script>
  </body>
</html>
