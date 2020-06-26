<section id="login-modal" class="modal fade">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-body">
        <p>
          ATRSカード会員なら、Eメールアドレスやクレジットカード番号が登録できるので簡単にご予約いただけます。
        </p>
        <div class="row">
          <div class="col-md-6">
            <div class="panel panel-default">
              <div class="panel-heading">
                ATRSカード会員の方
              </div>
              <div class="panel-body">
                <p>
                  会員の方はログインしてご予約ください。
                </p>
                <form id="login-form" action="${pageContext.request.contextPath}/auth/dologin" method="post">
                  <ul id="login-messages" class="alert alert-danger hidden list-unstyled">
                  </ul>
                  <div class="form-group">
                    <label for="membershipNumber">会員番号</label>
                    <input type="text" class="form-control" name="membershipNumber" maxlength="10">
                  </div>
                  <div class="form-group">
                    <label for="password">パスワード</label>
                    <input type="password" class="form-control" name="password">
                  </div>
                  <div class="text-center">
                    <input type="submit" class="btn btn-primary" value="ログインして予約">
                  </div>
                </form>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="panel panel-default">
              <div class="panel-heading">
                ATRSカード会員でない方
              </div>
              <div class="panel-body">
                <p>
                  会員登録をしなくてもご予約いただけます。
                </p>
                <div class="text-center">
                  <button id="reserve-without-login-button" type="button" class="btn btn-primary">ログインせず予約</button>
                </div>
              </div>
            </div>
          </div><!-- /col -->
        </div><!-- /row -->
      </div><!-- /container -->
    </div><!-- /modal-content -->
  </div><!-- /modal-dialog -->
</section>
