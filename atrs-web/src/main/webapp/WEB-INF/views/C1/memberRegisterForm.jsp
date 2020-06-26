<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>会員登録 | Airline Ticket Reservation System</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap-datepicker/css/bootstrap-datepicker3.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

  </head>
  <body>

    <%@ include file="../A0/header.jsp" %>

    <div class="container">

      <div class="row">

        <section class="col-md-offset-2 col-md-8">

          <spring:hasBindErrors name="memberRegisterForm">
            <c:if test="${errors.globalErrorCount > 0}">
              <div class="alert alert-danger">
                <spring:nestedPath path="memberRegisterForm">
                  <form:errors/>
                </spring:nestedPath>
              </div>
            </c:if>
          </spring:hasBindErrors>

          <div class="panel panel-default">
            <div class="panel-heading">
              新規会員登録
            </div>

            <div class="panel-body">
              <form:form id="membership-form" cssClass="form-horizontal"
                action="${pageContext.request.contextPath}/member/register?confirm" method="post"
                modelAttribute="memberRegisterForm">
                <div class="form-group">
                  <form:label path="kanjiFamilyName" cssClass="col-md-4 control-label">氏名</form:label>
                  <div class="col-md-8">
                    <div class="input-group inline-half">
                      <form:label path="kanjiFamilyName" cssClass="input-group-addon">姓</form:label>
                      <form:input path="kanjiFamilyName" cssClass="form-control" maxlength="10"
                        data-parsley-maxlength="10" data-parsley-maxlength-message="姓は %s 文字以下で入力してください。"
                        data-parsley-required="true" data-parsley-required-message="姓は入力必須項目です。"/>
                    </div>
                    <div class="input-group inline-half">
                      <form:label path="kanjiGivenName" cssClass="input-group-addon">名</form:label>
                      <form:input path="kanjiGivenName" cssClass="form-control" maxlength="10"
                        data-parsley-maxlength="10" data-parsley-maxlength-message="名は %s 文字以下で入力してください。"
                        data-parsley-required="true" data-parsley-required-message="名は入力必須項目です。"/>
                    </div>
                    <div class="clearfix"></div>
                    <form:errors path="kanjiFamilyName" cssClass="invalid" element="span"/>
                    <form:errors path="kanjiGivenName" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="kanaFamilyName" cssClass="col-md-4 control-label">氏名(カタカナ)</form:label>
                  <div class="col-md-8">
                    <div class="input-group inline-half">
                      <form:label path="kanaFamilyName" cssClass="input-group-addon">セイ</form:label>
                      <form:input path="kanaFamilyName" cssClass="form-control" maxlength="10"
                        data-parsley-maxlength="10" data-parsley-maxlength-message="セイは %s 文字以下で入力してください。"
                        data-parsley-required="true" data-parsley-required-message="セイは入力必須項目です。"/>
                    </div>
                    <div class="input-group inline-half">
                      <form:label path="kanaGivenName" cssClass="input-group-addon">メイ</form:label>
                      <form:input path="kanaGivenName" cssClass="form-control" maxlength="10"
                        data-parsley-maxlength="10" data-parsley-maxlength-message="メイは %s 文字以下で入力してください。"
                        data-parsley-required="true" data-parsley-required-message="メイは入力必須項目です。"/>
                    </div>
                    <div class="clearfix"></div>
                    <form:errors path="kanaFamilyName" cssClass="invalid" element="span"/>
                    <form:errors path="kanaGivenName" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="gender" cssClass="col-md-4 control-label">性別</form:label>
                  <div class="col-md-8 form-inline">
                    <c:forEach var="item" items="${CL_GENDER}">
                      <label class="radio-inline">
                        <form:radiobutton path="gender" value="${f:h(item.key)}"
                          data-parsley-required="true"/>${f:h(CL_GENDER[item.key])}
                      </label>
                    </c:forEach>
                    <div class="clearfix"></div>
                    <form:errors path="gender" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="dateOfBirth" cssClass="col-md-4 control-label">生年月日</form:label>
                  <div class="col-md-8">
                    <form:input path="dateOfBirth" cssClass="form-control"
                      data-provide="datepicker"
                      data-date-format="yyyy/mm/dd"
                      data-date-autoclose="true"
                      data-date-start-date="${dateOfBirthMinDate}"
                      data-date-end-date="${dateOfBirthMaxDate}"
                      data-date-language="ja"
                      data-parsley-required="true"
                      data-parsley-date="true"/>
                    <form:errors path="dateOfBirth" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="tel1" cssClass="col-md-4 control-label">電話番号</form:label>
                  <div class="col-md-8 form-inline">
                    <form:input path="tel1" cssClass="form-control form-control-separated" style="width: 5.5em" maxlength="5"
                      data-parsley-required="true" data-parsley-required-message="番号1は入力必須項目です。"
                      data-parsley-length="[2, 5]" data-parsley-length-message="番号1は %s 文字以上 %s 文字以下で入力してください。"
                      data-parsley-type="integer" data-parsley-type-message="番号1は数値で入力してください。"/> -
                    <form:input path="tel2" cssClass="form-control form-control-separated" style="width: 4.5em" maxlength="4"
                      data-parsley-required="true" data-parsley-required-message="番号2は入力必須項目です。"
                      data-parsley-length="[1, 4]" data-parsley-length-message="番号2は %s 文字以上 %s 文字以下で入力してください。"
                      data-parsley-type="integer" data-parsley-type-message="番号2は数値で入力してください。"/> -
                    <form:input path="tel3" cssClass="form-control form-control-separated" style="width: 4.5em" maxlength="4"
                      data-parsley-required="true" data-parsley-required-message="番号3は入力必須項目です。"
                      data-parsley-length="[4, 4]" data-parsley-length-message="番号3は %s 文字で入力してください。"
                      data-parsley-type="integer" data-parsley-type-message="番号3は数値で入力してください。"/>
                    <div class="clearfix"></div>
                    <form:errors path="tel1" cssClass="invalid" element="span"/>
                    <form:errors path="tel2" cssClass="invalid" element="span"/>
                    <form:errors path="tel3" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="zipCode1" cssClass="col-md-4 control-label">郵便番号</form:label>
                  <div class="col-md-8 form-inline">
                    <form:input cssClass="form-control form-control-separated" path="zipCode1" style="width: 3.5em" maxlength="3"
                      data-parsley-required="true" data-parsley-required-message="郵便番号1は入力必須項目です。"
                      data-parsley-length="[3, 3]" data-parsley-length-message="郵便番号1は %s 文字で入力してください。"
                      data-parsley-type="integer" data-parsley-type-message="郵便番号1は数値で入力してください。"/> -
                    <form:input cssClass="form-control form-control-separated" path="zipCode2" style="width: 4.5em" maxlength="4"
                      data-parsley-required="true" data-parsley-required-message="郵便番号2は入力必須項目です。"
                      data-parsley-length="[4, 4]" data-parsley-length-message="郵便番号2は %s 文字で入力してください。"
                      data-parsley-type="integer" data-parsley-type-message="郵便番号2は数値で入力してください。"/>
                    <div class="clearfix"></div>
                    <form:errors path="zipCode1" cssClass="invalid" element="span"/>
                    <form:errors path="zipCode2" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="address" cssClass="col-md-4 control-label">住所</form:label>
                  <div class="col-md-8">
                    <form:input path="address" cssClass="form-control" maxlength="60"
                      data-parsley-maxlength="60"
                      data-parsley-required="true"/>
                    <form:errors path="address" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="mail" cssClass="col-md-4 control-label">Eメール</form:label>
                  <div class="col-md-8">
                    <form:input path="mail" cssClass="form-control" maxlength="256"
                      data-parsley-maxlength="256"
                      data-parsley-required="true"
                      data-parsley-type="email"/>
                    <form:errors path="mail" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="reEnterMail" cssClass="col-md-4 control-label">Eメール再入力</form:label>
                  <div class="col-md-8">
                    <form:input path="reEnterMail" cssClass="form-control" maxlength="256"
                      data-parsley-maxlength="256"
                      data-parsley-required="true"
                      data-parsley-type="email"
                      data-parsley-equalto="#mail" data-parsley-equalto-message="Eメールアドレスが一致しません。"/>
                    <form:errors path="reEnterMail" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="creditTypeCd" cssClass="col-md-4 control-label">クレジットカード会社</form:label>
                  <div class="col-md-8">
                    <c:forEach var="item" items="${CL_CREDITTYPE}">
                      <label class="radio-inline">
                        <form:radiobutton path="creditTypeCd" value="${f:h(item.key)}"
                          data-parsley-required="true"/>${f:h(CL_CREDITTYPE[item.key])}
                      </label>
                    </c:forEach>
                    <div class="clearfix"></div>
                    <form:errors path="creditTypeCd" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="creditNo" cssClass="col-md-4 control-label">クレジットカード番号</form:label>
                  <div class="col-md-8">
                    <form:input path="creditNo" cssClass="form-control" maxlength="16"
                      data-parsley-required="true"
                      data-parsley-type="integer"
                      data-parsley-length="[16, 16]" data-parsley-length-message="%s 文字で入力してください。"/>
                    <form:errors path="creditNo" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="creditMonth" cssClass="col-md-4 control-label">クレジットカード有効期限</form:label>
                  <div class="col-md-8 form-inline">
                    <form:select path="creditMonth" cssClass="form-control"
                      data-parsley-required="true" data-parsley-required-message="クレジットカード有効期限(月)は入力必須項目です。">
                      <form:option value="" label="月" />
                      <form:options items="${CL_CREDITMONTH}"/>
                    </form:select>
                    <form:select path="creditYear" cssClass="form-control"
                      data-parsley-required="true" data-parsley-required-message="クレジットカード有効期限(年)は入力必須項目です。">
                      <form:option value="" label="年" />
                      <form:options items="${CL_CREDITYEAR}"/>
                    </form:select>
                    <div class="clearfix"></div>
                    <form:errors path="creditMonth" cssClass="invalid" element="span"/>
                    <form:errors path="creditYear" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="password" cssClass="col-md-4 control-label">パスワード</form:label>
                  <div class="col-md-8">
                    <form:password path="password" cssClass="form-control" maxlength="20"
                      data-parsley-required="true"
                      data-parsley-length="[8, 20]"/>
                    <form:errors path="password" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <form:label path="reEnterPassword" cssClass="col-md-4 control-label">パスワード再入力</form:label>
                  <div class="col-md-8">
                    <form:password path="reEnterPassword" cssClass="form-control" maxlength="20"
                      data-parsley-required="true"
                      data-parsley-length="[8, 20]"
                      data-parsley-equalto="#password" data-parsley-equalto-message="パスワードが一致しません。"/>
                    <form:errors path="reEnterPassword" cssClass="invalid" element="span"/>
                  </div>
                </div>

                <div class="form-group">
                  <div class="col-md-offset-4 col-md-8">
                    <input type="submit" class="btn btn-primary btn-size-default" value="登録確認">
                  </div>
                </div>
              </form:form>
            </div><!-- /panel-body -->
          </div><!-- /panel -->

        </section>

      </div><!-- /row -->

    </div><!-- /container -->

    <%@ include file="../A0/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap-datepicker/js/locales/bootstrap-datepicker.ja.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/moment/min/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/parsleyjs/parsley.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/atrs.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-config.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-validator-date.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/member-register-form.js"></script>
  </body>
</html>
