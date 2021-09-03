<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FML - Time Tables</title>
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
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body style="background-color: floralwhite">
<div class="container">
    <nav class="navbar fixed-top" style="background-color: #414141">
        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/handler/register"
                   style="margin-right: 45px; color: #FFFAF0;">Find My Lecture : ${loggedUser.firstname}</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <a class="nav-link" style="color: #ffdf9e" aria-current="page"
                           href="${pageContext.request.contextPath}/handler/home">Home</a>
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/handler/register">Register</a>
                        <a class="nav-link" style="color: #FFFAF0"
                           href="${pageContext.request.contextPath}/handler/view/students">Students : <a
                                class="nav-link" style="color: #FFFAF0"
                                href="${pageContext.request.contextPath}/handler/update/student/${studentBatch.username}">${studentBatch.username}</a></a>
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/handler/view/timetable">Timetable</a>
                    </div>
                    <div class="navbar-nav">
                        <a class="nav-link" style="color: #ffdf9e"
                           href="${pageContext.request.contextPath}/handler/view/account">My Account</a>
                        <a class="nav-link" style="color: #ffdf9e; cursor: pointer" onclick="logout()">Logout</a><a
                            href="${pageContext.request.contextPath}/logout" id="logout"></a>
                    </div>
                </div>
            </div>
        </nav>
    </nav>
</div>
<div class="container">
    <div style="margin-top: 170px;">
        <form:form method="post" modelAttribute="studentBatch" id="formSubmit">
            <div class="col-lg-6 col-md-6 col-sm-6 container justify-content-center">
                <h1 style="color: #414141;">Update Assigned Batch</h1>
                <label style="color: #414141;" class="form-label">Username</label>
                <form:input path="username" class="form-control" disabled="true" type="text"/>

                <label style="color: #414141;" class="form-label">Current Batch</label>
                <p style="display: none" id="batchCode">${studentBatch.batch.batchCode}</p>
                <input class="form-control" disabled="disabled" value="${studentBatch.batch.batchCode}" type="text"/>
                <div class="col text-center" style="margin-top: 15px;" id="deAssignButton">
                    <a type="button" class="btn btn-outline-danger"
                       href="/handler/update/student/batch/${studentBatch.username}/de-assign">De-assign from batch</a>
                </div>
                <div class="mb-3">
                    <label style="color: #414141;" class="form-label">Batches</label>
                    <form:select path="batch" class="form-control">
                        <form:option value="">Select Batch</form:option>
                        <c:forEach items="${allBatches}" var="batches">
                            <form:option cssStyle="color: #414141"
                                         value="${batches.batchId}">${batches.batchCode}</form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="col text-center">
                    <button type="submit" id="button" class="btn btn"
                            style="color: floralwhite; border-color: #414141; background-color: #414141">
                        Update
                    </button>
                </div>
                <div class="col text-center">
                    <a href="${pageContext.request.contextPath}/handler/update/student/${studentBatch.username}"
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
<div class="container-fluid">
    <nav class="fixed-bottom" style="background-color: #414141">
        <footer class="text-center text-lg-start" style="padding: 10px 0">
            <div class="text-center p-3" style="color: #998d88">
                © 2020 Copyright:
                <a style="color: #998d88;" class="text-center p-3"
                   href="${pageContext.request.contextPath}/handler/update/student/${studentBatch.username}">Find
                    My Lecture</a>
            </div>
        </footer>
    </nav>
</div>
</body>
</html>
<script>
    window.onload = function () {
        const errorMessage = document.getElementById("errorMessage").innerHTML;
        const successMessage = document.getElementById("successMessage").innerHTML;
        const batchCode = document.getElementById("batchCode").innerHTML;

        if (errorMessage !== "") {
            Swal.fire({
                title: "Error occurred while updating the user!!!",
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
        if (batchCode === "") {
            document.getElementById("deAssignButton").style.display = "none";
        }
    }
    const form = document.getElementById('formSubmit');
    form.addEventListener('submit', function (event) {
        Swal.fire({
            title: 'Updating...',
            html: 'Hold on a few seconds while we update the student!',
            timer: 10000,
            timerProgressBar: false,
        });
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