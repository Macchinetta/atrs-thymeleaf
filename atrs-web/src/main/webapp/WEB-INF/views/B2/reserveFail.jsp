<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>予約エラー | Airline Ticket Reservation System</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

  </head>
  <body>

    <%@ include file="../A0/header.jsp" %>

    <div class="container">

      <div class="row">

        <section class="col-md-12">
          <t:messagesPanel panelElement="ul" panelClassName="alert list-unstyled" outerElement="" disableHtmlEscape="true"/>

          <section>
            <h3>選択フライト</h3>
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th></th>
                  <th>搭乗日</th>
                  <th>便名</th>
                  <th>出発時刻</th>
                  <th>到着時刻</th>
                  <th>区間</th>
                  <th>搭乗クラス</th>
                  <th>運賃種別</th>
                  <th>運賃</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach var="flight" items="${selectFlightDtoList}">
                <tr>
                  <td>${f:h(flight.lineType.name)}</td>
                  <td><fmt:formatDate value="${flight.departureDate}" pattern="MM月dd日(E)"/></td>
                  <td>${f:h(flight.flightName)}</td>
                  <td>${f:h(fn:substring(flight.departureTime, 0, 2))}:${f:h(fn:substring(flight.departureTime, 2, 4))}</td>
                  <td>${f:h(fn:substring(flight.arrivalTime, 0, 2))}:${f:h(fn:substring(flight.arrivalTime, 2, 4))}</td>
                  <td>${f:h(CL_AIRPORT[flight.depAirportCd])}&nbsp;<span class="glyphicon glyphicon-arrow-right"></span>&nbsp;${f:h(CL_AIRPORT[flight.arrAirportCd])}</td>
                  <td>${f:h(CL_BOARDINGCLASS[flight.boardingClassCd.code])}</td>
                  <td>${f:h(CL_FARETYPE[flight.fareTypeCd.code])}</td>
                  <td>&yen;<fmt:formatNumber value="${flight.fare}" pattern="###,###"/></td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </section>

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
