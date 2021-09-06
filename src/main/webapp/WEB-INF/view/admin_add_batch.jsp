<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Add Batch</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
            integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet"
          type="text/css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body style="background-color: floralwhite">
<div class="container">
    <nav class="navbar fixed-top" style="background-color: #414141">
        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/register"
                   style="margin-right: 45px; color: #FFFAF0;">Find My Lecture : ${loggedUser.firstname}</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <a class="nav-link" style="color: #ffdf9e" aria-current="page"
                           href="${pageContext.request.contextPath}/admin/home">Home</a>
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/admin/register">Register</a>
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/admin/view/lecturers">Lecturers</a>
                        <a class="nav-link" style="color: #FFFAF0"
                           href="${pageContext.request.contextPath}/admin/view/batches">Batches :
                            <a class="nav-link" style="color: #FFFAF0"
                               href="${pageContext.request.contextPath}/admin/add/batch">Add</a>
                        </a>
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/admin/view/modules">Modules</a>
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/admin/view/rooms">Class rooms</a>
                    </div>
                    <div class="navbar-nav">
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/admin/view/account">My Account</a>
                        <a class="nav-link" style="color: #ffdf9e; cursor: pointer" onclick="logout()">Logout</a><a
                            href="${pageContext.request.contextPath}/logout" id="logout"></a>
                    </div>
                </div>
            </div>
        </nav>
    </nav>
</div>
<div class="container">
    <div style="margin-top: 120px;">
        <form:form id="formSubmit" method="post" modelAttribute="batchForm">
            <div class="col-lg-4 col-md-4 col-sm-4 container justify-content-center">
                <h2 style="color: #414141;">Add Batch</h2>
                <div class="mb-3">
                    <label style="color: #414141;" class="form-label">Batch Code</label>
                    <form:input path="batchCode" id="batchCode" type="text" class="form-control" autofocus="autofocus"/>
                </div>
                <div class="mb-3">
                    <label style="color: #414141;" class="form-label">Intake Date</label>
                    <form:input path="intakeDate" id="datepicker" type="text" autocomplete="off" class="form-control"/>
                </div>
                <div class="mb-3">
                    <label style="color: #414141;" class="form-label">Batch Period(Months)</label>
                    <form:input path="batchPeriod" id="period" type="number" min="3" max="60" class="form-control"/>
                </div>
                <div class="mb-3 col text-center">
                    <label style="color: #414141;" class="form-label">Assign Modules</label>
                    <form:select path="modules" size="6" cssStyle="width: 300px; overflow: auto" multiple="multiple">
                        <c:forEach items="${modules}" var="module">
                            <form:option value="${module.moduleId}" label="${module.moduleName}"/>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="col text-center">
                    <button type="submit" class="btn btn"
                            style="color: floralwhite; border-color: #414141; background-color: #414141">
                        Add Batch
                    </button>
                </div>
                <div class="col text-center">
                    <a href="${pageContext.request.contextPath}/admin/view/batches"
                       style="color: #414141">< Back</a>
                </div>
            </div>
            <div>
                <p style="display: none" id="errorMessage">${error}</p>
            </div>
            <div>
                <p style="display: none" id="successMessage">${success}</p>
            </div>
        </form:form>
    </div>
</div>
<div class="container-fluid" style="margin-top: 80px;">
    <nav class="fixed-bottom" style="background-color: #414141">
        <footer class="text-center text-lg-start" style="padding: 10px 0">
            <div class="text-center p-3" style="color: #998d88">
                © 2020 Copyright:
                <a style="color: #998d88;" class="text-center p-3"
                   href="${pageContext.request.contextPath}/admin/add/batch">Find
                    My Lecture</a>
            </div>
        </footer>
    </nav>
</div>
</body>
</html>

<script>


    $(function () {
        $("#datepicker").datepicker({dateFormat: "yy-mm-dd", minDate: +1, maxDate: "+36M"}).val()
    });

    window.onload = function () {
        const errorMessage = document.getElementById("errorMessage").innerHTML;
        const successMessage = document.getElementById("successMessage").innerHTML;

        if (errorMessage !== "") {
            Swal.fire({
                title: "Error occurred while updating!!!",
                text: errorMessage,
                icon: "error",
            });
        }
        if (successMessage !== "") {
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: successMessage,
                showConfirmButton: false,
                timer: 3000
            });
        }
    }

    const form = document.getElementById('formSubmit');

    form.addEventListener('submit', function (event) {

        const batchCodeFormat = /[a-zA-Z0-9]{15}/;

        const batchCode = $("#batchCode").val();
        const intakeDate = $("#datepicker").val();
        const period = $("#period").val();

        if (batchCode.length < 3 || batchCode.length > 15 || batchCode.indexOf(' ') >= 0 || batchCode.match(batchCodeFormat)) {
            event.preventDefault();
            Swal.fire({
                title: "Error in batch code!!!",
                text: "-> The batch code cannot be empty!\n" +
                    "-> Batch code cannot contain white spaces.\n " +
                    "-> Batch code cannot contain symbols.\n " +
                    "-> Batch code should contain 3 to 15 characters!",
                icon: "error",
            });
        } else if (intakeDate.length < 1) {
            event.preventDefault();
            Swal.fire({
                title: "Error in intake date!!!",
                text: "-> The intake date for the batch cannot be empty!",
                icon: "error",
            });
        } else if (period.length < 1) {
            event.preventDefault();
            Swal.fire({
                title: "Error in batch period!!!",
                text: "-> The period for the batch cannot be empty!",
                icon: "error",
            });
        } else {
            Swal.fire({
                title: 'Adding...',
                html: 'Hold on a few seconds while we add the batch!',
                timer: 10000,
                timerProgressBar: false,
            });
        }
    });

    function logout() {
        Swal.fire({
            icon: 'question',
            title: 'Sure you want to logout?',
            showCancelButton: true,
            confirmButtonText: `Yes!`,
            cancelButtonText: 'Nope!',
        }).then((result) => {
            if (result.isConfirmed) {
                document.getElementById('logout').click();
            }
        })
    }
</script>