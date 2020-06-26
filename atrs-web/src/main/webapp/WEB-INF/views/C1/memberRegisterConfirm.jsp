<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>会員登録確認 | Airline Ticket Reservation System</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

  </head>
  <body>

    <%@ include file="../A0/header.jsp" %>

    <div class="container">

      <div class="row">

        <section class="col-md-offset-2 col-md-8">
          <div class="panel panel-default">
            <div class="panel-heading">
              登録内容
            </div>

            <div class="panel-body">
              <div class="form-horizontal">
                <div class="form-group">
                  <label class="col-md-4 control-label">氏名</label>
                  <div class="col-md-8">
                    <p class="form-control-static">
                      ${f:h(memberRegisterForm.kanjiFamilyName)}&nbsp;${f:h(memberRegisterForm.kanjiGivenName)}
                    </p>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-4 control-label">氏名(カタカナ)</label>
                  <div class="col-md-8">
                    <p class="form-control-static">
                      ${f:h(memberRegisterForm.kanaFamilyName)}&nbsp;${f:h(memberRegisterForm.kanaGivenName)}
                    </p>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-4 control-label">性別</label>
                  <div class="col-md-8">
                    <p class="form-control-static">
                      ${f:h(CL_GENDER[memberRegisterForm.gender.code])}
                    </p>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-4 control-label">生年月日</label>
                  <div class="col-md-8">
                    <p class="form-control-static">
                      <fmt:formatDate value="${memberRegisterForm.dateOfBirth}" pattern="yyyy年MM月dd日"/>
                    </p>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-4 control-label">電話番号</label>
                  <div class="col-md-8">
                    <p class="form-control-static">
                      ${f:h(memberRegisterForm.tel1)}-${f:h(memberRegisterForm.tel2)}-${f:h(memberRegisterForm.tel3)}
                    </p>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-4 control-label">郵便番号</label>
                  <div class="col-md-8">
                    <p class="form-control-static">
                      &#12306;${f:h(memberRegisterForm.zipCode1)}-${f:h(memberRegisterForm.zipCode2)}
                    </p>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-4 control-label">住所</label>
                  <div class="col-md-8">
                    <p class="form-control-static">
                      ${f:h(memberRegisterForm.address)}
                    </p>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-4 control-label">Eメール</label>
                  <div class="col-md-8">
                    <p class="form-control-static">
                      ${f:h(memberRegisterForm.mail)}
                    </p>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-4 control-label">クレジットカード会社</label>
                  <div class="col-md-8">
                    <p class="form-control-static">
                      ${f:h(CL_CREDITTYPE[memberRegisterForm.creditTypeCd])}
                    </p>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-4 control-label">クレジットカード番号</label>
                  <div class="col-md-8">
                    <p class="form-control-static">
                      ${f:cut(memberRegisterForm.creditNo, 4)}************
                    </p>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-4 control-label">クレジットカード有効期限</label>
                  <div class="col-md-8">
                    <p class="form-control-static">
                      ${f:h(memberRegisterForm.creditMonth)}/${f:h(memberRegisterForm.creditYear)}
                    </p>
                  </div>
                </div>

                <div class="form-group">
                  <label class="col-md-4 control-label">パスワード</label>
                  <div class="col-md-8">
                    <p class="form-control-static">
                      **********
                    </p>
                  </div>
                </div>

                <div class="form-group">
                  <form:form cssClass="col-md-offset-4 col-md-8"
                    action="${pageContext.request.contextPath}/member/register"
                    method="post"
                    modelAttribute="memberRegisterForm">
                    <input type="submit" class="btn btn-default btn-size-default" value="修正" name="redo" />
                    <input type="submit" class="btn btn-success btn-size-default" value="登録" />
                    <form:hidden path="kanjiFamilyName"/>
                    <form:hidden path="kanjiGivenName"/>
                    <form:hidden path="kanaFamilyName"/>
                    <form:hidden path="kanaGivenName"/>
                    <form:hidden path="gender"/>
                    <form:hidden path="dateOfBirth"/>
                    <form:hidden path="tel1"/>
                    <form:hidden path="tel2"/>
                    <form:hidden path="tel3"/>
                    <form:hidden path="zipCode1"/>
                    <form:hidden path="zipCode2"/>
                    <form:hidden path="address"/>
                    <form:hidden path="mail"/>
                    <form:hidden path="reEnterMail"/>
                    <form:hidden path="creditTypeCd"/>
                    <form:hidden path="creditNo"/>
                    <form:hidden path="creditMonth"/>
                    <form:hidden path="creditYear"/>
                    <form:hidden path="password"/>
                    <form:hidden path="reEnterPassword"/>
                  </form:form>
                </div>
              </div><!-- /form-horizontal -->
            </div><!-- /panel-body -->
          </div><!-- /panel -->

        </section>

      </div><!-- /row -->

    </div><!-- /container -->

    <%@ include file="../A0/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/atrs.js"></script>
  </body>
</html>
