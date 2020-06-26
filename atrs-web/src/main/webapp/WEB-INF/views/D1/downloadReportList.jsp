<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>履歴レポートDL | Airline Ticket Reservation System</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

  </head>
  <body>

    <%@ include file="../A0/header.jsp" %>

    <div class="container">

      <div class="row">

        <section class="col-md-12">
          <h2>予約履歴レポートダウンロード</h2>
            <c:choose>
              <c:when test="${fn:length(reportNameList) == 0 }">
                <div class="alert alert-danger">
                  <ul>
                    <li>予約履歴レポートが存在しません。</li>
                  </ul>
                </div>
              </c:when>
              <c:otherwise>
                <div class="alert alert-info">
                  <ul>
                    <li>ダウンロードするレポートを選択して下さい。</li>
                  </ul>
                </div>

                <form:form method="get" action="${pageContext.request.contextPath}/HistoryReport/download">
                  <table class="table">
                    <thead>
                      <tr>
                        <th>予約履歴レポート</th><th><!-- DLボタン配置カラム --></th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${reportNameList}" var="reportName">
                        <tr>
                          <td>${f:h(reportName)}</td>
                          <td><button class="btn btn-default" name="reportName" value="${f:h(reportName)}">ダウンロード</button></td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </form:form>
              </c:otherwise>
            </c:choose>

          <div class="text-center">
            <a href="${pageContext.request.contextPath}/" class="btn btn-default">トップに戻る</a>
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
