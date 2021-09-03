<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container">
    <form:form method="post" modelAttribute="studentBatch">

        <h1>Update Batch: ${studentBatch.username}</h1>
        <br>
        <label>Username : ${studentBatch.username}</label>
        <br>
        <label>Current Batch : ${studentBatch.batch.batchCode}</label>
        <br>
        <a type="button" href="${pageContext.request.contextPath}/handler/update/student/batch/${studentBatch.username}/de-assign">De-assign batch</a>
        <br>
        <div>
            <form:select path="batch">
                <form:option value="">Select Batch</form:option>
                <c:forEach items="${allBatches}" var="batches">
                    <form:option value="${batches.batchId}">${batches.batchCode}</form:option>
                </c:forEach>
            </form:select>
        </div>
        <br>
        <button type="submit">Confirm Update</button>

    </form:form>
</div>
</body>
</html>
