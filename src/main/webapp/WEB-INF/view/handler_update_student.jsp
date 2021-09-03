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
  <form:form method="post" modelAttribute="student">

    <h2>Update: ${student.username}</h2>

    <label>Update First name: </label>
    <form:input path="firstname" type="text"/>

    <label>Update Last name: </label>
    <form:input path="lastname" type="text"/>

    <label>Update email: </label>
    <form:input path="email" type="text"/>

    <label>Update Contact number</label>
    <form:input path="contactNumber" type="text"/>

    <label>Current Batch: ${student.batch.batchCode}</label>
    <a type="button" href="${pageContext.request.contextPath}/handler/update/student/batch/${student.username}">Update/De-assign batch</a>

    <button type="submit">Update ${student.username}</button>

  </form:form>
</div>
</body>
</html>
