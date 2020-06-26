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

    <div class="header-right-menu">

      <security:authorize access="!hasRole('MEMBER')">
        <div class="vertical-middle-box">
          <a class="btn btn-primary" href="${pageContext.request.contextPath}/member/register?form">会員登録</a>
          <a class="btn btn-default" href="${pageContext.request.contextPath}/auth/login?form"><span class="glyphicon glyphicon-user"></span>ログイン</a>
        </div>
      </security:authorize>

      <security:authorize access="hasRole('MEMBER')">
      <security:authentication property="principal.member" var="member" scope="page"/>
        <div class="vertical-middle-box">
          <div class="dropdown">
            <span class="btn btn-default dropdown-toggle" data-toggle="dropdown">ようこそ&nbsp;<span>${f:h(member.kanjiFamilyName)}&nbsp;${f:h(member.kanjiGivenName)}</span>&nbsp;さま&nbsp;<span class="caret"></span></span>
            <ul class="dropdown-menu">
              <li ><a tabindex="-1" href="${pageContext.request.contextPath}/member/update?form">会員情報変更</a></li>
              <li ><a tabindex="-1" href="${pageContext.request.contextPath}/HistoryReport/create?form">履歴レポート作成</a></li>
              <li ><a tabindex="-1" href="${pageContext.request.contextPath}/HistoryReport/download?reportList">履歴レポートDL</a></li>
              <li ><a tabindex="-1" href="javascript:void(0)" onclick="document.logoutForm.submit(); return false;">ログアウト</a></li>
            </ul>
          </div>
        </div>
        <form:form name="logoutForm" action="${pageContext.request.contextPath}/auth/dologout" method="POST"></form:form>
      </security:authorize>

    </div>


  </div>
</header>
