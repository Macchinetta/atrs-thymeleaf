<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>予約内容確認 | Airline Ticket Reservation System</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

  </head>
  <body>

    <%@ include file="../A0/header.jsp" %>

    <div class="container">

      <div class="row">

        <section class="col-md-12">
          <h2>予約内容確認</h2>

          <section>
            <h3>合計金額</h3>
            <p>
              &yen;<fmt:formatNumber value="${reserveConfirmOutputDto.totalFare}" pattern="###,###"/>
            </p>
          </section>

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

          <section>
            <h3>お客様情報</h3>
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th class="col-md-1">No</th>
                  <th class="col-md-6">お名前(カタカナ)</th>
                  <th class="col-md-1">年齢</th>
                  <th class="col-md-2">性別</th>
                  <th class="col-md-2">会員番号(10桁)</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="passenger" items="${ticketReserveForm.passengerFormList}" varStatus="status">
                  <c:if test="${!passenger.isEmpty()}">
                    <tr>
                      <td>${f:h(status.count)}</td>
                      <td>${f:h(passenger.familyName)}&nbsp;${f:h(passenger.givenName)}</td>
                      <td>${f:h(passenger.age)}歳</td>
                      <td>${f:h(CL_GENDER[passenger.gender.code])}</td>
                      <td>${f:h(passenger.membershipNumber)}</td>
                    </tr>
                  </c:if>
                </c:forEach>
              </tbody>
            </table>
          </section>

          <section class="form-horizontal">
            <h3>代表者情報</h3>
            <div class="form-group">
              <label class="col-md-2 control-label">お名前(カタカナ)</label>
              <div class="col-md-8">
                <p class="form-control-static">${f:h(ticketReserveForm.repFamilyName)}&nbsp;${f:h(ticketReserveForm.repGivenName)}</p>
              </div>
            </div>

            <div class="form-group">
              <label class="col-md-2 control-label">年齢</label>
              <div class="col-md-8">
                <p class="form-control-static">${f:h(ticketReserveForm.repAge)}</p>
              </div>
            </div>

            <div class="form-group">
              <label class="col-md-2 control-label">性別</label>
              <div class="col-md-8">
                <p class="form-control-static">${f:h(CL_GENDER[ticketReserveForm.repGender.code])}</p>
              </div>
            </div>

            <div class="form-group">
              <label class="col-md-2 control-label">会員番号(10桁)</label>
              <div class="col-md-8">
                <p class="form-control-static">${f:h(ticketReserveForm.repMembershipNumber)}</p>
              </div>
            </div>

            <div class="form-group">
              <label class="col-md-2 control-label">電話番号</label>
              <div class="col-md-8">
                <p class="form-control-static">${f:h(ticketReserveForm.repTel1)}-${f:h(ticketReserveForm.repTel2)}-${f:h(ticketReserveForm.repTel3)}</p>
              </div>
            </div>

            <div class="form-group">
              <label class="col-md-2 control-label">Eメール</label>
              <div class="col-md-8">
                <p class="form-control-static">${f:h(ticketReserveForm.repMail)}</p>
              </div>
            </div>
          </section>

          <form:form cssClass="text-center btns-block"
            method="post"
            action="${pageContext.request.contextPath }/ticket/reserve"
            modelAttribute="ticketReserveForm">

            <%-- 選択フライト情報 --%>
            <form:hidden path="flightType" />
            <c:forEach items="${ticketReserveForm.selectFlightFormList}" varStatus="status">
            <spring:nestedPath path="selectFlightFormList[${status.index}]">
              <form:hidden path="depDate" />
              <form:hidden path="boardingClassCd" />
              <form:hidden path="fareTypeCd" />
              <form:hidden path="flightName" />
            </spring:nestedPath>
            </c:forEach>

            <%-- 搭乗者情報 --%>
            <c:forEach items="${ticketReserveForm.passengerFormList}" varStatus="status">
            <spring:nestedPath path="passengerFormList[${status.index}]">
              <form:hidden path="familyName" />
              <form:hidden path="givenName" />
              <form:hidden path="age" />
              <form:hidden path="gender" />
              <form:hidden path="membershipNumber" />
            </spring:nestedPath>
            </c:forEach>

            <%-- 予約代表者情報 --%>
            <form:hidden path="repFamilyName" />
            <form:hidden path="repGivenName" />
            <form:hidden path="repAge" />
            <form:hidden path="repGender" />
            <form:hidden path="repMembershipNumber" />
            <form:hidden path="repTel1" />
            <form:hidden path="repTel2" />
            <form:hidden path="repTel3" />
            <form:hidden path="repMail" />

            <input type="submit" class="btn btn-default btn-lg" name="redo" value="修正" />
            <input type="submit" class="btn btn-success btn-lg" value="予約確定" />
          </form:form>
        </section>

      </div><!-- /row -->

    </div><!-- /container -->

    <%@ include file="../A0/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/atrs.js"></script>
  </body>
</html>
