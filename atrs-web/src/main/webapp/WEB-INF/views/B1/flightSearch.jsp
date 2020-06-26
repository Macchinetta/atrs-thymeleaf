<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="contextPath" content="${pageContext.request.contextPath}" />
    <meta name="reserveIntervalTime" content="${f:h(flightSearchOutputDto.reserveIntervalTime)}" />
    <meta name="isInitialSearchUnnecessary" content="${f:h(isInitialSearchUnnecessary)}" />
    <security:csrfMetaTags />
    <title>空席照会 | Airline Ticket Reservation System</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap-datepicker/css/bootstrap-datepicker3.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/jquery.tablesorter/css/theme.bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/jquery.tablesorter/css/theme.default.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

  </head>
  <body>

    <%@ include file="../A0/header.jsp" %>

    <div class="container">

      <div class="row">

        <section class="col-md-12">
          <h2>空席照会</h2>
          <div class="alert alert-info">
            <ul>
              <li>
                <span><fmt:formatDate value="${flightSearchOutputDto.beginningPeriod}" pattern="MM月dd日"/></span>
                &nbsp;-&nbsp;
                <span><fmt:formatDate value="${flightSearchOutputDto.endingPeriod}" pattern="MM月dd日"/></span>の空席照会ができます。
              </li>
              <li>フライトは片道便か往復便が予約でき、6席まで予約できます。</li>
            </ul>
          </div>
          <spring:hasBindErrors name="ticketSearchForm">
            <c:if test="${errors.globalErrorCount > 0}">
              <div class="alert alert-danger">
                <spring:nestedPath path="ticketSearchForm">
                  <form:errors/>
                </spring:nestedPath>
              </div>
            </c:if>
          </spring:hasBindErrors>
          <spring:hasBindErrors name="reservationFlightForm">
            <c:if test="${errors.globalErrorCount > 0}">
              <div class="alert alert-danger">
                <spring:nestedPath path="reservationFlightForm">
                  <form:errors/>
                </spring:nestedPath>
              </div>
            </c:if>
          </spring:hasBindErrors>
          <t:messagesPanel panelElement="ul" panelClassName="alert list-unstyled" outerElement="" />
        </section>

      </div>

      <div class="row">

        <section class="col-md-4">
          <%@ include file="flightSearchForm.jsp" %>
        </section>

        <section class="col-md-8">
          <form:form id="flights-select-form" cssClass="hidden"
            action="${pageContext.request.contextPath}/ticket/search?select" method="post"
            modelAttribute="reservationFlightForm">

            <%-- 選択フライト情報 --%>
            <c:forEach begin="0" end="1" varStatus="status">
              <c:if test="${reservationFlightForm.selectFlightFormList[status.index] == null}">
                <input type="hidden" name="selectFlightFormList[${status.index}].depDate"/>
                <input type="hidden" name="selectFlightFormList[${status.index}].boardingClassCd" />
                <input type="hidden" name="selectFlightFormList[${status.index}].fareTypeCd" />
                <input type="hidden" name="selectFlightFormList[${status.index}].flightName" />
              </c:if>
              <c:if test="${reservationFlightForm.selectFlightFormList[status.index] != null}">
                <spring:nestedPath path="selectFlightFormList[${status.index}]">
                  <form:hidden path="depDate" />
                  <form:hidden path="boardingClassCd" />
                  <form:hidden path="fareTypeCd" />
                  <form:hidden path="flightName" />
                </spring:nestedPath>
              </c:if>
            </c:forEach>

            <section id="outward-flights" style="display: none">
              <h2>往路便の選択</h2>
              <ul class="pager pager-top text-left">
                <li><a class="prev-date-button" href="#"><span class="glyphicon glyphicon-chevron-left"></span> 前日</a></li>
                <li><span class="pager-current-date"></span></li>
                <li><a class="next-date-button" href="#">翌日 <span class="glyphicon glyphicon-chevron-right"></span></a></li>
              </ul>
              <ul id="outward-flights-messages" class="alert alert-danger hidden list-unstyled"></ul>
              <table id="outward-flights-table" class="flights-table">
                <thead><tr></tr></thead>
                <tbody><tr></tr></tbody>
              </table>
              <ul id="outward-flights-pager" class="pager pager-bottom text-right">
                <li class="prev"><a href="#"><span class="glyphicon glyphicon-chevron-left"></span> 前の10件</a></li>
                <li><span class="pagedisplay"></span></li>
                <li class="next"><a href="#">次の10件 <span class="glyphicon glyphicon-chevron-right"></span></a></li>
              </ul>
            </section>

            <section id="homeward-flights" style="display: none">
              <h2>復路便の選択</h2>
              <ul class="pager pager-top text-left">
                <li><a class="prev-date-button" href="#"><span class="glyphicon glyphicon-chevron-left"></span> 前日</a></li>
                <li><span class="pager-current-date"></span></li>
                <li><a class="next-date-button" href="#">翌日 <span class="glyphicon glyphicon-chevron-right"></span></a></li>
              </ul>
              <ul id="homeward-flights-messages" class="alert alert-danger hidden list-unstyled"></ul>
              <table id="homeward-flights-table" class="flights-table">
                <thead><tr></tr></thead>
                <tbody><tr></tr></tbody>
              </table>
              <ul id="homeward-flights-pager" class="pager pager-bottom text-right">
                <li class="prev"><a href="#"><span class="glyphicon glyphicon-chevron-left"></span> 前の10件</a></li>
                <li><span class="pagedisplay"></span></li>
                <li class="next"><a href="#">次の10件 <span class="glyphicon glyphicon-chevron-right"></span></a></li>
              </ul>
            </section>

            <ul id="flights-select-form-messages" class="messages alert alert-danger list-unstyled"></ul>

            <div class="text-center">
              <input type="submit" id="reserve-flights-button" class="btn btn-primary btn-lg" value="選択フライトを予約">
            </div>
          </form:form>


          <script type="text/template" id="flights-table-template">
            <@
              var fareTypes = [];
              var flightClassNameDict = {};
              for (var i = 0, len = data.length; i < len; i++) {
                for (var prop in data[i].fareTypes) {
                  if (_.indexOf(fareTypes, prop) === -1)
                    fareTypes.push(prop);
                    flightClassNameDict[prop] = data[i].fareTypes[prop].fareTypeName;
                }
              }
              var fareTypeWidth = ($table.width() - 64 - 88 - 88) / fareTypes.length;
            @>
            <thead>
              <tr>
                <th style="width: 64px">便名</th>
                <th style="width: 88px">出発</th>
                <th style="width: 88px">到着</th>
                <@ for (var i = 0, len = fareTypes.length; i < len; i++) { @>
                <th class="fare-header" style="width: <@- fareTypeWidth @>px"><@- flightClassNameDict[fareTypes[i]] @></th>
                <@ } @>
              </tr>
            </thead>
            <tbody>
              <@ for (var i = 0, len = data.length; i < len; i++) { @>
              <tr>
                <th class="bg-primary"><@- data[i].flightName @></th>
                <th class="bg-primary"><@- data[i].depTime @><br><@- data[i].depAirportName @></th>
                <th class="bg-primary"><@- data[i].arrTime @><br><@- data[i].arrAirportName @></th>
                <@ for (var j = 0, jlen = fareTypes.length; j < jlen; j++) {
                  var flightClass = data[i].fareTypes[fareTypes[j]]; @>
                  <td>
                    <@ if (flightClass) { @>
                    <label <@= flightClass.vacantNum > 0 ? 'style="cursor: pointer"' : '' @>>
                      <input type="radio" name="<@- direction @>-flight-select"
                             data-parsley-required="true" data-parsley-required-message="<@= direction === 'outward' ? '往路のフライトを選択してください。' : '復路のフライトを選択してください。' @>"
                             data-flight-name="<@- data[i].flightName @>"
                             data-fare-type-cd="<@- fareTypes[j] @>"
                             data-dep-date="<@- data[i].depDate @>"
                             data-dep-time="<@- data[i].depTime @>"
                             data-arr-time="<@- data[i].arrTime @>"
                             data-boarding-class-cd="<@- data[i].boardingClassCd @>"
                             <@= flightClass.vacantNum > 0 ? '' : 'disabled="disabled"' @>><br>
                      &yen;<span class="fare"><@- flightClass.fare @></span><br>
                      <@= flightClass.vacantNum > 10 ? '&nbsp;' : flightClass.vacantNum > 0 ? '<span class="label label-warning">残り' + flightClass.vacantNum + '席</span>' : '<span class="label label-default">満席</span>' || '-' @>
                    </label>
                    <@ } else {@>
                    -
                    <@ } @>
                  </td>
                <@ } @>
                </tr>
              <@ } @>
            </tbody>
          </script>

          <%@ include file="../B2/reserveSelectForm.jsp" %>

        </section>

      </div><!-- /row -->

    </div><!-- /container -->

    <%@ include file="../A0/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap-datepicker/js/locales/bootstrap-datepicker.ja.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/jquery.tablesorter/js/jquery.tablesorter.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/jquery.tablesorter/js/jquery.tablesorter.widgets.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/jquery.tablesorter/addons/pager/jquery.tablesorter.pager.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/lodash/lodash.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/moment/min/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/parsleyjs/parsley.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/atrs.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-config.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-validator-date.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-validator-dateafterto.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-validator-notequalto.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/flight-search.js"></script>
  </body>
</html>
