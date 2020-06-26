<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="contextPath" content="${pageContext.request.contextPath}" />
    <title>予約 | Airline Ticket Reservation System</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

  </head>
  <body>
    <%@ include file="../A0/header.jsp" %>

    <div class="container">

      <div class="row">

        <section class="col-md-12">
          <h2>予約</h2>

          <spring:hasBindErrors name="ticketReserveForm">
            <c:if test="${errors.globalErrorCount > 0}">
              <div class="alert alert-danger">
                <spring:nestedPath path="ticketReserveForm">
                  <form:errors/>
                </spring:nestedPath>
              </div>
            </c:if>
          </spring:hasBindErrors>
          <t:messagesPanel panelElement="ul" panelClassName="alert list-unstyled" outerElement="" />

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

          <form:form id="customerinfo-form" cssClass="form-horizontal passengers"
            action="${pageContext.request.contextPath}/ticket/reserve" method="post"
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

            <%-- 会員予約時の予約代表者情報 --%>
            <security:authorize access="hasRole('MEMBER')">
              <form:hidden path="repFamilyName" />
              <form:hidden path="repGivenName" />
              <form:hidden path="repAge" />
              <form:hidden path="repGender" />
              <form:hidden path="repMembershipNumber" />
              <form:hidden path="repTel1" />
              <form:hidden path="repTel2" />
              <form:hidden path="repTel3" />
              <form:hidden path="repMail" />
            </security:authorize>

            <section>
              <h3>お客様情報</h3>

              <c:forEach items="${ticketReserveForm.passengerFormList}" varStatus="status">
              <c:set var="passengerNo" value="${status.count}" />
              <spring:nestedPath path="passengerFormList[${status.index}]">

                <div id="passenger${passengerNo}" ${passengerNo < 4 || ticketReserveForm.hasAdditionalPassenger() ? '' : 'style="display: none" '}>
                  <h4>搭乗者${passengerNo}</h4>
                  <div class="form-group">
                    <form:label path="familyName" cssClass="col-md-2 control-label">お名前(カタカナ)</form:label>
                    <div class="col-md-6">
                      <div class="input-group inline-half">
                        <label class="input-group-addon">セイ</label>
                        <form:input path="familyName" cssClass="form-control ${passengerNo == 1 ? 'family-name' : ''}" maxlength="10"/>
                      </div>
                      <div class="input-group inline-half">
                        <label class="input-group-addon">メイ</label>
                        <form:input path="givenName" cssClass="form-control ${passengerNo == 1 ? 'given-name' : ''}" maxlength="10"/>
                      </div>
                      <form:errors path="familyName" cssClass="invalid" element="span"/>
                      <form:errors path="givenName" cssClass="invalid" element="span"/>
                    </div>
                  </div>

                  <div class="form-group">
                    <form:label path="age" cssClass="col-md-2 control-label">年齢</form:label>
                    <div class="col-md-3">
                      <form:input path="age" cssClass="form-control ${passengerNo == 1 ? 'age' : ''}" maxlength="3"
                        data-parsley-type="integer"/>
                      <form:errors path="age" cssClass="invalid" element="span"/>
                    </div>
                  </div>

                  <div class="form-group">
                    <label class="col-md-2 control-label">性別</label>
                    <div class="col-md-4 form-inline">
                      <c:forEach var="item" items="${CL_GENDER}">
                        <label class="radio-inline">
                          <form:radiobutton path="gender" cssClass="${passengerNo == 1 ? 'gender' : ''}" value="${f:h(item.key)}"/>${f:h(CL_GENDER[item.key])}
                        </label>
                      </c:forEach>
                      <div class="clearfix"></div>
                      <form:errors path="gender" cssClass="invalid" element="span"/>
                    </div>
                  </div>

                  <div class="form-group">
                    <form:label path="membershipNumber" cssClass="col-md-2 control-label">会員番号(10桁)</form:label>
                    <div class="col-md-3">
                      <form:input path="membershipNumber" cssClass="form-control ${passengerNo == 1 ? 'membership-number' : ''}" maxlength="10"
                        data-parsley-type="integer"
                        data-parsley-length="[10, 10]" data-parsley-length-message="%s 文字で入力してください。"
                        data-parsley-memberavailable="true"/>
                      <form:errors path="membershipNumber" cssClass="invalid" element="span"/>
                    </div>
                  </div>
                </div>

              </spring:nestedPath>
              </c:forEach>

              <div class="form-group">
                <div class="col-md-offset-2 col-md-8">
                <c:if test="${!ticketReserveForm.hasAdditionalPassenger()}">
                  <button type="button" id="add-passenger-button" class="btn btn-default">搭乗者を追加</button>
                </c:if>
                </div>
              </div>

            </section>

            <section id="representive">
              <h3>代表者情報</h3>

              <%-- 一般予約時 --%>
              <security:authorize access="!hasRole('MEMBER')">
                <div class="form-group">
                  <form:label path="repFamilyName" cssClass="col-md-2 control-label">お名前(カタカナ)</form:label>
                  <div class="col-md-8">
                    <div class="input-group inline-half">
                      <label class="input-group-addon">セイ</label>
                      <form:input path="repFamilyName" cssClass="family-name form-control" maxlength="10"
                        data-parsley-required="true" data-parsley-required-message="セイは入力必須項目です。"/>
                    </div>
                    <div class="input-group inline-half">
                      <label class="input-group-addon">メイ</label>
                      <form:input path="repGivenName" cssClass="given-name form-control" maxlength="10"
                        data-parsley-required="true" data-parsley-required-message="メイは入力必須項目です。"/>
                    </div>
                    <div class="clearfix"></div>
                    <form:errors path="repFamilyName" cssClass="invalid" element="span"/>
                    <form:errors path="repGivenName" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="repAge" cssClass="col-md-2 control-label">年齢</form:label>
                  <div class="col-md-3">
                    <form:input path="repAge" cssClass="age form-control" maxlength="3"
                      data-parsley-required="true"
                      data-parsley-maxlength="3"
                      data-parsley-type="integer"/>
                    <form:errors path="repAge" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-2 control-label">性別</label>
                  <div class="col-md-8 form-inline">
                    <c:forEach var="item" items="${CL_GENDER}">
                      <label class="radio-inline">
                        <form:radiobutton path="repGender" cssClass="gender" value="${f:h(item.key)}"
                          data-parsley-required="true"/>${f:h(CL_GENDER[item.key])}
                      </label>
                    </c:forEach>
                    <div class="clearfix"></div>
                    <form:errors path="repGender" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="repMembershipNumber" cssClass="col-md-2 control-label">会員番号(10桁)</form:label>
                  <div class="col-md-3">
                    <form:input path="repMembershipNumber" cssClass="membership-number form-control" maxlength="10"
                      data-parsley-type="integer"
                      data-parsley-length="[10, 10]" data-parsley-length-message="%s 文字で入力してください。"
                      data-parsley-memberavailable="true"/>
                    <form:errors path="repMembershipNumber" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="repTel1" cssClass="col-md-2 control-label">電話番号</form:label>
                  <div class="col-md-8 form-inline">
                    <form:input path="repTel1" cssClass="form-control form-control-separated" style="width: 5.5em" maxlength="5"
                      data-parsley-required="true" data-parsley-required-message="番号1は入力必須項目です。"
                      data-parsley-length="[2, 5]" data-parsley-length-message="番号1は %s 文字以上 %s 文字以下で入力してください。"
                      data-parsley-type="integer" data-parsley-type-message="番号1は数値で入力してください。"/> -
                    <form:input path="repTel2" cssClass="form-control form-control-separated" style="width: 4.5em" maxlength="4"
                      data-parsley-required="true" data-parsley-required-message="番号2は入力必須項目です。"
                      data-parsley-length="[1, 4]" data-parsley-length-message="番号2は %s 文字以上 %s 文字以下で入力してください。"
                      data-parsley-type="integer" data-parsley-type-message="番号2は数値で入力してください。"/> -
                    <form:input path="repTel3" cssClass="form-control form-control-separated" style="width: 4.5em" maxlength="4"
                      data-parsley-required="true" data-parsley-required-message="番号3は入力必須項目です。"
                      data-parsley-length="[4, 4]" data-parsley-length-message="番号3は %s 文字で入力してください。"
                      data-parsley-type="integer" data-parsley-type-message="番号3は数値で入力してください。"/>
                    <div class="clearfix"></div>
                    <form:errors path="repTel1" cssClass="invalid" element="span"/>
                    <form:errors path="repTel2" cssClass="invalid" element="span"/>
                    <form:errors path="repTel3" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="repMail" cssClass="col-md-2 control-label">Eメール</form:label>
                  <div class="col-md-8">
                    <form:input path="repMail" cssClass="form-control" maxlength="256"
                      data-parsley-required="true"
                      data-parsley-type="email"/>
                    <form:errors path="repMail" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <div class="col-md-offset-2 col-md-8">
                    <button type="button" id="copy-to-representive-button" class="btn btn-default">搭乗者1をコピー</button>
                  </div>
                </div>
              </security:authorize>

              <%-- 会員予約時 --%>
              <security:authorize access="hasRole('MEMBER')">
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
              </security:authorize>

            </section>

            <div class="text-center btns-block">
              <input type="submit" class="btn btn-primary btn-lg btns-block-rightest-btn" name="confirm" value="予約確認">
              <input type="submit" class="btn btn-default btn-lg" name="backToSearch" value="空席一覧に戻る">
            </div>

          </form:form>
        </section>

      </div><!-- /row -->

    </div><!-- /container -->

    <%@ include file="../A0/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/lodash/lodash.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/parsleyjs/parsley.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/atrs.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-config.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-validator-memberavailable.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/reserve-form.js"></script>
  </body>
</html>
