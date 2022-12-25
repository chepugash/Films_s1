<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Create film">
    <form id="editForm" class="form-horizontal" action="<c:url value="/create"/>" style="margin: 10px" method="POST">
        <div class="form-group" style="margin-bottom: 5px">
            <input type="text" required maxlength="255" name="title" class="form-control" id="formGroupExampleInput"
                   placeholder="Название">
        </div>
        <div class="form-group" style="margin-bottom: 5px">
            <input type="number" required min="1850" max="2025" name="published_year" class="form-control"
                   id="formGroupExampleInput2" placeholder="Дата выхода">
        </div>
        <div class="form-group" style="margin-bottom: 5px">
            <input type="text" required maxlength="64" name="director" class="form-control" id="formGroupExampleInput3"
                   placeholder="Режиссер">
        </div>
        <div class="form-group" style="margin-bottom: 5px">
            <input type="text" required maxlength="255" name="genres" class="form-control" id="formGroupExampleInput4"
                   placeholder="Жанры">
        </div>
        <div class="form-group" style="margin-bottom: 5px">
            <input type="text" required name="info" class="form-control" id="formGroupExampleInput5"
                   placeholder="Описание">
        </div>
        <button style="margin-bottom: 5px" type="submit" class="btn btn-dark">Добавить</button>
    </form>
</t:mainLayout>
