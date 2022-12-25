<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="<c:url value="/"/>">Top Movies</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav">
        <a class="nav-link" id="nav-link" aria-current="page" href="<c:url value="/"/>">Home</a>
        <c:if test="${user != null}">
          <c:if test="${user.getRole() == 'admin'}">
            <a class="nav-link" aria-current="page" href="<c:url value="/create"/>">Create Film</a>
          </c:if>
          <a class="nav-link">${user.getUsername()}, ${user.getRole()}</a>
          <a class="nav-link" aria-current="page" href="<c:url value="/logout"/>">Log Out</a>
        </c:if>
        <c:if test="${user == null}">
          <a class="nav-link" aria-current="page" href="<c:url value="/signin"/>">Sign In</a>
          <a class="nav-link" aria-current="page" href="<c:url value="/registration"/>">Registration</a>
        </c:if>
      </div>
    </div>
  </div>
</nav>
