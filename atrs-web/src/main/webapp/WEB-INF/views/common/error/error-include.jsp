<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>エラー | Airline Ticket Reservation System</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

  </head>
  <body>

    <%-- エラー画面のヘッダは個別定義 --%>
    <header id="header">
      <div class="container">

        <h1>
          <a href="${pageContext.request.contextPath}/">
            <span class="sr-only">Airline Ticket Reservation System</span>
          </a>
        </h1>
        <nav>
          <ul class="vertical-middle-box">
            <li>
              <a href="${pageContext.request.contextPath}/ticket/search?form">国内線</a>
            </li>
          </ul>
        </nav>

      </div>
    </header>

    <div class="container">

      <div class="row">

        <section class="col-md-12">
          <div class="alert alert-danger">
            <c:if test="${empty messageKey}">
              <c:set var="messageKey" value="e.ar.fw.9999" />
            </c:if>
            <spring:message code="${messageKey}" />
          </div>

          <div class="col-md-12 text-center">
            <a href="${pageContext.request.contextPath}/" class="btn btn-default">トップに戻る</a>
          </div>
        </section>

      </div>

    </div>

    <%@ include file="../../A0/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/atrs.js"></script>
  </body>
</html>
