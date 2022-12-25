<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Upload">
    <form method="post" action="/images" enctype="multipart/form-data" style="margin: 10px;">
        <input type="file" name="file" required/>
        <br/>
        <input type="submit" style="margin-top: 5px;" class="btn btn-dark" value="Загрузить" />
    </form>
</t:mainLayout>