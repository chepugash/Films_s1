<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Film Detail">
    <div class="film-page" style="margin: 10px;">
        <div class="row">
            <div class="col-md-2">
                <img src="/images/${film.getId()}" width="300" height="450">
            </div>
            <div class="col-md-2">

            </div>
            <div class="col-md-8">
                <h1 class="film-name">${film.getTitle()}</h1>
                <div class="film-field">
                    <span class="field-name">Год выхода:</span>
                    <span class="field-value">${film.getPublishedYear()}</span>
                </div>
                <div class="film-field">
                    <span class="field-name">Режиссер:</span>
                    <span class="field-value">${film.getDirector()}</span>
                </div>
                <div class="film-field">
                    <span class="field-name">Жанры:</span>
                    <span class="field-value">${film.getGenres()}</span>
                </div>
                <div class="film-field">
                    <span class="field-name">Описание:</span>
                    <span class="field-value">${film.getInfo()}</span>
                </div>
            </div>
        </div>
    <c:if test="${user != null}">
        <c:if test="${user.getRole() == 'admin'}">
            <a class="btn btn-secondary" href="<c:url value="/edit?id=${film.getId()}"/>">Редактировать</a>
            <a class="btn btn-secondary" href="<c:url value="/delete?id=${film.getId()}"/>">Удалить</a>
            <a class="btn btn-secondary" href="<c:url value="/detail?add=true"/>">Добавить постер</a>
        </c:if>
        <h4 style="margin-top: 10px; margin-bottom: 10px">Оставьте комментарий к фильму</h4>
        <form action="/detail" method="post">
            <div>
                <input class="form-control" name="comment" id="comment" rows="1"
                       required placeholder="Комментарий">
            </div>
            <button class="btn btn-dark">Отправить</button>
        </form>
    </c:if>
    <c:if test="${user == null}">
        <h4>Оставьте комментарий к фильму</h4>
        <div>
            <input class="form-control" name="comment" rows="1"
                   required placeholder="Комментарий">
        </div>
        <button class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#notAuth">Отправить</button>
    </c:if>
    <c:forEach items="${comments}" var="comment">
        <div class="film-field">
            <span class="field-name"><c:out value="${comment.getUserNickname()}"/>:</span>
            <span class="field-value"><c:out value="${comment.getText()}"/></span>
        </div>
    </c:forEach>
    </div>

    <div class="modal fade" id="notAuth" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
         aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="notAuthLabel">Нет доступа</h1>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" aria-label="Close">Закрыть</button>
                </div>
                <div class="modal-body">
                    Чтобы оставить комментарий, войдите или зарегистрируйтесь.
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Ок</button>
                </div>
            </div>
        </div>
</t:mainLayout>