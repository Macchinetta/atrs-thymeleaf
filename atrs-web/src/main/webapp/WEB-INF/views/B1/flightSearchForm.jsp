<div class="panel panel-default">
  <div class="panel-heading">
    ご希望の内容を入力してください。
  </div>

  <div class="panel-body">

    <form id="flights-search-form" class="form-horizontal" action="${pageContext.request.contextPath}/ticket/search" method="get">
    <spring:nestedPath path="ticketSearchForm">

      <div class="form-group">
        <label class="col-md-4 control-label">フライト種別</label>
        <div class="col-md-8">
          <c:forEach var="item" items="${CL_FLIGHTTYPE}">
            <label class="radio-inline">
              <form:radiobutton path="flightType" value="${f:h(item.key)}"
                data-parsley-required="true"/>${f:h(CL_FLIGHTTYPE[item.key])}
            </label>
          </c:forEach>
          <div class="clearfix"></div>
          <form:errors path="flightType" cssClass="invalid" element="span"/>
        </div>
      </div>

      <div class="form-group">
        <form:label path="depAirportCd" cssClass="col-md-4 control-label">区間</form:label>
        <div class="col-md-8">
          <form:select path="depAirportCd" cssClass="form-control" items="${CL_AIRPORT}"
            data-parsley-required="true"/>
          <span class="glyphicon glyphicon-arrow-down" style="width: 100%; line-height: 34px; text-align: center"></span>
          <form:select path="arrAirportCd" cssClass="form-control" items="${CL_AIRPORT}"
            data-parsley-required="true"
            data-parsley-notequalto="#depAirportCd" data-parsley-notequalto-message="出発空港と到着空港に同じ空港は指定できません。"/>
          <div class="clearfix"></div>
          <form:errors path="depAirportCd" cssClass="invalid" element="span"/>
          <form:errors path="arrAirportCd" cssClass="invalid" element="span"/>
        </div>
      </div>

      <fmt:formatDate var="flightSearchStartDate" value="${flightSearchOutputDto.beginningPeriod}" pattern="yyyy/MM/dd"/>
      <fmt:formatDate var="flightSearchEndDate" value="${flightSearchOutputDto.endingPeriod}" pattern="yyyy/MM/dd"/>
      <div id="date-for-outward" class="form-group">
        <form:label path="outwardDate" cssClass="col-md-4 control-label">往路搭乗日</form:label>
        <div class="col-md-8">
          <form:input path="outwardDate" cssClass="form-control"
            data-provide="datepicker"
            data-date-format="yyyy/mm/dd"
            data-date-autoclose="true"
            data-date-language="ja"
            data-date-start-date="${flightSearchStartDate}"
            data-date-end-date="${flightSearchEndDate}"
            data-parsley-trigger="change changeDate"
            data-parsley-required="true"
            data-parsley-date="true"/>
          <div class="clearfix"></div>
          <form:errors path="outwardDate" cssClass="invalid" element="span"/>
        </div>
      </div>

      <div id="date-for-homeward" class="form-group">
        <form:label path="homewardDate" cssClass="col-md-4 control-label">復路搭乗日</form:label>
        <div class="col-md-8">
          <form:input path="homewardDate" cssClass="form-control" maxlength="10"
            data-provide="datepicker"
            data-date-format="yyyy/mm/dd"
            data-date-autoclose="true"
            data-date-language="ja"
            data-date-start-date="${flightSearchStartDate}"
            data-date-end-date="${flightSearchEndDate}"
            data-parsley-trigger="change changeDate"
            data-parsley-required="true"
            data-parsley-date="true"
            data-parsley-dateafterto="#outwardDate" data-parsley-dateafterto-message="往路搭乗日以降である必要があります。"/>
          <div class="clearfix"></div>
          <form:errors path="homewardDate" cssClass="invalid" element="span"/>
        </div>
      </div>

      <div class="form-group">
        <label class="col-md-4 control-label">搭乗クラス</label>
        <div class="col-md-8">
          <c:forEach var="item" items="${CL_BOARDINGCLASS}">
            <label class="radio-inline">
              <form:radiobutton path="boardingClassCd" value="${f:h(item.key)}"
                data-parsley-required="true"/>${f:h(CL_BOARDINGCLASS[item.key])}
            </label>
          </c:forEach>
          <div class="clearfix"></div>
          <form:errors path="boardingClassCd" cssClass="invalid" element="span"/>
        </div>
      </div>

      <div class="form-group">
        <div class="col-md-offset-4 col-md-8">
          <input type="submit" id="flights-search-button" class="btn btn-primary" name="flightForm" value="照会">
        </div>
      </div>

    </spring:nestedPath>
    </form>

  </div>
</div>
