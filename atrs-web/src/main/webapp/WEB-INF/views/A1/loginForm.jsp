<!DOCTYPE html>
<html lang="ja">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ログイン | Airline Ticket Reservation System</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

  </head>
  <body>

    <%@ include file="../A0/header.jsp" %>

    <div id="container" class="container">

      <div class="row">
        <div class="col-md-offset-3 col-md-6">

          <t:messagesPanel panelElement="ul" panelClassName="alert list-unstyled" outerElement=""
            messagesType="danger" messagesAttributeName="SPRING_SECURITY_LAST_EXCEPTION" />

          <div class="panel panel-default">

            <div class="panel-heading">
              ログイン
            </div>

            <div class="panel-body">
              <form:form id="login-form" action="${pageContext.request.contextPath}/auth/dologin" method="post">
                <div class="form-group">
                  <label class="control-label" for="membershipNumber">会員番号</label>
                  <input type="text" class="form-control" maxlength="10" id="membershipNumber" name="membershipNumber"
                         data-parsley-required="true"
                         data-parsley-type="integer"
                         data-parsley-length="[10, 10]" data-parsley-length-message="%s 文字で入力してください。">
                </div>
                <div class="form-group">
                  <label class="control-label" for="password">パスワード</label>
                  <input type="password" class="form-control" id="password" name="password"
                         data-parsley-required="true">
                </div>
                <input type="submit" id="login-btn" class="btn btn-default" value="ログイン">
              </form:form>
            </div>

          </div>

        </div>
      </div>

    </div>

    <%@ include file="../A0/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/parsleyjs/parsley.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/atrs.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/parsley-config.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/login-form.js"></script>
  </body>
</html>
