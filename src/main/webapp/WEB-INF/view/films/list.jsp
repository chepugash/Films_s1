<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Film List">
    <div class="films-list">
        <div class="row">
            <c:forEach items="${films}" var="film">
                <div class="col-md-4">
                    <div class="card" style="width: 18rem; margin: 10px;">
                        <img class="card-img-top" src="/images/${film.getId()}" height="390" width="260" alt="Card image cap">
                        <div class="card-body">
                            <h5 class="card-title">${film.getTitle()}</h5>
                            <p class="card-text"><c:out value="${film.getPublishedYear()}"/></p>
                            <a href="<c:url value="/detail?id=${film.getId()}"/>" class="btn btn-dark">Подробнее</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

</t:mainLayout>
